package Server.ToolCards;

import Shared.Color;
import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;
import Shared.Model.Dice.DiceBag;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DiluentePerPastaSaldaTest {
    DiceBag diceBag = new DiceBag();
    Dice dice1;
    Dice dice2;
    Dice dice3;
    Dice dice4;
    Scheme scheme;
    SchemeCell[][] schemeCell= new SchemeCell[4][5];
    ArrayList<Dice>draftpool = new ArrayList<>();

    @Before
    public void setUp() throws Exception {

        while(diceBag.getDicesLeft()!=0){
            diceBag.extract();
        }
        for(int i=0; i<4;i++){
            for(int j=0; j<5;j++){
                schemeCell[i][j]=new SchemeCell();
            }
        }
        scheme = new Scheme("test", 5, schemeCell);
        dice1 = new Dice(Color.GREEN);
        dice1.setTop(1);
        dice2 = new Dice(Color.GREEN);
        dice2.setTop(2);
        dice3 = new Dice(Color.RED);
        dice3.setTop(3);
        dice4 = new Dice(Color.RED);
        dice4.setTop(5);

        draftpool.add(dice1);
        diceBag.addDice(dice4);

    }

    /**
     * check if we correctly switchDice from the diceBag to draftPool
     * @author Abu Hussnain Saeed
     */
    @Test
    public void t1switchDice(){
        DiluentePerPastaSalda.switchDice(diceBag, draftpool, 0);
        Assert.assertEquals(true, draftpool.get(0).equals(dice4)||draftpool.get(0).equals(dice1));
        Assert.assertEquals(true, diceBag.getDices().contains(dice1)||diceBag.getDices().contains(dice4));
        Assert.assertEquals(true, draftpool.size()==1);
        Assert.assertEquals(true, diceBag.getDices().size()==1);
    }

    @Test
    public void t2changeDiceTop() {
        try{
            DiluentePerPastaSalda.changeDiceTop(dice3, 5);
            DiluentePerPastaSalda.changeDiceTop(dice2,7);
            Assert.assertEquals(5, dice3.getTop());

        }
        catch (WrongInputException e){
            Assert.assertEquals(2, dice2.getTop());
        }
    }
}