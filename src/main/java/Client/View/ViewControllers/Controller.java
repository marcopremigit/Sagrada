package Client.View.ViewControllers;

import Client.View.GUI.GUIPlayerHUD;


public class Controller {
    private volatile boolean usingCLI;
    private volatile boolean singlePlayer;
    private volatile boolean isMyTurn;
    private volatile boolean playersReady;
    private String playerName;
    private volatile boolean gameStarted;
    private volatile boolean gameEnded;
    private volatile boolean continueGame;
    private volatile boolean serverComputing;
    private volatile GUIPlayerHUD playerHUD;

    public Controller(){
        this.usingCLI           = false;
        this.serverComputing    = false;
        this.playersReady       = false;
        this.gameEnded          = false;
        this.singlePlayer       = false;
        this.isMyTurn           = false;
        this.playerName         = null;
        this.gameStarted        = false;
        this.continueGame       = false;
        this.playerHUD          = null;
    }

    /**
     * @param usingCLI sets using CLI or GUI usage
     * @author Fabrizio Siciliano
     * */
    public void setUsingCLI(boolean usingCLI) {
        this.usingCLI = usingCLI;
    }

    /**
     * @return CLI or GUI usage
     * @author Fabrizio Siciliano
     * */
    public boolean isUsingCLI() {
        return usingCLI;
    }
    /**
     * @param playerHUD value of {@link Controller#playerHUD} to be set
     * @author Fabrizio Siciliano
     * */
    public void setPlayerHUD(GUIPlayerHUD playerHUD) {
        this.playerHUD = playerHUD;
    }

    /**
     * @return client's player HUD
     * @see GUIPlayerHUD
     * @author Fabrizio Siciliano
     * */
    public GUIPlayerHUD getPlayerHUD(){
        return this.playerHUD;
    }

    /**
     * @param name name of the client
     * @author Fabrizio Siciliano
     * */
    public void insertUsername(String name){
        this.playerName = name;
    }

    /**
     * @return name of the client
     * @author Fabrizio Siciliano
     * */
    public String getPlayerName() {
        return playerName;
    }
    /**
     * @return value of {@link Controller#playersReady}
     * @author Fabrizio Siciliano
     * */
    public boolean arePlayersReady() {
        return playersReady;
    }
    /**
     * setter for {@link Controller#playersReady}
     * @param playersReady value to be set
     * @author Fabrizio Siciliano
     * */
    public void setPlayersReady(boolean playersReady) {
        this.playersReady = playersReady;
    }

    /**
     * @param value single or multi player mode
     * @author Fabrizio Siciliano
     * */
    public void insertSinglePlayer(boolean value){
        singlePlayer = value;
    }

    /**
     * @return single or multi player mode
     * @author Fabrizio Siciliano
     * */
    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    /**
     * @return value of the game started
     * @author Fabrizio Siciliano
     * */
    public boolean isGameStarted() {
        return gameStarted;
    }

    /**
     * @param gameStarted valued of the game started
     * @author Fabrizio Siciliano
     * */
    public void setGameStarted(boolean gameStarted) {
        this.gameStarted = gameStarted;
    }

    /**
     * @return boolean value for isMyTurn
     * @author Marco Premi
     */
    public boolean isMyTurn() {
        return isMyTurn;
    }

    /**
     * @param myTurn boolean value for setting the turn
     * @author Marco Premi
     */
    public void setMyTurn(boolean myTurn) {
        if(!usingCLI) {
            try{
                playerHUD.getButtonMoves().setTurnOnGoing(myTurn);
            }catch (NullPointerException e){
                new Thread(() -> {
                    do{
                        try{
                            playerHUD.getButtonMoves();
                            break;
                        }catch (NullPointerException r){}
                    }while(true);
                    playerHUD.getButtonMoves().setTurnOnGoing(myTurn);
                }).start();
            }

        }
        isMyTurn = myTurn;

    }
    /**
     * @return name of the client
     * @author Fabrizio Siciliano
     * */
    public boolean canContinueGame() {
        return continueGame;
    }
    /**
     * @param continueGame value of {@link Controller#continueGame} to be set
     * @author Fabrizio Siciliano
     * */
    public void setContinueGame(boolean continueGame) {
        this.continueGame = continueGame;
    }
    /**
     * @return value of {@link Controller#gameEnded}
     * @author Fabrizio Siciliano
     * */
    public boolean hasGameEnded(){
        return gameEnded;
    }
    /**
     * @param gameEnded value of {@link Controller#gameEnded} to be set
     * @author Fabrizio Siciliano
     * */
    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
    /**
     * @return value of {@link Controller#serverComputing}
     * @author Fabrizio Siciliano
     * */
    public boolean isServerComputing() {
        return serverComputing;
    }
    /**
     * @param value value of {@link Controller#serverComputing} to be set
     * @author Fabrizio Siciliano
     * */
    public void setServerComputing(boolean value){
        this.serverComputing = value;
    }
}
