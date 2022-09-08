package Shared.Model.Schemes;

import org.json.JSONObject;

import java.io.Serializable;

public class SchemeCard implements Serializable {
    private static final long serialVersionUID = 57;
    private final Scheme front;
    private final Scheme rear;

    /**
     * @param front first scheme of the card
     * @param rear second scheme of the card
     * @see Scheme
     * @author Fabrizio Siciliano
     * */
    public SchemeCard(Scheme front, Scheme rear){
        this.front = front;
        this.rear = rear;
    }

    /**
     * @return first scheme of the card
     * @author Fabrizio Siciliano
     * */
    public Scheme getFront() {
        return front;
    }

    /**
     * @return second scheme of the card
     * @author Fabrizio Siciliano
     * */
    public Scheme getRear() {
        return rear;
    }

    /**
     * @param toCheck card to be checked
     * @return true if {@param toChek} equals this
     * @author Fabrizio Siciliano
     * */
    public boolean equals(SchemeCard toCheck){
        if(this.front!=null && this.rear != null)
            return this.front.equals(toCheck.getFront()) && this.rear.equals(toCheck.getRear());
        else
            if(this.front!=null && this.rear==null)
                return this.front.equals(toCheck.getFront()) && toCheck.getRear()==null;
        else
            if(this.front==null && this.rear!=null)
                return toCheck.getFront()==null && this.rear.equals(toCheck.getRear());
        else
            return toCheck.getFront()==null && toCheck.getRear()==null;
    }

    /**
     *
     * @author Fabrizio Siciliano
     * */
    @Override
    public String toString(){
        return this.front.toString() + this.rear.toString();
    }

    public static JSONObject fromCardToJSON(SchemeCard card){
        JSONObject object = new JSONObject();
        object.put("front", Scheme.fromSchemeToJSON(card.getFront()));
        object.put("rear", Scheme.fromSchemeToJSON(card.getRear()));
        return object;
    }

    public static SchemeCard fromJSONToCard(JSONObject object){
        Scheme front = Scheme.fromJSONToScheme(object.getJSONObject("front"));
        Scheme rear = Scheme.fromJSONToScheme(object.getJSONObject("rear"));
        return new SchemeCard(front, rear);
    }
}
