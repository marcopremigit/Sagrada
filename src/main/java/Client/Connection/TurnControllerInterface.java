package Client.Connection;

public interface TurnControllerInterface {
    /**
     * @return true if is the first turn
     * @author Marco Premi, Fabrizio Siciliano
     * */
    boolean isFirstTurn();
    /**
     * Notifies the server to end player turn
     * @author Marco Premi, Fabrizio Siciliano
     * */
    void endMyTurn();
    /**
     * @return the number of the round
     * @author Marco Premi, Fabrizio Siciliano
     * */
    String whatRoundIs();
    /**
     * Notifies the client to start the turn
     * @author Marco Premi, Fabrizio Siciliano
     * */
    void startTurn();
}
