package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;

public class DifferentShadesColumn extends PublicObjective {
    /**
     * constructor
     * @param name card name
     * @param objective card objective
     * @param victoryPoints the points given by this card
     * @author Abu Hussnain Saeed
     */
    public DifferentShadesColumn(String name, String objective, int victoryPoints){
        super(name, objective, victoryPoints);
    }

    /**
     * count the number of columns with different shades
     * @param schema scheme to be computed
     * @return total points given by the use of this card
     * @author Abu Hussnain Saeed
     */
    @Override
    public int calculatePoints(Scheme schema) {
        int total = 0;
        for (int i = 0; i < schema.getScheme()[0].length; i++) {
            if (different(schema, i))
                total++;
        }
        return (total * victoryPoints);
    }

    /**
     * check the shades in a column
     * @param schema scheme to be computed
     * @param i column index
     * @return true if it not find any same number in a column
     * @author Abu Hussnain Saeed
     */
    private boolean different(Scheme schema, int i) {
        for (int j = 0; j < schema.getScheme().length; j++) {
            for (int k = j + 1; k < schema.getScheme().length; k++) {
                if (schema.getScheme()[j][i].isOccupied() && schema.getScheme()[k][i].isOccupied()) {
                    if (schema.getScheme()[j][i].getDado().getTop() == schema.getScheme()[k][i].getDado().getTop()) {
                        return false;       //return 0 if it find same color
                    }
                }
                else
                    return false;
            }
        }
        return true;
    }
}