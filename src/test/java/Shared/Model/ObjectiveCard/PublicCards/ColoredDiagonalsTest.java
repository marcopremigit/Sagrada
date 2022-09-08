package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Model.Dice.Dice;
import Shared.Exceptions.IllegalColorException;
import Shared.Color;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ColoredDiagonalsTest {

    private ColoredDiagonals card;
    @Before
    public void setUp() throws Exception {
        card = new ColoredDiagonals("Diagonali Colorate", "Numero di dadi dello stesso colore diagonalmente adiacenti", 0);
        Assert.assertNotNull(card);
    }

    /**
     * check the correct function of Json to publicObjective card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromJSONtoPublic(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Diagonali Colorate");
        jsonObject.put("objective", "Test");
        jsonObject.put("points", 6);
        card = (ColoredDiagonals) PublicObjective.fromJSONtoPublic(jsonObject);
        Assert.assertEquals("Diagonali Colorate",card.getName());
        Assert.assertEquals("Test", card.getObjective());
        Assert.assertEquals(6,card.getVictoryPoints());
    }
    /**
     * check the correct function of publicObjective card to Json
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromPublicToJSON(){
        JSONObject jsonObject = ColoredDiagonals.fromPublicToJSON(card);
        Assert.assertEquals("Diagonali Colorate", jsonObject.getString("name"));
        Assert.assertEquals("Numero di dadi dello stesso colore diagonalmente adiacenti", jsonObject.getString("objective"));
        Assert.assertEquals(0,jsonObject.getInt("points"));
    }

    /**
     * calculate the point of the public card with the scheme
     * @author Abu Hussnain Saeed
     */
    @Test
    public void calculatePoints() {
        SchemeCell[][] schemeCell = new SchemeCell[4][5];

        for(int i=0; i<4;i++){
            for(int j=0; j<5;j++){
                schemeCell[i][j]=new SchemeCell();
            }
        }
        try {
            Dice dice1 = new Dice(Color.YELLOW);
            dice1.setTop(1);
            Dice dice2 = new Dice(Color.GREEN);
            dice2.setTop(2);
            Dice dice3 = new Dice(Color.RED);
            dice3.setTop(3);
            Dice dice4 = new Dice(Color.BLUE);
            dice4.setTop(4);
            Dice dice5 = new Dice(Color.PURPLE);
            dice5.setTop(5);
            schemeCell[0][0].setDado(dice5);
            schemeCell[0][1].setDado(dice2);
            schemeCell[0][2].setDado(dice2);
            schemeCell[0][3].setDado(dice3);
            schemeCell[0][4].setDado(dice1);
            schemeCell[1][0].setDado(dice1);
            schemeCell[1][1].setDado(dice5);
            schemeCell[1][2].setDado(dice3);
            schemeCell[1][3].setDado(dice3);
            schemeCell[1][4].setDado(dice1);
            schemeCell[2][0].setDado(dice1);
            schemeCell[2][1].setDado(dice3);
            schemeCell[2][2].setDado(dice5);
            schemeCell[2][3].setDado(dice3);
            schemeCell[2][4].setDado(dice1);
            schemeCell[3][0].setDado(dice1);
            schemeCell[3][1].setDado(dice1);
            schemeCell[3][2].setDado(dice1);
            schemeCell[3][3].setDado(dice3);
            schemeCell[3][4].setDado(dice1);
            Scheme scheme = new Scheme("test",5, schemeCell);
            Assert.assertEquals(10,card.calculatePoints(scheme));

            schemeCell[0][0].setDado(dice1);
            schemeCell[0][1].setDado(dice2);
            schemeCell[0][2].setDado(dice3);
            schemeCell[0][3].setDado(dice4);
            schemeCell[0][4].setDado(dice5);
            schemeCell[1][0].setDado(dice1);
            schemeCell[1][1].setDado(dice2);
            schemeCell[1][2].setDado(dice3);
            schemeCell[1][3].setDado(dice4);
            schemeCell[1][4].setDado(dice5);
            schemeCell[2][0].setDado(dice1);
            schemeCell[2][1].setDado(dice2);
            schemeCell[2][2].setDado(dice3);
            schemeCell[2][3].setDado(dice4);
            schemeCell[2][4].setDado(dice5);
            schemeCell[3][0].setDado(dice1);
            schemeCell[3][1].setDado(dice2);
            schemeCell[3][2].setDado(dice3);
            schemeCell[3][3].setDado(dice4);
            schemeCell[3][4].setDado(dice5);

            //we test the result if the scheme have all cell not occupied
            Scheme scheme2 = new Scheme("test2",5, schemeCell);
            Assert.assertEquals(0,card.calculatePoints(scheme2));
            SchemeCell[][] schemeCells = new SchemeCell[4][5];
            for(int i = 0; i<4; i++)
                for(int j = 0; j<5; j++)
                    schemeCells[i][j] = new SchemeCell();
            Scheme scheme3 = new Scheme("test3", 5, schemeCells);
            Assert.assertEquals(0,card.calculatePoints(scheme3));
        }
        catch(IllegalColorException e){/*never reached*/}
    }
}