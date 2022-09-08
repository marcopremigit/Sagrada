package Server.ToolCards;

import Shared.Color;
import Shared.Model.Dice.Dice;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class PennelloPerPastaSaldaTest {
    Dice dice1;

    @Before
    public void setUp() throws Exception {
        dice1 = new Dice(Color.PURPLE);
        dice1.setTop(3);
    }

    @Test
    public void reRollDice() {
        PennelloPerPastaSalda.reRollDice(dice1);
        Assert.assertEquals(Color.PURPLE, dice1.getColor());
    }
}