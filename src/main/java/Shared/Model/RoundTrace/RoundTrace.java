package Shared.Model.RoundTrace;

import Shared.Model.Dice.Dice;
import Shared.Exceptions.IllegalRoundException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RoundTrace implements Serializable {
    private static final long serialVersionUID = 68;
    private ArrayList<RoundTraceCell> trace;


    /**
     * @author Fabrizio Siciliano
     * */
    public RoundTrace(){
        this.trace = new ArrayList<>();
        for(int i=0; i<10; i++){
            this.trace.add(new RoundTraceCell());
        }
    }

    /**
     * @return list of cells for the trace
     * @author Fabrizio Siciliano
     * */
    public List<RoundTraceCell> getTrace() {
        return trace;
    }


    /**
     * @param round indicates index in trace
     * @param cell indicates cell to be inserted in trace
     * @exception IllegalRoundException if round is out of bounds
     * @author Fabrizio Siciliano
     * */
    public void setPool(int round, RoundTraceCell cell) throws IllegalRoundException{
        if(round>=0 && round<=trace.size())
            this.trace.add(round,cell);
        else
            throw new IllegalRoundException("E' fuori dai round massimi");
    }

    /**
     * @param round indicates index in trace to be picked
     * @return list of dices
     * @exception IllegalRoundException if round is out of bounds
     * @author Fabrizio Siciliano
     * */
    public List<Dice> getPool(int round) throws IllegalRoundException{
        if(round>=0 && round<trace.size())
            return this.trace.get(round).getCell();
        else
            throw new IllegalRoundException("E' fuori dai round massimi");
    }

    public static RoundTrace fromJSONToTrace(JSONObject jsonObject){
        RoundTrace trace = new RoundTrace();
        JSONArray array = jsonObject.getJSONArray("roundtrace");
        for(int i=0; i<array.length(); i++){
            trace.getTrace().set(i, RoundTraceCell.fromJSONToRTCell(array.getJSONObject(i)));
        }
        return trace;
    }

    public static JSONObject fromTraceToJSON(RoundTrace trace){
        JSONArray array = new JSONArray();
        for(int i=0; i<trace.getTrace().size(); i++){
            array.put(RoundTraceCell.fromRTCellToJSON(trace.getTrace().get(i)));
        }
        JSONObject object = new JSONObject();
        object.put("roundtrace", array);
        return object;
    }
}
