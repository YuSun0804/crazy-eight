import client.ReqMessage;
import client.ReqType;
import client.RespMessage;

import client.RespType;
import com.google.gson.Gson;
import server.Card;
import server.CrazyEights;
import server.Player;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.*;


public class GameServer {

    private CrazyEights crazyEights;
    private int totalPlayerNums;
    private int connectedPlayerNum;
    private Map<String, SocketChannel> clientChannelMap = new HashMap<>();
    private Set<String> skippedPlayers = new HashSet<>();

    public GameServer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input player number");
        totalPlayerNums = scanner.nextInt();

        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            ssc.configureBlocking(false);
            ssc.bind(new InetSocketAddress("localhost", 3333));
            Selector selector = Selector.open();
            ssc.register(selector, SelectionKey.OP_ACCEPT);

            crazyEights = new CrazyEights();

            System.out.println("Game server started with " + totalPlayerNums + " player(s)");

            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> keyIterator = keys.iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (!key.isValid()) {
                        continue;
                    }
                    if (key.isAcceptable()) {
                        accept(key, selector);
                    } else if (key.isReadable()) {
                        read(key, selector);
                    }
                    keyIterator.remove(); //该事件已经处理，可以丢弃
                }
            }

        } catch (IOException ex) {
            System.out.println("Server Failed to open");
        }
    }

    private void accept(SelectionKey key, Selector selector) throws IOException {
        ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
        SocketChannel clientChannel = ssc.accept();
        clientChannel.configureBlocking(false);
        clientChannel.register(selector, SelectionKey.OP_READ);
        connectedPlayerNum += 1;
        String playerId = connectedPlayerNum + "";
        Player player = new Player(playerId, crazyEights);
        crazyEights.addPlayer(player);
        System.out.println("a new player connected " + clientChannel.getRemoteAddress());
        ByteBuffer sendBuffer = ByteBuffer.wrap(new RespMessage(RespType.NEW_PLAYER, playerId).encode().getBytes());
        clientChannel.write(sendBuffer);
        clientChannelMap.put(playerId, clientChannel);
    }

    private void read(SelectionKey key, Selector selector) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int numRead = socketChannel.read(byteBuffer);
        if (numRead > 0) {
            String req = new String(byteBuffer.array(), 0, numRead);
            processRequest(req, socketChannel);
        } else {
            socketChannel.close();
        }
    }

    private void processRequest(String req, SocketChannel socketChannel) throws IOException {
        ReqMessage reqMessage = ReqMessage.decode(req);
        RespMessage respMessage = new RespMessage();
        switch (reqMessage.getReqType()) {
            case SEND_NAME:
                String[] split1 = reqMessage.getMessageBody().split(":");
                List<Card> cards = crazyEights.updatePlayerName(split1[0], split1[1]);
                respMessage.setRespType(RespType.DEAL_RESULT);
                respMessage.setMessageBody(new Gson().toJson(cards));
                ByteBuffer sendBuffer = ByteBuffer.wrap(respMessage.encode().getBytes());
                socketChannel.write(sendBuffer);
                if (totalPlayerNums == connectedPlayerNum) {
                    Card card = crazyEights.drawTopCard();
                    respMessage.setRespType(RespType.NEW_TOP_CARD);
                    respMessage.setMessageBody(new Gson().toJson(card));
                    sendBuffer = ByteBuffer.wrap(respMessage.encode().getBytes());
                    clientChannelMap.get(crazyEights.getCurrentPlayer()).write(sendBuffer);
                }
                break;
            case MATCH_CARD:
                split1 = reqMessage.getMessageBody().split(":");
                skippedPlayers.remove(split1[0]);
                int remainCard = crazyEights.playCard(split1[0], Integer.parseInt(split1[1]), reqMessage.getReqType() == ReqType.MATCH_CARD);
                if (remainCard > 0) {
                    sendBuffer = ByteBuffer.wrap(RespMessage.NULL_MESSAGE.encode().getBytes());
                    socketChannel.write(sendBuffer);
                    Card card = crazyEights.getTopCard();

                    if (card.getValue() == 2) {
                        respMessage.setRespType(RespType.FORCE_DRAW);
                    } else {
                        respMessage.setRespType(RespType.NEW_TOP_CARD);
                    }

                    respMessage.setMessageBody(new Gson().toJson(card));
                    sendBuffer = ByteBuffer.wrap(respMessage.encode().getBytes());
                    clientChannelMap.get(crazyEights.calNextOne(false)).write(sendBuffer);
                } else {
                    Map<String, Integer> scorePad = crazyEights.calculateScore();
                    broadCastRoundResult(scorePad);
                }
                break;
            case DRAW_NEW_FOR_TWO:
                String playerId = reqMessage.getMessageBody();
                Card card = crazyEights.drawCard(playerId);
                crazyEights.minusNextDrawNum();
                if (crazyEights.getNextDrawNum() > 1) {
                    respMessage.setRespType(RespType.FORCE_DRAW);
                } else {
                    respMessage.setRespType(RespType.NEW_CARD);
                }
                respMessage.setMessageBody(new Gson().toJson(card));
                sendBuffer = ByteBuffer.wrap(respMessage.encode().getBytes());
                clientChannelMap.get(playerId).write(sendBuffer);
                break;
            case DRAW_NEW:
                playerId = reqMessage.getMessageBody();
                card = crazyEights.drawCard(playerId);
                respMessage.setRespType(RespType.NEW_CARD);
                respMessage.setMessageBody(new Gson().toJson(card));
                sendBuffer = ByteBuffer.wrap(respMessage.encode().getBytes());
                clientChannelMap.get(playerId).write(sendBuffer);
                break;
            case SKIP_TURN:
                playerId = reqMessage.getMessageBody();
                crazyEights.skip(playerId);
                skippedPlayers.add(playerId);
                if (skippedPlayers.size() == totalPlayerNums) {
                    Map<String, Integer> scorePad = crazyEights.calculateScore();
                    broadCastRoundResult(scorePad);
                } else {
                    sendBuffer = ByteBuffer.wrap(RespMessage.NULL_MESSAGE.encode().getBytes());
                    socketChannel.write(sendBuffer);

                    Card topCard = crazyEights.getTopCard();
                    respMessage.setRespType(RespType.NEW_TOP_CARD);
                    respMessage.setMessageBody(new Gson().toJson(topCard));
                    sendBuffer = ByteBuffer.wrap(respMessage.encode().getBytes());
                    clientChannelMap.get(crazyEights.calNextOne(false)).write(sendBuffer);
                }
        }
    }

    private void broadCastRoundResult(Map<String, Integer> scorePad) throws IOException {
        RespMessage respMessage = new RespMessage();
        if ("".equals(crazyEights.getWinner())) {
            respMessage.setRespType(RespType.ROUND_RESULT);
            respMessage.setMessageBody(scorePad.toString());
        } else {
            respMessage.setRespType(RespType.GAME_RESULT);
            respMessage.setMessageBody(scorePad.toString() + "-" + crazyEights.getWinner());
        }
        for (Map.Entry<String, SocketChannel> entry : clientChannelMap.entrySet()) {
            SocketChannel socketChannel = entry.getValue();
            ByteBuffer sendBuffer = ByteBuffer.wrap(respMessage.encode().getBytes());
            socketChannel.write(sendBuffer);
        }
    }

    public static void main(String[] args) {
        new GameServer();
    }
}
