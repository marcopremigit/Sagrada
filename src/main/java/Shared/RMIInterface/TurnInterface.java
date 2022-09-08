package Shared.RMIInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TurnInterface extends Remote{
    /**
     * notifies server of client's ending turn
     * @param name name of the client
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void endMyTurn(String name) throws RemoteException;
    /**
     * requests server of game status
     * @return value of {@link Server.Lobby#gameEnded}
     * @author Fabrizio Siciliano, Marco Premi
     * */
    boolean isGameEnded() throws RemoteException;
    /**
     * requests server about round status
     * @return value of {@link Server.GameController#round}
     * @author Fabrizio Siciliano, Marco Premi
     * */
    int round() throws  RemoteException;
    /**
     * requests server about round clockwise direction
     * @return value of {@link Server.GameController#clockwise}
     * @author Fabrizio Siciliano, Marco Premi
     * */
    boolean isClockwise() throws RemoteException;
}