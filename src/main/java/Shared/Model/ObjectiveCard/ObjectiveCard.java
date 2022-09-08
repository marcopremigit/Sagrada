package Shared.Model.ObjectiveCard;

import Shared.Model.Schemes.Scheme;

import java.io.Serializable;

public abstract class ObjectiveCard implements Serializable {

    private static final long serialVersionUID = 17;
    private String name;
    private String objective;

    /**
     * @param name name of the card
     * @param objective description of the card
     * @author Fabrizio Siciliano
     * */
    public ObjectiveCard(String name, String  objective){
        this.name = name;
        this.objective = objective;
    }

    /**
     * @return name of the card
     * @author Fabrizio Siciliano
     * */
    public String getName() {
        return name;
    }

    /**
     * @return description of the card
     * @author Fabrizio Siciliano
     * */
    public String getObjective() {
        return objective;
    }

    /**
     * @param schema scheme to be computed
     * @return points computed
     * @author Fabrizio Siciliano
     * */
    public abstract int calculatePoints(Scheme schema);


}
