package Shared;

import java.io.Serializable;

public enum Color implements Serializable {
    RED("R"),
    YELLOW("Y"),
    PURPLE("P"),
    GREEN("G"),
    BLUE("B"),
    WHITE("W");

    private final String name;
    private static final long serialVersionUID = 24;

    /**
     * @param s naming enum
     * @author Fabrizio Siciliano
     * */
    Color(String s){
        name = s;
    }

    /**
     * @return color as char
     * @author Fabrizio Siciliano
     * */
    @Override
    public String toString(){
        return this.name;
    }

    /**
     * @param color string to convert
     * @return {@param color} as enum
     * @author Fabrizio Siciliano
     * */
    public static Color stringToColor (String color){
        switch (color){
            case "W": return Color.WHITE;

            case "B": return Color.BLUE;

            case "G": return Color.GREEN;

            case "P": return Color.PURPLE;

            case "Y": return Color.YELLOW;

            case "R": return Color.RED;

            default: return null;
        }
    }
}
