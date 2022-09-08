package Shared.RMIInterface;

import Shared.Color;
import Shared.Exceptions.LobbyFullException;
import Shared.Exceptions.ObjectNotFoundException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ConnectionInterface extends Remote {
    /**
     * login client on server
     * @param newName name of the client to be added to map
     * @param client client to be added to map
     * @param RMI connection type of the client
     * @return true if login is successful
     * @author Fabrizio Siciliano
     * */
    boolean login(String newName, RMIClientInterface client, boolean RMI) throws RemoteException;

    /**
     * @param difficult value of the difficulty of the single player game to be set
     * @author Fabrizio Siciliano
     * */
    void setDifficulty(int difficult) throws RemoteException;
    /**
     * removes client from {@link Server.RMIInterfaceImplementation.ConnectionController#allClients}
     * @param name name of the client to be removed
     * @author Fabrizio Siciliano
     * */
    void removePlayer(String name) throws ObjectNotFoundException, RemoteException;
    /**
     * pings server from client
     * @author Marco Premi
     * */
    void pingServer() throws RemoteException;
    /**
     * @param actualPlayerName name of the player requesting the other names
     * @return list of players' names
     * @author Marco Premi
     * */
    ArrayList<String> getOtherPlayerName(String actualPlayerName) throws  RemoteException;
    /**
     * gets color of the player given by the lobby
     * @param playerName name of the player
     * @return {@link Color} color of the player
     * @author Fabrizio Siciliano
     * */
    Color getPlayerColor(String playerName) throws RemoteException;
    /**
     * @param playerName name of the player joining
     * @author Fabrizio Siciliano
     * */
    boolean joinGame(String playerName, boolean singleplayer) throws LobbyFullException, RemoteException;
    /**
     * @return ranking of the game
     * @author Fabrizio Siciliano
     * */
    ArrayList<String> getRanking() throws RemoteException;
    /**
     * @param playerName name of the player
     * @return points of the player
     * @author Fabrizio Siciliano
     * */
    int getPoints(String playerName) throws RemoteException;
    /**
     * @return value of total points to be reached in order to win the single player game
     * @author Fabrizio Siciliano
     * */
    int getObjectivePoints() throws RemoteException;
}