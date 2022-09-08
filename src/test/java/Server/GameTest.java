package Server;

import Shared.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GameTest {
    Game game2;
    @Before
    public void setUp(){
        game2 = new Game();
    }
    @Test
    public void setUpSingleGameTester(){
        String name = "pippo";
        int difficult = 3;
        Player player = new Player(name);
        Game game = new Game();
        try{
            game.setupSingleGame(player,difficult);
        }catch (NullPointerException e){}
        Assert.assertNotNull(game.getSchemeCards());
    }

    @Test
    public void setUpMultiGameTester(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(new Player("carlo"));
        players.add(new Player("pippo"));
        players.add(new Player("gallo"));
        players.add(new Player("banana"));
        Game game = new Game();
        try{
            game.setupMultiGame(players);
        }catch (NullPointerException e){

        }
        Assert.assertNotNull(game.getSchemeCards());
    }

    @Test
    public void getPublicObjectives(){
        Assert.assertTrue(game2.getPublicObjectives()!=null);

    }

    @Test
    public void getTools(){
        Assert.assertTrue(game2.getTools()!=null);
    }

    @Test
    public void getAndSetRanking(){
        ArrayList<String> ranking = new ArrayList<>();
        ranking.add(0, "player1");
        ranking.add(1,"player2");
        game2.setRanking(ranking);
        Assert.assertTrue(game2.getRanking().get(0).equals("player1"));
        Assert.assertTrue(game2.getRanking().get(1).equals("player2"));
        Assert.assertTrue(game2.getRanking().size()==2);
    }
}
