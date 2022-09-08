package Shared.Model.Schemes;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;

public class Scheme implements Serializable {
    private static final long serialVersionUID = 42;
    private String name;
    private int favors;
    private SchemeCell[][] scheme;

    /**
     * @param name name of the scheme
     * @param favors value of favors given by the scheme
     * @param scheme grid of the scheme
     * @see SchemeCell
     * @author Fabrizio Siciliano
     * */
    public Scheme(String name, int favors, SchemeCell[][] scheme){
        this.name = name;
        this.favors = favors;
        this.scheme = scheme;
    }

    /**
     * @return name of the scheme
     * @author Fabrizio Siciliano
     * */
    public String getName() {
        return name;
    }

    /**
     * @return favors of the scheme
     * @author Fabrizio Siciliano
     * */
    public int getFavors() {
        return favors;
    }

    /**
     * @return grid of the scheme
     * @author Fabrizio Siciliano
     * */
    public SchemeCell[][] getScheme() {
        return scheme;
    }

    /**
     * @param toCheck scheme to be checked
     * @return true if {@param toCheck} equals this
     * @author Fabrizio Siciliano
     * */
    public boolean equals(Scheme toCheck){
        if(this.name.equals(toCheck.name) && this.favors == toCheck.favors){
            for(int i=0; i<scheme.length; i++){
                for(int j=0; j<scheme[i].length; j++){
                    if(!this.scheme[i][j].equals(toCheck.getScheme()[i][j]))
                        return false;
                }
            }
            return true;
        }
        else
            return false;
    }

    /**
     * @return visual rappresentation of the scheme
     * @author Fabrizio Siciliano
     * */
    @Override
    public String toString(){
        return "Scheme named " + name + " gives you " + favors + " favors";
    }


    public static JSONObject fromSchemeToJSON(Scheme scheme){
        JSONObject object = new JSONObject();
        object.put("name", scheme.getName());
        object.put("favors", scheme.getFavors());
        JSONArray array = new JSONArray();
        for(int i=0; i<scheme.getScheme().length; i++){
            JSONArray rowArray = new JSONArray();
            for(int j=0; j<scheme.getScheme()[i].length; j++){
                rowArray.put(SchemeCell.fromSCellToJSON(scheme.getScheme()[i][j]));
            }
            array.put(rowArray);
        }
        object.put("scheme", array);
        return object;
    }
    public static Scheme fromJSONToScheme(JSONObject jsonObject){
        String name = jsonObject.getString("name");
        int favors = jsonObject.getInt("favors");
        JSONArray array = jsonObject.getJSONArray("scheme");
        SchemeCell[][] cells = new SchemeCell[array.length()][array.getJSONArray(0).length()];
        for(int i=0; i<array.length(); i++){
            JSONArray row = array.getJSONArray(i);
            for(int j=0; j<row.length(); j++){
                cells[i][j] = SchemeCell.fromJSONToSCell(row.getJSONObject(j));
            }
        }
        return new Scheme(name, favors, cells);
    }

    public boolean isWellFormed(){
        return this.scheme.length == 4 && this.scheme[0].length == 5;
    }
}
