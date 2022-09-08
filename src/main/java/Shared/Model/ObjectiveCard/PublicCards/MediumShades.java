package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;

public class MediumShades extends PublicObjective {
    /**
     * constructor
     * @param name card name
     * @param objective card objective
     * @param victoryPoints the points given by this card
     * @author Abu Hussnain Saeed
     */
    public MediumShades(String name, String objective, int victoryPoints){
        super(name, objective, victoryPoints);
    }

    /**
     * count the number of 3 and 4 in the scheme
     * @param schema scheme to be computed
     * @return total points given by the use of this card
     * @author Abu Hussnain Saeed
     */
    @Override
    public int calculatePoints(Scheme schema) {
        int contThree = 0;
        int contFour = 0;
        for (int i = 0; i < schema.getScheme().length; i++) {
            for (int j = 0; j < schema.getScheme()[i].length; j++) {
                if (schema.getScheme()[i][j].isOccupied()) {
                    if (schema.getScheme()[i][j].getDado().getTop() == 3)
                        contThree++;
                    if (schema.getScheme()[i][j].getDado().getTop() == 4)
                        contFour++;
                }
            }
        }
        if (contThree >= contFour)
            return contFour * victoryPoints;
        else
            return contThree * victoryPoints;
    }
}
