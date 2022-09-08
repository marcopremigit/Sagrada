package Shared.RMIInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIClientInterface extends Remote {

    /**
     * pings client
     * @author Fabrizio Siciliano
     * */
    void ping() throws RemoteException;
    /**
     * send message about game started to client
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void startGame() throws RemoteException;
    /**
     * send message about turn started to client
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void notifyStartTurn() throws RemoteException;
    /**
     * send message about turn ended to client
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void notifyEndTurn() throws RemoteException;
    /**
     * send message about player not available to client
     * @param name name of the player not available
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void playerNotAvailable(String name) throws RemoteException;
    /**
     * send message about players ready to client
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void playersReady() throws RemoteException;
    /**
     * send message about available update to client
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void update() throws RemoteException;
    /**
     * send message about player available to client
     * @param name name of the player available
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void playerAgainAvailable(String name) throws RemoteException;
    /**
     * send message about continuing game to client
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void continuePlaying() throws RemoteException;
    /**
     * send message about game ended to client
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void gameEnded() throws RemoteException;
    /**
     * send message about computing ended to client
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void notifyEndedComputing() throws RemoteException;
}
