package Shared.RMIInterface;

import Shared.Model.ObjectiveCard.PrivateObjective;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PrivateObjectiveInterface extends Remote {
    /**
     * @param name name of the player requesting first private objective (multi and single player)
     * @return {@link PrivateObjective} of the player
     * @author Abu Hussnain Saeed
     * */
    PrivateObjective getPrivateObjective1(String name) throws RemoteException;
    /**
     * @param name name of the player requesting second private objective (single player only)
     * @return {@link PrivateObjective} of the player
     * @author Abu Hussnain Saeed
     * */
    PrivateObjective getPrivateObjective2(String name) throws RemoteException;
    /**
     * @param name name of the private objective chosen from user at the end of single player game
     * @author Fabrizio Siciliano, Abu Hussnain Saeed
     * */
    void setPrivateObjective(String name) throws RemoteException;
}
