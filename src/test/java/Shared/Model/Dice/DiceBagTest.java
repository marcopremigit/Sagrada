package Shared.Model.Dice;

import Shared.Exceptions.IllegalColorException;
import Shared.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DiceBagTest {
    private DiceBag bag;

    @Before
    public void setUp() throws Exception {
        bag  = new DiceBag();
        int countRed = 0;
        int countBlue = 0;
        int countPurple = 0;
        int countGreen = 0;
        int countYellow = 0;
        for(int i = 0; i<bag.getDicesLeft(); i++){
            Assert.assertTrue(bag.getDices().get(i).getColor().equals(Color.RED)
                    || bag.getDices().get(i).getColor().equals(Color.GREEN)
                    || bag.getDices().get(i).getColor().equals(Color.BLUE)
                    || bag.getDices().get(i).getColor().equals(Color.PURPLE)
                    || bag.getDices().get(i).getColor().equals(Color.YELLOW));
            switch (bag.getDices().get(i).getColor()){
                case GREEN: countGreen++; break;
                case BLUE: countBlue++; break;
                case YELLOW: countYellow++; break;
                case RED: countRed++; break;
                case PURPLE: countPurple++; break;
            }
        }
        Assert.assertEquals(18, countBlue);
        Assert.assertEquals(18, countGreen);
        Assert.assertEquals(18, countPurple);
        Assert.assertEquals(18, countRed);
        Assert.assertEquals(18, countYellow);
        System.out.println(bag.toString());
    }
    @Test
    public void getDicesLeft() {
        Assert.assertEquals(90, bag.getDicesLeft());
    }

    @Test
    public void extract() {
        int beforeExtracting = bag.getDicesLeft();
        int extract = 10;
        for(int i =0; i<extract; i++){
            bag.extract();
        }

        Assert.assertEquals(beforeExtracting - extract, bag.getDicesLeft());
    }

    @Test
    public void addDice() {
        try {
            int beforeAdding = bag.getDicesLeft();
            Dice diceToAdd = new Dice(Color.PURPLE);
            bag.addDice(diceToAdd);

            Assert.assertEquals(beforeAdding + 1, bag.getDicesLeft());

            Dice newDiceToAdd = new Dice(Color.WHITE);
            bag.addDice(newDiceToAdd);

            Assert.assertNull(bag.getDices().get(bag.getDices().indexOf(newDiceToAdd)).getColor());
        }
        catch(IllegalColorException e){/*never reached*/}
    }

    /**
     * extract N dices from DiceBag
     * @author Abu Hussnain Saeed
     */
    @Test
    public void extractNDices(){
        ArrayList<Dice> dice = bag.extractNDices(2);
        Assert.assertNotNull(dice.get(0));
        Assert.assertNotNull(dice.get(1));
    }
}