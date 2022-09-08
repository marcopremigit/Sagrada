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

public class TenagliaARotelleTest {
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

    @Test
    public void placeDice() {
        try {
            Dice dice = new Dice(Color.YELLOW);
            dice.setTop(5);
            TenagliaARotelle.placeDice(scheme,dice,1,1);
            Assert.assertEquals(dice, scheme.getScheme()[1][1].getDado());
            Assert.assertEquals(dice1, scheme.getScheme()[1][3].getDado());
            Assert.assertEquals(dice2, scheme.getScheme()[2][2].getDado());
            Assert.assertEquals(dice3, scheme.getScheme()[2][1].getDado());

            for(int i=0; i<4; i++){
                for(int j=0; j<5;j++){
                    if(!scheme.getScheme()[i][j].isOccupied()){
                        Assert.assertEquals(Color.WHITE,scheme.getScheme()[i][j].getColor());
                    }
                }
            }
        }
        catch (IllegalColorException e){}
        catch (CannotPlaceDiceException e ){}


    }
}