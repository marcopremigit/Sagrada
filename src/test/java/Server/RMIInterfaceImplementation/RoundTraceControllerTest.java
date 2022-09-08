package Server.RMIInterfaceImplementation;

import Server.Game;
import Server.Lobby;
import Server.MainServer;
import Server.RMIInterfaceImplementation.RoundTraceController;
import Shared.Color;
import Shared.Exceptions.IllegalColorException;
import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoundTraceControllerTest {
    RoundTraceController roundTraceController;
    RoundTrace roundTrace;
    Dice dice1;
    Dice dice2;
    ArrayList<Dice> dicesToAdd=new ArrayList<>();

    /**
     * set the lobby and the game for MainServer
     * @author Marco Premi
     */
    @Before
    public void setUp(){
        try{
            roundTraceController=new RoundTraceController();
        }catch (RemoteException e){}
        Game game = new Game();
        MainServer.setLobby(new Lobby("test"));



        try{
            dice1 = new Dice(Color.RED);
            dice1.setTop(3);
            dice2 = new Dice(Color.PURPLE);
            dice2.setTop(5);
            dicesToAdd.add(0, dice1);
            dicesToAdd.add(1, dice2);
            game.setRoundTrace(dicesToAdd, 0);
            MainServer.getLobby().setGame(game);
        }catch (IllegalColorException e){}

    }
    /**
     * Test if the roundTrace is correctly created and modified
     * @author Marco Premi
     */
    @Test
    public void getRoundTrace() {
        roundTrace=roundTraceController.getRoundTrace();
        Assert.assertEquals(true, roundTrace.getTrace().get(0).getCell().get(0).equals(dice1));
        Assert.assertEquals(true, roundTrace.getTrace().get(0).getCell().get(1).equals(dice2));
        Assert.assertEquals(true, roundTrace.getTrace().get(0).getCell().size()==2);

    }
}