package Server.ToolCards;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;

public class RigaInSughero {
    public static Scheme setDice (Scheme scheme, Dice chosenDice, int X, int Y) throws WrongInputException, CannotPlaceDiceException {
        if(!scheme.getScheme()[X][Y].isOccupied()){
            if(PlaceDiceRestrictions.checkColorOrNumber(scheme,chosenDice,X,Y)&&PlaceDiceRestrictions.cellIsRight(scheme,chosenDice,X,Y)){
                scheme.getScheme()[X][Y].setDado(chosenDice);
                return scheme;
            }else{
                throw new CannotPlaceDiceException("La cella in posizione " + X  +" :: " + Y + " non va bene");
            }

        }else{
            throw new WrongInputException("La posizione scelta è già occupata");
        }
    }
}
