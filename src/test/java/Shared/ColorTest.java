package Shared;

import Shared.Color;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;



public class ColorTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void stringToColor() {
     Color color =  Color.WHITE;
     Assert.assertEquals(Color.WHITE, color.stringToColor("W"));
     Assert.assertEquals(null,Color.stringToColor("E"));
    }
}