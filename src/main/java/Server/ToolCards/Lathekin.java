package Server.ToolCards;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;

public class Lathekin {
    public static Scheme moveDices(Scheme scheme, int firstDiceX, int firstDiceY, int firstX, int firstY, int secondDiceX, int secondDiceY, int secondX, int secondY) throws CannotPlaceDiceException {
        Dice dice1 = scheme.getScheme()[firstDiceX][firstDiceY].getDado();
        Dice dice2 = scheme.getScheme()[secondDiceX][secondDiceY].getDado();
        scheme.getScheme()[firstDiceX][firstDiceY].removeDado();
        scheme.getScheme()[secondDiceX][secondDiceY].removeDado();

        try{
            PlaceDiceRestrictions.placeDice(scheme, dice1, firstX, firstY);
            try{
                PlaceDiceRestrictions.placeDice(scheme, dice2, secondX, secondY);
            }catch (CannotPlaceDiceException e3){
                scheme.getScheme()[secondDiceX][secondDiceY].setDado(dice2);
            }
        }catch (CannotPlaceDiceException e){
            scheme.getScheme()[firstDiceX][firstDiceY].setDado(dice1);
            try{
                PlaceDiceRestrictions.placeDice(scheme, dice2, secondX, secondY);
            }catch (CannotPlaceDiceException e3){
                scheme.getScheme()[secondDiceX][secondDiceY].setDado(dice2);
                throw new CannotPlaceDiceException("");
            }
        }

        return scheme;

    }
}
