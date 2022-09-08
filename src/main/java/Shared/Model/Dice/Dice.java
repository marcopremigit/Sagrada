package Shared.Model.Dice;

import Shared.Color;
import Shared.Exceptions.IllegalColorException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Random;

public class Dice implements Serializable {
    private static final long serialVersionUID = 3;
    private  Color color; 

    private int top;

    /**
     * @param color gives the {@link Color} to the dice
     * @throws IllegalColorException if color equals to WHITE
     * @author Fabrizio Siciliano
     * */
    public Dice(Color color) throws IllegalColorException{
        if (color == Color.RED || color == Color.YELLOW || color == Color.GREEN || color == Color.PURPLE || color == Color.BLUE)
            this.color = color;
        else
            throw new IllegalColorException("Colore sbagliato");
        this.roll();
    }

    /**
     * @return color of the dice
     * @author Fabrizio Siciliano
     * */
    public /*@pure@*/ Color getColor() {
        return color;
    }


    /**
     * @return top of the dice
     * @author Fabrizio Siciliano
     * */
    public /*@pure*/ int getTop() {
        return this.top;
    }

    /**
     * @param top is the number to set to the dice
     * @author Fabrizio Siciliano
     * */
    public void setTop(int top){
        if(top>=1 && top<=6)
            this.top = top;
    }

    /**
     * sets top as random number between min and max
     * @author Fabrizio Siciliano
     * */
    public void roll() {
        int min = 1;
        int max = 6 - min;
        Random random = new Random();
        this.top = random.nextInt(max) + min;
    }

    /**
     * @param dado dice to be checked
     * @return true if {@param dado} equals this
     * @author Fabrizio Siciliano
     * */
    public boolean equals(Dice dado){
        return this.color.equals(dado.getColor()) && this.top == dado.getTop();
    }

    /**
     * @return visual rappresentation of the dice
     * @author Fabrizio Siciliano
     * */
    @Override
    public String toString(){
        return "Color: " + color + "\tTop: " + top;
    }

    public static Dice fromJSONToDice(JSONObject jsonObject){
        Dice dice = null;
        Color color = Color.stringToColor(jsonObject.getString("color"));
        int top = jsonObject.getInt("top");
        try {
            dice = new Dice(color);
            dice.setTop(top);
        } catch (IllegalColorException i) { }
        return dice;
    }

    public static JSONObject fromDiceToJSON(Dice dice){
        JSONObject object = null;
        if(dice!=null) {
            object = new JSONObject();
            object.put("color", dice.getColor().toString());
            object.put("top", dice.getTop());
        }
        return object;
    }
}