package Shared.Model.ObjectiveCard;

import Shared.Model.Dice.Dice;
import Shared.Exceptions.IllegalColorException;
import Shared.Color;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PrivateObjectiveTest {

    private PrivateObjective card;

    @Before
    public void setUp() throws Exception {
        this.card = new PrivateObjective("Sfumature Rosse", "Somma dei valori su tutti i dadi rossi", Color.RED);

        Assert.assertNotNull(this.card);
        this.card.toString();
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
            schemeCell[1][4].setDado(new Dice(Color.YELLOW));
            schemeCell[2][0].setDado(new Dice(Color.BLUE));
            schemeCell[2][1].setDado(new Dice(Color.YELLOW));
            schemeCell[2][2].setDado(new Dice(Color.BLUE));
            schemeCell[2][3].setDado(new Dice(Color.YELLOW));
            schemeCell[2][4].setDado(new Dice(Color.BLUE));
            schemeCell[3][0].setDado(new Dice(Color.YELLOW));
            schemeCell[3][1].setDado(new Dice(Color.RED));
            schemeCell[3][2].setDado(new Dice(Color.YELLOW));
            schemeCell[3][3].setDado(new Dice(Color.RED));
            schemeCell[3][4].setDado(new Dice(Color.YELLOW));
        }
        catch(IllegalColorException e){/*never reached*/}
        Scheme schema = new Scheme("Proof", 5, schemeCell);


        Assert.assertEquals(4,card.calculatePoints(schema));
    }

    @Test
    public void equals() {
        PrivateObjective fakeEquals = new PrivateObjective("Sfumature Gialle", "Somma dei valori su tutti i dadi gialli", Color.RED);

        Assert.assertFalse(this.card.equals(fakeEquals));

        PrivateObjective trueEquals = new PrivateObjective("Sfumature Rosse", "Somma dei valori su tutti i dadi rossi", Color.RED);

        Assert.assertTrue(this.card.equals(trueEquals));
    }
    /**
     * check the correct function of Json to privateObjective card
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromJSONToPrivate(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "test");
        jsonObject.put("description", "nulla");
        jsonObject.put("color", "B");
        PrivateObjective privateObjective = PrivateObjective.fromJSONToPrivate(jsonObject);
        Assert.assertEquals("test", privateObjective.getName());
        Assert.assertEquals("nulla", privateObjective.getObjective());
        Assert.assertEquals(Color.BLUE,privateObjective.getColor());
    }

    /**
     * check the correct function of privateObjective card to Json
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromPrivateToJSON(){
        PrivateObjective privateObjective = new PrivateObjective("test", "nulla", Color.BLUE );
        JSONObject jsonObject = PrivateObjective.fromPrivateToJSON(privateObjective);
        Assert.assertEquals("test", jsonObject.getString("name"));
        Assert.assertEquals("nulla", jsonObject.getString("description"));
        Assert.assertEquals("B",jsonObject.getString("color"));


    }
    @Test
    public void getColor(){
        PrivateObjective privateObjective =new PrivateObjective("test", "testObj", Color.GREEN);
        Assert.assertEquals(Color.GREEN,privateObjective.getColor());

    }
}