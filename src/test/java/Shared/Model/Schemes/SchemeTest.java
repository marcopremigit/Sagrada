package Shared.Model.Schemes;

import Shared.Color;
import Shared.Exceptions.IllegalColorException;
import Shared.Model.Dice.Dice;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SchemeTest {

    private Scheme scheme;
    @Before
    public void setUp() throws Exception {
        SchemeCell[][] schemeCells = new SchemeCell[4][5];
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                schemeCells[i][j]=new SchemeCell(i);
            }
        }
        scheme = new Scheme("Lux Mondi", 4, schemeCells);
    }

    @Test
    public void getName() {
        Assert.assertEquals("Lux Mondi", scheme.getName());
    }

    @Test
    public void getFavors() {
        Assert.assertEquals(4, scheme.getFavors());
    }

    @Test
    public void getScheme() {
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                Assert.assertEquals(i, scheme.getScheme()[i][j].getNum());
                Assert.assertNull(scheme.getScheme()[i][j].getDado());
                Assert.assertTrue(scheme.getScheme()[i][j].getColor().equals(Color.WHITE));
            }
        }
    }

    @Test
    public void equals() {
        SchemeCell[][] fakeCells = new SchemeCell[4][5];
        for (int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                fakeCells[i][j] = new SchemeCell();
            }
        }
        Scheme fakeScheme = new Scheme("Lux Mondi", 4, fakeCells);
        Assert.assertFalse(scheme.equals(fakeScheme));

        Scheme fakeScheme2 = new Scheme("Pippo" , 5, fakeCells);
        Assert.assertFalse(scheme.equals(fakeScheme2));

        SchemeCell[][] trueCells = new SchemeCell[4][5];
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                trueCells[i][j]=new SchemeCell(i);
            }
        }

        Scheme trueScheme = new Scheme("Lux Mondi" , 4, trueCells);
        Assert.assertTrue(this.scheme.equals(trueScheme));
    }

    /**
     * check if we get correctly the scheme from Json
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromJSONToScheme(){
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        JSONArray row = new JSONArray();
        JSONArray row1 = new JSONArray();
        JSONObject cell = new JSONObject();
        JSONObject cell1 = new JSONObject();
        JSONObject dice = new JSONObject();
        dice.put("color","B");
        dice.put("top", 5);
        cell.put("dice",dice);
        cell.put("color", "B");
        cell.put("num", 0);

        cell1.put("dice",dice);
        cell1.put("color","W");
        cell1.put("num",5);

        row1.put(cell1);
        row1.put(cell);
        row1.put(cell);
        row1.put(cell);
        row1.put(cell);

        row.put(cell);
        row.put(cell);
        row.put(cell);
        row.put(cell);
        row.put(cell);

        jsonArray.put(row1);
        jsonArray.put(row);
        jsonArray.put(row);
        jsonArray.put(row);
        jsonObject.put("scheme", jsonArray);
        jsonObject.put("name", "scheme");
        jsonObject.put("favors",5);

        scheme = Scheme.fromJSONToScheme(jsonObject);
        for(int i = 0; i < scheme.getScheme().length; i++){
            for(int j = 0; j < scheme.getScheme()[i].length; j++) {
                if (i == 0 && j == 0) {
                    Assert.assertEquals(Color.BLUE, scheme.getScheme()[i][j].getDado().getColor());
                    Assert.assertEquals(5, scheme.getScheme()[i][j].getDado().getTop());
                    Assert.assertEquals(5, scheme.getScheme()[i][j].getNum());

                } else {
                    Assert.assertEquals(Color.BLUE, scheme.getScheme()[i][j].getDado().getColor());
                    Assert.assertEquals(5, scheme.getScheme()[i][j].getDado().getTop());
                    Assert.assertEquals(Color.BLUE, scheme.getScheme()[i][j].getColor());

                }
            }
        }

    }

    /**
     * check if we get rightly the JsonObject from the scheme
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromSchemeToJSON() {
        try {
            SchemeCell[][] schemeCell = new SchemeCell[4][5];
            Dice dice = new Dice(Color.GREEN);
            dice.setTop(6);
            for(int i=0; i<4;i++){
                for(int j=0; j<5;j++){
                    schemeCell[i][j]=new SchemeCell();
                    schemeCell[i][j].setDado(dice);
                }
            }
            scheme = new Scheme("test",5,schemeCell);
            JSONObject jsonObject = Scheme.fromSchemeToJSON(scheme);
            JSONArray array = jsonObject.getJSONArray("scheme");
            for(int i = 0; i < array.length(); i++){
                for(int j = 0; j < array.getJSONArray(i).length(); j++){
                    Assert.assertEquals("G", array.getJSONArray(i).getJSONObject(j).getJSONObject("dice").getString("color"));
                    Assert.assertEquals(6,array.getJSONArray(i).getJSONObject(j).getJSONObject("dice").getInt("top"));
                    Assert.assertEquals("W",array.getJSONArray(i).getJSONObject(j).getString("color"));
                }
            }

        }
        catch (IllegalColorException i){}
    }
}