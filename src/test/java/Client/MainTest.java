package Client;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MainTest {
    @Test
    public void getMainBackground() {
        Assert.assertEquals("Images/Sagrada.jpg" , Main.getMainBackground());
    }

    @Test
    public void getPlayingBackground() {
        Assert.assertEquals("Images/background.jpg" , Main.getPlayingBackground());
    }

    @Test
    public void getSetupBackeground() {
        Assert.assertEquals("Images/Tools/toolCardBack.jpg" , Main.getSetupBackeground());
    }

    @Test
    public void main() {
        /*
         * Can't be tested.
         * Requires view
         * */
    }

    @Test
    public void start() {
        /*
         * Can't be tested.
         * Requires view
         * */
    }

    @Test
    public void startGUI() {
        /*
         * Can't be tested.
         * Requires view
         * */
    }

    @Test
    public void getRootLayout() {
        /*
         * Can't be tested.
         * Requires view
         * */
    }

    @Test
    public void getMainBackground1() {
        /*
         * Can't be tested.
         * Requires view
         * */
    }

    @Test
    public void getPlayingBackground1() {
        /*
         * Can't be tested.
         * Requires view
         * */
    }

    @Test
    public void getSetupBackeground1() {
        /*
         * Can't be tested.
         * Requires view
         * */
    }

    @Test
    public void setBackgroundImage() {
        /*
         * Can't be tested.
         * Requires view
         * */
    }
}