package Server.RMIInterfaceImplementation;

import Server.Game;
import Server.Lobby;
import Server.MainServer;
import Shared.Color;
import Shared.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ConnectionControllerTest {
    ConnectionController connectionController;
    ArrayList<Player> players;
    Player player1;
    Player player2;
    @Before
    public void setUp() throws Exception {
        ArrayList<String> otherPlayer = new ArrayList<>();
        connectionController = new ConnectionController();
        player1 = new Player("player1");
        player1.setColor(Color.BLUE);
        player2 = new Player("player2");
        player2.setColor(Color.RED);
        players = new ArrayList<>();
        players.add(0,player1);
        players.add(1, player2);
        Game game = new Game();
        MainServer.setLobby(new Lobby("testLobby"));
        MainServer.getLobby().setGame(game);
        MainServer.getLobby().setPlayers(players);
        otherPlayer = connectionController.getOtherPlayerName("player1");
        Assert.assertTrue(otherPlayer.size()==1);
        Assert.assertTrue(otherPlayer.get(0).equals("player2"));
    }

    @Test
    public void getClients() {
       Assert.assertNull(connectionController.getClients());
        
    }

    @Test
    public void getClient() {
        boolean isOK=false;
        try {
            connectionController.getClient("test");
        }catch (NullPointerException e){
            isOK=true;
        }
        Assert.assertTrue(isOK);
    }

    @Test
    public void removeClient() {
        boolean isOK=false;
        try {
            connectionController.removeClient("test");
        }catch (NullPointerException e){
            isOK=true;
        }
        Assert.assertTrue(isOK);
    }

    @Test
    public void pingAll() {
        boolean isOK=false;
        try {
            connectionController.pingAll();
        }catch (NullPointerException e){
            isOK=true;
        }
        Assert.assertTrue(isOK);
    }

    @Test
    public void login() {
        //can't test
    }

    @Test
    public void setDifficulty() {
        boolean isOk = false;
        try{
            connectionController.setDifficulty(5);
            isOk=true;
        }catch (NullPointerException e){}
        Assert.assertTrue(isOk);
    }

    @Test
    public void removePlayer() {
        connectionController.removePlayer("player2");
        Assert.assertTrue(players.get(0).getName().equals("player1"));
        Assert.assertTrue(players.size()==1);
    }

    @Test
    public void pingServer() {
        connectionController.pingServer();
    }


    @Test
    public void getPlayerColor() {
        Assert.assertTrue(connectionController.getPlayerColor("player1").equals(Color.BLUE));
    }


    @Test
    public void getRanking() {
        Assert.assertTrue(connectionController.getRanking().size()==0);
    }

    @Test
    public void getPoints() {
        connectionController.getPoints("player1");
        Assert.assertFalse(connectionController.getPoints("player1")<-10);
        Assert.assertTrue(connectionController.getPoints("inexistentPlayer")==-99);
    }

    @Test
    public void playerAgainAvailable() {
        boolean isOK=false;
        try {
            connectionController.playerAgainAvailable("paperino");
        }catch (NullPointerException e){
            isOK=true;
        }
        Assert.assertTrue(isOK);
    }

    @After
    public void joinGame() {
        //can't test
    }
    /**
     * test the return of the value of ObjectivePoints
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getObjectivesPoints(){
        players.get(0).setObjectivePoint(6);
        Assert.assertEquals(6,connectionController.getObjectivePoints());
    }
}