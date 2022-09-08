package Server.RMIInterfaceImplementation;

import Server.Game;
import Server.Lobby;
import Server.MainServer;
import Server.RMIInterfaceImplementation.DiceBagController;
import Server.RMIInterfaceImplementation.DraftPoolController;
import Shared.Color;
import Shared.Exceptions.IllegalColorException;
import Shared.Model.Dice.Dice;
import Shared.Model.Dice.DiceBag;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;

public class DiceBagControllerTest {
    DiceBag diceBag;
    DiceBagController diceBagController;

    /**
     * set the lobby and the game for MainServer
     * @author Abu Hussnain Saeed
     */
    @Before
    public void setUp() {
        try {
            diceBagController = new DiceBagController();
        } catch (RemoteException r) {
        }
        Game game = new Game();
        diceBag = new DiceBag();
        MainServer.setLobby(new Lobby("test"));
        try {
            Dice dice1 = new Dice(Color.GREEN);
            Dice dice2 = new Dice(Color.BLUE);
            dice1.setTop(6);
            dice2.setTop(4);
            diceBag.addDice(dice1);
            diceBag.addDice(dice2);
            game.setDiceBag(diceBag);
            MainServer.getLobby().setGame(game);
        } catch (IllegalColorException i) {
        }

    }

    /**
     * check if we get correctly the diceBag from the server
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getDiceBag(){
        Assert.assertEquals(diceBag,diceBagController.getDiceBag());
    }
}
