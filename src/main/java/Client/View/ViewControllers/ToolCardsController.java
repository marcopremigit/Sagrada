package Client.View.ViewControllers;

import Client.Client;
import Client.View.Tools.*;
import Client.View.GUI.GUIStaticMethods;

public class ToolCardsController{
    private Client client;
    private ToolCardStrategyController toolController;

    //TOOL CONTROLLERS-----------------------------------------------------------------------------------------
    private AlesatorePerLaminaDiRame alesatorePerLaminaDiRame;
    private DiluentePerPastaSalda diluentePerPastaSalda;
    private Lathekin lathekin;
    private Martelletto martelletto;
    private NormalMove normalMove;
    private PennelloPerEglomise pennelloPerEglomise;
    private PennelloPerPastaSalda pennelloPerPastaSalda;
    private PinzaSgrossatrice pinzaSgrossatrice;
    private RigaInSughero rigaInSughero;
    private TaglierinaCircolare taglierinaCircolare;
    private TaglierinaManuale taglierinaManuale;
    private TamponeDiamantato tamponeDiamantato;
    private TenagliaARotelle tenagliaARotelle;
    private CheckSingleToolsCLI checkSingleToolsCLI;
    //--------------------------------------------------------------------------------------------------------

    public ToolCardsController(Client client){
        this.client = client;
        this.toolController = new ToolCardStrategyController();
        this.alesatorePerLaminaDiRame = new AlesatorePerLaminaDiRame(client);
        this.diluentePerPastaSalda = new DiluentePerPastaSalda(client);
        this.lathekin = new Lathekin(client);
        this.martelletto = new Martelletto(client);
        this.normalMove = new NormalMove(client);
        this.pennelloPerEglomise = new PennelloPerEglomise(client);
        this.pennelloPerPastaSalda = new PennelloPerPastaSalda(client);
        this.pinzaSgrossatrice = new PinzaSgrossatrice(client);
        this.rigaInSughero = new RigaInSughero(client);
        this.taglierinaCircolare = new TaglierinaCircolare(client);
        this.taglierinaManuale = new TaglierinaManuale(client);
        this.tamponeDiamantato = new TamponeDiamantato(client);
        this.tenagliaARotelle = new TenagliaARotelle(client);
    }

    public boolean useToolCard(String id, boolean dicePlaced){
        if(!client.getHandler().getMainController().isUsingCLI()){
            client.getHandler().getMainController().getPlayerHUD().getPlayerFacade().updategrid(client.getHandler().getTableController().getScheme(client.getHandler().getMainController().getPlayerName()));
            GUIStaticMethods.setButtonFacadeNull(client);
            GUIStaticMethods.setButtonDraftPoolNull(client);
        }

        switch (id){
            case "Pinza Sgrossatrice":
                toolController.setStrategy(pinzaSgrossatrice);
                return toolController.executeStrategy(dicePlaced);
            case "Pennello per Eglomise":{
                toolController.setStrategy(pennelloPerEglomise);
                return toolController.executeStrategy(dicePlaced);

            }
            case "Alesatore per lamina di rame":{
                toolController.setStrategy(alesatorePerLaminaDiRame);
                return toolController.executeStrategy(dicePlaced);

            }
            case "Lathekin":{
                toolController.setStrategy(lathekin);
                return toolController.executeStrategy(dicePlaced);

            }
            case "Taglierina circolare":{
                toolController.setStrategy(taglierinaCircolare);
                return toolController.executeStrategy(dicePlaced);

            }
            case "Pennello per pasta salda":{
                toolController.setStrategy(pennelloPerPastaSalda);
                return toolController.executeStrategy(dicePlaced);

            }
            case "Martelletto":{
                toolController.setStrategy(martelletto);
                return toolController.executeStrategy(dicePlaced);

            }
            case "Tenaglia a Rotelle":{
                toolController.setStrategy(tenagliaARotelle);
                return toolController.executeStrategy(dicePlaced);

            }
            case "Riga in Sughero":{
                toolController.setStrategy(rigaInSughero);
                return toolController.executeStrategy(dicePlaced);

            }
            case "Tampone Diamantato":{
                toolController.setStrategy(tamponeDiamantato);
                return toolController.executeStrategy(dicePlaced);

            }
            case "Diluente per Pasta Salda":{
                toolController.setStrategy(diluentePerPastaSalda);
                return toolController.executeStrategy(dicePlaced);

            }
            case "Taglierina Manuale":{
                toolController.setStrategy(taglierinaManuale);
                return toolController.executeStrategy(dicePlaced);

            }
            case "Normal Move":{
                toolController.setStrategy(normalMove);
                return toolController.executeStrategy(dicePlaced);

            }
        }
      return false;
    }

    public AlesatorePerLaminaDiRame getAlesatorePerLaminaDiRame(){
        return alesatorePerLaminaDiRame;
    }

    public DiluentePerPastaSalda getDiluentePerPastaSalda() {
        return diluentePerPastaSalda;
    }

    public Lathekin getLathekin() {
        return lathekin;
    }

    public Martelletto getMartelletto() {
        return martelletto;
    }

    public NormalMove getNormalMove() {
        return normalMove;
    }

    public PennelloPerEglomise getPennelloPerEglomise() {
        return pennelloPerEglomise;
    }

    public PennelloPerPastaSalda getPennelloPerPastaSalda() {
        return pennelloPerPastaSalda;
    }

    public PinzaSgrossatrice getPinzaSgrossatrice() {
        return pinzaSgrossatrice;
    }

    public RigaInSughero getRigaInSughero() {
        return rigaInSughero;
    }

    public TaglierinaCircolare getTaglierinaCircolare() {
        return taglierinaCircolare;
    }

    public TaglierinaManuale getTaglierinaManuale() {
        return taglierinaManuale;
    }

    public TamponeDiamantato getTamponeDiamantato() {
        return tamponeDiamantato;
    }

    public TenagliaARotelle getTenagliaARotelle() {
        return tenagliaARotelle;
    }
}

