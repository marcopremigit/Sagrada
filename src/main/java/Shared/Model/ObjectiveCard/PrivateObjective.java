package Shared.Model.ObjectiveCard;

import Shared.Color;
import Shared.Model.Schemes.Scheme;
import org.fusesource.jansi.Ansi;
import org.json.JSONObject;

import static org.fusesource.jansi.Ansi.ansi;

public class PrivateObjective extends ObjectiveCard {
    private  Color color;

    public PrivateObjective(String name, String objective, Color color){
        super(name, objective);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public int calculatePoints(Scheme schema) {
        int total=0;
        for(int i=0; i<schema.getScheme().length; i++){
            for(int j=0; j<schema.getScheme()[i].length; j++){
                if(schema.getScheme()[i][j].isOccupied()&&schema.getScheme()[i][j].getDado().getColor() == color)
                    total++;
            }
        }
        return total;
    }

    @Override
    public String toString(){
        return (ansi().fg(Ansi.Color.CYAN).a("Nome: ").fg(Ansi.Color.DEFAULT).a(this.getName()).fg(Ansi.Color.CYAN).a("\nDescrizione: ").fg(Ansi.Color.DEFAULT).a(this.getObjective()).fg(Ansi.Color.CYAN).a("\nColor: ").fg(Ansi.Color.DEFAULT).a(this.color.toString()).toString());
    }

    public boolean equals(PrivateObjective toCheck) {
        return this.color.equals(toCheck.color) && this.getName().equals(toCheck.getName()) && this.getObjective().equals(toCheck.getObjective());
    }

    public static PrivateObjective fromJSONToPrivate(JSONObject jsonObject){
        String name = jsonObject.getString("name");
        String objective = jsonObject.getString("description");
        Color color = Color.stringToColor(jsonObject.getString("color"));
        return new PrivateObjective(name, objective, color);
    }

    public static JSONObject fromPrivateToJSON(PrivateObjective privateObjective){
        JSONObject object = new JSONObject();
        object.put("name", privateObjective.getName());
        object.put("color", privateObjective.getColor().toString());
        object.put("description", privateObjective.getObjective());
        return object;
    }
}
