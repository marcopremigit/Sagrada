package Server.RMIInterfaceImplementation;

import Shared.RMIInterface.PublicObjectiveInterface;
import Shared.Model.ObjectiveCard.PublicObjective;
import Server.MainServer;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class PublicObjectiveController extends UnicastRemoteObject implements PublicObjectiveInterface {
    public PublicObjectiveController() throws RemoteException {

    }

    @Override
    public ArrayList<PublicObjective> getPublicObjective() {
        return MainServer.getLobby().getGame().getPublicObjectives();
    }

}
