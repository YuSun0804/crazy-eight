import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import server.Card;
import server.CrazyEights;
import server.Player;
import server.Suit;

import java.util.Map;

public class GameTest {

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
    public void testGame() {
        crazyEights.setTopCard(new Card(4, Suit.D));
        System.out.println("top card is " + crazyEights.getTopCard());

        player1.addCard(new Card(4, Suit.H)).addCard(new Card(7, Suit.S))
                .addCard(new Card(5, Suit.D)).addCard(new Card(6, Suit.D))
                .addCard(new Card(9, Suit.D));
        System.out.println("player1's card list is " + player1.getCardList());

        player2.addCard(new Card(4, Suit.S)).addCard(new Card(6, Suit.S))
                .addCard(new Card(13, Suit.C)).addCard(new Card(8, Suit.H))
                .addCard(new Card(11, Suit.D));
        System.out.println("player2's card list is " + player2.getCardList());

        player3.addCard(new Card(5, Suit.S)).addCard(new Card(6, Suit.C))
                .addCard(new Card(12, Suit.C)).addCard(new Card(12, Suit.D))
                .addCard(new Card(3, Suit.H));
        System.out.println("player3's card list is " + player3.getCardList());

        player4.addCard(new Card(7, Suit.D)).addCard(new Card(11, Suit.H))
                .addCard(new Card(12, Suit.H)).addCard(new Card(13, Suit.H))
                .addCard(new Card(10, Suit.C));
        System.out.println("player4's card list is " + player4.getCardList());

        play(player1, new Card(4, Suit.H));
        play(player2, new Card(4, Suit.S));
        play(player3, new Card(5, Suit.S));
        draw(player4, new Card(2, Suit.C)).draw(player4, new Card(3, Suit.C)).draw(player4, new Card(4, Suit.C));
        System.out.println("--------------------");

        play(player1, new Card(7, Suit.S));
        play(player2, new Card(6, Suit.S));
        play(player3, new Card(6, Suit.C));
        play(player4, new Card(2, Suit.C));
        System.out.println("--------------------");

        draw(player1, new Card(10, Suit.C)).draw(player1, new Card(11, Suit.C));
        play(player1, new Card(11, Suit.C));
        play(player2, new Card(13, Suit.C));
        play(player3, new Card(12, Suit.C));
        play(player4, new Card(3, Suit.C));
        System.out.println("--------------------");

        draw(player1, new Card(9, Suit.C));
        play(player1, new Card(9, Suit.C));
        play(player2, new Card(8, Suit.H));
        crazyEights.setSuit(Suit.D);
        play(player3, new Card(12, Suit.D));
        play(player4, new Card(7, Suit.D));
        System.out.println("--------------------");

        play(player1, new Card(9, Suit.D));
        play(player2, new Card(11, Suit.D));
        System.out.println("--------------------");

        score();
        Assert.assertEquals(false, crazyEights.isGameOver());

        player1.addCard(new Card(1, Suit.D)).addCard(new Card(4, Suit.S))
                .addCard(new Card(1, Suit.C)).addCard(new Card(4, Suit.H))
                .addCard(new Card(5, Suit.D));
        System.out.println("player1's card list is " + player1.getCardList());

        player2.addCard(new Card(2, Suit.D)).addCard(new Card(3, Suit.S))
                .addCard(new Card(2, Suit.C)).addCard(new Card(3, Suit.H))
                .addCard(new Card(11, Suit.C));
        System.out.println("player2's card list is " + player2.getCardList());

        player3.addCard(new Card(3, Suit.D)).addCard(new Card(2, Suit.S))
                .addCard(new Card(3, Suit.C)).addCard(new Card(2, Suit.H))
                .addCard(new Card(5, Suit.H));
        System.out.println("player3's card list is " + player3.getCardList());

        player4.addCard(new Card(4, Suit.D)).addCard(new Card(1, Suit.S))
                .addCard(new Card(4, Suit.C)).addCard(new Card(5, Suit.S))
                .addCard(new Card(8, Suit.D));
        System.out.println("player4's card list is " + player4.getCardList());

        play(player1, new Card(1, Suit.D));
        play(player2, new Card(2, Suit.D));
        play(player3, new Card(3, Suit.D));
        play(player4, new Card(4, Suit.D));

        play(player1, new Card(4, Suit.S));
        play(player2, new Card(3, Suit.S));
        play(player3, new Card(2, Suit.S));
        play(player4, new Card(1, Suit.S));

        play(player1, new Card(1, Suit.C));
        play(player2, new Card(2, Suit.C));
        play(player3, new Card(3, Suit.C));
        play(player4, new Card(4, Suit.C));

        play(player1, new Card(4, Suit.H));
        play(player2, new Card(3, Suit.H));
        play(player3, new Card(2, Suit.H));
        draw(player4, new Card(13, Suit.S)).draw(player4, new Card(12, Suit.S)).draw(player4, new Card(1, Suit.H));
        play(player4, new Card(1, Suit.H));

        draw(player1, new Card(6, Suit.D)).draw(player1, new Card(7, Suit.D)).draw(player1, new Card(9, Suit.D));
        draw(player2, new Card(6, Suit.S)).draw(player2, new Card(7, Suit.S)).draw(player2, new Card(9, Suit.S));
        play(player3, new Card(5, Suit.H));

        score();

        String winner = crazyEights.getWinner();
        System.out.println("Player" + winner + " win the game");
        Assert.assertEquals("3", winner);
    }

    private void score() {
        for (Map.Entry<String, Player> entry : crazyEights.getPlayers().entrySet()) {
            System.out.println(entry.getValue().getCardList());
        }
        Map<String, Integer> scorePad = crazyEights.calculateScore();
        for (Map.Entry<String, Integer> entry : scorePad.entrySet()) {
            String playerId = entry.getKey();
            System.out.println("Player" + playerId + "'s score is " + entry.getValue());
        }
    }

    private void play(Player player, Card card) {
        System.out.println("Player" + player.getId() + " play " + card);
        player.playCard(card, true);
        System.out.println(player.getCardList());
        System.out.println(crazyEights.getTopCard());
    }

    private GameTest draw(Player player, Card card) {
        System.out.println("Player" + player.getId() + " draw " + card);
        player.draw(card);
        return this;
    }
}
