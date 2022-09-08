package Server.ToolCards;

import Shared.Exceptions.IllegalColorException;
import Shared.Color;
import Shared.Model.Dice.Dice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TamponeDiamantatoTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void pickAndChangeTop() {
        try{
            Dice dice = new Dice(Color.BLUE);

            //1 to 6
            dice.setTop(1);
            TamponeDiamantato.pickAndChangeTop(dice);
            Assert.assertEquals(6, dice.getTop());

            //2 to 5
            dice.setTop(2);
            TamponeDiamantato.pickAndChangeTop(dice);
            Assert.assertEquals(5, dice.getTop());

            //3 to 4
            dice.setTop(3);
            TamponeDiamantato.pickAndChangeTop(dice);
            Assert.assertEquals(4, dice.getTop());

            //4 to 3
            dice.setTop(4);
            TamponeDiamantato.pickAndChangeTop(dice);
            Assert.assertEquals(3, dice.getTop());

            //5 to 2
            dice.setTop(5);
            TamponeDiamantato.pickAndChangeTop(dice);
            Assert.assertEquals(2, dice.getTop());

            //6 to 1
            dice.setTop(6);
            TamponeDiamantato.pickAndChangeTop(dice);
            Assert.assertEquals(1, dice.getTop());
        }catch (IllegalColorException e){}



    }
}