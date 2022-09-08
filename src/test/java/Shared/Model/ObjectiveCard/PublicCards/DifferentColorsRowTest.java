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

public class DifferentColorsRowTest {

    private DifferentColorsRow card;
    @Before
    public void setUp() throws Exception {
        card = new DifferentColorsRow("Colori diversi - Riga", "Righe senza colori ripetuti", 6);
        Assert.assertNotNull(card);
    }

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
            Dice dice2 = new Dice(Color.GREEN);
            Dice dice3 = new Dice(Color.RED);
            Dice dice4 = new Dice(Color.BLUE);
            Dice dice5 = new Dice(Color.PURPLE);

            schemeCell[0][0].setDado(dice1);
            schemeCell[0][1].setDado(dice2);
            schemeCell[0][2].setDado(dice3);
            schemeCell[0][3].setDado(dice5);
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

            Scheme scheme = new Scheme("test", 5, schemeCell);
            Assert.assertEquals(6, card.calculatePoints(scheme));

            //Test the case that every cell isn't occupied
            SchemeCell[][] schemeCells2 = new SchemeCell[4][5];

            for(int i=0; i<4;i++){
                for(int j=0; j<5;j++){
                    schemeCells2[i][j]=new SchemeCell();
                }
            }
            Scheme scheme1 = new Scheme("test2",5,schemeCells2);
            Assert.assertEquals(0,card.calculatePoints(scheme1));
        }
        catch (IllegalColorException e){/*never reached*/}
    }
    /**
     * check the correct function of Json to publicObjective card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromJSONtoPublic(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Colori diversi - Riga");
        jsonObject.put("objective", "Test");
        jsonObject.put("points", 6);
        card= (DifferentColorsRow) PublicObjective.fromJSONtoPublic(jsonObject);
        Assert.assertEquals("Colori diversi - Riga",card.getName());
        Assert.assertEquals("Test", card.getObjective());
        Assert.assertEquals(6,card.getVictoryPoints());
    }
    /**
     * check the correct function of publicObjective card to Json
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromPublicToJSON(){
        JSONObject jsonObject = DifferentColorsRow.fromPublicToJSON(card);
        Assert.assertEquals("Colori diversi - Riga", jsonObject.getString("name"));
        Assert.assertEquals("Righe senza colori ripetuti", jsonObject.getString("objective"));
        Assert.assertEquals(6,jsonObject.getInt("points"));
    }
}