package Client.Connection.RMIClient;

import Client.View.ViewControllers.Controller;
import Client.Connection.CardsControllerInterface;
import Shared.Exceptions.CannotSaveCardException;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.SchemeCard;
import Shared.RMIInterface.PrivateObjectiveInterface;
import Shared.RMIInterface.PublicObjectiveInterface;
import Shared.RMIInterface.SchemeInterface;

import java.rmi.RemoteException;
import java.util.ArrayList;

public class RMICardsController implements CardsControllerInterface {

    private PrivateObjectiveInterface privateObjectiveController;
    private PublicObjectiveInterface publicObjectiveController;
    private SchemeInterface schemeController;
    private Controller clientController;

    public RMICardsController(PrivateObjectiveInterface privateObjectiveController, Controller clientController, PublicObjectiveInterface publicObjectiveController, SchemeInterface schemeController){
        this.privateObjectiveController = privateObjectiveController;
        this.publicObjectiveController = publicObjectiveController;
        this.schemeController = schemeController;
        this.clientController = clientController;
    }


    @Override
    public ArrayList<PrivateObjective> getPrivateObjective() {
        ArrayList<PrivateObjective> privateObjectives = new ArrayList<>();
        PrivateObjective privateObjective2;
        try {
             privateObjectives.add(privateObjectiveController.getPrivateObjective1(clientController.getPlayerName()));
             privateObjective2 = privateObjectiveController.getPrivateObjective2(clientController.getPlayerName());
             if(privateObjective2!=null){
                 privateObjectives.add(privateObjective2);
             }
             return privateObjectives;
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
            return null;
        }
    }

    @Override
    public SchemeCard getSchemeCard() {
        try {
            return schemeController.getSchemeCard();
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
            return null;
        }
    }

    @Override
    public ArrayList<PublicObjective> getPublicObjectives() {
        try {
            return publicObjectiveController.getPublicObjective();
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
            return null;
        }
    }

    @Override
    public void setScheme(String schemeName) {
        try {
            schemeController.setScheme(clientController.getPlayerName(), schemeName);
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void saveCustomCard(String[] names, String[] favors, String[] scheme1, String[] scheme2, String[] col, String[] rows) throws CannotSaveCardException {
        try {
            schemeController.saveCardOnServer(names, favors, scheme1, scheme2, col, rows);
        } catch (RemoteException r) {
            RMIClient.handleRMIDisconnection();
        }
    }

    @Override
    public void setPrivateObjective(String name) {
        try {
            privateObjectiveController.setPrivateObjective(name);
        } catch (RemoteException r){
            RMIClient.handleRMIDisconnection();
        }
    }
}
