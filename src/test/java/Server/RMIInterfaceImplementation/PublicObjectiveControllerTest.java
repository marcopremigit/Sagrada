package Server.RMIInterfaceImplementation;

import Server.Game;
import Server.Lobby;
import Server.MainServer;
import Shared.Color;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.ObjectiveCard.PublicObjDeck;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class PublicObjectiveControllerTest {

    PublicObjectiveController controller;
    PublicObjective publicObjective1;
    PublicObjective publicObjective2;

    /**
     * set up the publicObjectives in the game of the lobby
     * @author Abu Hussnain Saeed
     */
    @Before
    public void setUp(){
        try {
            controller = new PublicObjectiveController();
        }catch(RemoteException e){}

        PublicObjDeck publicObjDeck = new PublicObjDeck();
        publicObjDeck.buildPublic();
        publicObjDeck.shuffle();
        publicObjective1 = publicObjDeck.pick();
        publicObjective2 = publicObjDeck.pick();

        ArrayList<PublicObjective> publicObjectives = new ArrayList<>();
        Game game = new Game();
        publicObjectives.add(publicObjective1);
        publicObjectives.add(publicObjective2);
        game.setPublicObjectives(publicObjectives);

        MainServer.setLobby(new Lobby("partita"));
        MainServer.getLobby().setGame(game);

    }

    /**
     * check if we get correctly the list of publicObjectives from the server
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getPublicObectives(){
        Assert.assertEquals(publicObjective1,controller.getPublicObjective().get(0));
        Assert.assertEquals(publicObjective2,controller.getPublicObjective().get(1));
    }
}
