package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;

public class LightShades extends PublicObjective {
    /**
     * constructor
     * @param name card name
     * @param objective card objective
     * @param victoryPoints the points given by this card
     * @author Abu Hussnain Saeed
     */
    public LightShades(String name, String objective, int victoryPoints){
        super(name, objective, victoryPoints);
    }
    @Override

    /**
     * count the number of 1 and 2 in the scheme
     * @param schema scheme to be computed
     * @return total points given by the use of this card
     * @author Abu Hussnain Saeed
     */
    public int calculatePoints(Scheme schema) {
        int contOne = 0;
        int contTwo = 0;
        for (int i = 0; i < schema.getScheme().length; i++) {
            for (int j = 0; j < schema.getScheme()[i].length; j++) {
                if (schema.getScheme()[i][j].isOccupied()) {
                    if (schema.getScheme()[i][j].getDado().getTop() == 1)
                        contOne++;
                    if (schema.getScheme()[i][j].getDado().getTop() == 2)
                        contTwo++;
                }
            }
        }
        if (contOne <= contTwo)
            return contOne * victoryPoints;
        else
            return contTwo * victoryPoints;
    }
}
