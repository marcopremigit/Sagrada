package Shared.Model.Tools;

import Shared.Color;
import org.json.JSONObject;

import javax.tools.Tool;
import java.io.Serializable;

public class ToolCard implements Serializable {
    private static final long serialVersionUID = 92;
    private String name;
    private String description;
    private Color color;
    private boolean used;

    public ToolCard(String name, String description, Color color){
        this.name = name;
        this.description = description;
        this.color = color;
        this.used = false;
    }
    public /*@pure@*/ String getName() {
        return name;
    }

    public /*@pure@*/ String getDescription() {
        return description;
    }

    public /*@pure@*/ Color getColor() {
        return color;
    }

    public boolean /*@pure@*/ isUsed() {
        return used;
    }

    public void useCard(){
        this.used = true;
    }

    public static JSONObject fromCardToJSON(ToolCard card){
        JSONObject obj = new JSONObject();
        obj.put("name", card.getName());
        obj.put("description", card.getDescription());
        obj.put("color", card.getColor().toString());
        obj.put("used", card.isUsed());
        return obj;
    }

    public static ToolCard fromJSONToCard(JSONObject obj){
        ToolCard card = new ToolCard(obj.getString("name"), obj.getString("description"), Color.stringToColor(obj.getString("color")));
        if((boolean) obj.get("used"))
            card.useCard();
        return card;
    }

    @Override
    public String toString(){
        return "Nome: " + name + "\nDescrizione: " + description + "\nColore: " + color + "\nE' stata usata? " + used;
    }
}
