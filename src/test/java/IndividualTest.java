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
    public void test47() {
        crazyEights.setTopCard(new Card(3, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        crazyEights.setCurrentPlayer(1);
        System.out.println("current player is 1");
        String next = crazyEights.calNextOne(false);
        System.out.println("next player is " + next);
        Assert.assertEquals("2", next);
    }

    @Test
    public void test48() {
        crazyEights.setTopCard(new Card(1, Suit.H));
        System.out.println("top card is " + crazyEights.getTopCard());
        crazyEights.setCurrentPlayer(1);
        System.out.println("current player is 1");
        String next = crazyEights.calNextOne(false);
        System.out.println("next player is " + next);
        System.out.println("play direction is " + crazyEights.getDirection());
        Assert.assertEquals("4", next);
        next = crazyEights.calNextOne(false);
        System.out.println("next player is " + next);
        System.out.println("play direction is " + crazyEights.getDirection());
        Assert.assertEquals("3", next);
    }

    @Test
    public void test50() {
        crazyEights.setTopCard(new Card(12, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        crazyEights.setCurrentPlayer(1);
        System.out.println("current player is " + crazyEights.getCurrentPlayer());
        String next = crazyEights.calNextOne(false);
        System.out.println("next player is " + next);
        Assert.assertEquals("3", next);
    }

    @Test
    public void test51() {
        crazyEights.setTopCard(new Card(3, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        crazyEights.setCurrentPlayer(4);
        System.out.println("current player is " + crazyEights.getCurrentPlayer());
        String next = crazyEights.calNextOne(false);
        System.out.println("next player is " + next);
        Assert.assertEquals("1", next);
    }

    @Test
    public void test52() {
        crazyEights.setTopCard(new Card(1, Suit.H));
        System.out.println("top card is " + crazyEights.getTopCard());
        crazyEights.setCurrentPlayer(4);
        System.out.println("current player is " + crazyEights.getCurrentPlayer());
        String next = crazyEights.calNextOne(false);
        System.out.println("next player is " + next);
        Assert.assertEquals("3", next);
        next = crazyEights.calNextOne(false);
        System.out.println("next player is " + next);
        System.out.println("play direction is " + crazyEights.getDirection());
        Assert.assertEquals("2", next);
    }

    @Test
    public void test54() {
        crazyEights.setTopCard(new Card(12, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        crazyEights.setCurrentPlayer(4);
        System.out.println("current player is " + crazyEights.getCurrentPlayer());
        String next = crazyEights.calNextOne(false);
        System.out.println("next player is " + next);
        Assert.assertEquals("2", next);
    }


    @Test
    public void test57() {
        crazyEights.setTopCard(new Card(13, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        Card card = new Card(13, Suit.H);
        boolean played = player1.canPlay(card);
        System.out.println("the card played is " + card);
        Assert.assertEquals(true, played);
    }


    @Test
    public void test58() {
        crazyEights.setTopCard(new Card(13, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        Card card = new Card(7, Suit.C);
        boolean played = player1.canPlay(card);
        System.out.println("the card played is " + card);
        Assert.assertEquals(true, played);
    }

    @Test
    public void test59() {
        crazyEights.setTopCard(new Card(13, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        Card card = new Card(8, Suit.H);
        boolean played = player1.canPlay(card);
        System.out.println("the card played is " + card);
        Assert.assertEquals(true, played);
        System.out.println("choose a suit");
    }

    @Test
    public void test60() {
        crazyEights.setTopCard(new Card(13, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        Card card = new Card(5, Suit.S);
        boolean played = player1.canPlay(card);
        System.out.println("the card played is " + card);
        Assert.assertEquals(false, played);
    }

    @Test
    public void test63() {
        crazyEights.setTopCard(new Card(7, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player1.addCard(new Card(3, Suit.H));
        System.out.println("car list is " + player1.getCardList());
        boolean b = player1.forceToDraw();
        Assert.assertEquals(true, b);
        boolean b1 = player1.drawPlay(new Card(6, Suit.C));
        System.out.println("the card draw is 6C");
        if (b1) {
            System.out.println("the card played is 6C");
        }
        Assert.assertEquals(true, b1);
    }

    @Test
    public void test64() {
        crazyEights.setTopCard(new Card(7, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player1.addCard(new Card(3, Suit.H));
        System.out.println("car list is " + player1.getCardList());
        boolean b = player1.forceToDraw();
        Assert.assertEquals(true, b);
        boolean b1 = player1.drawPlay(new Card(6, Suit.D));
        System.out.println("the card draw is 6D");
        Assert.assertEquals(false, b1);
        b1 = player1.drawPlay(new Card(5, Suit.C));
        System.out.println("the card draw is 5C");
        if (b1) {
            System.out.println("the card played is 5C");
        }
        Assert.assertEquals(true, b1);
    }

    @Test
    public void test65() {
        crazyEights.setTopCard(new Card(7, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player1.addCard(new Card(3, Suit.H));
        System.out.println("car list is " + player1.getCardList());
        boolean b = player1.forceToDraw();
        Assert.assertEquals(true, b);
        boolean b1 = player1.drawPlay(new Card(6, Suit.D));
        System.out.println("the card draw is 6D");
        Assert.assertEquals(false, b1);
        b1 = player1.drawPlay(new Card(5, Suit.S));
        System.out.println("the card draw is 5S");
        Assert.assertEquals(false, b1);
        b1 = player1.drawPlay(new Card(7, Suit.H));
        System.out.println("the card draw is 7H");
        if (b1) {
            System.out.println("the card played is 7H");
        }
        Assert.assertEquals(true, b1);
    }

    @Test
    public void test66() {
        crazyEights.setTopCard(new Card(7, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player1.addCard(new Card(3, Suit.H));
        System.out.println("car list is " + player1.getCardList());
        boolean b = player1.forceToDraw();
        Assert.assertEquals(true, b);
        boolean b1 = player1.drawPlay(new Card(6, Suit.D));
        System.out.println("the card draw is 6D");
        Assert.assertEquals(false, b1);
        b1 = player1.drawPlay(new Card(5, Suit.S));
        System.out.println("the card draw is 5S");
        Assert.assertEquals(false, b1);
        b1 = player1.drawPlay(new Card(4, Suit.H));
        System.out.println("the card draw is 4H");
        if (!b1) {
            System.out.println("cannot play");
        }
        Assert.assertEquals(false, b1);
    }

    @Test
    public void test67() {
        crazyEights.setTopCard(new Card(7, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player1.addCard(new Card(3, Suit.H));
        System.out.println("car list is " + player1.getCardList());
        boolean b = player1.forceToDraw();
        Assert.assertEquals(true, b);
        boolean b1 = player1.drawPlay(new Card(6, Suit.D));
        System.out.println("the card draw is 6D");
        Assert.assertEquals(false, b1);
        b1 = player1.drawPlay(new Card(8, Suit.H));
        System.out.println("the card draw is 8H");
        if (!b1) {
            System.out.println("the card played is 8H");
        }
        Assert.assertEquals(true, b1);
        if (crazyEights.getTopCard().getValue() == 8) {
            System.out.println("choose a suit");
        }
    }

    @Test
    public void test68() {
        crazyEights.setTopCard(new Card(7, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player1.addCard(new Card(3, Suit.H)).addCard(new Card(13, Suit.S));
        System.out.println("car list is " + player1.getCardList());
        player1.draw(new Card(6, Suit.C));
        System.out.println("the card draw is 6C");
        boolean b = player1.canPlay(new Card(6, Suit.C));
        System.out.println("the card played is 6C");
        Assert.assertEquals(true, b);
    }

    @Test
    public void test69() {
        crazyEights.setTopCard(new Card(7, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player1.addCard(new Card(3, Suit.H)).addCard(new Card(13, Suit.S));
        System.out.println("car list is " + player1.getCardList());
        player1.draw(new Card(6, Suit.C));
        System.out.println("the card draw is 6C");
        boolean b = player1.canPlay(new Card(3, Suit.C));
        System.out.println("the card played is 3C");
        Assert.assertEquals(true, b);
    }

    @Test
    public void test70() {
        crazyEights.setTopCard(new Card(7, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player2.addCard(new Card(8, Suit.C)).addCard(new Card(13, Suit.S));
        System.out.println("car list is " + player1.getCardList());
        player2.draw(new Card(6, Suit.D));
        System.out.println("the card draw is 6C");
        boolean b = player1.canPlay(new Card(8, Suit.C));
        System.out.println("the card played is 8C");
        Assert.assertEquals(true, b);
        System.out.println("choose a suit");

    }

    @Test
    public void test73() {
        crazyEights.setTopCard(new Card(2, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player2.addCard(new Card(4, Suit.H));
        System.out.println("car list is " + player1.getCardList());
        int nextDrawNum = crazyEights.getNextDrawNum();
        System.out.println("next player need to draw " + nextDrawNum + " cards");
        player2.draw(new Card(6, Suit.C));
        player2.draw(new Card(9, Suit.D));
        boolean b = player2.canPlay(new Card(6, Suit.C));
        System.out.println("the card played is 6C");
        Assert.assertEquals(true, b);
    }

    @Test
    public void test74() {
        crazyEights.setTopCard(new Card(2, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player2.addCard(new Card(4, Suit.H));
        System.out.println("car list is " + player1.getCardList());
        int nextDrawNum = crazyEights.getNextDrawNum();
        System.out.println("next player need to draw " + nextDrawNum + " cards");
        player2.draw(new Card(6, Suit.S));
        player2.draw(new Card(9, Suit.D));
        player2.draw(new Card(9, Suit.H));
        player2.draw(new Card(6, Suit.C));
        boolean b = player2.canPlay(new Card(6, Suit.C));
        System.out.println("the card played is 6C");
        Assert.assertEquals(true, b);
    }

    @Test
    public void test75() {
        crazyEights.setTopCard(new Card(2, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player2.addCard(new Card(4, Suit.H));
        System.out.println("car list is " + player1.getCardList());
        int nextDrawNum = crazyEights.getNextDrawNum();
        System.out.println("next player need to draw " + nextDrawNum + " cards");
        player2.draw(new Card(6, Suit.S));
        player2.draw(new Card(9, Suit.D));
        player2.draw(new Card(9, Suit.H));
        player2.draw(new Card(6, Suit.C));
        player2.draw(new Card(5, Suit.H));
        System.out.println("car list is " + player2.getCardList());
        System.out.println("turn end");
    }

    @Test
    public void test76() {
        crazyEights.setTopCard(new Card(2, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player2.addCard(new Card(4, Suit.H));
        System.out.println("car list is " + player1.getCardList());
        int nextDrawNum = crazyEights.getNextDrawNum();
        System.out.println("next player need to draw " + nextDrawNum + " cards");
        player2.draw(new Card(6, Suit.S));
        player2.draw(new Card(8, Suit.D));
        player2.draw(new Card(5, Suit.S));
        boolean b = player2.canPlay(new Card(8, Suit.D));
        System.out.println("the card played is 8D");
        Assert.assertEquals(true, b);
    }

    @Test
    public void test77() {
        crazyEights.setTopCard(new Card(2, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player2.addCard(new Card(4, Suit.H));
        System.out.println("car list is " + player1.getCardList());
        int nextDrawNum = crazyEights.getNextDrawNum();
        System.out.println("next player need to draw " + nextDrawNum + " cards");
        player2.draw(new Card(2, Suit.H));
        player2.draw(new Card(9, Suit.D));
        player2.playCard(new Card(2, Suit.H), true);
        nextDrawNum = crazyEights.getNextDrawNum();
        System.out.println("next player need to draw " + nextDrawNum + " cards");
        player3.draw(new Card(5, Suit.S));
        player3.draw(new Card(6, Suit.D));
        player3.draw(new Card(6, Suit.C));
        player3.draw(new Card(7, Suit.C));
        boolean b = player2.canPlay(new Card(6, Suit.C));
        System.out.println("the card played is 6C");
        Assert.assertEquals(true, b);
    }

    @Test
    public void test79() {
        crazyEights.setTopCard(new Card(2, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player2.addCard(new Card(4, Suit.C)).addCard(new Card(6, Suit.C)).addCard(new Card(9, Suit.D));
        System.out.println("car list is " + player1.getCardList());
        int nextDrawNum = crazyEights.getNextDrawNum();
        System.out.println("next player need to draw " + nextDrawNum + " cards");
        player2.playCard(new Card(4, Suit.C), true);
        player2.playCard(new Card(6, Suit.C), true);
    }


    @Test
    public void test80() {
        crazyEights.setTopCard(new Card(2, Suit.C));
        System.out.println("top card is " + crazyEights.getTopCard());
        player2.addCard(new Card(4, Suit.C)).addCard(new Card(6, Suit.C));
        System.out.println("car list is " + player1.getCardList());
        int nextDrawNum = crazyEights.getNextDrawNum();
        System.out.println("next player need to draw " + nextDrawNum + " cards");
        player2.playCard(new Card(4, Suit.C), true);
        player2.playCard(new Card(6, Suit.C), true);
    }

}
