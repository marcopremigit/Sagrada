package Client.Connection.SocketClient;

import Client.Connection.TurnControllerInterface;
import Client.View.ViewControllers.Controller;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class SocketTurnController implements TurnControllerInterface {
    private static final String FIRSTTURN = "iffirstturn";
    private static final String ENDTURN = "endturn";
    private static final String ASKROUND = "whatroundis";

    private String regex;
    private ClientBuffer buffer;
    private Controller controller;
    private PrintWriter print;

    public SocketTurnController(ClientBuffer buffer, String regex, Controller controller, PrintWriter print){
        this.buffer = buffer;
        this.regex = regex;
        this.controller = controller;
        this.print=print;
    }

    @Override
    public boolean isFirstTurn() {
        print.println(FIRSTTURN);
        print.flush();
        String obj = null;
        while(obj==null){
            obj=buffer.checkInBuffer("clockwise&");
        }
        if(obj.endsWith("true")){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void endMyTurn() {
        print.println(ENDTURN + regex + controller.getPlayerName());
        print.flush();
    }

    @Override
    public String whatRoundIs() {
        print.println(ASKROUND);
        print.flush();
        String obj = null;
        while(obj==null){
            obj=buffer.checkInBuffer("round&");
        }
        List<String> list = Arrays.asList(obj.split("&"));
        return list.get(1);
    }

    @Override
    public void startTurn() {
        controller.setMyTurn(true);
    }
}
