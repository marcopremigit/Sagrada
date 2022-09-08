package Client.Connection;

import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.Schemes.Scheme;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface TableControllerInterface {
    /**
     * Shows all the schemes of the players in the multiplayer game CLI
     * @throws RemoteException
     * @author Marco Premi
     * */
    void showOtherPlayerSchemes() throws RemoteException;
    /**
     * @return draftpool ArrayList
     * @author Marco Premi, Fabrizio Siciliano
     * */
    ArrayList<Dice> getDraftPool();
    /**
     * @return round trace
     * @author Marco Premi, Fabrizio Siciliano
     * */
    RoundTrace getRoundTrace();
    /**
     * @return selected player's scheme
     * @param playerName selected player's name
     * @author Marco Premi, Fabrizio Siciliano
     * */
    Scheme getScheme(String playerName);
    /**
     * @return ArrayList of the players name in the multiplayer game
     * @author Marco Premi, Fabrizio Siciliano
     * */
    ArrayList<String> getOtherPlayersNames();
    /**
     * Notifies the players that the selected player is no longer avaible
     * @param name the selected player
     * @author Marco Premi, Fabrizio Siciliano
     * */
    void playerNotAvailable(String name);
    /**
     * Notifies the players that the selected player is avaible again
     * @param name the selected player
     * @author Marco Premi, Fabrizio Siciliano
     * */
    void playerAgainAvailable(String name);
}
