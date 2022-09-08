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

public class ColorVarietyTest {

    private ColorVariety card;
    @Before
    public void setUp() throws Exception {
        card = new ColorVariety("Varietà di Colore", "Set di dadi di ogni colore ovunque", 4);
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
            schemeCell[0][0].setDado(new Dice(Color.YELLOW));
            schemeCell[0][1].setDado(new Dice(Color.RED));
            schemeCell[0][2].setDado(new Dice(Color.YELLOW));
            schemeCell[0][3].setDado(new Dice(Color.GREEN));
            schemeCell[0][4].setDado(new Dice(Color.RED));
            schemeCell[1][0].setDado(new Dice(Color.GREEN));
            schemeCell[1][1].setDado(new Dice(Color.PURPLE));
            schemeCell[1][2].setDado(new Dice(Color.GREEN));
            schemeCell[1][3].setDado(new Dice(Color.BLUE));
            schemeCell[1][4].setDado(new Dice(Color.PURPLE));
            schemeCell[2][0].setDado(new Dice(Color.BLUE));
            schemeCell[2][1].setDado(new Dice(Color.YELLOW));
            schemeCell[2][2].setDado(new Dice(Color.BLUE));
            schemeCell[2][3].setDado(new Dice(Color.PURPLE));
            schemeCell[2][4].setDado(new Dice(Color.BLUE));
            schemeCell[3][0].setDado(new Dice(Color.YELLOW));
            schemeCell[3][1].setDado(new Dice(Color.RED));
            schemeCell[3][2].setDado(new Dice(Color.YELLOW));
            schemeCell[3][3].setDado(new Dice(Color.RED));
            schemeCell[3][4].setDado(new Dice(Color.YELLOW));
        }
        catch(IllegalColorException e){/*Never reached*/}
        Scheme schema = new Scheme("Proof", 5, schemeCell);

        Assert.assertEquals(12,card.calculatePoints(schema));
    }

    /**
     * check the correct function of Json to publicObjective card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromJSONtoPublic(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "Varietà di Colore");
        jsonObject.put("objective", "Test");
        jsonObject.put("points", 4);
        card= (ColorVariety) PublicObjective.fromJSONtoPublic(jsonObject);
        Assert.assertEquals("Varietà di Colore",card.getName());
        Assert.assertEquals("Test", card.getObjective());
        Assert.assertEquals(4,card.getVictoryPoints());
    }
    /**
     * check the correct function of publicObjective card to Json
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromPublicToJSON(){
        JSONObject jsonObject = ColorVariety.fromPublicToJSON(card);
        Assert.assertEquals("Varietà di Colore", jsonObject.getString("name"));
        Assert.assertEquals("Set di dadi di ogni colore ovunque", jsonObject.getString("objective"));
        Assert.assertEquals(4,jsonObject.getInt("points"));
    }
}