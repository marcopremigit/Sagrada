package Server.ToolCards;

import Shared.Exceptions.WrongInputException;
import Shared.Color;
import Shared.Model.Dice.Dice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PinzaSgrossatriceTest {
    Dice dice1;
    Dice dice2;
    Dice dice6;

    @Before
    public void setUp() throws Exception {
        dice1 = new Dice(Color.YELLOW);
        dice2 = new Dice(Color.YELLOW);
        dice6 = new Dice(Color.YELLOW);

        dice1.setTop(1);
        dice2.setTop(2);
        dice6.setTop(6);
    }

    @Test
    public void pickDiceTool() {

        //test 1
        try {
            PinzaSgrossatrice.pickDiceTool(dice1,true);
            Assert.assertEquals(2, dice1.getTop());
        }catch (WrongInputException e){}

        //test 2
        try {
            dice1.setTop(1);
            PinzaSgrossatrice.pickDiceTool(dice1,false);
        }catch (WrongInputException e){
            Assert.assertEquals(1, dice1.getTop());
        }

        //test 3
        try {
            PinzaSgrossatrice.pickDiceTool(dice2,true);
            Assert.assertEquals(3, dice2.getTop());
        }catch (WrongInputException e){}

        //test 4
        try {
            dice2.setTop(2);
            PinzaSgrossatrice.pickDiceTool(dice2,false);
            Assert.assertEquals(1, dice2.getTop());
        }catch (WrongInputException e){}

        //test 5
        try {
            PinzaSgrossatrice.pickDiceTool(dice6,true);
        }catch (WrongInputException e){
            Assert.assertEquals(6, dice6.getTop());
        }

        //test 6
        try {
            PinzaSgrossatrice.pickDiceTool(dice6,false);
            Assert.assertEquals(5, dice6.getTop());
        }catch (WrongInputException e){}

    }
}