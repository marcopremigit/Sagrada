package Client.Connection;

import Shared.Exceptions.CannotSaveCardException;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.SchemeCard;

import java.util.ArrayList;

public interface CardsControllerInterface {
    /**
     * Asks the server the private objectives
     * @return ArrayList of private objectives
     * @author Marco Premi, Fabrizio Siciliano
     * */
    ArrayList<PrivateObjective> getPrivateObjective();
    /**
     * Asks the server the scheme card
     * @return the scheme card
     * @author Marco Premi, Fabrizio Siciliano
     * */
    SchemeCard getSchemeCard();
    /**
     * Asks the server the public objectives
     * @return ArrayList of public objectives
     * @author Marco Premi, Fabrizio Siciliano
     * */
    ArrayList<PublicObjective> getPublicObjectives();
    /**
     * Sets player's scheme on server
     * @param schemeName player's scheme name
     * @author Marco Premi, Fabrizio Siciliano
     * */
    void setScheme(String schemeName);
    /**
     * Asks the server the public objectives
     * @param names  the two names of the two schemes of the card
     * @param favors  the two favors of the two schemes of the card
     * @param scheme1 the first scheme
     * @param scheme2 the second scheme
     * @param col  the two columns of the two schemes of the card
     * @param rows  the two rows of the two schemes of the card
     * @author Fabrizio Siciliano, Marco Premi
     * */
    void saveCustomCard(String[] names, String[] favors, String[] scheme1, String[] scheme2, String[] col, String[] rows)throws CannotSaveCardException;
    /**
     * Sets the private objective selected in single players
     * @param name private objective name selected
     * @author Marco Premi, Fabrizio Siciliano
     * */
    void setPrivateObjective(String name);
}
