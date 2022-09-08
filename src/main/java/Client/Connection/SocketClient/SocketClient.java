package Client.Connection.SocketClient;

import Client.ClientInterface;
import Client.Connection.*;
import Client.View.ViewControllers.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketClient implements ClientInterface {
    private Socket socket;

    private Controller mainController;
    private CardsControllerInterface cardsController;
    private SetupControllerInterface setupController;
    private TableControllerInterface tableController;
    private ToolClientControllerInterface toolController;
    private TurnControllerInterface turnController;

    private static final String REGEX = "/";

    public SocketClient(String address, int port) throws IOException {
        this.socket = new Socket(address, port);
        startControllers(socket.getInputStream(), socket.getOutputStream());
    }

    private void startControllers(InputStream iStream, OutputStream oStream){
        this.mainController = new Controller();
        ClientBuffer clientBuffer = new ClientBuffer(this,iStream);
        PrintWriter printWriter = new PrintWriter(oStream, true);
        this.cardsController = new SocketCardsController(clientBuffer, REGEX, mainController, printWriter);
        this.setupController = new SocketSetupController(clientBuffer, REGEX, mainController,printWriter);
        this.tableController = new SocketTableController(mainController, toolController, clientBuffer, REGEX,printWriter);
        this.toolController = new SocketToolController(clientBuffer, REGEX,printWriter);
        this.turnController = new SocketTurnController(clientBuffer, REGEX, mainController, printWriter);
        new Thread(() -> {
            while(true) {
                clientBuffer.insertInBuffer();
            }
        }).start();
    }

    /**
     * {@link Controller} is the main controller for client's variables
     * @return main client Controller
     * @author Fabrizio Siciliano
     * */
    @Override
    public Controller getMainController() {
        return mainController;
    }

    /**
     * {@link CardsControllerInterface} handles client's to server requests regarding cards (public, private, schemes, etc...)
     * @return cards controller
     * @author Fabrizio Siciliano
     * */
    @Override
    public CardsControllerInterface getCardsController() {
        return cardsController;
    }

    /**
     * {@link SetupControllerInterface} handles client's to server requests regarding setup (login, joinGame, etc...)
     * @return main client Controller
     * @author Fabrizio Siciliano
     * */
    @Override
    public SetupControllerInterface getSetupController() {
        return setupController;
    }

    /**
     * {@link TableControllerInterface} handles client's to server requests regarding table status (draftPool, roundtrace, other players' AVL, etc...)
     * @return main client Controller
     * @author Fabrizio Siciliano
     * */
    @Override
    public TableControllerInterface getTableController() {
        return tableController;
    }

    /**
     * {@link ToolClientControllerInterface} handles client's to server requests regarding tools (move dice, use toolcard, etc...)
     * @return main client Controller
     * @author Fabrizio Siciliano
     * */
    @Override
    public ToolClientControllerInterface getToolController() {
        return toolController;
    }

    /**
     * {@link TurnControllerInterface} handles client's to server requests regarding turn status (end turn, start turn, etc...)
     * @return main client Controller
     * @author Fabrizio Siciliano
     * */
    @Override
    public TurnControllerInterface getTurnController() {
        return turnController;
    }
}
