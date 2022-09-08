package Server.ToolCards;

import Shared.Color;
import Shared.Exceptions.CannotPlaceDiceException;
import Shared.Exceptions.IllegalRoundException;
import Shared.Exceptions.WrongInputException;
import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.RoundTrace.RoundTraceCell;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TaglierinaManualeTest {
    Scheme scheme;
    SchemeCell[][] schemeCell= new SchemeCell[4][5];

    RoundTrace roundTrace = new RoundTrace();

    Dice dice1D;
    Dice dice2D;
    Dice dice3D;


    /**
     * set up the roundTrace and the scheme
     * @author Abu Hussnain Saeed
     * @throws Exception if the color of the dice is wrong, exception never reached
     */
    @Before
    public void setUp() throws Exception {

        dice1D = new Dice(Color.PURPLE);
        dice1D.setTop(3);
        dice2D = new Dice(Color.BLUE);
        dice2D.setTop(2);
        dice3D = new Dice(Color.BLUE);
        dice3D.setTop(5);
        Dice dice1;
        Dice dice2;
        Dice dice3;
        Dice dice4;
        Dice dice9;
        Dice dice10;
        Dice dice11;
        Dice dice12;
        Dice dice13;
        Dice dice14;
        Dice dice15;
        List<Dice> dices1;
        List<Dice> dices2;
        List<Dice> dices3;
        List<Dice> dices4;
        List<Dice> dices5;
        List<Dice> dices6;
        List<Dice> dices7;
        List<Dice> dices8;
        List<Dice> dices9;
        List<Dice> dices10;
        RoundTraceCell roundTraceCell1 = new RoundTraceCell();
        RoundTraceCell roundTraceCell2 = new RoundTraceCell();
        RoundTraceCell roundTraceCell3 = new RoundTraceCell();
        RoundTraceCell roundTraceCell4 = new RoundTraceCell();
        RoundTraceCell roundTraceCell5 = new RoundTraceCell();
        RoundTraceCell roundTraceCell6 = new RoundTraceCell();
        RoundTraceCell roundTraceCell7 = new RoundTraceCell();
        RoundTraceCell roundTraceCell8 = new RoundTraceCell();
        RoundTraceCell roundTraceCell9 = new RoundTraceCell();
        RoundTraceCell roundTraceCell10 = new RoundTraceCell();


        dice1 = new Dice(Color.BLUE);
        dice1.setTop(1);
        dice2 = new Dice(Color.BLUE);
        dice2.setTop(1);
        dice3 = new Dice(Color.BLUE);
        dice3.setTop(1);
        dice4 = new Dice(Color.BLUE);
        dice4.setTop(1);
        dice9 = new Dice(Color.BLUE);
        dice9.setTop(1);
        dice10 = new Dice(Color.BLUE);
        dice10.setTop(1);
        dice11 = new Dice(Color.BLUE);
        dice11.setTop(1);
        dice12 = new Dice(Color.BLUE);
        dice12.setTop(1);
        dice13 = new Dice(Color.BLUE);
        dice13.setTop(1);
        dice14 = new Dice(Color.BLUE);
        dice14.setTop(1);
        dice15 = new Dice(Color.RED);
        dice15.setTop(1);

        dices1 = new ArrayList<>();
        dices2 = new ArrayList<>();
        dices3 = new ArrayList<>();
        dices4 = new ArrayList<>();
        dices5 = new ArrayList<>();
        dices6 = new ArrayList<>();
        dices7 = new ArrayList<>();
        dices8 = new ArrayList<>();
        dices9 = new ArrayList<>();
        dices10 = new ArrayList<>();

        dices1.add(dice1);
        dices1.add(dice4);
        dices2.add(dice2);
        dices3.add(dice3);
        dices4.add(dice9);
        dices5.add(dice10);
        dices6.add(dice11);
        dices7.add(dice12);
        dices8.add(dice13);
        dices9.add(dice14);
        dices10.add(dice15);

        roundTraceCell1.addDicesToTrace(dices1);
        roundTraceCell2.addDicesToTrace(dices2);
        roundTraceCell3.addDicesToTrace(dices3);
        roundTraceCell4.addDicesToTrace(dices4);
        roundTraceCell5.addDicesToTrace(dices5);
        roundTraceCell6.addDicesToTrace(dices6);
        roundTraceCell7.addDicesToTrace(dices7);
        roundTraceCell8.addDicesToTrace(dices8);
        roundTraceCell9.addDicesToTrace(dices9);
        roundTraceCell10.addDicesToTrace(dices10);

        roundTrace.setPool(0, roundTraceCell1);
        roundTrace.setPool(1, roundTraceCell2);
        roundTrace.setPool(2, roundTraceCell3);
        roundTrace.setPool(3, roundTraceCell4);
        roundTrace.setPool(4, roundTraceCell5);
        roundTrace.setPool(5, roundTraceCell6);
        roundTrace.setPool(6, roundTraceCell7);
        roundTrace.setPool(7, roundTraceCell8);
        roundTrace.setPool(8, roundTraceCell9);
        roundTrace.setPool(9, roundTraceCell10);


        for(int i=0; i<4;i++){
            for(int j=0; j<5;j++){
                schemeCell[i][j]=new SchemeCell();
            }
        }


        schemeCell[2][1].setDado(dice3D);
        schemeCell[2][2].setDado(dice2D);
        schemeCell[1][3].setDado(dice1D);
        scheme = new Scheme("test", 5, schemeCell);


    }

    /**
     * check if we move correctly the dices
     * @author Abu Hussnain Saeed
     */
    @Test
    public void moveDices() {

        //first test
        try{
            scheme = TaglierinaManuale.moveDices(roundTrace, scheme, 1 , 3,1, 0,2, 2, 2, 1);
        }
        catch (WrongInputException e){
            Assert.assertEquals(scheme,scheme);
        }
        catch (CannotPlaceDiceException e){}

        //second test
        try{
            scheme = TaglierinaManuale.moveDices(roundTrace, scheme, 2 , 1,0, 0,2, 2, 3, 1);
        }
        catch (WrongInputException e){}
        catch (CannotPlaceDiceException e){
            Assert.assertEquals(scheme,scheme);
        }

        //third test
        try{
            scheme = TaglierinaManuale.moveDices(roundTrace, scheme, 2, 1,1, 4,2, 2, 0, 3);

        }
        catch (WrongInputException e){}
        catch (CannotPlaceDiceException e){}

        Assert.assertEquals(dice3D, scheme.getScheme()[1][4].getDado());
        Assert.assertEquals(dice2D, scheme.getScheme()[0][3].getDado());
        Assert.assertEquals(dice1D, scheme.getScheme()[1][3].getDado());
        Assert.assertEquals(true, !scheme.getScheme()[2][1].isOccupied());
        Assert.assertEquals(true, !scheme.getScheme()[2][2].isOccupied());

        //fourth test
        try{
            scheme = TaglierinaManuale.moveDices(roundTrace,scheme,1,4,2,3,0,3,0,0);
        }
        catch (WrongInputException|CannotPlaceDiceException e){}
        Assert.assertEquals(dice3D,scheme.getScheme()[2][3].getDado());
        Assert.assertEquals(dice2D,scheme.getScheme()[0][3].getDado());
        Assert.assertEquals(dice1D,scheme.getScheme()[1][3].getDado());
        Assert.assertEquals(true,!scheme.getScheme()[1][4].isOccupied());

        int cont=0;
        for(int i=0; i<4; i++){
            for(int j=0; j<5; j++){
                if(!scheme.getScheme()[i][j].isOccupied()){
                    Assert.assertEquals(Color.WHITE, scheme.getScheme()[i][j].getColor());
                    cont++;
                }
            }
        }
        Assert.assertEquals(17, cont);
    }
}