package Shared.Model.Schemes;

import Shared.Color;
import Shared.Exceptions.IllegalColorException;
import Shared.Model.Dice.Dice;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SchemeCardTest {

    private SchemeCard card;

    @Before
    public void setUp() throws Exception {
        SchemeCell[][] frontCells = new SchemeCell[4][5];
        SchemeCell[][] rearCells = new SchemeCell[4][5];
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                frontCells[i][j]=new SchemeCell();
                rearCells[i][j]=new SchemeCell();
            }
        }
        card = new SchemeCard(new Scheme("LuxMondi",4,frontCells),new Scheme("LuxAstram", 5, rearCells));
        System.out.println(card.toString());
    }

    @Test
    public void getFront() {
        Assert.assertTrue(card.getFront().getName().equals("LuxMondi"));
        Assert.assertTrue(card.getFront().getFavors() == 4);
        Assert.assertNotNull(card.getFront());
    }

    @Test
    public void getRear() {
        Assert.assertNotNull(card.getRear());
        Assert.assertTrue(card.getRear().getFavors()==5);
        Assert.assertTrue(card.getRear().getName().equals("LuxAstram"));
    }

    @Test
    public void equals() {
        SchemeCell[][] fakeFrontCells = new SchemeCell[4][5];
        SchemeCell[][] fakeRearCells = new SchemeCell[4][5];
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                fakeFrontCells[i][j]=new SchemeCell();
                fakeRearCells[i][j]=new SchemeCell();
            }
        }
        SchemeCard fakeCard1 = new SchemeCard(new Scheme("LuxMondi", 4, fakeFrontCells), new Scheme("LuxAstram", 5, fakeRearCells));
        Assert.assertTrue(fakeCard1.equals(card));

        SchemeCard fakeCard2 = new SchemeCard(null, null);
        Assert.assertFalse(fakeCard2.equals(card));

        SchemeCard fakeCard3 = new SchemeCard(new Scheme("LuxMondi", 4, fakeFrontCells),null);
        Assert.assertFalse(fakeCard3.equals(card));

        SchemeCard fakeCard4 = new SchemeCard(null, new Scheme("LuxMondi", 4, fakeRearCells));
        Assert.assertFalse(fakeCard4.equals(card));
    }

    /**
     * check if we get correctly the schemeCard from Json
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromJSONToCard(){
        JSONObject jsonObject = new JSONObject();
        JSONObject jsonObject1 = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        JSONArray row = new JSONArray();
        JSONObject cell = new JSONObject();
        JSONObject dice = new JSONObject();
        dice.put("color","B");
        dice.put("top", 5);
        cell.put("dice",dice);
        cell.put("color", "B");
        cell.put("num", 0);


        row.put(cell);
        row.put(cell);
        row.put(cell);
        row.put(cell);
        row.put(cell);
        jsonArray.put(row);
        jsonArray.put(row);
        jsonArray.put(row);
        jsonArray.put(row);

        jsonObject.put("scheme", jsonArray);
        jsonObject.put("name", "scheme1");
        jsonObject.put("favors",4);

        jsonObject1.put("scheme", jsonArray);
        jsonObject1.put("name", "scheme2");
        jsonObject1.put("favors", 6);

        JSONObject main = new JSONObject();
        main.put("front", jsonObject);
        main.put("rear", jsonObject1);

        card = SchemeCard.fromJSONToCard(main);
        Assert.assertEquals("scheme1", card.getFront().getName());
        Assert.assertEquals(4, card.getFront().getFavors());
        Assert.assertEquals("scheme2", card.getRear().getName());
        Assert.assertEquals(6,card.getRear().getFavors());




    }

    /**
     * check if we get rightly the JsonObject from the schemeCard
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromCardToJSON(){
        try {
            SchemeCell[][] schemeCell = new SchemeCell[4][5];
            SchemeCell[][] schemeCell1 = new SchemeCell[4][5];
            Dice dice = new Dice(Color.GREEN);
            dice.setTop(6);
            for(int i=0; i<4;i++){
                for(int j=0; j<5;j++){
                    schemeCell[i][j]=new SchemeCell();
                    schemeCell[i][j].setDado(dice);
                }
            }
            schemeCell1 = schemeCell;
            Scheme scheme = new Scheme("front",5,schemeCell);
            Scheme scheme1 = new Scheme("rear", 6, schemeCell1);
            card = new SchemeCard(scheme,scheme1);
            JSONObject jsonObject = SchemeCard.fromCardToJSON(card);
            JSONObject front = jsonObject.getJSONObject("front");
            JSONObject rear = jsonObject.getJSONObject("rear");
            Assert.assertEquals("front", front.getString("name"));
            Assert.assertEquals(5,front.getInt("favors"));
            Assert.assertEquals("rear",rear.getString("name"));
            Assert.assertEquals(6,rear.getInt("favors"));

        }
        catch (IllegalColorException i){}
    }
}