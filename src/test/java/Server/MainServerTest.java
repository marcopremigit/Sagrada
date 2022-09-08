package Server;

import Server.RMIInterfaceImplementation.ConnectionController;
import Server.RMIInterfaceImplementation.DraftPoolController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainServerTest {
    MainServer mainServer;

    @Before
    public void setUp() throws Exception {
        mainServer=new MainServer();
    }
    @Before
    public void startRMIServer() {
        mainServer.startRMIServer();
    }


    @Test
    public void main() {

    }

    @Test
    public void startSocketServer(){
        mainServer.startSocketServer();
    }



    @Test
    public void getAndSetLobby() {
        Lobby lobby = new Lobby("123456");
        mainServer.setLobby(lobby);
        Assert.assertNotNull(mainServer.getLobby());
    }

    @Test
    public void getLobbyTimer() {
        Assert.assertNotNull(mainServer.getLobbyTimer());
    }

    @Test
    public void getConnectionController() {
        Assert.assertNotNull(mainServer.getConnectionController());
    }

    @Test
    public void getDraftPoolController() {
        Assert.assertNotNull(mainServer.getDraftPoolController());
    }

    @Test
    public void getPrivateObjectiveController() {
        Assert.assertNotNull(mainServer.getPrivateObjectiveController());
    }

    @Test
    public void getPublicObjectiveController() {
        Assert.assertNotNull(mainServer.getPublicObjectiveController());
    }

    @Test
    public void getRoundTraceController() {
        Assert.assertNotNull(mainServer.getRoundTraceController());
    }

    @Test
    public void getSchemeController() {
        Assert.assertNotNull(mainServer.getSchemeController());
    }

    @Test
    public void getToolController() {
        Assert.assertNotNull(mainServer.getToolController());
    }

    @Test
    public void getTurnController() {
        Assert.assertNotNull(mainServer.getTurnController());
    }
}