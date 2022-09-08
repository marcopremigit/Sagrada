package Server.ToolCards;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.WrongInputException;
import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RigaInSugheroTest {
    Scheme scheme;
    SchemeCell[][] schemeCell= new SchemeCell[4][5];
    Dice dice1;
    Dice dice2;
    Dice dice3;

    @Before
    public void setUp() throws Exception {
        for(int i=0; i<4;i++){
            for(int j=0; j<5;j++){
                schemeCell[i][j]=new SchemeCell();
            }
        }
        scheme = new Scheme("test", 5, schemeCell);
        dice1 = new Dice(Color.PURPLE);
        dice1.setTop(3);
        dice2 = new Dice(Color.GREEN);
        dice2.setTop(2);
        dice3 = new Dice(Color.YELLOW);
        dice3.setTop(5);
        schemeCell[0][0].setDado(dice1);
    }

    @Test
    public void setDice() {

        //first test
        try {
            RigaInSughero.setDice(scheme,dice2, 3, 3);
            Assert.assertEquals(dice1, scheme.getScheme()[0][0].getDado());
            Assert.assertEquals(dice2,scheme.getScheme()[3][3].getDado());
            int cont=0;
            for(int i=0; i<4; i++){
                for(int j=0; j<5;j++){
                    if(!scheme.getScheme()[i][j].isOccupied()){
                        Assert.assertEquals(Color.WHITE,scheme.getScheme()[i][j].getColor());
                        cont++;
                    }
                }
            }
            Assert.assertEquals(18,cont);
        }
        catch (WrongInputException e){}
        catch (CannotPlaceDiceException e){}

        //second test
        try {
            RigaInSughero.setDice(scheme,dice3, 0, 0);
        }
        catch (WrongInputException e){
            Assert.assertEquals(dice1, scheme.getScheme()[0][0].getDado());
            Assert.assertEquals(dice2,scheme.getScheme()[3][3].getDado());
            int cont=0;
            for(int i=0; i<4; i++){
                for(int j=0; j<5;j++){
                    if(!scheme.getScheme()[i][j].isOccupied()){
                        Assert.assertEquals(Color.WHITE,scheme.getScheme()[i][j].getColor());
                        cont++;
                    }
                }
            }
            Assert.assertEquals(18,cont);}
        catch (CannotPlaceDiceException e){}

        //third test
        try {
            scheme.getScheme()[1][4]=new SchemeCell(6);
            RigaInSughero.setDice(scheme,dice3, 1, 4);
        }
        catch (WrongInputException e){}
        catch (CannotPlaceDiceException e){
            Assert.assertEquals(dice2,scheme.getScheme()[3][3].getDado());
            int cont=0;
            for(int i=0; i<4; i++){
                for(int j=0; j<5;j++){
                    if(!scheme.getScheme()[i][j].isOccupied()){
                        Assert.assertEquals(Color.WHITE,scheme.getScheme()[i][j].getColor());
                        cont++;
                    }
                }
            }
            Assert.assertEquals(18,cont);
        }


    }
}