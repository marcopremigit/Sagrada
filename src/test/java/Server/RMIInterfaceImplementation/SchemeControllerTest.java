package Server.RMIInterfaceImplementation;

import Server.Game;
import Server.Lobby;
import Server.MainServer;
import Shared.Exceptions.CannotSaveCardException;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCard;
import Shared.Model.Schemes.SchemeCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class SchemeControllerTest {
    SchemeController schemeController;
    SchemeCard schemeCard;
    ArrayList<SchemeCard> schemeCards=new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        try{
            schemeController = new SchemeController();
        }catch (RemoteException e){}
        Game game = new Game();
        MainServer.setLobby(new Lobby("test"));
        SchemeCell[][] frontCells = new SchemeCell[4][5];
        SchemeCell[][] rearCells = new SchemeCell[4][5];
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                frontCells[i][j]=new SchemeCell();
                rearCells[i][j]=new SchemeCell();
            }
        }
        schemeCard = new SchemeCard(new Scheme("LuxMondi",4,frontCells),new Scheme("LuxAstram", 5, rearCells));
        schemeCards.add(schemeCard);
        game.setSchemeCards(schemeCards);
        MainServer.getLobby().addPlayerToLobby("testPlayer");
        MainServer.getLobby().addPlayerToLobby("testPlayer2");
        MainServer.getLobby().setGame(game);
        schemeController.setScheme("testPlayer", "Lux mondi");
        schemeController.setScheme("testPlayer2", "Industria");
    }

    @Test
    public void getSchemeCard() {
        SchemeCard schemeCard = schemeController.getSchemeCard();
        Assert.assertEquals(true, schemeCard!=null);
        Assert.assertEquals(true, schemeCard.getFront().getName().equals("LuxMondi"));
        Assert.assertEquals(true, schemeCard.getRear().getName().equals("LuxAstram"));

    }

    @Test
    public void setScheme() {
        Assert.assertEquals(true,MainServer.getLobby().getPlayers().get(0).getScheme().getName().equals("Lux mondi"));
        Assert.assertEquals(true,MainServer.getLobby().getPlayers().get(1).getScheme().getName().equals("Industria"));
    }

    @Test
    public void getScheme() {
        Scheme scheme = schemeController.getScheme("testPlayer2");
        Assert.assertEquals(true, scheme!=null);
    }

    @Test
    public void getPlayerFavours() {
        Assert.assertEquals(5, schemeController.getPlayerFavours("testPlayer2"));
    }

    @Test
    public void saveCardOnServer() {
        String[] name = new String[]{"frontTest","rearTest"};
        String[] favours = new String[]{"5","3"};
        String[] front = new String[]{"W0", "W0", "W1", "R0", "W0", "W1"};
        String[] rear = new String[]{"R0", "W1", "W0", "G0", "W4", "W6"};
        String[] col = {"2", "2"};
        String[] rows = {"3", "3"};
        try {
            schemeController.saveCardOnServer(name,favours,front,rear, col, rows);
        }catch (CannotSaveCardException c){}

    }
}