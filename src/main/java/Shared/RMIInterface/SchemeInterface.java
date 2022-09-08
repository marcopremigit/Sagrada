package Shared.RMIInterface;

import Shared.Exceptions.CannotSaveCardException;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCard;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SchemeInterface extends Remote{
    /**
     * @return {@link SchemeCard} for scheme choosing
     * @author Fabrizio Siciliano, Marco Premi
     * */
    SchemeCard getSchemeCard() throws RemoteException;
    /**
     * sets scheme of the player for the whole game
     * @param name name of the player
     * @param schemeName name of the {@link Scheme} chosen by player
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void setScheme(String name, String schemeName) throws RemoteException;
    /**
     * @param name name of the player
     * @return {@link Scheme} of the player with given name
     * @author Fabrizio Siciliano, Marco Premi
     * */
    Scheme getScheme(String name) throws RemoteException;
    /**
     * @param name name of the player
     * @return favors of the player with given name
     * @author Fabrizio Siciliano, Marco Premi
     * */
    int getPlayerFavours(String name) throws RemoteException;
    /**
     * saves card on server with given schemes, favors and names
     * @param names list of schemes' names
     * @param favors list of schemes' favors
     * @param scheme1 front scheme of the card
     * @param scheme2 rear scheme of the card
     * @param col list of schemes' columns
     * @param rows list of schemes' rows
     * @throws CannotSaveCardException when one of the names of the
     * @author Fabrizio Siciliano
     * */
    void saveCardOnServer(String[] names, String[] favors, String[] scheme1, String[] scheme2, String[] col, String[] rows) throws RemoteException, CannotSaveCardException;
}
