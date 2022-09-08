package Client.Connection.RMIClient;

import Client.View.ViewControllers.Controller;
import Client.Connection.TableControllerInterface;
import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.Schemes.Scheme;
import Shared.RMIInterface.ConnectionInterface;
import Shared.RMIInterface.DraftPoolInterface;
import Shared.RMIInterface.RoundTraceInterface;
import Shared.RMIInterface.SchemeInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static Client.View.CLI.CLIHUDItems.CLIScheme.showFavorsAndName;
import static Client.View.CLI.CLIHUDItems.CLIScheme.showScheme;

public class RMITableController implements TableControllerInterface {

    private SchemeInterface schemeController;
    private DraftPoolInterface draftPoolController;
    private RoundTraceInterface roundTraceController;
    private ConnectionInterface connectionController;
    private Controller clientController;

    public RMITableController(ConnectionInterface connectionController, SchemeInterface schemeController, DraftPoolInterface draftPoolController, RoundTraceInterface roundTraceController, Controller clientController){
        this.connectionController = connectionController;
        this.schemeController = schemeController;
        this.draftPoolController = draftPoolController;
        this.roundTraceController = roundTraceController;
        this.clientController = clientController;
    }

    @Override
    public void showOtherPlayerSchemes() throws RemoteException {
        ArrayList<String> players = connectionController.getOtherPlayerName(clientController.getPlayerName());
        for(String player : players){
            showFavorsAndName(player, schemeController.getPlayerFavours(player));
            showScheme(schemeController.getScheme(player));
        }
    }

    @Override
    public ArrayList<Dice> getDraftPool() {
        try {
            return draftPoolController.getDraftPool();
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
            return null;
        }
    }

    @Override
    public RoundTrace getRoundTrace() {
        try {
            return roundTraceController.getRoundTrace();
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
            return null;
        }
    }

    @Override
    public Scheme getScheme(String playerName) {
        try{
            return schemeController.getScheme(playerName);
        }catch (RemoteException e){
            RMIClient.handleRMIDisconnection();
            return null;
        }
    }

    @Override
    public ArrayList<String> getOtherPlayersNames() {
        try {
            return connectionController.getOtherPlayerName(clientController.getPlayerName());
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
            return null;
        }
    }

    @Override
    public void playerNotAvailable(String name) {
        if(clientController.isUsingCLI())
            System.out.println("Player " + name + " has disconnected");
        else
            clientController.getPlayerHUD().getPlayersSchemes().playerNotAvailable(name);
    }

    @Override
    public void playerAgainAvailable(String name) {
        if(clientController.isUsingCLI())
            System.out.println("Player " + name + " has reconnected");
        else
            clientController.getPlayerHUD().getPlayersSchemes().playerAvailable(name);
    }
}
