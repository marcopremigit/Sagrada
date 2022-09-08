package Client.View.CLI;

import Shared.Color;
import org.fusesource.jansi.Ansi;

public class CLI{
    //strategy context
    private CLIInterface strategy;

    private static final Ansi.Color OPTIONCOLOR = Ansi.Color.BLUE;

    private static final Ansi.Color DEFAULTCOLOR = Ansi.Color.DEFAULT;

    private static final Ansi.Color ACCEPTEDCOLOR = Ansi.Color.GREEN;

    private static final Ansi.Color WARNINGCOLOR = Ansi.Color.RED;

    /**
     * @param strategy context's strategy to be set
     * @author Fabrizio Siciliano
     * */
    void setStrategy(CLIInterface strategy){
        this.strategy = strategy;
    }

    /**
     * Executes actual CLI strategy
     * @author Fabrizio Siciliano
     * */
    void executeStrategy(){
        strategy.showCLI();
    }

    /**
     * @param color color to be computed
     * @return ansi color value
     * @author Fabrizio Siciliano
     * */
    public static Ansi.Color getAnsiColor(Color color) {
        switch (color){
            case RED:
                return Ansi.Color.RED;
            case YELLOW:
                return Ansi.Color.YELLOW;
            case PURPLE:
                return Ansi.Color.MAGENTA;
            case GREEN:
                return Ansi.Color.GREEN;
            case BLUE:
                return Ansi.Color.BLUE;
            default:
                return Ansi.Color.WHITE;
        }
    }

    /**
     * @param top value of the dice
     * @return unicode string for dice visualization
     * @author Fabrizio Siciliano
     * */
    public static String getDiceUnicode(int top){
        switch (top){
            case 1:
                return "\u2680";
            case 2:
                return "\u2681";
            case 3:
                return "\u2682";
            case 4:
                return "\u2683";
            case 5:
                return "\u2684";
            case 6:
                return "\u2685";
            default:
                return null;
        }
    }

    /**
     * @return color for accepted string values
     * @author Fabrizio Siciliano
     * */
    Ansi.Color getAcceptedColor() {
        return ACCEPTEDCOLOR;
    }

    /**
     * @return color for default string values
     * @author Fabrizio Siciliano
     * */
    Ansi.Color getDefaultColor() {
        return DEFAULTCOLOR;
    }

    /**
     * @return color for option string values
     * @author Fabrizio Siciliano
     * */
    Ansi.Color getOptionColor() {
        return OPTIONCOLOR;
    }

    /**
     * @return color for warning string values
     * @author Fabrizio Siciliano
     * */
    Ansi.Color getWarningColor() {
        return WARNINGCOLOR;
    }
}
