import client.ReqType;
import client.ReqMessage;
import client.RespMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import server.Card;
import server.Suit;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameClient {
    private String playerId;
    private List<Card> cardList;
    private Card topCard;
    private int skipCount = 0;
    private boolean canDrawNew = true;
    private int drawNum = 1;
    private boolean stop;
    private Suit suit;

    public static void main(String[] args) {
        new GameClient();
    }

    public GameClient() {
        try (Socket socket = new Socket("localhost", 3333)) {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Please input player name");
            String name = scanner.next();

            while (true) {

                if (stop) break;

                while (playerId == null) {
                    waitAndParseResp(socket);
                    ReqMessage reqMessage = new ReqMessage(ReqType.SEND_NAME, playerId + ":" + name);
                    write(socket, reqMessage);
                    System.out.println("Hi, " + name + " connect success!");
                }

                while (cardList == null) {
                    waitAndParseResp(socket);
                }

                if (topCard == null) {
                    waitAndParseResp(socket);
                }

                if (skipCount >= 3 || !canDrawNew) {
                    ReqMessage reqMessage = new ReqMessage(ReqType.SKIP_TURN, playerId);
                    write(socket, reqMessage);
                    waitAndParseResp(socket);
                    skipCount = 0;
                    continue;
                }

                int cardIndex = scanner.nextInt();
                while (!isMatch(cardIndex)) {
                    System.out.println("The card you choose is not match with top card, please choose again");
                    cardIndex = scanner.nextInt();
                }

                if (cardIndex == -1) {
                    ReqMessage reqMessage = new ReqMessage(ReqType.DRAW_NEW, playerId);
                    write(socket, reqMessage);
                    skipCount++;
                    waitAndParseResp(socket);
                } else {
                    ReqMessage reqMessage = new ReqMessage(ReqType.MATCH_CARD, playerId + ":" + cardIndex);
                    if (cardList.get(cardIndex).getValue() == 8) {
                        System.out.println("Please choose a suit for next player");
                        Suit[] suits = Suit.values();
                        for (int i = 0; i < suits.length; i++) {
                            System.out.println(" **** Press " + i + " for " + suits[i].name());
                        }
                        int suitIndex = scanner.nextInt();
                        reqMessage = new ReqMessage(ReqType.MATCH_CARD, playerId + ":" + cardIndex + ":" + suitIndex);
                    }
                    write(socket, reqMessage);
                    cardList.remove(cardIndex);
                    System.out.println("You have finished current turn, the cards you have are " + cardList);
                    waitAndParseResp(socket);
                    skipCount = 0;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isMatch(int cardIndex) {
        if (cardIndex == -1) return true;
        Card card = cardList.get(cardIndex);
        if (card.getValue() == 8) return true;
        if (card.getSuit() == topCard.getSuit() || card.getValue() == topCard.getValue()
                || (topCard.getValue() == 8 && card.getSuit() == suit)) return true;
        return false;
    }

    private void waitAndParseResp(Socket socket) throws IOException {
        List<RespMessage> respMessageList = getResp(socket);
        for (RespMessage respMessage : respMessageList) {
            switch (respMessage.getRespType()) {
                case NEW_PLAYER:
                    playerId = respMessage.getMessageBody();
                    break;
                case NEW_TOP_CARD:
                    String messageBody = respMessage.getMessageBody();
                    String[] split1 = messageBody.split("-");
                    topCard = new Gson().fromJson(split1[0], Card.class);
                    if (split1.length > 1) {
                        suit = Suit.values()[Integer.parseInt(split1[1])];
                        System.out.println("The top card is " + topCard + ", you choose card with " + suit.name() + " to match");
                    } else {
                        System.out.println("The top card is " + topCard + ", you choose card to match");
                    }
                    System.out.println(" **** Press -1 to draw a new card");
                    for (int i = 0; i < cardList.size(); i++) {
                        System.out.println(" **** Press " + i + " to draw " + cardList.get(i));
                    }
                    break;
                case FORCE_DRAW:
                    Card card = new Gson().fromJson(respMessage.getMessageBody(), Card.class);
                    if (card == Card.NULL_CARD) {
                        System.out.println("There is no new cards, the cards you get are still " + cardList);
                        canDrawNew = false;
                    } else {
                        System.out.println("The card you get is " + card + ", you still need to draw");
                        cardList.add(card);
                    }
                    ReqMessage reqMessage = new ReqMessage(ReqType.DRAW_NEW_FOR_TWO, playerId);
                    write(socket, reqMessage);
                    waitAndParseResp(socket);
                case DEAL_RESULT:
                    cardList = new Gson().fromJson(respMessage.getMessageBody(), new TypeToken<List<Card>>() {
                    }.getType());
                    System.out.println("The cards you get are " + cardList);
                    break;
                case NEW_CARD:
                    card = new Gson().fromJson(respMessage.getMessageBody(), Card.class);
                    if (card == Card.NULL_CARD) {
                        System.out.println("There is no new cards, the cards you get are still " + cardList);
                        canDrawNew = false;
                    } else {
                        System.out.println("The card you get is " + card);
                        cardList.add(card);
                    }
                    if (skipCount < 3) {
                        System.out.println("The top card is " + topCard + ", you choose card to match");
                        System.out.println(" **** Press -1 to draw a new card");
                        for (int i = 0; i < cardList.size(); i++) {
                            System.out.println(" **** Press " + i + " to draw " + cardList.get(i));
                        }
                    }
                    break;
                case ROUND_RESULT:
                    System.out.println("The round score is " + respMessage.getMessageBody());
                    System.out.println("----------------------------------------");
                    waitAndParseResp(socket);
                    break;
                case GAME_RESULT:
                    String[] split = respMessage.getMessageBody().split("-");
                    System.out.println("The final score is " + split[0] + ", winner is Player" + split[1]);
                    stop = true;
                    break;
                case NULL:
                    waitAndParseResp(socket);
            }
        }
    }

    private List<RespMessage> getResp(Socket socket) throws IOException {
        String message = read(socket);
        List<RespMessage> respMessageList = new ArrayList<>();
        String[] split = message.split(RespMessage.MESSAGE_SPLIT);
        for (String str : split) {
            respMessageList.add(RespMessage.decode(str));
        }

        return respMessageList;
    }

    public String read(Socket socket) throws IOException {
        byte[] data = new byte[1024];
        int len = socket.getInputStream().read(data);
        if (len > 0) {
            String message = new String(data, 0, len);
            return message;
        }
        return null;
    }

    public void write(Socket socket, ReqMessage reqMessage) throws IOException {
        socket.getOutputStream().write(reqMessage.encode().getBytes());
        socket.getOutputStream().flush();
    }
}
