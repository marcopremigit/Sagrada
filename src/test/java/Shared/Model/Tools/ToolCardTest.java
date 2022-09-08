package Shared.Model.Tools;

import Shared.Color;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ToolCardTest {

    private ToolCard card;

    @Before
    public void setUp() throws Exception {
        card = new ToolCard("Pinza Sgrossatrice", "Dopo aver scelto un dado, aumenta o diminuisci il valore del dado scelto di 1. Non puoi cambiare un 6 in 1 o un 1 in 6", Color.PURPLE);
        System.out.println(card.toString());
    }

    @Test
    public void getName() {
        Assert.assertEquals("Pinza Sgrossatrice", card.getName());
    }

    @Test
    public void getDescription() {
        Assert.assertEquals("Dopo aver scelto un dado, aumenta o diminuisci il valore del dado scelto di 1. Non puoi cambiare un 6 in 1 o un 1 in 6", card.getDescription());
    }

    @Test
    public void getColor() {
        Assert.assertEquals(Color.PURPLE, card.getColor());
    }

    @Test
    public void isUsed() {
        Assert.assertFalse(card.isUsed());
    }

    /**
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromCardToJSON(){
        card = new ToolCard("test","obiettivo",Color.GREEN);
        JSONObject jsonObject = new JSONObject();
        jsonObject = ToolCard.fromCardToJSON(card);
        Assert.assertEquals("test",jsonObject.getString("name"));
        Assert.assertEquals("obiettivo", jsonObject.getString("description"));
        Assert.assertEquals("G",jsonObject.getString("color"));
        Assert.assertEquals(false, jsonObject.getBoolean("used"));
    }

    /**
     * @author Abu Hussnain Saeed
     */
    @Test
    public void fromJSONToCard(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name","test");
        jsonObject.put("description","obiettivo");
        jsonObject.put("color", "G");
        jsonObject.put("used", true);
        card = ToolCard.fromJSONToCard(jsonObject);
        Assert.assertEquals("test", card.getName());
        Assert.assertEquals("obiettivo", card.getDescription());
        Assert.assertEquals(Color.GREEN,card.getColor());
        Assert.assertEquals(true,card.isUsed());
    }
    @Test
    public void useCard() {
        card.useCard();
        Assert.assertTrue(card.isUsed());
    }

}