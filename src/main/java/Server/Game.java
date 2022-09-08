package Server;

import Server.RMIInterfaceImplementation.ConnectionController;
import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.Dice.DiceBag;
import Shared.Model.ObjectiveCard.PrivateObjDeck;
import Shared.Model.ObjectiveCard.PublicObjDeck;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.Schemes.SchemeCard;
import Shared.Model.Schemes.SchemeCardDeck;
import Shared.Model.Tools.ToolCard;
import Shared.Model.Tools.ToolDeck;
import Shared.Player;

import java.rmi.RemoteException;
import java.util.ArrayList;


public class Game {
    private volatile ArrayList<String> ranking;

    private volatile ArrayList<Dice> draftPool;

    private volatile ArrayList<PublicObjective> publicObjectives;

    private volatile ArrayList<ToolCard> tools;

    private volatile RoundTrace roundTrace;

    private volatile DiceBag diceBag;

    private volatile ArrayList<SchemeCard> schemeCards;

    private volatile SchemeCardDeck schemeCardDeck;

    /**
     * constructor
     * @author Abu hussnain Saeed
     */
    public Game(){
        schemeCardDeck = new SchemeCardDeck();
        schemeCardDeck.buildDeck();
        publicObjectives = new ArrayList<>();
        tools = new ArrayList<>();
        draftPool = new ArrayList<>();
        roundTrace = new RoundTrace();
        diceBag = new DiceBag();
        schemeCards = new ArrayList<>();
        ranking = new ArrayList<>();
    }

    /**
     * get the diceBag
     * @return diceBag
     * @author Abu Hussnain Saeed
     */
    public DiceBag getDiceBag(){
        return diceBag;
    }

    /**
     * set the diceBag
     * @param diceBag the diceBag to set
     * @author Abu Hussnain Saeed
     */
    public void setDiceBag(DiceBag diceBag) {
        this.diceBag = diceBag;
    }

    /**
     * get list of publicObjectives
     * @return the arrayList of the publicObjectives
     * @author Abu Hussnain Saeed
     */
    public ArrayList<PublicObjective> getPublicObjectives() {
        return publicObjectives;
    }

    /**
     * set the publicObjectives
     * @param publicObjectives publicObjectives to set
     * @author Abu Hussnain Saeed
     */
    public void setPublicObjectives(ArrayList<PublicObjective> publicObjectives) {
        this.publicObjectives = publicObjectives;
    }

    /**
     * get tools
     * @return the arrayList of the tools
     * @author Abu Hussnain Saeed
     */
    public ArrayList<ToolCard> getTools(){
        return tools;
    }

    /**
     * set tools
     * @param tools the tool cards to set
     * @author Abu Hussnain Saeed
     */
    public void setTools(ArrayList<ToolCard> tools) {
        this.tools = tools;
    }

    /**
     * get draftPool
     * @return the draftPool
     * @author Abu Hussnain Saeed
     */
    public ArrayList<Dice> getDraftPool() {
        return draftPool;
    }

    /**
     * get roundTrace
     * @return the roundTrace
     * @author Abu Hussnain Saeed
     */
    public RoundTrace getTrace(){
        return roundTrace;
    }

    /**
     * notify every client that the game is started
     * @param players list of player that are playing the game
     * @author Abu Hussnain Saeed
     */
    private void startGame(ArrayList<Player> players){
        for(Player player : players){
            try{
                ServerClient client = ConnectionController.getClient(player.getName());
                client.getClient().startGame();
                player.setAvailable(true);
            } catch (RemoteException r){
                player.setAvailable(false);
            }
        }
    }

    /**
     * setup the game for multi players
     * @param players list of player that are playing the game
     * @author Abu Hussnain Saeed
     */
    public void setupMultiGame(ArrayList<Player> players){
        PrivateObjDeck privateObjDeck = new PrivateObjDeck();
        privateObjDeck.buildPrivate();
        privateObjDeck.shuffle();
        PublicObjDeck publicObjDeck = new PublicObjDeck();
        publicObjDeck.buildPublic();
        publicObjDeck.shuffle();
        publicObjectives.add(publicObjDeck.pick());
        publicObjectives.add(publicObjDeck.pick());
        publicObjectives.add(publicObjDeck.pick());
        ToolDeck toolDeck = new ToolDeck();
        toolDeck.shuffle();
        tools.add(toolDeck.pick());
        tools.add(toolDeck.pick());
        tools.add(toolDeck.pick());
        SchemeCardDeck schemeCardDeck = new SchemeCardDeck();
        schemeCardDeck.buildDeck();
        schemeCardDeck.shuffle();
        for (int i = 0; i<players.size(); i++) {
            players.get(i).setPrivateObjective1(privateObjDeck.pick());
            switch(i){
                case 0:
                    players.get(i).setColor(Color.RED);
                    break;
                case 1:
                    players.get(i).setColor(Color.BLUE);
                    break;
                case 2:
                    players.get(i).setColor(Color.GREEN);
                    break;
                case 3:
                    players.get(i).setColor(Color.PURPLE);
                    break;
                default:
                    break;
            }

            schemeCards.add(schemeCardDeck.pick());
            schemeCards.add(schemeCardDeck.pick());

        }
        startGame(players);
    }

    /**
     * setup the game for single player
     * @param player that are playing the game
     * @param difficult level of difficulty decided by the player
     * @author Abu Hussnain Saeed
     */
    public void setupSingleGame(Player player, int difficult){
        ArrayList<Player> players = new ArrayList<>();
        players.add(player);
        PrivateObjDeck privateObjDeck = new PrivateObjDeck();
        privateObjDeck.buildPrivate();
        privateObjDeck.shuffle();
        PublicObjDeck publicObjDeck = new PublicObjDeck();
        publicObjDeck.buildPublic();
        publicObjDeck.shuffle();
        publicObjectives.add(publicObjDeck.pick());
        publicObjectives.add(publicObjDeck.pick());
        ToolDeck toolDeck = new ToolDeck();
        toolDeck.shuffle();
        for(int i = 0; i<difficult; i++){
            tools.add(toolDeck.pick());
        }
        player.setPrivateObjective1(privateObjDeck.pick());
        player.setPrivateObjective2(privateObjDeck.pick());
        player.setColor(Color.RED);

        SchemeCardDeck schemeCardDeck = new SchemeCardDeck();
        schemeCardDeck.buildDeck();
        schemeCardDeck.shuffle();
        schemeCards.add(schemeCardDeck.pick());
        schemeCards.add(schemeCardDeck.pick());
        startGame(players);

    }

    /**
     * add the dices to roundTrace
     * @param dices to add to the roundTrace
     * @param round it indicates the round where the dices have to be added
     * @author Abu Hussnain Saeed
     */
    public void setRoundTrace(ArrayList<Dice> dices, int round){
        roundTrace.getTrace().get(round).addDicesToTrace(dices);
    }

    /**
     * set the draftPool
     * @param draftPool the draftPool to set
     * @author Abu Hussnain Saeed
     */
    public void setDraftPool(ArrayList<Dice> draftPool) {
        this.draftPool = draftPool;
    }

    /**
     * set the schemeCards
     * @param schemeCards list of schemeCard to set
     * @author Abu Hussnain Saeed
     */
    public void setSchemeCards(ArrayList<SchemeCard> schemeCards){
        this.schemeCards = schemeCards;
    }

    /**
     * get the schemeCards
     * @return the list of schemeCard
     */
    public ArrayList<SchemeCard> getSchemeCards(){
        return this.schemeCards;
    }

    public ArrayList<SchemeCard> getSchemeCardDeck(){
        return this.schemeCardDeck.getDeck();
    }

    /**
     * get the ranking
     * @return the ranking with the player's name
     * @author Abu Hussnain Saeed
     */
    public ArrayList<String> getRanking() {
        return ranking;
    }

    /**
     * set the ranking
     * @param ranking list of string, player's name, that have to set in the ranking
     * @author Abu Hussnain Saeed
     */
    public void setRanking(ArrayList<String> ranking) {
        this.ranking = ranking;
    }


}