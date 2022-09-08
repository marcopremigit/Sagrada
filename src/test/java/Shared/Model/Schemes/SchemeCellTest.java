package Shared.Model.Schemes;

import Shared.Model.Dice.Dice;
import Shared.Exceptions.IllegalColorException;
import Shared.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SchemeCellTest {

    private SchemeCell cellColor;
    private SchemeCell cellNumber;
    private SchemeCell cellEmpty;

    @Before
    public void setUp() throws Exception {
        cellEmpty = new SchemeCell();
        cellColor = new SchemeCell(Color.PURPLE);
        cellNumber = new SchemeCell(4);
        System.out.println(cellNumber.toString());
        System.out.println(cellColor.toString());
        System.out.println(cellEmpty.toString());
    }

    @Test
    public void getColor() {
        Assert.assertTrue(cellColor.getColor().equals(Color.PURPLE));
        Assert.assertTrue(cellEmpty.getColor().equals(Color.WHITE));
        Assert.assertTrue(cellNumber.getColor().equals(Color.WHITE));
    }

    @Test
    public void getNum() {
        Assert.assertTrue(cellColor.getNum()== 0);
        Assert.assertTrue(cellEmpty.getNum() == 0);
        Assert.assertTrue(cellNumber.getNum() == 4);
    }

    @Test
    public void getDado() {
        Assert.assertTrue(cellColor.getDado() == null);
        Assert.assertTrue(cellEmpty.getDado() == null);
        Assert.assertTrue(cellNumber.getDado() == null);
    }

    @Test
    public void setDado() {
        try {
            Dice dadoBlu = new Dice(Color.BLUE);
            Dice dadoRosso = new Dice(Color.RED);
            cellNumber.setDado(dadoBlu);
            cellColor.setDado(dadoRosso);

            Assert.assertTrue(cellNumber.getDado().equals(dadoBlu));
            Assert.assertTrue(cellColor.getDado().equals(dadoRosso));
            Assert.assertFalse(cellEmpty.getDado() != null);
        }
        catch (IllegalColorException e){/*never reached*/}
    }

    @Test
    public void removeDado() {
        try {
            cellNumber.setDado(new Dice(Color.RED));
        }
        catch (IllegalColorException e){/*never reached*/}
        Assert.assertTrue(cellNumber.getDado()!=null);
        cellNumber.removeDado();
        Assert.assertTrue(cellNumber.getDado()==null);
    }

    @Test
    public void isOccupied() {
        Assert.assertFalse(cellColor.isOccupied());
        Assert.assertFalse(cellNumber.isOccupied());
        try {
            cellColor.setDado(new Dice(Color.PURPLE));
            Assert.assertTrue(cellColor.isOccupied());
        }catch(IllegalColorException e){/*never reached*/}
    }

    @Test
    public void equals() {
        Assert.assertFalse(cellNumber.equals(cellEmpty));
    }
}