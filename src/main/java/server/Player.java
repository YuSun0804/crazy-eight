package server;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String id;
    private String name;
    private List<Card> cardList;
    private CrazyEights crazyEights;
    private int drawCount = 0;

    public Player(String id, CrazyEights crazyEights) {
        this.id = id;
        this.crazyEights = crazyEights;
        cardList = new ArrayList<>();
    }

    public Player addCard(Card card) {
        getCardList().add(card);
        return this;
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

    public int playCard(int cardIndex, boolean updateTop) {
        if (canPlay(cardList.get(cardIndex)) && updateTop) {
            crazyEights.setTopCard(cardList.get(cardIndex));
            cardList.remove(cardIndex);
        }
        return cardList.size();
    }

    public boolean forceToDraw() {
        for (Card card : cardList) {
            if (canPlay(card)) {
                return false;
            }
        }
        return true;
    }

    public int playCard(Card card, boolean updateTop) {
        int i = 0;
        for (; i < cardList.size(); i++) {
            if (card.equals(cardList.get(i))) break;
        }
        return playCard(i, updateTop);
    }

    public boolean drawPlay(Card card) {
        cardList.add(card);
        if (canPlay(card)) {
            crazyEights.setTopCard(card);
            cardList.remove(card);
            return true;
        }
        return false;
    }

    public Player draw(Card card) {
        addCard(card);
        return this;
    }

    public Card draw() {
        if (drawCount >= 3) {
            return Card.NULL_CARD;
        }
        Card card = crazyEights.getCard();
        addCard(card);
        drawCount++;
        return card;
    }

    public void skip() {
        drawCount = 0;
    }

    public boolean canPlay(Card card) {
        if (card.getValue() == crazyEights.getTopCard().getValue() || card.getSuit() == crazyEights.getTopCard().getSuit()
                || (crazyEights.getTopCard().getValue() == 8 && card.getSuit() == crazyEights.getSuit())) {
            drawCount = 0;
            return true;
        }

        if (card.getValue() == 8) {
            drawCount = 0;
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

    public int getCardNum() {
        return this.cardList.size();
    }
}
