package Server.ToolCards;

import Shared.Model.Dice.Dice;

public class TamponeDiamantato {
    /**
     * change the top of the dice
     * @param chosenDice dice of which we have to change the top
     * @author Abu Hussnain Saeed
     */
    public static void pickAndChangeTop(Dice chosenDice){
        switch (chosenDice.getTop()){
            case 1: {
                chosenDice.setTop(6);
                break;
            }
            case 2:{
                chosenDice.setTop(5);
                break;
            }
            case 3:{
                chosenDice.setTop(4);
                break;
            }
            case 4:{
                chosenDice.setTop(3);
                break;
            }
            case 5:{
                chosenDice.setTop(2);
                break;
            }
            case 6:{
                chosenDice.setTop(1);
                break;
            }
            default: break;
        }
    }
}
