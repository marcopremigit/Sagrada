package Shared.Model.Schemes;

import Shared.Color;
import Shared.Model.Dice.Dice;
import org.json.JSONObject;

import java.io.Serializable;

public class  SchemeCell implements Serializable {
    private static final long serialVersionUID = 96;
    private Dice dado;
    private final int num;
    private final Color color;

    /**
     * @param num fixed value of the cell
     * @author Fabrizio Siciliano
     * */
    public SchemeCell(int num){
        this.dado = null;
        if(num>=1 && num<=6)
            this.num = num;
        else
            this.num = 0;
        this.color = Color.WHITE;
    }

    /**
     * @param color fixed value of the cell
     * @author Fabrizio Siciliano
     * */
    public SchemeCell(Color color){
        this.dado = null;
        this.num = 0;
        this.color = color;
    }

    /**
     * @author Fabrizio Siciliano
     * */
    public SchemeCell(){
        this.dado = null;
        this.num = 0;
        this.color = Color.WHITE;
    }


    /**
     * @return color value of the cell
     * @author Fabrizio Siciliano
     * */
    public Color getColor() {
        return color;
    }

    /**
     * @return num value of the cell
     * @author Fabrizio Siciliano
     * */
    public int getNum() {
        return num;
    }

    /**
     * @return dice of the cell
     * @author Fabrizio Siciliano
     * */
    public Dice getDado() {
        return dado;
    }

    /**
     * @param dado dice to be set in cell
     * @author Fabrizio Siciliano
     * */
    public void setDado(Dice dado) {
        this.dado = dado;
    }

    //returns and remove from the cell the dice on the cell

    /**
     * @return dice on the cell, after removing it
     * @author Fabrizio Siciliano
     * */
    public Dice removeDado(){
        Dice temp = this.dado;
        this.dado = null;
        return temp;
    }

    //boolean for checking if cell is occupied or not

    /**
     * @return true if dice in cell
     * @author Fabrizio Siciliano
     * */
    public boolean isOccupied(){
        return this.dado!=null;
    }

    /**
     * @param toCheck cell to be checked
     * @return true if {@param toCheck} equals this
     * @author Fabrizio Siciliano
     * */
    public boolean equals(SchemeCell toCheck){
        return this.num == toCheck.getNum() && this.color.equals(toCheck.getColor()) && ((this.dado == null && toCheck.getDado() == null) || (this.dado.equals(toCheck.getDado())));
    }

    /**
     * @return visual rappresentation of the cell
     * @author Fabrizio Siciliano
     * */
    @Override
    public String toString(){
        if(num == 0 && !color.equals(Color.WHITE)){
            return color.toString();
        }
        else if(num == 0 && color.equals(Color.WHITE)){
           return String.valueOf(num);
        }
        else{
           return String.valueOf(num);
        }
    }

    public static JSONObject fromSCellToJSON(SchemeCell cell){
        JSONObject object = new JSONObject();
        if(cell.getDado() != null)
            object.put("dice", Dice.fromDiceToJSON(cell.getDado()));

        object.put("num", cell.getNum());
        object.put("color", cell.getColor().toString());
        return object;
    }

    public static SchemeCell fromJSONToSCell(JSONObject object){
        int num = object.getInt("num");
        Color color = Color.stringToColor(object.getString("color"));
        SchemeCell cell;
        if(num==0)
            cell = new SchemeCell(color);
        else
            cell = new SchemeCell(num);

        if(object.has("dice")) {
            Dice dice = Dice.fromJSONToDice(object.getJSONObject("dice"));
            if (dice != null) {
                cell.setDado(dice);
            }
        }

        return cell;
    }
}
