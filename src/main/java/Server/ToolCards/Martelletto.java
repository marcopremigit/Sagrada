package Server.ToolCards;

import Shared.Exceptions.CannotUseCardException;
import Shared.Model.Dice.Dice;

import java.util.List;

public class Martelletto {
    public static void reRollDices(List<Dice> draftPool, boolean firstTurn) throws CannotUseCardException{
        if(!firstTurn){
            if(draftPool!=null){
                for (Dice dice: draftPool)
                    dice.roll();
            }
        }
        else throw new CannotUseCardException("Non puoi usare Martelletto durante il primo turno");
    }
}
