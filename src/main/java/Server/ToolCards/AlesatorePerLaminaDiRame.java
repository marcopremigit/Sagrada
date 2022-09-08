package Server.ToolCards;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.WrongInputException;
import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;

public class AlesatorePerLaminaDiRame {
    private AlesatorePerLaminaDiRame(){}

    public static Scheme changeDicePosition(Scheme scheme, int oldX, int oldY, int newX, int newY) throws WrongInputException, CannotPlaceDiceException{
        //check if old cell is occupied (so you can remove dice later)
        if(scheme.getScheme()[oldX][oldY].isOccupied()){
            Dice dice = scheme.getScheme()[oldX][oldY].getDado();
            scheme.getScheme()[oldX][oldY].removeDado();
            //restrictions of number and color in nearby cell && check if at least one cell nearby is occupied
            //&& cell's color must be either WHITE or equals to dice's color
            if(PlaceDiceRestrictions.checkColorOrNumber(scheme, dice, newX, newY)
                    && (PlaceDiceRestrictions.aroundCheck(scheme, newX, newY))
                    && (scheme.getScheme()[newX][newY].getColor().equals(dice.getColor()) || scheme.getScheme()[newX][newY].getColor().equals(Color.WHITE))){

                scheme.getScheme()[newX][newY].setDado(dice);
                return scheme;
            }
            else{
                scheme.getScheme()[oldX][oldY].setDado(dice);
                throw new CannotPlaceDiceException("La cella in posizione " + oldX + " :: " + oldY + " non va bene");
            }

        }
        else
            throw new WrongInputException("Non c'è nessun dado nella posizione "+ oldX + " :: " + oldY);
    }
}
