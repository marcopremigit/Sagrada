package Shared;

import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.Schemes.Scheme;

import java.io.Serializable;

public class Player implements Serializable {
    private static final long serialVersionUID = 33;
    private String name;
    private Scheme scheme;
    private Color color;
    //in singlePlayer we have 2 privateObjective, in multiPlayer only one
    private PrivateObjective privateObjective1;
    private PrivateObjective privateObjective2;
    private int favours;

    //used in multiPlayer
    private int privateObjectivePoints;

    private int points;
    private volatile boolean endTurn;
    private volatile boolean Available;
    private boolean tenagliaARotelleUsed;
    //used in singlePlayer to calculate the points of the player
    private volatile String nameChosenPrivateObjective;
    //used in singlePlayer
    private int objectivePoint;

    /**
     * constructor
     * @param name the name of the player
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public Player(String name){
        this.name = name;
        this.scheme = null;
        this.privateObjective1 = null;
        this.privateObjective2 = null;
        favours = 0;
        points = 0;
        privateObjectivePoints = 0;
        endTurn = false;
        tenagliaARotelleUsed = false;
        nameChosenPrivateObjective = null;
        objectivePoint = 0;
    }

    /**
     * @param privateObjective1 the private Objcetive 1 to set
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public void setPrivateObjective1(PrivateObjective privateObjective1) {
        this.privateObjective1 = privateObjective1;
    }

    /**
     * @param privateObjective2 the private Objective 2 to set
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public void setPrivateObjective2(PrivateObjective privateObjective2) {
        this.privateObjective2 = privateObjective2;
    }

    /**
     * @param endTurn value to set to endTurn
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public void setEndTurn(boolean endTurn) {
        this.endTurn = endTurn;
    }

    /**
     * @return the value of end turn
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public boolean isEndTurn() {
        return this.endTurn;
    }

    /**
     * @return the player's scheme
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public Scheme getScheme() {
        return scheme;
    }

    /**
     * @return name of the player
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public String getName() {
        return name;
    }

    /**
     * @return the favours of the player
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public int getFavours() {
        return favours;
    }

    /**
     * @param scheme the scheme to set
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public void setScheme(Scheme scheme) {
        this.scheme = scheme;
    }

    /**
     * @param favours the favours to set
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public void setFavours(int favours) {
        this.favours = favours;
    }

    /**
     * @return privateObjective1
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public PrivateObjective getPrivateObjective1() {
        return privateObjective1;
    }

    /**
     * @return privateObjective2
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public PrivateObjective getPrivateObjective2() {
        return privateObjective2;
    }

    /**
     * @param points the points to set
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * @return the points of the player
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public int getPoints() {
        return points;
    }

    /**
     * @return privateObjective2
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public int getPrivateObjectivePoints() {
        return privateObjectivePoints;
    }

    /**
     * only for multi player
     * @param privateObjectivePoints the private Objective points to set
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public void setPrivateObjectivePoints(int privateObjectivePoints) {
        this.privateObjectivePoints = privateObjectivePoints;
    }

    public Color getColor(){
        return color;
    }

    public void setColor(Color color){
        this.color = color;
    }

    /**
     * @return if tenagliaARotelle was used or not
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public boolean isTenagliaARotelleUsed() {
        return tenagliaARotelleUsed;
    }

    /**
     * @param tenagliaARotelleUsed the value to set to tenagliaARotelleUsed
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public void setTenagliaARotelleUsed(boolean tenagliaARotelleUsed) {
        this.tenagliaARotelleUsed = tenagliaARotelleUsed;
    }

    /**
     * @param value the value to set to available
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public void setAvailable(boolean value){
        this.Available = value;
    }

    /**
     * @return if the player is available or not
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public boolean isAvailable() {
        return Available;
    }

    public String toString(){
        return "Player: "+name;
    }

    /**
     * only in single player
     * @param nameChosenPrivateObjective value to set to nameChosenPrivateObjective
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public void setNameChosenPrivateObjective(String nameChosenPrivateObjective) {
        this.nameChosenPrivateObjective = nameChosenPrivateObjective;
    }

    /**
     * only in single player
     * @return the value of nameChosenPrivateObjective
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public String getNameChosenPrivateObjective() {
        return nameChosenPrivateObjective;
    }

    /**
     * only single player
     * @return the objective points
     * @author Marco Premi, Fabrizio Siciliano,Abu Hussnain Saeed
     */
    public int getObjectivePoint() {
        return objectivePoint;
    }

    /**
     * only single player
     * @param objectivePoint the value to set to objectivePoint
     */
    public void setObjectivePoint(int objectivePoint) {
        this.objectivePoint = objectivePoint;
    }
}
