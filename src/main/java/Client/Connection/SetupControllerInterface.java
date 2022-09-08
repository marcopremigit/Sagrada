package Client.Connection;

import Shared.Color;
import Shared.Exceptions.LobbyFullException;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface SetupControllerInterface {
    /**
     * Player's login to match
     * @return true if login is correct
     * @author Fabrizio Siciliano, Marco Premi
     * */
    boolean login();
    /**
     * Ping server
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void pingServer();
    /**
     * Player join the game
     * @param singleplayer true if is a single player match
     * @return true if join game is correct
     * @author Fabrizio Siciliano, Marco Premi
     * */
    boolean joinGame(boolean singleplayer) throws LobbyFullException;
    /**
     * Asks the server the rankings
     * @return ArrayList of player ranking
     * @author Fabrizio Siciliano, Marco Premi
     * */
    ArrayList<String> getRanking();
    /**Asks the server the points of the selected player
     * @param name name of the selected player
     * @author Fabrizio Siciliano, Marco Premi
     * */
    int getPoints(String name);
    /**
     * @return player's color
     * @author Fabrizio Siciliano, Marco Premi
     * */
    Color getPlayerColor();
    /**
     * Sets the difficulty of the single player match
     * @param difficulty
     * @author Marco Premi, Fabrizio Siciliano
     * */
    void setDifficulty(int difficulty);
    /**
     * @return objective points for single player
     * @author Fabrizio Siciliano, Marco Premi
     * */
    int getObjectivePoints();
}
