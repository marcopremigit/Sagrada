package Shared.Model.ObjectiveCard;


import Shared.Model.ObjectiveCard.PublicCards.*;
import org.fusesource.jansi.Ansi;
import org.json.JSONObject;

import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

public abstract class PublicObjective extends ObjectiveCard implements Serializable {
    private static final long serialVersionUID = 43;
    protected  int victoryPoints;

    public PublicObjective(String name, String obj, int victoryPoints) {
        super(name, obj);
        this.victoryPoints = victoryPoints;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    @Override
    public String toString(){
        return (ansi().fg(Ansi.Color.CYAN).a("Carta pubblica: ").fg(Ansi.Color.DEFAULT).a(this.getName()).fg(Ansi.Color.CYAN).a("\nDescrizione: ").fg(Ansi.Color.CYAN).a(this.getObjective()).fg(Ansi.Color.CYAN).a("\nPunti Vittoria: ").fg(Ansi.Color.DEFAULT).a(this.victoryPoints).toString());
    }

    public boolean equals(PublicObjective toCheck) {
        return this.getName().equals(toCheck.getName())&&this.getObjective().equals(toCheck.getObjective())&&this.victoryPoints==toCheck.getVictoryPoints();
    }

    public static PublicObjective fromJSONtoPublic(JSONObject object){
        String name = object.getString("name");
        String objective = object.getString("objective");
        int points = object.getInt("points");
        switch (name){
            case "Colori diversi - Riga":
                return new DifferentColorsRow(name, objective, points);
            case "Colori diversi - Colonna":
                return new DifferentColorsColumn(name, objective, points);
            case "Sfumature diverse - Riga":
                return new DifferentShadesRow(name, objective, points);
            case "Sfumature diverse - Colonna":
                return new DifferentShadesColumn(name, objective, points);
            case "Sfumature Chiare":
                return new LightShades(name, objective, points);
            case "Sfumature Medie":
                return new MediumShades(name, objective, points);
            case "Sfumature Scure":
                return new DarkShades(name, objective, points);
            case "Sfumature diverse":
                return new DifferentShades(name, objective, points);
            case "Diagonali Colorate":
                return new ColoredDiagonals(name, objective, points);
            case "Varietà di Colore":
                return new ColorVariety(name, objective, points);
            default:
                return null;
        }
    }

    public static JSONObject fromPublicToJSON(PublicObjective objective){
        JSONObject object = new JSONObject();
        object.put("name", objective.getName());
        object.put("points", objective.getVictoryPoints());
        object.put("objective", objective.getObjective());
        return object;
    }
}
