package server;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String id;
    private String name;
    private List<Card> cardList;
    private CrazyEights crazyEights;

    public Player(String id, CrazyEights crazyEights) {
        this.id = id;
        this.crazyEights = crazyEights;
        cardList = new ArrayList<>();
    }

    public void addCard(Card card) {
        this.getCardList().add(card);
    }

    public void clearCard() {
        this.cardList.clear();
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public CrazyEights getCrazyEights() {
        return crazyEights;
    }

    public void setCrazyEights(CrazyEights crazyEights) {
        this.crazyEights = crazyEights;
    }

    public int matchCard(String cardIndex) {
        Card topCard = crazyEights.getTopCard();

        if (match(cardList.get(Integer.parseInt(cardIndex)), topCard)) {
            return cardList.size();
        }

        return Integer.MAX_VALUE;
    }


    private boolean drawAndMatch(Card topCard) {
        Card card = crazyEights.getCard();
        return match(card, topCard);
    }

    private boolean match(Card card, Card topCard) {
        if (card.getValue() == topCard.getValue() || card.getSuit() == topCard.getSuit()) {
            crazyEights.setTopCard(card);
            cardList.remove(card);
            return true;
        }

        if (card.getValue() == 8) {
            crazyEights.setTopCard(card);
            cardList.remove(card);
            return true;
        }

        return false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
