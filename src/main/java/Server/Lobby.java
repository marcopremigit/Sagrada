package Server;

import Server.RMIInterfaceImplementation.ConnectionController;
import Shared.Exceptions.LobbyFullException;
import Shared.Exceptions.ObjectNotFoundException;
import Shared.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

public class Lobby extends Thread {

    private int difficulty;

    private ArrayList<Player> players;

    private String lobbyId;

    private Game game;

    private GameController gameController;

    private boolean singlePlayer;

    private boolean gameStarted;

    private boolean gameEnded;

    private static final int MAX_PLAYERS_LOBBY = 4;

    /**
     * @param lobbyId id of the lobby
     * @author Fabrizio Siciliano, Abu Hussnain Saeed
     * */
    public Lobby(String lobbyId){
        this.players        = new ArrayList<>();
        this.difficulty     = 0;
        this.lobbyId        = lobbyId;
        this.singlePlayer   = false;
        this.game           = null;
        this.gameStarted    = false;
        this.gameEnded      = false;
        this.gameController = null;
    }

    /**
     * start of the lobby thread, here we manage the start and end of the game
     * @author Abu Hussnain Saeed, Fabrizio Siciliano, Marco premi
     */
    @Override
    public void run() {
        if (!singlePlayer) {
            //start waiting timer
            long startTime = System.currentTimeMillis();
            long elapsedTime = 0L;
            do {
                System.out.flush();
                if (players.size() == MAX_PLAYERS_LOBBY)
                    break;
                else if (players.size() < 2) {
                    startTime = System.currentTimeMillis();
                }
                else if (players.size() >= 2)
                    elapsedTime = System.currentTimeMillis() - startTime;

                if(elapsedTime>=MainServer.getLobbyTimer()){
                    for(Player p : players){
                        try {
                            ServerClient client = ConnectionController.getClient(p.getName());
                            client.getClient().ping();
                        } catch (RemoteException r){
                            removePlayerFromLobby(p.getName());
                            ConnectionController.removeClient(p.getName());
                            if(players.size() < 2)
                                elapsedTime = 0L;
                        }
                    }
                }
            } while (elapsedTime < MainServer.getLobbyTimer());
        }
        //if we get out from the while it means that at least 2 players have joined the lobby
        game = new Game();
        if (!singlePlayer) {
            game.setupMultiGame(players);
        } else {
            while(difficulty==0){
                System.out.flush();
            }
            game.setupSingleGame(players.get(0), difficulty);
        }
        gameStarted = true;
        gameController = new GameController(game);
        System.out.print("[Server]\tStarting game with players: ");
        players.forEach(player -> System.out.print(player.getName() + "\t"));
        System.out.println();

        //multiplayer
        if (!singlePlayer) {
            waitPlayersSetup();
            if (players.size() <= 1) {
                playersReady();
                gameEnded = true;
                Iterator iterator = players.iterator();
                while(iterator.hasNext()) {
                    Player player = (Player) iterator.next();
                    if (player.isAvailable()) {
                        ServerClient client;
                        client = ConnectionController.getClient(player.getName());
                        try {
                            client.getClient().gameEnded();
                        } catch (RemoteException e) {
                            iterator.remove();
                            ConnectionController.removeClient(client.getUsername());
                        } catch (NullPointerException n){
                            //client already removed
                        }
                    }
                }
                gameController.playGame(players);
                System.out.println("[Server]\tGame has ended");
            } else {
                playersReady();
                gameController.playGame(players);
                gameEnded = true; Iterator iterator = players.iterator();
                while(iterator.hasNext()){
                    Player player = (Player) iterator.next();
                    if (player.isAvailable()) {
                        ServerClient client;
                        client = ConnectionController.getClient(player.getName());
                        try {
                            client.getClient().gameEnded();
                        } catch (RemoteException e) {
                            iterator.remove();
                            ConnectionController.removeClient(player.getName());
                        } catch (NullPointerException n){
                            //client already removed
                        }
                    }
                }
                System.out.println("[Server]\tGame has ended");
            }
        }
        else {
            //single player
            waitPlayersSetup();
            playersReady();
            gameController.playGame(players);
            gameEnded = true;
            if (players.get(0).isAvailable()) {
                ServerClient client = ConnectionController.getClient(players.get(0).getName());
                try {
                    client.getClient().notifyEndedComputing();
                } catch (RemoteException e) {
                    players.remove(players.get(0));
                    ConnectionController.removeClient(client.getUsername());
                } catch (NullPointerException n){
                    //client already removed
                }
            }
            System.out.println("[Server]\tGame has ended");

        }
        for(Player player : players) {
            ServerClient client;
            while ((client  = ConnectionController.getClient(player.getName())) != null) {
                System.out.flush();
                try {
                    client.getClient().ping();
                } catch (RemoteException r){
                    ConnectionController.removeClient(client.getUsername());
                    break;
                }
            }
        }
        endGame();
    }

    private void playersReady(){
        for(Player player : players){
            if(player.isAvailable()){
                ServerClient client;
                client  = ConnectionController.getClient(player.getName());
                try {
                    client.getClient().playersReady();
                }catch (RemoteException e){
                    ConnectionController.removeClient(client.getUsername());
                }
            }
        }
    }

    /**
     * here we manage the lobby after the end of the game, if there are players waiting for the end game they will be added to the lobby
     * @author Abu Hussnain Saeed
     */
    public void endGame(){
        MainServer.setLobby(null);
    }

    /**
     * @return waits for clients' scheme choice
     * @author Fabrizio Siciliano
     * */
    private void waitPlayersSetup(){
        ArrayList<Player> playersToRemove=new ArrayList<>();
        for(Player player : players){
            try{
                ServerClient client;
                while(player.getScheme()==null){
                    client  = ConnectionController.getClient(player.getName());
                    if(client==null){
                        playersToRemove.add(player);
                        break;
                    }
                    client.getClient().ping();
                    System.out.flush();
                }
            }catch (RemoteException r){
                playersToRemove.add(player);
            }

        }
        try{
            players.removeAll(playersToRemove);
        }catch (NullPointerException e){

        }

    }

    /**
     * @param name name of the player to be added
     * @exception LobbyFullException if lobby's size reaches maximum
     * @exception ObjectNotFoundException if player is not found in lobby
     * @author Fabrizio Siciliano
     * */
    public boolean addPlayerToLobby(String name) throws LobbyFullException {
        if(!gameStarted) {
            if (players.size() < MAX_PLAYERS_LOBBY) {
                players.add(new Player(name));
                return true;
            } else
                throw new LobbyFullException("La lobby è piena!");
        } else{
            for(Player player : players){
                if(player.getName().equals(name)){
                    player.setAvailable(true);
                    ConnectionController.playerAgainAvailable(name);
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * @param name name of the player to be removed
     * @exception ObjectNotFoundException if player is not found in lobby
     * @author Fabrizio Siciliano
     * */
    public void removePlayerFromLobby(String name) {
        for(Player player : players){
            if(player.getName().equals(name)) {
                if(!gameStarted){
                    players.remove(player);
                }
                else{
                    if(isSinglePlayer()){
                        MainServer.setLobby(null);
                    }else{
                        player.setAvailable(false);
                    }
                }
                System.out.println("[Server]\tRemoved player: " + name );
                return;
            }
        }
    }

    /**
     * send to all client the name of the player that have left the lobby
     * @param name of the player that have left the lobby
     * @author Abu Hussnain Saeed
     */
    public void clientNotAvailable(String name){
        for(Player player : players){
            try {
                if(player.isAvailable()) {
                    ServerClient client = ConnectionController.getClient(player.getName());
                    client.getClient().playerNotAvailable(name);
                }
            } catch (RemoteException r){
                MainServer.getLobby().removePlayerFromLobby(player.getName());
                ConnectionController.removeClient(player.getName());
            } catch (NullPointerException n){
                //client alread removed
            }
        }
    }

    public String getLobbyId(){
        return lobbyId;
    }

    /**
     * get the Game
     * @return Game
     * @author Abu Hussnain Saeed
     */
    public Game getGame() {
        return game;
    }

    /**
     * @param game the Game to set
     * @author Abu Hussnain Saeed
     */
    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    /**
     * @return GameController
     * @author Abu Hussnain Saeed
     */
    public GameController getGameController() {
        return gameController;
    }

    /**
     * used only for testing
     * @param gameController GameController to set
     * @author Abu Hussnain Saeed
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public ArrayList<Player> getPlayers(){
        return players;
    }

    /**
     * useful for testing
     * @param players list of the players to set
     * @author Abu Hussnain Saeed
     */
    public void setPlayers(ArrayList<Player> players){
        this.players = players;
    }

    /*--------------------------Single Player methods------------------------------*/

    public void setDifficulty(int difficult) {
        if(difficult>=1 && difficult<= 5)
            this.difficulty = difficult;
    }

    public boolean isSinglePlayer() {
        return singlePlayer;
    }

    public void setSinglePlayer(boolean singlePlayer) {
        this.singlePlayer = singlePlayer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder("Lobby" + lobbyId + ". Players: :\n\t");
        for(int i =0; i<players.size(); i++)
            builder.append(i +": " + players.get(i).getName() + "\n\t");
        return builder.toString();
    }
}

