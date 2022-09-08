package Server.ToolCards;

import Server.ToolCards.PlaceDiceRestrictions;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;

public class TenagliaARotelle {
    public static Scheme placeDice(Scheme scheme, Dice dice, int x, int y) throws CannotPlaceDiceException{
        return PlaceDiceRestrictions.placeDice(scheme, dice, x, y);
    }
}
