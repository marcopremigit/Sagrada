package Server;

import Shared.Exceptions.LobbyFullException;
import Shared.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class LobbyTest {

    private Lobby lobby;
    private Player p1;
    private Player p2;
    private Player p3;
    private Player p4;

    @Before
    public void setUp() throws Exception {
        lobby = new Lobby("testLobby");
        p1 = new Player("testPlayer1");
        p2 = new Player("testPlayer2");
        p3 = new Player("testPlayer3");
        p4 = new Player("testPlayer4");
    }

    @Test
    public void addPlayerToLobby() {
        try {
            lobby.addPlayerToLobby(p1.getName());
        } catch (LobbyFullException l){
            Assert.assertNull(l);
        }
        Assert.assertTrue(lobby.getPlayers().size() == 1);
        try {
            lobby.addPlayerToLobby(p2.getName());
        } catch (LobbyFullException l){
            Assert.assertNull(l);
        }
        Assert.assertTrue(lobby.getPlayers().size() == 2);
        try {
            lobby.addPlayerToLobby(p3.getName());
        } catch (LobbyFullException l){
            Assert.assertNull(l);
        }
        Assert.assertTrue(lobby.getPlayers().size() == 3);
        try {
            lobby.addPlayerToLobby(p4.getName());
        } catch (LobbyFullException l){
            Assert.assertNull(l);
        }
        Assert.assertTrue(lobby.getPlayers().size() == 4);

        try{
            lobby.addPlayerToLobby(p1.getName());
        } catch (LobbyFullException l){
            Assert.assertNotNull(l);
        }
        Assert.assertTrue(lobby.getPlayers().size() == 4);
    }

    @Test
    public void removePlayerFromLobby() {
        for(int i=0; i<4; i++){
            Player p = new Player("testPlayer" + i);
            try {
                lobby.addPlayerToLobby(p.getName());
            } catch (LobbyFullException l){
                Assert.assertNull(l);
            }
        }

        Assert.assertTrue(lobby.getPlayers().size() == 4);
        lobby.removePlayerFromLobby(p1.getName());
        Assert.assertTrue(lobby.getPlayers().size() == 3);
        lobby.removePlayerFromLobby(p2.getName());
        Assert.assertTrue(lobby.getPlayers().size() == 2);
        lobby.removePlayerFromLobby(p3.getName());
        Assert.assertTrue(lobby.getPlayers().size() == 1);
        lobby.removePlayerFromLobby(p4.getName());
    }

    @Test
    public void getLobbyId() {
        Assert.assertEquals(lobby.getLobbyId(), "testLobby");
    }

    @Test
    public void getGame() {
        Assert.assertNull(lobby.getGame());
    }

    @Test
    public void setGame() {
        Assert.assertNull(lobby.getGame());
        lobby.setGame(new Game());
        Assert.assertNotNull(lobby.getGame());
    }

    @Test
    public void isGameEnded() {
        Assert.assertFalse(lobby.isGameEnded());
    }

    @Test
    public void getGameController() {
        Assert.assertNull(lobby.getGameController());
    }

    @Test
    public void setGameController() {
        Assert.assertNull(lobby.getGameController());
        lobby.setGameController(new GameController(new Game()));
        Assert.assertNotNull(lobby.getGameController());
    }

    @Test
    public void getPlayers() {
        for(int i=0; i<4; i++){
            Player p = new Player("testPlayer" + i);
            try {
                lobby.addPlayerToLobby(p.getName());
                Assert.assertEquals(i + 1,lobby.getPlayers().size());
            } catch (LobbyFullException l){
                Assert.assertNull(l);
            }
        }



    }

    @Test
    public void setPlayers() {
        ArrayList<Player> players = new ArrayList<>();
        for(int i=0; i<4; i++){
            Player p = new Player("testPlayer" + i);
            players.add(p);
        }

        Assert.assertEquals(0,lobby.getPlayers().size());
        lobby.setPlayers(players);
        Assert.assertEquals(4, lobby.getPlayers().size());
    }

    @Test
    public void setDifficulty() {
        Assert.assertEquals(0, lobby.getDifficulty());
        lobby.setDifficulty(3);
        Assert.assertEquals(3, lobby.getDifficulty());
        lobby.setDifficulty(7);
        Assert.assertNotEquals(7, lobby.getDifficulty());
    }

    @Test
    public void isSinglePlayer() {
        Assert.assertFalse(lobby.isSinglePlayer());
    }

    @Test
    public void setSinglePlayer() {
        Assert.assertFalse(lobby.isSinglePlayer());
        lobby.setSinglePlayer(true);
        Assert.assertTrue(lobby.isSinglePlayer());
    }

    @Test
    public void getDifficulty() {
        Assert.assertEquals(0, lobby.getDifficulty());
    }

    @Test
    public void run() {
        /*
         * Can't be tested.
         * Requires connection
         * */
    }

    @Test
    public void clientNotAvailable() {
        /*
         * Can't be tested.
         * Requires connection
         * */
    }
    /**
     * check if we set correctly to null the lobby
     * @author Abu Hussnain Saeed
     */
    @Test
    public void endGame(){
        lobby.endGame();
        Assert.assertNull(MainServer.getLobby());
    }
    /**
     * test toString
     * @author Abu Hussnain Saeed
     */
    @Test
    public void toStringTest(){
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        lobby.setPlayers(players);
        Assert.assertEquals("LobbytestLobby. Players: :\n" +
                "\t0: testPlayer1\n" +
                "\t1: testPlayer2\n" +
                "\t" , lobby.toString());
    }
}