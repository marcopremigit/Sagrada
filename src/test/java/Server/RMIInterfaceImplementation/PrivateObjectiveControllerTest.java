package Server.RMIInterfaceImplementation;

import Server.Lobby;
import Server.MainServer;
import Shared.Color;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class PrivateObjectiveControllerTest {
    PrivateObjectiveController controller;
    PrivateObjective privateObjective1;
    PrivateObjective privateObjective2;
    Player player1;

    /**
     * set up the two privateObjective card and two player
     * @author Abu Hussnain Saeed
     */
    @Before
    public void setUp(){
        try {
            controller = new PrivateObjectiveController();
        }catch(RemoteException e){}
        privateObjective1 = new PrivateObjective("test1","testare il controller", Color.GREEN);
        privateObjective2 = new PrivateObjective("test2","testare il controller 2",Color.BLUE);
        player1 = new Player("player1");
        Player player2 = new Player("player2");
        player1.setPrivateObjective1(privateObjective1);
        player2.setPrivateObjective2(privateObjective2);
        ArrayList<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        MainServer.setLobby(new Lobby("partita"));
        MainServer.getLobby().setPlayers(players);
    }

    /**
     * check if we get correctly the privateObjective1 for the first player and null for the second player
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getPrivateObjective1(){
        Assert.assertEquals("test1", controller.getPrivateObjective1("player1").getName());
        Assert.assertEquals("testare il controller", controller.getPrivateObjective1("player1").getObjective());
        Assert.assertEquals(Color.GREEN, controller.getPrivateObjective1("player1").getColor());
        Assert.assertNull(controller.getPrivateObjective1("player2"));

    }

    /**
     * check if we get correctly the privateObjective2 for the second player and null for the first player
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getPrivateObjective2(){
        Assert.assertEquals("test2", controller.getPrivateObjective2("player2").getName());
        Assert.assertEquals("testare il controller 2", controller.getPrivateObjective2("player2").getObjective());
        Assert.assertEquals(Color.BLUE, controller.getPrivateObjective2("player2").getColor());
        Assert.assertNull(controller.getPrivateObjective2("player1"));
    }
    @Test
    public void setPrivateObjective() {
        controller.setPrivateObjective("RED");
        Assert.assertTrue(player1.getNameChosenPrivateObjective().equals("RED"));
    }
}
