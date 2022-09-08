package Server.ToolCards;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.CannotUseCardException;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;

import java.util.List;

public class PennelloPerPastaSalda {
    public static void reRollDice(Dice dice){
        dice.roll();
    }
}
