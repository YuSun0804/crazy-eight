import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.Card;
import server.CrazyEights;
import server.Player;
import server.Suit;

import java.util.Map;

public class ScoreTest {

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
    public void test84() {
        player1.addCard(new Card(1, Suit.S));
        player3.addCard(new Card(8, Suit.H)).addCard(new Card(11, Suit.H)).addCard(new Card(6, Suit.H))
        .addCard(new Card(13, Suit.H)).addCard(new Card(13, Suit.S));
        player4.addCard(new Card(8, Suit.C)).addCard(new Card(8, Suit.D)).addCard(new Card(2, Suit.D));

        Map<String, Integer> scorePad = crazyEights.calculateScore();
        for (Map.Entry<String, Integer> entry : scorePad.entrySet()) {
            String playerId = entry.getKey();
            System.out.println("Player" + playerId + "'s score is " + entry.getValue());
        }
    }

    @Test
    public void testScore0() {
        System.out.println("card list is " + player1.getCardList());
        Map<String, Integer> scorePad = crazyEights.calculateScore();
        int score = scorePad.get(player1.getId());
        System.out.println("score is " + score);
        Assert.assertEquals(0, score);
    }

    @Test
    public void testScore1() {
        player1.addCard(new Card(1, Suit.S));
        System.out.println("card list is " + player1.getCardList());
        Map<String, Integer> scorePad = crazyEights.calculateScore();
        int score = scorePad.get(player1.getId());
        System.out.println("score is " + score);
        Assert.assertEquals(1, score);
    }

    @Test
    public void testScore8() {
        player1.addCard(new Card(8, Suit.S));
        System.out.println("card list is " + player1.getCardList());
        Map<String, Integer> scorePad = crazyEights.calculateScore();
        int score = scorePad.get(player1.getId());
        System.out.println("score is " + score);
        Assert.assertEquals(50, score);
    }

    @Test
    public void testScore11() {
        player1.addCard(new Card(11, Suit.S));
        System.out.println("card list is " + player1.getCardList());
        Map<String, Integer> scorePad = crazyEights.calculateScore();
        int score = scorePad.get(player1.getId());
        System.out.println("score is " + score);
        Assert.assertEquals(10, score);
    }

    @Test
    public void testScore12() {
        player1.addCard(new Card(12, Suit.S));
        System.out.println("card list is " + player1.getCardList());
        Map<String, Integer> scorePad = crazyEights.calculateScore();
        int score = scorePad.get(player1.getId());
        System.out.println("score is " + score);
        Assert.assertEquals(10, score);
    }

    @Test
    public void testScore13() {
        player1.addCard(new Card(13, Suit.S));
        System.out.println("card list is " + player1.getCardList());
        Map<String, Integer> scorePad = crazyEights.calculateScore();
        int score = scorePad.get(player1.getId());
        System.out.println("score is " + score);
        Assert.assertEquals(10, score);
    }

    @Test
    public void testScore113() {
        player1.addCard(new Card(13, Suit.S)).addCard(new Card(2, Suit.S));
        System.out.println("card list is " + player1.getCardList());
        Map<String, Integer> scorePad = crazyEights.calculateScore();
        int score = scorePad.get(player1.getId());
        System.out.println("score is " + score);
        Assert.assertEquals(12, score);
    }
}
