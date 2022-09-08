package Server.ToolCards;

import Shared.Exceptions.IllegalRoundException;
import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;
import Shared.Model.RoundTrace.RoundTraceCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TaglierinaCircolareTest {
    RoundTrace roundTrace = new RoundTrace();
    List<Dice> draftpool;
    Dice dice1D;
    Dice dice2D;
    Dice dice3D;
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



    @Before
    public void setUp() throws Exception {
        draftpool = new ArrayList<>();
        dice1D = new Dice(Color.PURPLE);
        dice1D.setTop(3);
        dice2D = new Dice(Color.GREEN);
        dice2D.setTop(2);
        dice3D = new Dice(Color.YELLOW);
        dice3D.setTop(5);
        draftpool.add(0,dice1D);
        draftpool.add(1, dice2D);
        draftpool.add(2, dice3D);

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
        dice15 = new Dice(Color.BLUE);
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

    }

    @Test
    public void switchDices() {
        try {
            int oldDraftPoolSize = draftpool.size();
            TaglierinaCircolare.switchDices(draftpool, roundTrace, dice1D, 1, 0);
            Assert.assertEquals(dice1D, roundTrace.getPool(1).get(0));
            Assert.assertEquals(dice2, draftpool.get(draftpool.size()-1));
            Assert.assertEquals(dices1, roundTrace.getPool(0));
            Assert.assertEquals(dices3, roundTrace.getPool(2));
            Assert.assertEquals(dices4, roundTrace.getPool(3));
            Assert.assertEquals(dices5, roundTrace.getPool(4));
            Assert.assertEquals(dices6, roundTrace.getPool(5));
            Assert.assertEquals(dices7, roundTrace.getPool(6));
            Assert.assertEquals(dices8, roundTrace.getPool(7));
            Assert.assertEquals(dices9, roundTrace.getPool(8));
            Assert.assertEquals(dices10, roundTrace.getPool(9));
            Assert.assertEquals(oldDraftPoolSize, draftpool.size());
            Assert.assertEquals(true, draftpool.contains(dice2D));
            Assert.assertEquals(true, draftpool.contains(dice3D));
            Assert.assertEquals(false, draftpool.contains(dice1D));
        } catch (IllegalRoundException e) {
        }
    }
}