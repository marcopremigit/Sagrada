package Shared.Model.Schemes;

import Server.SchemeCardsHandler.SchemeCardHandler;
import Shared.Color;
import Shared.Exceptions.CannotSaveCardException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class SchemeCardHandlerTest {
    private SchemeCardHandler SCH;

    @Before
    public void setUp() throws Exception {
        SCH = new SchemeCardHandler();
        Assert.assertNotNull(SCH);
    }

    @Before
    public void buildCustomCard() {
        SchemeCardHandler UUT = new SchemeCardHandler();
        String[] frontCellNumbers1 = new String[]{"0","0","1","0","0","1","0","3","0","2","0","5","4","6","0","0","0","5","0","0"};
        String[] frontCellColors1 = new String[]{Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString(), Color.WHITE.toString(),Color.WHITE.toString(), Color.GREEN.toString(), Color.WHITE.toString(),Color.BLUE.toString(),Color.WHITE.toString(),Color.BLUE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.GREEN.toString(), Color.WHITE.toString(), Color.BLUE.toString(), Color.WHITE.toString(),Color.GREEN.toString(), Color.WHITE.toString()};
        String[] frontCellNumbers2 = new String[]{"0","1","0","0","4","6","0","2","5","0","1","0","5","3","0","0","0","0","0","0"};
        String[] frontCellColors2 = new String[]{Color.WHITE.toString(),Color.WHITE.toString(), Color.GREEN.toString(), Color.PURPLE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.PURPLE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.GREEN.toString(), Color.WHITE.toString(), Color.GREEN.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.PURPLE.toString(),Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString() };

        String[] col = {"5", "5"};
        String[] rows = {"4", "4"};
        try {
            SCH.buildCustomCard("LuxMondi", "4", frontCellNumbers1, frontCellColors1, "LuxAstram", "5", frontCellNumbers2, frontCellColors2, col, rows);
        } catch (CannotSaveCardException c){ }
    }

    @Test
    public void readCustomCard() {
        try{
            SchemeCard carta = SCH.readCustomCard("Gravitas-Water of life");
            Assert.assertTrue(carta.getFront().getName().equals("Gravitas"));
            Assert.assertTrue(carta.getFront().getFavors()==5);
            for(int i=0; i<4; i++){
                for(int j=0; j<5; j++){
                    Assert.assertFalse(carta.getFront().getScheme()[i][j].isOccupied());
                    Assert.assertTrue(carta.getFront().getScheme()[i][j].getNum()==0
                            || carta.getFront().getScheme()[i][j].getNum()==1
                            || carta.getFront().getScheme()[i][j].getNum()==2
                            || carta.getFront().getScheme()[i][j].getNum()==3
                            || carta.getFront().getScheme()[i][j].getNum()==4
                            || carta.getFront().getScheme()[i][j].getNum()==5
                            || carta.getFront().getScheme()[i][j].getNum()==6);
                    Assert.assertFalse(carta.getFront().getScheme()[i][j].getNum()<0 || carta.getFront().getScheme()[i][j].getNum()>6);
                }
            }
            Assert.assertTrue(carta.getRear().getName().equals("Water of life"));
            Assert.assertTrue(carta.getRear().getFavors()==6);
            for(int i=0; i<4; i++){
                for(int j=0; j<5; j++){
                    Assert.assertFalse(carta.getRear().getScheme()[i][j].isOccupied());
                    Assert.assertTrue(carta.getRear().getScheme()[i][j].getNum()==0
                            || carta.getRear().getScheme()[i][j].getNum()==1
                            || carta.getRear().getScheme()[i][j].getNum()==2
                            || carta.getRear().getScheme()[i][j].getNum()==3
                            || carta.getRear().getScheme()[i][j].getNum()==4
                            || carta.getRear().getScheme()[i][j].getNum()==5
                            || carta.getRear().getScheme()[i][j].getNum()==6);
                    Assert.assertFalse(carta.getRear().getScheme()[i][j].getNum()<0 || carta.getRear().getScheme()[i][j].getNum()>6);
                }
            }
        }catch (IOException e){}

    }

    @After
    public void removeCustomCard() {
       // Assert.assertEquals(true,SCH.removeCustomCard("LuxMondi-LuxAstram"));
       // Assert.assertEquals(false, SCH.removeCustomCard("Pippo-Pluto"));
    }
}