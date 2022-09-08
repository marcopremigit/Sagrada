package Client.View.ViewControllers;

public class ToolCardStrategyController {
    private ToolCardStrategyInterface strategy;

    public void setStrategy(ToolCardStrategyInterface strategy1){
        strategy = strategy1;
    }
    public boolean executeStrategy(boolean dicePlaced){
        return strategy.useToolCard(dicePlaced);
    }

}
