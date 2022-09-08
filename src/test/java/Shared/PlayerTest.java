package Shared;

import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

    Player player;
    Scheme scheme;
    SchemeCell[][] schemeCell= new SchemeCell[4][5];

    /**
     * set up the player and the scheme
     * @author Abu Hussnain Saeed
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        player = new Player("testPlayer");
        for(int i=0; i<4;i++){
            for(int j=0; j<5;j++){
                schemeCell[i][j]=new SchemeCell();
            }
        }
        scheme = new Scheme("test", 5, schemeCell);

    }

    /**
     * check if we get correctly the player name
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getName() {
        Assert.assertEquals("testPlayer", player.getName());
    }


    /**
     * check if we get correctly the player scheme
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getScheme() {
        player.setScheme(scheme);
        Assert.assertEquals(scheme, player.getScheme());
    }


    /**
     * check if we get correctly the player privateObjective1
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getPrivateObjective1() {
        PrivateObjective privateObjective = new PrivateObjective("testPrivateObjective", "objectiveString", Color.GREEN);
        player.setPrivateObjective1(privateObjective);
        Assert.assertEquals(privateObjective, player.getPrivateObjective1());
    }


    /**
     * check if we get correctly the player privateObjective2
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getPrivateObjective2() {
        PrivateObjective privateObjective = new PrivateObjective("testPrivateObjective2", "objectiveString2", Color.BLUE);
        player.setPrivateObjective2(privateObjective);
        Assert.assertEquals(privateObjective, player.getPrivateObjective2());
    }

    /**
     * check if we get correctly the player favours
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getFavours() {
        player.setFavours(5);
        Assert.assertEquals(5, player.getFavours());
    }


    /**
     * check if we get correctly the player total points
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getTotalPoints() {
        player.setPoints(10);
        Assert.assertEquals(10,player.getPoints());
    }


    /**
     * check if we get correctly the player publicObjective points
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getPrivateObjectivePoints() {
        player.setPrivateObjectivePoints(15);
        Assert.assertEquals(15, player.getPrivateObjectivePoints());
    }

    /**
     * check if we get correctly the player endTurn value
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getEndTurn() {
        player.setEndTurn(true);
        Assert.assertEquals(true,player.isEndTurn());

    }

    /**
     * check if we get correctly the player available value
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getAvailable(){
        player.setAvailable(true);
        Assert.assertEquals(true,player.isAvailable());
    }

    /**
     * check if we get correctly the player tenagliaARotelleUsed value
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getTenagliaARotelleUsed(){
        player.setTenagliaARotelleUsed(true);
        Assert.assertEquals(true,player.isTenagliaARotelleUsed());
    }

    /**
     * check if we get correctly the player color
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getColor(){
        player.setColor(Color.BLUE);
        Assert.assertEquals(Color.BLUE,player.getColor());
    }

    /**
     * check if we get correctly the player toString
     * @author Abu Hussnain Saeed
     */
    @Test
    public void toStringTest(){

        Assert.assertEquals("Player: testPlayer", player.toString());
    }

    /**
     * control the objectivePoints
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getObjectivePoints(){
        player.setObjectivePoint(14);
        Assert.assertEquals(14,player.getObjectivePoint());
    }
    /**
     * test NameChosenPrivateObjective
     * @author Abu Hussnain Saeed
     */
    @Test
    public void getNameChosenPrivateObjective(){
        player.setNameChosenPrivateObjective("testPrivate");
        Assert.assertEquals("testPrivate",player.getNameChosenPrivateObjective());
    }
}