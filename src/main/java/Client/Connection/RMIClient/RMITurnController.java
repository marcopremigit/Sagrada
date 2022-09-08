package Client.Connection.RMIClient;

import Client.View.ViewControllers.Controller;
import Client.Connection.TurnControllerInterface;
import Shared.RMIInterface.ConnectionInterface;
import Shared.RMIInterface.TurnInterface;

import java.rmi.RemoteException;

public class RMITurnController implements TurnControllerInterface {

    private TurnInterface turnController;
    private Controller controller;

    public RMITurnController(TurnInterface turnController, Controller controller, ConnectionInterface connectionController){
        this.controller = controller;
        this.turnController = turnController;
    }

    @Override
    public boolean isFirstTurn() {
        try{
            return turnController.isClockwise();
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
            return false;
        }
    }

    @Override
    public void endMyTurn() {
        try {
            turnController.endMyTurn(controller.getPlayerName());
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public String whatRoundIs() {
        try {
            return String.valueOf(turnController.round());
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
            return null;
        }
    }

    @Override
    public void startTurn() {
        controller.setMyTurn(true);
    }
}
