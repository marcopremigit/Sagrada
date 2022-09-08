package Shared.Model.Schemes;

import Server.SchemeCardsHandler.SchemeCardMapper;
import Shared.Color;
import Shared.Exceptions.CannotSaveCardException;
import Shared.Exceptions.CannotSaveCardExceptionTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SchemeCardMapperTest {
    private static SchemeCardMapper SCM;

    @Before
    public void getCardMapper() {
        SCM = SchemeCardMapper.getCardMapper();
        Assert.assertNotNull(SCM);
    }

    @Test
    public void readAllCards() {
        int count = 0;
        InputStream in = getClass().getResourceAsStream("/CustomCards/SchemeCardMap.txt");
        try(BufferedReader buffer = new BufferedReader(new InputStreamReader(in))){
            for(String currentLine = buffer.readLine(); currentLine!=null; currentLine = buffer.readLine()){
                count++;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        //Assert.assertTrue(count+1 == SCM.readAllCards().size());

        for(int i=0; i<SCM.readAllCards().size(); i++){
            System.out.println(SCM.readAllCards().get(i).toString());
        }
    }

    @Test
    public void saveNewCard() throws Exception {
        String[] frontCellNumbers1 = new String[]{"0","0","1","0","0","1","0","3","0","2","0","5","4","6","0","0","0","5","0","0"};
        String[] frontCellColors1 = new String[]{Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString(), Color.WHITE.toString(),Color.WHITE.toString(), Color.GREEN.toString(), Color.WHITE.toString(),Color.BLUE.toString(),Color.WHITE.toString(),Color.BLUE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.GREEN.toString(), Color.WHITE.toString(), Color.BLUE.toString(), Color.WHITE.toString(),Color.GREEN.toString(), Color.WHITE.toString()};
        String[] frontCellNumbers2 = new String[]{"0","1","0","0","4","6","0","2","5","0","1","0","5","3","0","0","0","0","0","0"};
        String[] frontCellColors2 = new String[]{Color.WHITE.toString(),Color.WHITE.toString(), Color.GREEN.toString(), Color.PURPLE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.PURPLE.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.GREEN.toString(), Color.WHITE.toString(), Color.GREEN.toString(), Color.WHITE.toString(), Color.WHITE.toString(), Color.PURPLE.toString(),Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString(),Color.WHITE.toString() };
        String[] col = {"5", "5"};
        String[] rows = {"4", "4"};

        try{
            SCM.saveNewCard("testFront","5",frontCellNumbers1,frontCellColors1,"testRear","3",frontCellNumbers2,frontCellColors2, col, rows);
        }catch (CannotSaveCardException c){}
    }
    @Test
    public void removeCardNamed() {

    }

}