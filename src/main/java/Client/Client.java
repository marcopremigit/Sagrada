package Client;

import Client.View.ViewControllers.ToolCardsController;

public class Client {

    private volatile ClientInterface context;

    private volatile ToolCardsController toolViewController;

    public Client(ClientInterface context) {
        this.toolViewController = new ToolCardsController(this);
        this.context = context;
    }

    public ClientInterface getHandler() {
        return context;
    }

    public ToolCardsController getToolViewController() {
        return toolViewController;
    }
}
