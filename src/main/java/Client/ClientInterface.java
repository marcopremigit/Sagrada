package Client;

import Client.View.ViewControllers.Controller;
import Client.Connection.*;

public interface ClientInterface {

    /**
     * {@link Controller} is the main controller for client's variables
     * @return main client Controller
     * @author Fabrizio Siciliano
     * */
    Controller getMainController();

    /**
     * {@link CardsControllerInterface} handles client's to server requests regarding cards (public, private, schemes, etc...)
     * @return cards controller
     * @author Fabrizio Siciliano
     * */
    CardsControllerInterface getCardsController();

    /**
     * {@link SetupControllerInterface} handles client's to server requests regarding setup (login, joinGame, etc...)
     * @return main client Controller
     * @author Fabrizio Siciliano
     * */
    SetupControllerInterface getSetupController();

    /**
     * {@link TableControllerInterface} handles client's to server requests regarding table status (draftPool, roundtrace, other players' AVL, etc...)
     * @return main client Controller
     * @author Fabrizio Siciliano
     * */
    TableControllerInterface getTableController();

    /**
     * {@link ToolClientControllerInterface} handles client's to server requests regarding tools (move dice, use toolcard, etc...)
     * @return main client Controller
     * @author Fabrizio Siciliano
     * */
    ToolClientControllerInterface getToolController();

    /**
     * {@link TurnControllerInterface} handles client's to server requests regarding turn status (end turn, start turn, etc...)
     * @return main client Controller
     * @author Fabrizio Siciliano
     * */
    TurnControllerInterface getTurnController();
}
