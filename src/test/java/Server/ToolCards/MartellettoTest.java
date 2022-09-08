package Server.ToolCards;

import Shared.Exceptions.CannotUseCardException;
import Shared.Color;
import Shared.Model.Dice.Dice;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class MartellettoTest {
    ArrayList<Dice> draftpool;
    ArrayList<Dice> draftpool2;
    @Before
    public void setUp() throws Exception {
        draftpool = new ArrayList<>();
        Dice dice1 = new Dice(Color.PURPLE);
        dice1.setTop(3);
        Dice dice2 = new Dice(Color.GREEN);
        dice2.setTop(2);
        Dice dice3 = new Dice(Color.YELLOW);
        dice3.setTop(5);
        draftpool.add(0,dice1);
        draftpool.add(1, dice2);
        draftpool.add(2, dice3);

        draftpool2 = new ArrayList<>();
        Dice dice4 = new Dice(Color.PURPLE);
        dice4.setTop(3);
        Dice dice5 = new Dice(Color.GREEN);
        dice5.setTop(2);
        Dice dice6 = new Dice(Color.YELLOW);
        dice6.setTop(5);
        draftpool2.add(0, dice4);
        draftpool2.add(1, dice5);
        draftpool2.add(2, dice6);


    }

    @Test
    public void reRollDices() {

        //first test
        try{
            Martelletto.reRollDices(draftpool, false);
            Assert.assertEquals(Color.PURPLE, draftpool.get(0).getColor());
            Assert.assertEquals(Color.GREEN, draftpool.get(1).getColor());
            Assert.assertEquals(Color.YELLOW, draftpool.get(2).getColor());
            Assert.assertEquals(3, draftpool.size());
        }catch (CannotUseCardException e){}

        //second test
        try{
            Martelletto.reRollDices(draftpool2, true);
            Assert.assertEquals(Color.PURPLE, draftpool2.get(0).getColor());
            Assert.assertEquals(3, draftpool2.get(0).getTop());
            Assert.assertEquals(Color.GREEN, draftpool2.get(1).getColor());
            Assert.assertEquals(2, draftpool2.get(1).getTop());
            Assert.assertEquals(Color.YELLOW, draftpool2.get(2).getColor());
            Assert.assertEquals(5, draftpool2.get(2).getTop());
            Assert.assertEquals(3, draftpool2.size());
        }catch (CannotUseCardException e){}


    }
}