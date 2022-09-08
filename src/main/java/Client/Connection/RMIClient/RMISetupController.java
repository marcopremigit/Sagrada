package Client.Connection.RMIClient;

import Client.View.ViewControllers.Controller;
import Client.Connection.SetupControllerInterface;
import Shared.Color;
import Shared.Exceptions.LobbyFullException;
import Shared.RMIInterface.ConnectionInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMISetupController implements SetupControllerInterface {

    private ConnectionInterface connectionController;
    private RMIClient client;
    private Controller clientController;

    public RMISetupController(RMIClient client, Controller clientController, ConnectionInterface
            connectionController){
        this.client = client;
        this.clientController = clientController;
        this.connectionController = connectionController;
    }

    @Override
    public boolean login() {
        try {
            //giving the RMIClient server a copy of this client
            return connectionController.login(clientController.getPlayerName(), client, true);
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
            return false;
        }
    }

    @Override
    public void pingServer() {
        try{
            connectionController.pingServer();
        }catch (RemoteException r){
            System.err.println("Errore di comunicazione con il server, chiudo il programma...");
            System.exit(42);
        }
    }

    @Override
    public boolean joinGame(boolean singleplayer) throws LobbyFullException {
        try{
            return connectionController.joinGame(clientController.getPlayerName(), singleplayer);
        }catch (RemoteException e){
            RMIClient.handleRMIDisconnection();
            return false;
        }
    }

    @Override
    public ArrayList<String> getRanking() {
        try {
            return connectionController.getRanking();
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
            return null;
        }
    }

    @Override
    public int getPoints(String name) {
        try {
            return connectionController.getPoints(name);
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
            return 0;
        }
    }

    @Override
    public Color getPlayerColor() {
        try {
            return connectionController.getPlayerColor(clientController.getPlayerName());
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
            return null;
        }
    }
    @Override
    public void setDifficulty(int difficulty){
        try {
            connectionController.setDifficulty(difficulty);
        }catch (RemoteException e){
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public int getObjectivePoints(){
        try{
            return connectionController.getObjectivePoints();
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
            return 0;
        }
    }
}
