package Client.Connection.RMIClient;

import Client.ClientInterface;
import Client.View.ViewControllers.Controller;
import Client.Connection.*;
import Client.Connection.ToolClientControllerInterface;
import Shared.RMIInterface.*;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RMIClient extends UnicastRemoteObject implements RMIClientInterface, Serializable, ClientInterface {
    private transient Controller mainController;
    private transient CardsControllerInterface cardsController;
    private transient SetupControllerInterface setupController;
    private transient TableControllerInterface tableController;
    private transient ToolClientControllerInterface toolController;
    private transient TurnControllerInterface turnController;

    public RMIClient(String address, int port) throws RemoteException{
        try {
            System.setProperty("java.rmi.server.hostname", address);
            Registry registry = LocateRegistry.getRegistry(address, port);
            downloadControllers(registry);
            ExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
            ((ScheduledExecutorService) executorService).scheduleAtFixedRate(() -> setupController.pingServer(), 0, 100, TimeUnit.MILLISECONDS);
        } catch (NotBoundException e) {
            System.err.println("Could not locate RMIClient registry, NotBoundException");
        }
    }

    @Override
    public void ping(){
        //pinged client
    }

    @Override
    public void startGame(){
        mainController.setGameStarted(true);
    }

    @Override
    public void notifyEndTurn(){
        mainController.setMyTurn(false);
        if(mainController.hasGameEnded()){
            mainController.getPlayerHUD().setGameEnded(true);
        }
    }

    @Override
    public void notifyStartTurn() {
        turnController.startTurn();
    }

    @Override
    public void update(){
        if(!mainController.isUsingCLI()) {
            try {
                mainController.getPlayerHUD().getDraftPool().updateDraftPoolBox(tableController.getDraftPool());
                mainController.getPlayerHUD().getRoundTrace().updateRoundTrace(tableController.getRoundTrace());
                mainController.getPlayerHUD().getPlayerFacade().updateFavors(toolController.getFavours(mainController.getPlayerName()));
            } catch (NullPointerException n){

            }
        }
    }


    @Override
    public void playerNotAvailable(String name) {
        tableController.playerNotAvailable(name);
    }

    @Override
    public void playersReady(){
        mainController.setPlayersReady(true);
    }

    @Override
    public void playerAgainAvailable(String name){
        tableController.playerAgainAvailable(name);
    }

    @Override
    public void continuePlaying(){
        mainController.setContinueGame(true);
    }

    @Override
    public void gameEnded(){
        mainController.setGameEnded(true);
    }

    @Override
    public void notifyEndedComputing() {
        mainController.setServerComputing(true);
    }

    /**
     * downloads all controllers from RMIClient registry
     * @param registry RMIClient registry of the server
     * @exception RemoteException if error with registry occurs
     * @exception NotBoundException if registry is not bound
     * @author Fabrizio Siciliano, Marco Premi
     * */
    private void downloadControllers(Registry registry) throws RemoteException, NotBoundException {
        Shared.RMIInterface.ConnectionInterface serverConnectionController = (Shared.RMIInterface.ConnectionInterface) registry.lookup("ConnectionController");
        DraftPoolInterface serverDraftPoolController = (DraftPoolInterface) registry.lookup("DraftPoolController");
        PublicObjectiveInterface serverPublicObjectiveController = (PublicObjectiveInterface) registry.lookup("PublicObjectiveController");
        PrivateObjectiveInterface serverPrivateObjectiveController = (PrivateObjectiveInterface)  registry.lookup("PrivateObjectiveController");
        SchemeInterface serverSchemeController = (SchemeInterface) registry.lookup("SchemeController");
        TurnInterface serverTurnController = (TurnInterface) registry.lookup("RMITurnController");
        Shared.RMIInterface.ToolControllerInterface serverToolController = (Shared.RMIInterface.ToolControllerInterface) registry.lookup("RMIToolClientController");
        RoundTraceInterface serverRoundTraceController = (RoundTraceInterface) registry.lookup("RoundTraceController");
        this.mainController = new Controller();
        this.cardsController = new RMICardsController(serverPrivateObjectiveController, mainController, serverPublicObjectiveController, serverSchemeController);
        this.setupController = new RMISetupController(this, mainController, serverConnectionController);
        this.toolController = new RMIToolClientController(serverToolController);
        this.tableController = new RMITableController(serverConnectionController, serverSchemeController, serverDraftPoolController, serverRoundTraceController, mainController);
        this.turnController = new RMITurnController(serverTurnController, mainController, serverConnectionController);
    }


    @Override
    public Controller getMainController() {
        return mainController;
    }

    @Override
    public CardsControllerInterface getCardsController() {
        return this.cardsController;
    }

    @Override
    public SetupControllerInterface getSetupController() {
        return this.setupController;
    }

    @Override
    public TableControllerInterface getTableController() {
        return this.tableController;
    }

    @Override
    public ToolClientControllerInterface getToolController() {
        return this.toolController;
    }

    @Override
    public TurnControllerInterface getTurnController() {
        return this.turnController;
    }

    static void handleRMIDisconnection(){
        System.out.println("Errore nella comunicazione con il server, chiudo il programma...");
        System.exit(3);
    }
}
