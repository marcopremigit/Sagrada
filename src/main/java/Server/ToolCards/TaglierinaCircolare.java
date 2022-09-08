package Server.ToolCards;

import Shared.Exceptions.IllegalRoundException;
import Shared.Model.Dice.Dice;
import Shared.Model.RoundTrace.RoundTrace;

import java.util.List;

public class TaglierinaCircolare {
    public static void switchDices(List<Dice> draftPool, RoundTrace roundTrace, Dice fromDraftPool, int round, int posInRound) throws IllegalRoundException{
        Dice temp = roundTrace.getTrace().get(round).getCell().get(posInRound);
        roundTrace.getPool(round).set(posInRound, fromDraftPool);
        draftPool.remove(fromDraftPool);
        draftPool.add(temp);
    }
}
