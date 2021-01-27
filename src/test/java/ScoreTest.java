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

    @Before
    public void beforeTest() {
        crazyEights.addPlayer(player1);
        crazyEights.addPlayer(player2);
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
