import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.Card;
import server.CrazyEights;
import server.Player;
import server.Suit;

public class IndividualTest {

    private CrazyEights crazyEights = new CrazyEights();
    private Player player1 = new Player("1", crazyEights);
    private Player player2 = new Player("2", crazyEights);
    private Player player3 = new Player("3", crazyEights);
    private Player player4 = new Player("4", crazyEights);

    @Before
    public void beforeTest() {
        crazyEights.addPlayer(player1);
        crazyEights.addPlayer(player2);
        crazyEights.addPlayer(player3);
        crazyEights.addPlayer(player4);
    }

    @Test
    public void testNextPlayer() {
        crazyEights.setTopCard(new Card(3, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        crazyEights.setCurrentPlayer(1);
        System.out.println("current player is 1");
        String next = crazyEights.calNextOne(false);
        System.out.println("next player is " + next);
        Assert.assertEquals("2", next);
    }

    @Test
    public void testNextPlayerWithOne() {
        crazyEights.setTopCard(new Card(1, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        crazyEights.setCurrentPlayer(1);
        System.out.println("current player is 1");
        String next = crazyEights.calNextOne(false);
        System.out.println("next player is " + next);
        Assert.assertEquals("4", next);
    }

    @Test
    public void testNextPlayerWithQueen() {
        crazyEights.setTopCard(new Card(12, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        crazyEights.setCurrentPlayer(1);
        System.out.println("current player is 1");
        String next = crazyEights.calNextOne(false);
        System.out.println("next player is " + next);
        Assert.assertEquals("3", next);
    }

    @Test
    public void testCardCannotBePlayed() {
        crazyEights.setTopCard(new Card(12, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        Card card = new Card(1, Suit.S);
        boolean played = player1.canPlay(card);
        System.out.println("the card played is " + card);
        Assert.assertEquals(false, played);
    }

    @Test
    public void testCardCanBePlayed() {
        crazyEights.setTopCard(new Card(12, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        Card card = new Card(8, Suit.S);
        boolean played = player1.canPlay(card);
        System.out.println("the card played is " + card);
        Assert.assertEquals(true, played);
    }

    @Test
    public void testCardCanBePlayed2() {
        crazyEights.setTopCard(new Card(12, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        Card card = new Card(1, Suit.C);
        boolean played = player1.canPlay(card);
        System.out.println("the card played is " + card);
        Assert.assertEquals(true, played);
    }

    @Test
    public void testDraw2Cards() {
        crazyEights.setTopCard(new Card(2, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        int nextDrawNum = crazyEights.getNextDrawNum();
        System.out.println("next player need to draw " + nextDrawNum + " cards");
        Assert.assertEquals(2, nextDrawNum);
    }


    @Test
    public void testPlayedEight() {
        crazyEights.setTopCard(new Card(12, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        Card card = new Card(8, Suit.S);
        crazyEights.setSuit(Suit.D);
        boolean played = player1.canPlay(card);
        System.out.println("the card played is " + card);
        Assert.assertEquals(true, played);
    }

    @Test
    public void testForceToDraw() {
        crazyEights.setTopCard(new Card(1, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player1.addCard(new Card(2, Suit.D)).addCard(new Card(4, Suit.S));
        System.out.println("car list is " + player1.getCardList());
        boolean b = player1.forceToDraw();
        Assert.assertEquals(true, b);
    }

    @Test
    public void testForceToDrawWithTwo() {
        crazyEights.setTopCard(new Card(2, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        int nextDrawNum = crazyEights.getNextDrawNum();
        System.out.println("next player need to draw " + nextDrawNum + " cards");
        player1.addCard(new Card(2, Suit.S));
        player1.playCard(0, true);
        nextDrawNum = crazyEights.getNextDrawNum();
        System.out.println("next player need to draw " + nextDrawNum + " cards");
        Assert.assertEquals(4, nextDrawNum);
    }

    @Test
    public void testDraw() {
        crazyEights.setTopCard(new Card(2, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player1.addCard(new Card(1, Suit.S));
        System.out.println("card list before draw is " + player1.getCardList());
        player1.draw();
        System.out.println("card list after draw is " + player1.getCardList());
        Assert.assertEquals(2, player1.getCardNum());
    }

    @Test
    public void testDrawMaxThree() {
        crazyEights.setTopCard(new Card(2, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player1.addCard(new Card(1, Suit.S));
        Card card1 = player1.draw();
        System.out.println("draw card is " + card1);
        Card card2 = player1.draw();
        System.out.println("draw card is " + card2);
        Card card3 = player1.draw();
        System.out.println("draw card is " + card3);
        Card card4 = player1.draw();
        System.out.println("draw card is " + card4);
        Assert.assertEquals(false, card1 == Card.NULL_CARD);
        Assert.assertEquals(false, card2 == Card.NULL_CARD);
        Assert.assertEquals(false, card3 == Card.NULL_CARD);
        Assert.assertEquals(true, card4 == Card.NULL_CARD);
    }

    @Test
    public void testDrawPlay() {
        crazyEights.setTopCard(new Card(2, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player1.addCard(new Card(1, Suit.S));
        boolean drawPlay = player1.drawPlay(new Card(8, Suit.S));
        System.out.println("forcing to play a drawn card = " + drawPlay);
        Assert.assertEquals(true, drawPlay);
    }
}
