package Server.ToolCards;

import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;

public class PinzaSgrossatrice {
    public static boolean pickDiceTool(Dice chosenDice, boolean increase)throws WrongInputException {
        if(chosenDice.getTop()==6 && increase){
            throw new WrongInputException("Non posso aumentare il valore del dado perchè è già massimo");
        }
        if(chosenDice.getTop()==1 && !increase){
            throw new WrongInputException("Non posso diminuire il valore del dado perchè è già minimo");
        }

        if(increase){
            chosenDice.setTop(chosenDice.getTop()+1);
        }else{
            chosenDice.setTop(chosenDice.getTop()-1);
        }

        return true;

    }
}
