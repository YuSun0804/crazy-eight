package server;

import java.util.*;

public class CrazyEights {
    private List<Card> cardList;
    private Set<Card> usedCardSet;
    private Card topCard;
    private Map<String, Player> players;
    private Map<String, Integer> scorePad;
    private int nextDrawNum = 1;
    private String winner;

    public CrazyEights() {
        cardList = new ArrayList<>();
        for (int i = 1; i <= 13; i++) {
            for (Suit suit : Suit.values()) {
                Card card = new Card(i, suit);
                cardList.add(card);
            }
        }

        usedCardSet = new HashSet<>();
        scorePad = new HashMap<>();
        players = new HashMap<>();
    }

    public Card getCardInner() {
        int random = new Random().nextInt(52);
        Card card = cardList.get(random);
        while (usedCardSet.contains(card)) {
            return getCardInner();
        }
        usedCardSet.add(card);
        return card;
    }

    public Card getCard() {
        if (usedCardSet.size() == 52) return Card.NULL_CARD;
        System.out.println(usedCardSet.size());
        return getCardInner();
    }

    public int getNextDrawNum() {
        return nextDrawNum;
    }

    public void setNextDrawNum(int nextDrawNum) {
        this.nextDrawNum = nextDrawNum;
    }

    public void minusNextDrawNum() {
        this.nextDrawNum--;
    }

    private List<Card> dealCards(Player player) {
        for (int i = 0; i < 5; i++) {
            Card card = getCardInner();
            player.addCard(card);
        }
        return player.getCardList();
    }

    public Card drawTopCard() {
        int random = new Random().nextInt(52);
        Card card = cardList.get(random);
        while (usedCardSet.contains(card) || card.getValue() == 8) {
            return drawTopCard();
        }
        usedCardSet.add(card);
        topCard = card;
        return card;
    }

    public void setTopCard(Card topCard) {
        this.topCard = topCard;
    }

    public Map<String, Integer> getScorePad() {
        return scorePad;
    }

    public Card getTopCard() {
        return topCard;
    }

    public void addPlayer(Player player) {
        this.players.put(player.getId(), player);
        this.scorePad.put(player.getId(), 0);
    }

    public Map<String, Integer> calculateScore() {
        boolean calculateWinner = false;
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            String playerId = entry.getKey();
            Player player = entry.getValue();
            List<Card> cardList = player.getCardList();
            int score = scorePad.get(playerId) + calCard(cardList);
            if (score > 100) calculateWinner = true;
            scorePad.put(playerId, score);
            player.clearCard();
        }
        int winnerScore = Integer.MAX_VALUE;
        if (calculateWinner) {
            for (Map.Entry<String, Player> entry : players.entrySet()) {
                String playerId = entry.getKey();
                Player player = entry.getValue();
                int score = scorePad.get(playerId);
                if (score < winnerScore) {
                    winner = player.getName();
                }
            }
        }
        return scorePad;
    }

    private int calCard(List<Card> cardList) {
        int result = 0;
        for (Card card : cardList) {
            result += card.getCalValue();
        }
        return result;
    }

    public List<Card> updatePlayerName(String playerId, String name) {
        Player player = players.get(playerId);
        player.setName(name);
        return dealCards(player);
    }

    public int matchCard(String playerId, String cardIndex, boolean updateTop) {
        Player player = players.get(playerId);
        if (updateTop) {
            topCard = player.getCardList().get(Integer.parseInt(cardIndex));
        }
        if (topCard.getValue() == 2) {
            nextDrawNum = nextDrawNum * 2;
        }
        return player.matchCard(cardIndex);
    }

    public Card drawCard(String playerId) {
        Player player = players.get(playerId);
        Card card = this.getCard();
        player.addCard(card);
        return card;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
