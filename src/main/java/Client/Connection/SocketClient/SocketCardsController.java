package Client.Connection.SocketClient;

import Client.Connection.CardsControllerInterface;
import Client.View.ViewControllers.Controller;
import Shared.Exceptions.CannotSaveCardException;
import Shared.Model.ObjectiveCard.PrivateObjective;
import Shared.Model.ObjectiveCard.PublicObjective;
import Shared.Model.Schemes.SchemeCard;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.util.ArrayList;

public class SocketCardsController implements CardsControllerInterface {
    private static final String PRIVATEOBJECTIVE1 = "getprivateobjective1";
    private static final String PRIVATEOBJECTIVE2 = "getprivateobjective2";
    private static final String GETSCHEMECARD = "getSchemeCard";
    private static final String PUBLICOBJECTIVES = "getpublicobjectives";
    private static final String SETSCHEME = "setscheme";
    private static final String SAVECARD = "savecard";
    private static final String SETPRIVATE = "setprivate";

    private ClientBuffer buffer;
    private String regex;
    private Controller controller;
    private PrintWriter print;

    public SocketCardsController(ClientBuffer buffer, String regex, Controller controller, PrintWriter print){
        this.buffer = buffer;
        this.regex = regex;
        this.controller = controller;
        this.print=print;
    }

    @Override
    public ArrayList<PrivateObjective> getPrivateObjective() {
        ArrayList<PrivateObjective> privateObjectives = new ArrayList<>();
        PrivateObjective privateObjective2 = null;
        print.println(PRIVATEOBJECTIVE1 + regex + controller.getPlayerName());
        String object1 = null;
        while(object1==null){
            object1=buffer.checkInBuffer("{\"privatecard\"");
        }
        if(!object1.equals("null")){
            privateObjectives.add(PrivateObjective.fromJSONToPrivate((new JSONObject(object1)).getJSONObject("privatecard")));
        }

        print.println(PRIVATEOBJECTIVE2 + regex +controller.getPlayerName());
        String object2 = null;
        while(object2==null){
            object2=buffer.checkInBuffer("{\"privatecard\"");
        }
        if(!object2.endsWith("null")){
            privateObjective2=PrivateObjective.fromJSONToPrivate((new JSONObject(object2)).getJSONObject("privatecard"));
            privateObjectives.add(privateObjective2);
        }
       return privateObjectives;
    }

    @Override
    public SchemeCard getSchemeCard() {
        String object = null;
        print.println(GETSCHEMECARD);
        while(object==null){
            object=buffer.checkInBuffer("{\"schemecard\"");
        }
        if(!object.equals("null"))
            return SchemeCard.fromJSONToCard((new JSONObject(object)).getJSONObject("schemecard"));
        else
            return null;
    }

    @Override
    public ArrayList<PublicObjective> getPublicObjectives() {
        print.println(PUBLICOBJECTIVES);
        String object=null;
        while(object==null){
            object=buffer.checkInBuffer("{\"publics\"");
        }
        ArrayList<PublicObjective> publicObjectives = new ArrayList<>();
        JSONObject obj = new JSONObject(object);
        for(Object jsonPublic : obj.getJSONArray("publics")){
            publicObjectives.add(PublicObjective.fromJSONtoPublic((JSONObject) jsonPublic));
        }
        return publicObjectives;
    }

    @Override
    public void setScheme(String schemeName) {
        print.println(SETSCHEME + regex + controller.getPlayerName() + regex + schemeName);
    }

    @Override
    public void saveCustomCard(String[] names, String[] favors, String[] scheme1, String[] scheme2, String[] col, String[] rows) throws CannotSaveCardException {
        JSONObject obj = new JSONObject();
        obj.put("schemename1", names[0]);
        obj.put("schemefavors1", favors[0]);
        JSONArray array1 = new JSONArray();
        for(int i=0; i<scheme1.length; i++){
            array1.put(scheme1[i]);
        }
        obj.put("scheme1", array1);
        obj.put("schemename2", names[1]);
        obj.put("schemefavors2", favors[1]);
        JSONArray array2 = new JSONArray();
        for(int i=0; i<scheme2.length; i++){
            array2.put(scheme2[i]);
        }
        obj.put("scheme2", array2);
        obj.put("frontcol", col[0]);
        obj.put("rearcol", col[1]);
        obj.put("frontrow", rows[0]);
        obj.put("rearrow", rows[1]);
        print.println(SAVECARD + regex + obj.toString());
        String returnVal= null;
        while(returnVal==null){
            returnVal = buffer.checkInBuffer("savecard&");
        }
        if(returnVal.endsWith("CannotSaveCardException"))
            throw new CannotSaveCardException();
    }

    @Override
    public void setPrivateObjective(String name) {
        print.println(SETPRIVATE + regex + name);
    }
}
