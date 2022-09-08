package Server.RMIInterfaceImplementation;

import Server.Game;
import Server.Lobby;
import Server.MainServer;
import Server.RMIInterfaceImplementation.DraftPoolController;
import Shared.Color;
import Shared.Exceptions.IllegalColorException;
import Shared.Model.Dice.Dice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class DraftPoolControllerTest {
    ArrayList<Dice> draftPool;
    DraftPoolController draftPoolController;

    /**
     * set the lobby and the game for MainServer
     * @author Abu Hussnain Saeed
     */
    @Before
    public void setUp(){
        try {
            draftPoolController = new DraftPoolController();
        }catch (RemoteException r){}
        Game game = new Game();
        draftPool = new ArrayList<>();
        MainServer.setLobby(new Lobby("test"));

        try {
            draftPool.add(new Dice(Color.GREEN));
            draftPool.get(0).setTop(6);
            draftPool.add(new Dice(Color.BLUE));
            draftPool.get(1).setTop(3);
            game.setDraftPool(draftPool);
            MainServer.getLobby().setGame(game);
        }
        catch (IllegalColorException i){}
    }

    /**
     * check if we get correctly the draftPool from the server
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getDraftPool(){
        Assert.assertEquals(draftPool, draftPoolController.getDraftPool());
    }

    /**
     * check if we roll correctly the dice indicated by the client with the index number
     * @author Abu Hussnain Saeed
     */
    @Test
    public void rollDiceInDraftpool(){
        draftPool = draftPoolController.rollDiceInDraftpool(1);
        Assert.assertEquals(Color.BLUE, draftPool.get(1).getColor());
        Assert.assertEquals(Color.GREEN, draftPool.get(0).getColor());
        Assert.assertEquals(6,draftPool.get(0).getTop());
    }

}
