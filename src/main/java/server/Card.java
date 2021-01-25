package server;

public class Card {
    public final static Card NULL_CARD = new Card();

    private int value;
    private Suit suit;

    public Card() {
    }

    public Card(int value, Suit suit) {
        this.value = value;
        this.suit = suit;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit suit) {
        this.suit = suit;
    }

    public int getCalValue() {
        if (value == 11 || value == 12 || value == 13) return 10;
        return value;
    }

    public String getTextValue() {
        if (value == 11) return "J";
        else if (value == 12) return "Q";
        else if (value == 13) return "K";
        return value + "";
    }

    public static int getValueFromText(String text) {
        if ("J".equals(text)) return 11;
        else if ("Q".equals(text)) return 12;
        else if ("K".equals(text)) return 13;
        return Integer.parseInt(text);
    }

    @Override
    public String toString() {
        return value + suit.name();
    }

    public String encode() {
        if (this.getValue() == 0 || this.getSuit() == null) {
            return "-1";
        }
        return this.getTextValue() + suit.name();
    }

    public static Card decode(String c) {
        if ("-1".equals(c)) {
            return Card.NULL_CARD;
        }
        Card card = new Card();
        card.setValue(getValueFromText(c.substring(0, c.length() - 1)));
        card.setSuit(Suit.valueOf(c.substring(c.length() - 1)));
        return card;
    }
}
