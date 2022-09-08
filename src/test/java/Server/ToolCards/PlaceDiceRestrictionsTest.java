package Server.ToolCards;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.IllegalColorException;
import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlaceDiceRestrictionsTest {
    Scheme scheme;
    Scheme scheme2;
    SchemeCell[][] schemeCell2=new SchemeCell[4][5];
    SchemeCell[][] schemeCell= new SchemeCell[4][5];

    @Before
    public void setUp() throws Exception {for(int i=0; i<4;i++){
        for(int j=0; j<5;j++){
            schemeCell[i][j]=new SchemeCell(4);
        }
        scheme = new Scheme("test", 5, schemeCell);

        for(int j=0; j<5;j++){
            schemeCell2[i][j]=new SchemeCell();
        }
        scheme2 = new Scheme("test2", 5, schemeCell2);
    }

    }

    @Test
    public void placeDice() {
        try{
            Dice dice = new Dice(Color.YELLOW);
            dice.setTop(6);
            PlaceDiceRestrictions.placeDice(scheme,dice,0,0);
        }
        catch (IllegalColorException e){}
        catch (CannotPlaceDiceException e){}

        for(int i=0; i<4; i++){
            for (int j=0; j<5; j++){
                Assert.assertEquals(4, scheme.getScheme()[i][j].getNum());
            }
        }


    }

    @Test
    public void aroundCheck() {
    }

    @Test
    public void checkColorOrNumber() {
        try{
            Dice dice1 = new Dice(Color.RED);
            dice1.setTop(3);
            Dice dice2 = new Dice(Color.RED);
            dice2.setTop(5);
            Assert.assertEquals(scheme2,PlaceDiceRestrictions.placeDice(scheme2, dice1, 3,0));
            Assert.assertEquals(false, PlaceDiceRestrictions.checkColorOrNumber(scheme2, dice2, 2, 0));
            Assert.assertEquals(false, PlaceDiceRestrictions.checkColorOrNumber(scheme2,dice2,3, 1));
        }
        catch (IllegalColorException e){}
        catch (CannotPlaceDiceException e){}
    }

    /**
     * check if the dice is placed correctly in the scheme
     * @author Abu Hussnain Saeed
     */
    @Test
    public void cellIsRight() {
        try{
            int i = 0;
            int j = 0;
            Dice dice = new Dice(Color.GREEN);
            dice.setTop(3);
            scheme.getScheme()[i][j] = new SchemeCell(Color.GREEN);
            scheme.getScheme()[i][j].setDado(dice);
            Assert.assertEquals(true,PlaceDiceRestrictions.cellIsRight(scheme,dice,i,j));
            Assert.assertEquals(false,PlaceDiceRestrictions.cellIsRight(scheme,null,i,j));
        }
        catch (IllegalColorException i){}

    }
}