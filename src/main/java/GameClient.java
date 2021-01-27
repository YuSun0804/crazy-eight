import client.ReqType;
import client.ReqMessage;
import client.RespMessage;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import server.Card;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameClient {
    private Socket socket;
    private String playerId;
    private List<Card> cardList;
    private Card topCard;
    private int skipCount = 0;
    private boolean canDrawNew = true;
    private int drawNum = 1;

    public static void main(String[] args) {
        new GameClient();
    }

    public GameClient() {
        try {
            Scanner scanner = new Scanner(System.in);
            socket = new Socket("localhost", 3333);

            System.out.println("Please input player name");
            String name = scanner.next();

            while (true) {

                while (playerId == null) {
                    waitAndParseResp();
                    ReqMessage reqMessage = new ReqMessage(ReqType.SEND_NAME, playerId + ":" + name);
                    write(reqMessage);
                    System.out.println("Hi, " + name + " connect success!");
                }

                while (cardList == null) {
                    waitAndParseResp();
                }

                if (topCard == null) {
                    waitAndParseResp();
                }

                int cardIndex = scanner.nextInt();
                while (!isMatch(cardIndex)) {
                    System.out.println("The card you choose is not match with top card, please choose again");
                    cardIndex = scanner.nextInt();
                }

                if (skipCount >= 3 || !canDrawNew) {
                    ReqMessage reqMessage = new ReqMessage(ReqType.SKIP_TURN, playerId);
                    write(reqMessage);
                    waitAndParseResp();
                    skipCount = 0;
                    topCard = null;
                } else if (cardIndex == -1) {
                    ReqMessage reqMessage = new ReqMessage(ReqType.DRAW_NEW, playerId);
                    write(reqMessage);
                    waitAndParseResp();
                    skipCount++;
                } else {
                    ReqMessage reqMessage = new ReqMessage(ReqType.MATCH_CARD, playerId + ":" + cardIndex);
                    write(reqMessage);
                    cardList.remove(cardIndex);
                    System.out.println("You have finished current turn, the cards you have are " + cardList);
                    waitAndParseResp();
                    skipCount = 0;
                    topCard = null;
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
        if (card.getSuit() == topCard.getSuit() || card.getValue() == topCard.getValue()) return true;
        return false;
    }

    private void waitAndParseResp() throws IOException {
        List<RespMessage> respMessageList = getResp();
        for (RespMessage respMessage : respMessageList) {
            switch (respMessage.getRespType()) {
                case NEW_PLAYER:
                    playerId = respMessage.getMessageBody();
                    break;
                case NEW_TOP_CARD:
                    String messageBody = respMessage.getMessageBody();
                    topCard = new Gson().fromJson(messageBody, Card.class);
                    System.out.println("The top card is " + topCard + ", you choose card to match");
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
                        cardList.add(card);
                        System.out.println("The cards you get are " + cardList + ", you choose card to match(starting with 0), or press -1 to draw a new card");
                    }
                    ReqMessage reqMessage = new ReqMessage(ReqType.DRAW_NEW_FOR_TWO, playerId);
                    write(reqMessage);
                    waitAndParseResp();
                case DEAL_RESULT:
                    cardList = new Gson().fromJson(respMessage.getMessageBody(), new TypeToken<List<Card>>() {}.getType());
                    System.out.println("The cards you get are " + cardList);
                    break;
                case NEW_CARD:
                    card = new Gson().fromJson(respMessage.getMessageBody(), Card.class);
                    if (card == Card.NULL_CARD) {
                        System.out.println("There is no new cards, the cards you get are still " + cardList);
                        canDrawNew = false;
                    } else {
                        cardList.add(card);
                        System.out.println("The cards you get are " + cardList + ", you choose card to match(starting with 0), or press -1 to draw a new card");
                    }
                    break;
                case ROUND_RESULT:
                    System.out.println("The round score is " + respMessage.getMessageBody());
                    break;
                case GAME_RESULT:
                    String[] split = respMessage.getMessageBody().split("-");
                    System.out.println("The final score is " + split[0] + ", winner is Player" + split[1]);
                    break;
            }
        }
    }

    private List<RespMessage> getResp() throws IOException {
        String message = read();
        List<RespMessage> respMessageList = new ArrayList<>();
        String[] split = message.split(RespMessage.MESSAGE_SPLIT);
        for (String str : split) {
            respMessageList.add(RespMessage.decode(str));
        }

        return respMessageList;
    }

    public String read() throws IOException {
        byte[] data = new byte[1024];
        int len = socket.getInputStream().read(data);
        if (len > 0) {
            String message = new String(data, 0, len);
            return message;
        }
        return null;
    }

    public void write(ReqMessage reqMessage) throws IOException {
        socket.getOutputStream().write(reqMessage.encode().getBytes());
        socket.getOutputStream().flush();
    }
}
