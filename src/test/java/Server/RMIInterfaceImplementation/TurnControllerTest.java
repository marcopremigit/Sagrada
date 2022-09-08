package Server.RMIInterfaceImplementation;

import Client.Main;
import Server.Game;
import Server.GameController;
import Server.Lobby;
import Server.MainServer;
import Server.RMIInterfaceImplementation.TurnController;
import Shared.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class TurnControllerTest {
    TurnController turnController;
    GameController gameController;

    /**
     * set the lobby and the game for MainServer
     * @author Marco Premi, Abu Hussnain Saeed
     */
    @Before
    public void setUp() throws Exception {
        try {
            turnController = new TurnController();
        } catch (RemoteException e) {
        }
        Game game = new Game();
        MainServer.setLobby(new Lobby("test"));
        ArrayList<Player> playerArrayList = new ArrayList<>();
        Player player1 = new Player("testPlayer");
        Player player2 = new Player("testPlayer2");
        playerArrayList.add(player1);
        playerArrayList.add(player2);
        MainServer.getLobby().setPlayers(playerArrayList);
        MainServer.getLobby().setGame(game);
        gameController = new GameController(game);
        MainServer.getLobby().setGameController(gameController);


    }

    /**
     * check if endMyTurn works properly and doesn't stop
     * @author Marco Premi, Abu Hussnain Saeed
     */
    @Test
    public void endMyTurn() {

        turnController.endMyTurn("testPlayer2");
        Assert.assertEquals(true,MainServer.getLobby().getPlayers().get(1).isEndTurn());

    }


    @Test
    public void isGameEnded() {
        Assert.assertFalse(turnController.isGameEnded());
    }

    /**
     * check if at the start of the game we have the round setted to 1
     * @author Abu Hussnain Saeed
     */
    @Test
    public void round() {
        Assert.assertEquals(1, turnController.round());
    }

    /**
     * check if at the start of the game we have the clockWise setted to true
     * @author Abu Hussnain Saeed,Marco Premi
     */
    @Test
    public void isClockwise() {
        Assert.assertEquals(true, turnController.isClockwise());
    }
}