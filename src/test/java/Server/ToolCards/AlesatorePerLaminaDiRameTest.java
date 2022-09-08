package Server.ToolCards;

import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.IllegalColorException;
import Shared.Exceptions.WrongInputException;
import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AlesatorePerLaminaDiRameTest {
    Scheme scheme;
    SchemeCell[][] schemeCell= new SchemeCell[4][5];

    @Before
    public void setUp() throws Exception {
        try {
            for(int i=0; i<4;i++){
                for(int j=0; j<5;j++){
                    schemeCell[i][j]=new SchemeCell();
                }
            }
            schemeCell[1][3].setDado(new Dice(Color.YELLOW));
            schemeCell[1][3].getDado().setTop(4);
            schemeCell[2][2].setDado(new Dice(Color.YELLOW));
            schemeCell[2][2].getDado().setTop(5);
            schemeCell[2][1].setDado(new Dice(Color.BLUE));
            schemeCell[2][1].getDado().setTop(3);
        }
        catch (IllegalColorException e){/*never reached*/}
        scheme = new Scheme("test", 5, schemeCell);
    }

    @Test
    public void changeDicePosition() {

        //first test
        try {
            AlesatorePerLaminaDiRame.changeDicePosition(scheme,0,0,2,3);

        }

        catch (WrongInputException e1){}
        catch (CannotPlaceDiceException e2){}

        //second test
        try {
            schemeCell[2][3]=new SchemeCell(2);
            AlesatorePerLaminaDiRame.changeDicePosition(scheme,2,2,2,3);

        }
        catch (WrongInputException e1){}
        catch (CannotPlaceDiceException e2){}


        //third test
        try {
            Assert.assertEquals(true, AlesatorePerLaminaDiRame.changeDicePosition(scheme,2,1,2,3).equals(scheme));
            Assert.assertEquals(3, scheme.getScheme()[2][3].getDado().getTop());
            Assert.assertEquals(Color.BLUE, scheme.getScheme()[2][3].getDado().getColor());
            Assert.assertEquals(false, scheme.getScheme()[2][1].isOccupied());

        }
        catch (WrongInputException e1){}
        catch (CannotPlaceDiceException e2){}


    }
}