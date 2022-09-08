package Shared.Model.ObjectiveCard.PublicCards;

import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.Scheme;

public class DifferentShades extends PublicObjective {
    /**
     * constructor
     * @param name card name
     * @param objective card objective
     * @param victoryPoints the points given by this card
     * @author Abu Hussnain Saeed
     */
    public DifferentShades(String name, String objective, int victoryPoints){
        super(name, objective, victoryPoints);
    }

    /**
     * count all shades in the scheme
     * @param schema scheme to be computed
     * @return total points given by the use of this card
     * @author Abu Hussnain Saeed
     */
    @Override
    public int calculatePoints(Scheme schema) {
        int[] contArray = {0, 0, 0, 0, 0, 0};   //each position counts the presence of relative number, example first position counts the number of one, the second of two and so on
        int min;                              //the counter of number that has been found fewer times is used to calculate the total

        for (int i = 0; i < schema.getScheme().length; i++) {
            for (int j = 0; j < schema.getScheme()[i].length; j++) {
                if (schema.getScheme()[i][j].isOccupied()) {
                    contArray[schema.getScheme()[i][j].getDado().getTop() - 1]++;
                }

            }
        }
        return findMin(contArray) * victoryPoints;
    }

    /**
     * find the minimum from the array
     * @param contArray the array where each position counts the presence of the different shades
     * @return minimum value from the array
     * @author Abu Hussnain Saeed
     */
    private int findMin(int[] contArray){
        int min = contArray[0];
        for (int j=1; j < contArray.length; j++) {
            if (contArray[j] < min)
                min = contArray[j];
        }
        return min;
    }
}
