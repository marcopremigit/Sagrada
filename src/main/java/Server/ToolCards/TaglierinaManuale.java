package Server.ToolCards;

import Server.ToolCards.PlaceDiceRestrictions;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.Schemes.Scheme;

public class TaglierinaManuale {

    public static Scheme moveDices(RoundTrace trace,Scheme scheme, int firstDiceX, int firstDiceY, int firstX, int firstY, int secondDiceX, int secondDiceY, int secondX, int secondY) throws WrongInputException,CannotPlaceDiceException {
        boolean firstIsCorrect = false;
        boolean secondIsCorrect = false;
        //both dices' color are equal to roundTrace's chosen dice
        Dice dice1 = scheme.getScheme()[firstDiceX][firstDiceY].getDado();
        Dice dice2 = scheme.getScheme()[secondDiceX][secondDiceY].getDado();
        scheme.getScheme()[firstDiceX][firstDiceY].removeDado();
        scheme.getScheme()[secondDiceX][secondDiceY].removeDado();

        for(int i=0; i<trace.getTrace().size();i++){
            for(int j=0;j<trace.getTrace().get(i).getCell().size();j++){
                if(trace.getTrace().get(i).getCell().get(j).getColor().toString().equals(dice1.getColor().toString())){

                    firstIsCorrect=true;
                }
                if(trace.getTrace().get(i).getCell().get(j).getColor().toString().equals(dice2.getColor().toString())){
                    secondIsCorrect=true;
                }
                if(firstIsCorrect && secondIsCorrect)
                    break;
            }
            if(firstIsCorrect && secondIsCorrect)
                break;
        }
        if(firstIsCorrect&&secondIsCorrect&&dice1.getColor().toString().equals(dice2.getColor().toString())){
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
        }
        else{
            scheme.getScheme()[firstDiceX][firstDiceY].setDado(dice1);
            scheme.getScheme()[secondDiceX][secondDiceY].setDado(dice2);
            throw new WrongInputException("Il colore dei dadi scelti non è presente nel tracciato dei round");
        }
        return scheme;
    }
}
