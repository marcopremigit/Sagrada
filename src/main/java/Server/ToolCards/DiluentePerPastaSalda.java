package Server.ToolCards;

import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;
import Shared.Model.Dice.DiceBag;

import java.util.List;


public class DiluentePerPastaSalda {

    public static void switchDice(DiceBag diceBag, List<Dice> draftPool, int dicePosition){
        diceBag.addDice(draftPool.get(dicePosition));
        Dice newDice = diceBag.extract();
        draftPool.set(dicePosition,newDice);
    }

    public static void changeDiceTop(Dice diceFromDraftPool, int top) throws WrongInputException{
        if(top>=1 && top<=6)
            diceFromDraftPool.setTop(top);
        else
            throw new WrongInputException("Il valore scelto per il dado è sbagliato");
    }
}
