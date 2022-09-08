package Server;

import Server.Configuration.ConfigurationLoader;
import Server.RMIInterfaceImplementation.ConnectionController;
import Shared.Exceptions.IllegalRoundException;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.Schemes.Scheme;
import Shared.Player;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class GameController {
    private Game game;
    private int round;
    private boolean clockwise;
    private int starterPlayerPos;
    private long turnTimer;
    private volatile int numberOfPlayersAvailable;

    /**
     * constructor
     * @param game it will be different for the singlePlayer and multiPlayer, it contain the game setup
     * @author Abu Hussnain Saeed
     */
    public GameController(Game game){
        round = 0;
        clockwise = true;
        starterPlayerPos = 0;
        this.game = game;
        try{
            ConfigurationLoader configurationLoader = new ConfigurationLoader();
            turnTimer = configurationLoader.getTurnTimer();
        }
        catch (IOException i){
            System.err.println("[Main Server]\tIOException in main\n" + i.getMessage());
        }
    }

    /**
     * here we divide the game in single or multi, and in the end calculate the points, and for the single player the objective point
     * @param players list of the player, if there is only one player in the list it means this is single player game
     * @author Abu Hussnain Saeed
     */
    public void playGame(ArrayList<Player> players){
        if(MainServer.getLobby().isGameEnded()){
            return;
        }
        numberOfPlayersAvailable = players.size();
        if(players.size() == 1){
            playSingleGame(players.get(0));
            if (players.get(0).isAvailable()) {
                ServerClient client = ConnectionController.getClient(players.get(0).getName());
                try {
                    client.getClient().gameEnded();
                } catch (RemoteException|NullPointerException e) {
                    try{
                        MainServer.getLobby().removePlayerFromLobby(players.get(0).getName());
                    }catch (NullPointerException n){
                        //if single player lobby goes null
                        players.get(0).setAvailable(false);
                    }
                    ConnectionController.removeClient(players.get(0).getName());
                }

            }
            if(players.get(0).isAvailable()) {
                while(players.get(0).getNameChosenPrivateObjective()==null){}
                if(players.get(0).getPrivateObjective1().getName().equals(players.get(0).getNameChosenPrivateObjective()))
                    players.get(0).setPoints(calculatePointSingle(players.get(0),players.get(0).getPrivateObjective1()));
                else
                    players.get(0).setPoints(calculatePointSingle(players.get(0),players.get(0).getPrivateObjective2()));
                players.get(0).setObjectivePoint(calculateObjectivePoint(game.getTrace()));
            }
        }
        else {
            ExecutorService es = Executors.newSingleThreadScheduledExecutor();
            ((ScheduledExecutorService) es).scheduleAtFixedRate(() -> {
                for(Player p : players) {
                    if (p.isAvailable()) {
                        ServerClient client = ConnectionController.getClient(p.getName());
                        try {
                            client.getClient().ping();
                        } catch (RemoteException r) {
                            MainServer.getLobby().removePlayerFromLobby(p.getName());
                            ConnectionController.removeClient(p.getName());
                            p.setAvailable(false);
                            numberOfPlayersAvailable--;
                            MainServer.getLobby().clientNotAvailable(p.getName());
                            if (numberOfPlayersAvailable <= 1)
                                es.shutdownNow();
                        }
                    }
                }
            }, 0, 500,TimeUnit.MILLISECONDS);
            playMultiGame(players);
            ArrayList<Player> temporaly = new ArrayList<>();
            for(Player player : players){
                if(player.isAvailable())
                    player.setPoints(calculatePointMulti(player));
                else
                    //if the player is disconneted he will get 0 point
                    player.setPoints(-99);
                temporaly.add(player);
            }
            orderRanking(temporaly);
        }
    }

    /**
     * only single player: we play the 10 rounds, and in every round 2 turns, and after single turn if the player is not available we end the game, at the start of the round we set the draftPool, in the end the roundTrace
     * @param player the player that play the game
     * @author Abu Hussnain Saeed
     */
    private void playSingleGame(Player player){
        numberOfPlayersAvailable = 1;
        for(round = 0; round<10; round++) {
            game.setDraftPool(game.getDiceBag().extractNDices(4));
            //first turn
            clockwise = true;
            playTurnSingle(player);
            if(numberOfPlayersAvailable == 0)
                return;
            //second turn
            clockwise = false;
            if(!player.isTenagliaARotelleUsed()) {
                playTurnSingle(player);
                if(numberOfPlayersAvailable == 0)
                    return;
            }
            //it mean that in the last turn this player have used this tool, so now he can't play this turn
            else
                player.setTenagliaARotelleUsed(false);
            game.setRoundTrace(game.getDraftPool(),round);
        }
    }

    /**
     * only multi player: we play the 10 rounds, at the start of the round we set the draftPool, in the end the roundTrace
     * @param players list of all players that will play the game
     * @author Abu Hussnain Saeed
     */
    private void playMultiGame(ArrayList<Player> players){
        while(round<10){
            game.setDraftPool(game.getDiceBag().extractNDices((players.size()*2)+1));
            if (starterPlayerPos==players.size())
                starterPlayerPos = 0;
            playRound(players, starterPlayerPos);
            System.out.flush();
            if(numberOfPlayersAvailable<=1)
                return;
            game.setRoundTrace(game.getDraftPool(),round);
            starterPlayerPos++;
            round++;
        }
        starterPlayerPos--;
    }

    /**
     * only multi player: here we play the single round and in every round 2 turns, and after single turn if there is only one player available we end the game
     * @param players list of the players that will play the round
     * @param i it indicate the round
     * @author Abu Hussnain Saeed
     */
    private void playRound(ArrayList<Player> players, int i){
        clockwise=true;
        for(int j = 0; j<players.size() - 1 ; j++){
            if(players.get(i).isAvailable()) {
                playTurn(players.get(i), players);
            }
            if(numberOfPlayersAvailable<=1)
                return;
            if(i<players.size()-1)
                i++;
            else
                i=0;
        }
        playTurn(players.get(i), players);
        if(numberOfPlayersAvailable<=1)
            return;
        clockwise = false;
        for(int j = 0; j<players.size(); j++){
            if(!players.get(i).isTenagliaARotelleUsed() && players.get(i).isAvailable()) {
                playTurn(players.get(i), players);
                if(numberOfPlayersAvailable<=1)
                    return;
            }
            //it mean that in the last turn this player have used this tool, so now he can't play this turn
            else
                players.get(i).setTenagliaARotelleUsed(false);
            if(i == 0)
                i = players.size() - 1;
            else
                i--;
        }
    }

    /**
     * only multi player: play the single turn, at the start of the turn if there is only one player available we end the game
     * @param player the player that have to play in this turn
     * @param players the list of players that play the game
     * @author Abu Hussnain Saeed
     */
    private void playTurn(Player player, ArrayList<Player> players){
        ServerClient client = ConnectionController.getClient(player.getName());
        long elapsedTime = 0L;
        long startTime;
        numberOfPlayersAvailable = checkPlayersAvailable(players);
        if(numberOfPlayersAvailable<=1)
            return;
        if(player.isAvailable()){
            try{
                client.getClient().notifyStartTurn();
                for(Player p : MainServer.getLobby().getPlayers()) {
                    if (p.isAvailable()) {
                            client.getClient().update();
                    }
                }
                startTime = System.currentTimeMillis();
                while(elapsedTime<turnTimer && player.isAvailable()){
                    if(player.isEndTurn()){
                        break;
                    }else{
                        elapsedTime=System.currentTimeMillis()-startTime;
                    }
                }
                if(player.isAvailable()) {
                    if (player.isEndTurn()) {
                        player.setEndTurn(false);
                    } else {
                        client.getClient().notifyEndTurn();
                    }
                }
            }catch (RemoteException e){
               MainServer.getLobby().removePlayerFromLobby(player.getName());
               ConnectionController.removeClient(player.getName());
                numberOfPlayersAvailable--;
                MainServer.getLobby().clientNotAvailable(player.getName());
            }
        }
    }

    /**
     * only single player: here we play the single turn, at the start of the turn if the player isn't available we will end the game
     * @param player player that will play the turn
     * @author Abu Hussnain Saeed
     */
    private void playTurnSingle(Player player){
        ServerClient client = ConnectionController.getClient(player.getName());
        long elapsedTime = 0L;
        long startTime;
        numberOfPlayersAvailable = checkSinglePlayerAvailabe(player);
        if(numberOfPlayersAvailable==0)
            return;
        try{
            client.getClient().notifyStartTurn();
            client.getClient().update();
            startTime = System.currentTimeMillis();
            ExecutorService es = Executors.newSingleThreadScheduledExecutor();
            ((ScheduledExecutorService) es).scheduleAtFixedRate(() -> {
                try{
                    client.getClient().ping();
                } catch (RemoteException r){
                    MainServer.getLobby().removePlayerFromLobby(player.getName());
                    player.setAvailable(false);
                    numberOfPlayersAvailable--;
                    es.shutdownNow();
                }
            }, 0, 500,TimeUnit.MILLISECONDS);
            while(elapsedTime<turnTimer){
                if(player.isEndTurn()){
                    break;
                }else if(numberOfPlayersAvailable==0){
                    return;
                }else{
                    elapsedTime=System.currentTimeMillis()-startTime;
                }
            }
            if(player.isEndTurn()){
                player.setEndTurn(false);
            }else{
                client.getClient().notifyEndTurn();
            }
        }catch (RemoteException  e){
            try {
                MainServer.getLobby().removePlayerFromLobby(player.getName());
            }catch (NullPointerException n){
                //if single player lobby goes null, so exception catched
            }
            ConnectionController.removeClient(player.getName());
            numberOfPlayersAvailable--;
        } catch (NullPointerException n){
        }
    }

    /**
     * only single player: here we calculate the points for the single player
     * @param player player that have played the game
     * @param chosen the chosen private objective card from the player
     * @return the points of the player
     * @author Abu Hussnain Saeed
     */
    public int calculatePointSingle(Player player, PrivateObjective chosen){
        int point = 0;
        Scheme schema = player.getScheme();
        if(!schema.isWellFormed())
            return -99;

        //public objective point
        for(PublicObjective publicObjective : game.getPublicObjectives()){
            point += publicObjective.calculatePoints(schema);
        }

        //private objective point
        int privateObjectivePoints = chosen.calculatePoints(schema);
        point += privateObjectivePoints;

        //blank cell in the scheme
        int blank = 0;
        for(int i=0; i<schema.getScheme().length; i++){
            for(int j=0; j<schema.getScheme()[i].length; j++){
                if(!schema.getScheme()[i][j].isOccupied())
                    blank++;
            }
        }

        //in single player we lose 3 point for every blank cell
        blank = blank * 3;
        point -= blank;

        return  point;
    }

    /**
     * only multi player: here we calculate the points for the single player in the game
     * @param player the player whose we calculate points
     * @return the points of the player
     * @author Abu Hussnain Saeed
     */
    public int calculatePointMulti(Player player){
        int point = 0;
        Scheme schema = player.getScheme();
        if(!schema.isWellFormed())
            return -99;
        //public objective point
        for(PublicObjective publicObjective : game.getPublicObjectives()){
            point += publicObjective.calculatePoints(schema);
        }
        //private objective point
        int privateObjectivePoints = player.getPrivateObjective1().calculatePoints(schema);
        point += privateObjectivePoints;
        player.setPrivateObjectivePoints(privateObjectivePoints);

        //favours
        point += player.getFavours();

        //blank cell in the scheme
        int blank = 0;
        for(int i=0; i<schema.getScheme().length; i++){
            for(int j=0; j<schema.getScheme()[i].length; j++){
                if(!schema.getScheme()[i][j].isOccupied())
                    blank++;
            }
        }
        point -= blank;
        return point;
    }

    /**
     * only single player: here we calculate the objective points for the player
     * @param roundTrace the round trace at the end of the game
     * @return the objectives point
     * @author Abu Hussnain Saeed
     */
    public int calculateObjectivePoint(RoundTrace roundTrace){
        int points = 0;
        try {
            for (int i = 0; i < roundTrace.getTrace().size(); i++) {
                for (int j = 0; j < roundTrace.getPool(i).size(); j++)
                    points += roundTrace.getPool(i).get(j).getTop();
            }
        }catch (IllegalRoundException i){}
        return  points;
    }

    /**
     * get the actual round
     * @return the round
     * @author Abu Hussnain Saeed
     */
    public int getRound() {
        return round + 1;
    }

    /**
     * get the clockwise
     * @return clockwise
     * @author Abu Hussnain Saeed
     */
    public boolean isClockwise() {
        return clockwise;
    }

    /**
     * only multi player: set the ordered ranking in the Game
     * @param players list of all player that have played the game
     * @author Abu Hussnain Saeed
     */
    public void orderRanking(ArrayList<Player> players){
        int max;
        int i;
        int toRemove = 0;
        int pos = 0;
        int size = players.size();
        for (int j = 0; j < size; j++){
            i = 0;
            max = -999;
            for (Player player : players) {
                if (player.getPoints() > max) {
                    max = player.getPoints();
                    pos = MainServer.getLobby().getPlayers().indexOf(player);
                    toRemove = i;
                }
                //if they have the same point we will look to their private objectve points
                else if (player.getPoints() == max){
                    if(player.getPrivateObjectivePoints() > players.get(toRemove).getPrivateObjectivePoints() ){
                        pos = MainServer.getLobby().getPlayers().indexOf(player);
                        toRemove = i;
                    }
                    //in the case that also the objective pounts are same we have to control the favours point
                    else if(player.getPrivateObjectivePoints() == players.get(toRemove).getPrivateObjectivePoints() ){
                        if(player.getFavours() > players.get(toRemove).getFavours()){
                            pos = MainServer.getLobby().getPlayers().indexOf(player);
                            toRemove = i;
                        }
                        //if also the favours points are same, the winner will who occupies the lowest position of the last round.
                        else if (player.getFavours() == players.get(toRemove).getFavours()){
                            int starterPos = starterPlayerPos;
                            for(int k = 0; k<size; k++ ){
                                if(pos == starterPos) {
                                    pos = MainServer.getLobby().getPlayers().indexOf(player);
                                    toRemove = i;
                                    break;
                                }
                                else if(MainServer.getLobby().getPlayers().indexOf(player) == starterPos)
                                    break;
                                if(starterPos == (size - 1))
                                    starterPos = 0;
                                else
                                    starterPos++;
                            }
                        }
                    }
                }
                i++;
            }
            game.getRanking().add(players.get(toRemove).getName());
            players.remove(toRemove);

        }
    }

    /**
     * only single player: check if the player is available
     * @param player the player that is playing the game
     * @return 0 if the player isn't available, 1 if it is available
     * @author Abu Hussnain Saeed
     */
    private int checkSinglePlayerAvailabe(Player player){
        try {
            if (player.isAvailable()) {
                ConnectionController.getClient(player.getName()).getClient().ping();
            }
        }catch (RemoteException r) {
            player.setAvailable(false);
            ConnectionController.removeClient(player.getName());
            return 0;
        }
        return 1;
    }

    /**
     * check the number of players available
     * @param players list of the player the game
     * @return the number of player available
     * @author Abu Hussnain Saeed
     */
    private int checkPlayersAvailable(ArrayList<Player> players){
        int available = 0;
        int pos = 0;
        for(Player player : MainServer.getLobby().getPlayers()) {
            try {
                if(player.isAvailable()) {
                    ConnectionController.getClient(player.getName()).getClient().ping();
                    if (!players.get(pos).isAvailable()) {
                        ConnectionController.getClient(player.getName()).getClient().playerAgainAvailable(player.getName());
                        players.get(pos).setAvailable(true);
                    }
                    available++;
                }
            } catch (RemoteException r) {
                MainServer.getLobby().removePlayerFromLobby(player.getName());
                ConnectionController.removeClient(player.getName());
                MainServer.getLobby().clientNotAvailable(player.getName());
            }
            pos++;
        }
        return available;
    }

    /**
     * used only for testing
     * @param starterPlayerPos value to set to starterPlayer
     * @author Abu Hussnain Saeed
     */
    public void setStarterPlayerPos(int starterPlayerPos) {
        this.starterPlayerPos = starterPlayerPos;
    }

    /**
     * used only for testing
     * @return the starterPlayerPos
     */
    public int getStarterPlayerPos() {
        return starterPlayerPos;
    }
}
