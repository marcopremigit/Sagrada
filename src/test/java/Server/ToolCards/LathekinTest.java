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

public class LathekinTest {
    Scheme scheme;
    SchemeCell[][] schemeCell= new SchemeCell[4][5];
    Dice dice1;
    Dice dice2;
    Dice dice3;

    @Before
    public void setUp() throws Exception {
        try {
            for(int i=0; i<4;i++){
                for(int j=0; j<5;j++){
                    schemeCell[i][j]=new SchemeCell();
                }
            }

            dice1 = new Dice(Color.YELLOW);
            dice1.setTop(4);
            dice2 = new Dice(Color.GREEN);
            dice2.setTop(5);
            dice3 = new Dice(Color.BLUE);
            dice3.setTop(3);

            schemeCell[2][1].setDado(dice3);
            schemeCell[2][2].setDado(dice2);
            schemeCell[1][3].setDado(dice1);
        }
        catch (IllegalColorException e){/*never reached*/}
        scheme = new Scheme("test", 5, schemeCell);

    }

    /**
     * check if move correctly the dices
     * @author Abu Hussnain Saeed
     */
    @Test
    public void moveDices() {

        //first test
        try{
            Lathekin.moveDices(scheme,2,1,2,3,2,2,1,2);
            Assert.assertEquals(dice3, scheme.getScheme()[2][3].getDado());
            Assert.assertEquals(true, !scheme.getScheme()[2][1].isOccupied());
            Assert.assertEquals(dice2, scheme.getScheme()[1][2].getDado());
            Assert.assertEquals(true, !scheme.getScheme()[2][2].isOccupied());
        }catch (CannotPlaceDiceException e){}

        //second test
        try{
            schemeCell[3][3].setDado(new Dice(Color.GREEN));
            schemeCell[3][4].setDado(new Dice(Color.BLUE));
            Lathekin.moveDices(scheme,2,3,3,3,1,2,3,4);
        }
        catch (IllegalColorException|CannotPlaceDiceException e){}

        //third test
        try{
            Lathekin.moveDices(scheme,1,2,1,4,2,3,0,0);
            Assert.assertEquals(dice2,scheme.getScheme()[1][4].getDado());
            Assert.assertEquals(dice3, scheme.getScheme()[2][3].getDado());
        }catch (CannotPlaceDiceException e){}
    }
}