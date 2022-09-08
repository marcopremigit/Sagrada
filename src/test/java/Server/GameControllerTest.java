package Server;

import Shared.Color;
import Shared.Exceptions.IllegalColorException;
import Shared.Exceptions.IllegalRoundException;
import Shared.Model.Dice.Dice;
import Shared.Model.ObjectiveCard.PrivateObjDeck;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.ObjectiveCard.PublicCards.*;
import Shared.Model.ObjectiveCard.PublicObjDeck;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.RoundTrace.RoundTraceCell;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import Shared.Model.Tools.ToolDeck;
import Shared.Player;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GameControllerTest {
    GameController gameControllerMulti;
    GameController gameControllerSingle;
    Game gameSingle;
    Game gameMulti;
    ArrayList<Player> players;

    /**
     * setup the two gameController, the two game and the players list with their scheme
     * @author Abu Hussnain Saeed
     */
    @Before
    public void setUp() {
        try {
            gameSingle = new Game();
            gameMulti = new Game();
            players = new ArrayList<>();

            //setup the scheme
            SchemeCell[][] schemeCell = new SchemeCell[4][5];
            Scheme scheme = null;

            for(int i=0; i<4;i++){
                for(int j=0; j<5;j++){
                    schemeCell[i][j]=new SchemeCell();
                }
            }

            Dice dice1 = new Dice(Color.YELLOW);
            dice1.setTop(1);
            Dice dice2 = new Dice(Color.GREEN);
            dice2.setTop(2);
            Dice dice3 = new Dice(Color.RED);
            dice3.setTop(5);
            Dice dice4 = new Dice(Color.BLUE);
            dice4.setTop(6);

            //6 dice 1; 4 dice 2; 4 dice 3; 6 dice 4
            schemeCell[0][0].setDado(dice1);
            schemeCell[0][1].setDado(dice2);
            schemeCell[0][2].setDado(dice4);
            schemeCell[0][3].setDado(dice2);
            schemeCell[0][4].setDado(dice4);
            schemeCell[1][0].setDado(dice1);
            schemeCell[1][1].setDado(dice3);
            schemeCell[1][2].setDado(dice1);
            schemeCell[1][3].setDado(dice4);
            schemeCell[1][4].setDado(dice3);
            schemeCell[2][0].setDado(dice2);
            schemeCell[2][1].setDado(dice4);
            schemeCell[2][2].setDado(dice1);
            schemeCell[2][3].setDado(dice3);
            schemeCell[2][4].setDado(dice1);
            schemeCell[3][0].setDado(dice4);
            schemeCell[3][1].setDado(dice3);
            schemeCell[3][2].setDado(dice2);
            schemeCell[3][3].setDado(dice1);
            schemeCell[3][4].setDado(dice4);

            scheme = new Scheme("test", 5, schemeCell);

            //setup the players
            players.add(new Player("player1"));
            players.get(0).setScheme(scheme);
            players.get(0).setFavours(5);
            players.add(new Player("player2"));
            players.add(new Player("player3"));

            PrivateObjective privateObjective = new PrivateObjective("Sfumature Rosse", "Somma dei valori su tutti i dadi rossi", Color.RED);
            players.get(0).setPrivateObjective1(privateObjective);

            //setup the gameSingle
            ArrayList<PublicObjective> publicDeck1 = new ArrayList<>();

            publicDeck1.add(new LightShades("Sfumature Chiare", "Set di 1 & 2 ovunque", 2));
            publicDeck1.add(new MediumShades("Sfumature Medie", "Set di 3 & 4 ovunque", 2));

            gameSingle.setPublicObjectives(publicDeck1);

            //setup the gameMulti
            ArrayList<PublicObjective> publicDeck = new ArrayList<>();

            publicDeck.add(new LightShades("Sfumature Chiare", "Set di 1 & 2 ovunque", 2));
            publicDeck.add(new MediumShades("Sfumature Medie", "Set di 3 & 4 ovunque", 2));
            publicDeck.add(new DarkShades("Sfumature Scure", "Set di 5 & 6 ovunque",2));

            gameMulti.setPublicObjectives(publicDeck);

            //setup gameController
            gameControllerMulti = new GameController(gameMulti);
            gameControllerSingle = new GameController(gameSingle);
        }
        catch(IllegalColorException i){ }
    }

    /**
     * check the correct calculation of the points for single player
     * @author Abu Hussnain Saeed
     */
    @Test
    public void calculatePointSingle(){
        Assert.assertEquals(12,gameControllerSingle.calculatePointSingle(players.get(0), players.get(0).getPrivateObjective1()));
    }

    /**
     * check the correct calculation of the points for multi player
     *@author Abu Hussnain Saeed
     */
    @Test
    public void calculatePointMulti(){
        Assert.assertEquals(25,gameControllerMulti.calculatePointMulti(players.get(0)));
    }

    /**
     * check the correct calculation of the objective points for single player
     * @author Abu Hussnain Saeed
     */
    @Test
    public void calculateObjectivePoints() {
        try {
            RoundTrace roundTrace = new RoundTrace();
            RoundTraceCell roundTraceCell1 = new RoundTraceCell();
            RoundTraceCell roundTraceCell2 = new RoundTraceCell();
            Dice dice1 = new Dice(Color.BLUE);
            dice1.setTop(1);
            Dice dice2 = new Dice(Color.BLUE);
            dice2.setTop(2);
            Dice dice3 = new Dice(Color.BLUE);
            dice3.setTop(3);
            ArrayList<Dice> round0 = new ArrayList<>();
            round0.add(dice1);
            round0.add(dice2);
            ArrayList<Dice> round1 = new ArrayList<>();
            round1.add(dice1);
            round1.add(dice2);
            round1.add(dice3);
            roundTraceCell1.addDicesToTrace(round0);
            roundTraceCell2.addDicesToTrace(round1);

            roundTrace.setPool(0, roundTraceCell1);
            roundTrace.setPool(1,roundTraceCell2);

            Assert.assertEquals(9,gameControllerSingle.calculateObjectivePoint(roundTrace));

        }
        catch (IllegalColorException i){}
        catch (IllegalRoundException r){}
    }

    /**
     * check the right order of the final ranking
     * @author Abu Hussnain Saeed
     */
    @Test
    public void orderRanking(){
        gameControllerMulti.setStarterPlayerPos(1);
        MainServer.setLobby(new Lobby("test"));
        MainServer.getLobby().setPlayers(players);

        //test 1
        players.get(0).setPoints(10);
        players.get(1).setPoints(15);
        players.get(2).setPoints(25);
        ArrayList<Player> temp = new ArrayList<>();
        temp.add(players.get(0));
        temp.add(players.get(1));
        temp.add(players.get(2));
        gameControllerMulti.orderRanking(temp);
        Assert.assertEquals("player3",gameMulti.getRanking().get(0));
        Assert.assertEquals("player2",gameMulti.getRanking().get(1));
        Assert.assertEquals("player1",gameMulti.getRanking().get(2));

        //test 2
        gameMulti.getRanking().remove(0);
        gameMulti.getRanking().remove(0);
        gameMulti.getRanking().remove(0);
        players.get(0).setPoints(15);
        players.get(1).setPoints(15);
        players.get(2).setPoints(25);
        players.get(0).setPrivateObjectivePoints(5);
        players.get(1).setPrivateObjectivePoints(3);
        temp.add(players.get(0));
        temp.add(players.get(1));
        temp.add(players.get(2));
        gameControllerMulti.orderRanking(temp);
        Assert.assertEquals("player3",gameMulti.getRanking().get(0));
        Assert.assertEquals("player1",gameMulti.getRanking().get(1));
        Assert.assertEquals("player2",gameMulti.getRanking().get(2));


        //third test
        gameMulti.getRanking().remove(0);
        gameMulti.getRanking().remove(0);
        gameMulti.getRanking().remove(0);
        players.get(0).setPoints(35);
        players.get(1).setPoints(15);
        players.get(2).setPoints(15);
        players.get(2).setPrivateObjectivePoints(5);
        players.get(1).setPrivateObjectivePoints(5);
        players.get(1).setFavours(4);
        players.get(2).setFavours(3);
        temp.add(players.get(0));
        temp.add(players.get(1));
        temp.add(players.get(2));
        gameControllerMulti.orderRanking(temp);
        Assert.assertEquals("player1",gameMulti.getRanking().get(0));
        Assert.assertEquals("player2",gameMulti.getRanking().get(1));
        Assert.assertEquals("player3",gameMulti.getRanking().get(2));

        //fourth test
        gameMulti.getRanking().remove(0);
        gameMulti.getRanking().remove(0);
        gameMulti.getRanking().remove(0);
        players.get(0).setPoints(15);
        players.get(1).setPoints(25);
        players.get(2).setPoints(15);
        players.get(2).setPrivateObjectivePoints(5);
        players.get(0).setPrivateObjectivePoints(5);
        players.get(1).setFavours(4);
        players.get(0).setFavours(4);

        temp.add(players.get(0));
        temp.add(players.get(1));
        temp.add(players.get(2));
        gameControllerMulti.orderRanking(temp);
        Assert.assertEquals("player2",gameMulti.getRanking().get(0));
        Assert.assertEquals("player1",gameMulti.getRanking().get(1));
        Assert.assertEquals("player3",gameMulti.getRanking().get(2));

        //fifth test
        gameMulti.getRanking().remove(0);
        gameMulti.getRanking().remove(0);
        gameMulti.getRanking().remove(0);
        players.get(0).setPoints(15);
        players.get(1).setPoints(15);
        players.get(2).setPoints(15);
        players.get(0).setPrivateObjectivePoints(5);
        players.get(1).setPrivateObjectivePoints(5);
        players.get(2).setPrivateObjectivePoints(5);
        players.get(2).setFavours(4);
        players.get(1).setFavours(4);
        players.get(0).setFavours(4);

        temp.add(players.get(0));
        temp.add(players.get(1));
        temp.add(players.get(2));
        gameControllerMulti.orderRanking(temp);
        Assert.assertEquals("player1",gameMulti.getRanking().get(0));
        Assert.assertEquals("player3",gameMulti.getRanking().get(1));
        Assert.assertEquals("player2",gameMulti.getRanking().get(2));

        //sixth test
        gameMulti.getRanking().remove(0);
        gameMulti.getRanking().remove(0);
        gameMulti.getRanking().remove(0);
        gameControllerMulti.setStarterPlayerPos(0);
        temp.add(players.get(0));
        temp.add(players.get(1));
        temp.add(players.get(2));
        gameControllerMulti.orderRanking(temp);
        Assert.assertEquals("player3",gameMulti.getRanking().get(0));
        Assert.assertEquals("player2",gameMulti.getRanking().get(1));
        Assert.assertEquals("player1",gameMulti.getRanking().get(2));

        //seventh test
        gameMulti.getRanking().remove(0);
        gameMulti.getRanking().remove(0);
        gameMulti.getRanking().remove(0);
        gameControllerMulti.setStarterPlayerPos(2);
        temp.add(players.get(0));
        temp.add(players.get(1));
        temp.add(players.get(2));
        gameControllerMulti.orderRanking(temp);
        Assert.assertEquals("player2",gameMulti.getRanking().get(0));
        Assert.assertEquals("player1",gameMulti.getRanking().get(1));
        Assert.assertEquals("player3",gameMulti.getRanking().get(2));

    }

    /**
     * check setStarterPlayerPos value
     * @author Abu Hussnain Saeed
     */
    @Test
    public void setStarterPlayerPos(){
        gameControllerMulti.setStarterPlayerPos(1);
        Assert.assertEquals(1, gameControllerMulti.getStarterPlayerPos());
    }

    /**
     * check clockwise value
     * @author Abu Hussnain Saeed
     */
    @Test
    public void isClockwise(){
        //in the constructor clockwise is setted to true
        Assert.assertEquals(true, gameControllerMulti.isClockwise());

    }

    /**
     * check round value
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getRound(){
        //in the constructor round is set to 0
        Assert.assertEquals(1, gameControllerMulti.getRound());
    }

}