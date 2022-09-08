package Server.SchemeCardsHandler;

import Shared.Color;
import Shared.Exceptions.CannotSaveCardException;
import Shared.Model.Schemes.Scheme;
import Shared.Model.Schemes.SchemeCard;
import Shared.Model.Schemes.SchemeCell;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;

public class SchemeCardHandler {
    private static final String PATH = "/CustomCards/";
    private Document doc;

    public SchemeCardHandler(){
        this.doc = null;
    }

    /**
     * builds new XML file with proper tags and attributes
     * @param frontName first card's name to be saved
     * @param frontFavors first card's favors' value to be saved
     * @param frontCellsNumber first ordered list of numbers of cells to be saved
     * @param frontCellsColor first ordered list of colors of cells to be saved
     * @param rearName second card's name to be saved
     * @param rearFavors second card's favors' value to be saved
     * @param rearCellsNumber second ordered list of numbers of cells to be saved
     * @param rearCellsColor second ordered list of colors of cells to be saved
     * @author Fabrizio Siciliano
     * */
    public void buildCustomCard(String frontName, String frontFavors, String[] frontCellsNumber, String[] frontCellsColor, String rearName, String rearFavors, String[] rearCellsNumber, String[] rearCellsColor, String[] columns, String[] rows) throws CannotSaveCardException{
        try {
            File file = new File(Paths.get(System.getProperty("user.home"), ".serversagrada").toAbsolutePath().toString(), "UserCard.xml");
            if(!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
                try(PrintWriter pw = new PrintWriter(new FileOutputStream(file), true)){

                    Transformer transformer = TransformerFactory.newInstance().newTransformer();
                    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                    StringWriter stringWriter = new StringWriter();
                    StreamResult result = new StreamResult(stringWriter);
                    DOMSource source = new DOMSource(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument().createElement("CustomCards"));
                    transformer.transform(source, result);
                    String finalString = stringWriter.getBuffer().toString();
                    pw.write(finalString);
                }
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(file);

            Element root = doc.getDocumentElement();

            if(!exists(root.getElementsByTagName("card"),frontName) && !exists(root.getElementsByTagName("card"), rearName)) {
                Element card = doc.createElement("card");
                //root.front
                Element front = doc.createElement("scheme");
                card.appendChild(front);

                //front attributes
                Attr frontNameAttr = doc.createAttribute("name");
                frontNameAttr.setValue(frontName);
                front.setAttributeNode(frontNameAttr);

                Attr frontFavorsAttr = doc.createAttribute("favors");
                frontFavorsAttr.setValue(frontFavors);
                front.setAttributeNode(frontFavorsAttr);

                Attr frontColumns = doc.createAttribute("columns");
                frontColumns.setValue(columns[0]);
                front.setAttributeNode(frontColumns);

                Attr frontRows = doc.createAttribute("rows");
                frontRows.setValue(rows[0]);
                front.setAttributeNode(frontRows);

                //root.front.cell
                addXMLCells(front, frontCellsNumber, frontCellsColor);

                //root.rear
                Element rear = doc.createElement("scheme");
                card.appendChild(rear);

                //rear attributes
                Attr rearNameAttr = doc.createAttribute("name");
                rearNameAttr.setValue(rearName);
                rear.setAttributeNode(rearNameAttr);

                Attr rearFavorsAttr = doc.createAttribute("favors");
                rearFavorsAttr.setValue(rearFavors);
                rear.setAttributeNode(rearFavorsAttr);

                Attr rearColumns = doc.createAttribute("columns");
                rearColumns.setValue(columns[1]);
                rear.setAttributeNode(rearColumns);

                Attr rearRows = doc.createAttribute("rows");
                rearRows.setValue(rows[1]);
                rear.setAttributeNode(rearRows);

                //root.rear.cell
                addXMLCells(rear, rearCellsNumber, rearCellsColor);

                root.appendChild(card);

                //initializes transformer
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");

                StringWriter stringWriter = new StringWriter();
                StreamResult result = new StreamResult(stringWriter);
                DOMSource source = new DOMSource(doc);
                transformer.transform(source, result);
                String finalString = stringWriter.getBuffer().toString();

                try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(Paths.get(System.getProperty("user.home"), ".serversagrada").toAbsolutePath().toString(), "UserCard.xml")), true)) {
                    writer.print(finalString);
                }
            } else{
                throw new CannotSaveCardException();
            }
            //uncomment this lines for output to console for testing and checking
            //StreamResult consoleResult = new StreamResult(System.out);
            //transformer.transform(source,consoleResult);
        } catch(Exception e){
            e.printStackTrace();
            if(e instanceof CannotSaveCardException)
                throw new CannotSaveCardException();
        }
    }

    /**
     * XML cells adder
     * @param scheme adding children to this element
     * @param cellsNumber children to be added
     * @param cellsColor children to be added
     * @author Fabrizio Siciliano
     * */
    private void addXMLCells(Element scheme, String[] cellsNumber, String[] cellsColor){

        Element cell;
        Attr cellNumAttr;
        Attr cellColorAttr;

        for(int i=0; i<cellsNumber.length; i++){
            cell = doc.createElement("cell");

            cellNumAttr = doc.createAttribute("number");
            cellNumAttr.setValue(cellsNumber[i]);
            cell.setAttributeNode(cellNumAttr);

            cellColorAttr = doc.createAttribute("color");
            cellColorAttr.setValue(cellsColor[i]);
            cell.setAttributeNode(cellColorAttr);

            cell.appendChild(doc.createTextNode("Cell" +i));
            scheme.appendChild(cell);
        }
    }

    /**
     * reads and returns card with given name
     * @param cardName name of the card to read
     * @return card read
     * @exception NullPointerException if file could not be found
     * @exception IOException if IO error occurs
     * @author Fabrizio Siciliano, Marco Premi
     * */
    public SchemeCard readCustomCard(String cardName) throws NullPointerException, IOException{
        try{
            InputStream in;
            in = getClass().getResourceAsStream(PATH+cardName+".xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            this.doc = dBuilder.parse(in);
        }
        catch(ParserConfigurationException | SAXException e){
            e.printStackTrace();
        }

        doc.getDocumentElement().normalize();

        NodeList schemes = doc.getElementsByTagName("scheme");

        //saves completeSchemeFront
        Node scheme = schemes.item(0);
        Element element = (Element) scheme;
        String frontName = new String(element.getAttribute("name"));
        int frontFavors =Integer.parseInt(element.getAttribute("favors"));

        SchemeCell[][] completeSchemeFront = createScheme(scheme.getChildNodes(), Integer.parseInt(element.getAttribute("columns")), Integer.parseInt(element.getAttribute("rows")));

        //saves completeSchemeRear
        scheme = schemes.item(1);
        element = (Element) scheme;
        String rearName = new String(element.getAttribute("name"));
        int rearFavors = Integer.parseInt(element.getAttribute("favors"));

        SchemeCell[][] completeSchemeRear = createScheme(scheme.getChildNodes(), Integer.parseInt(element.getAttribute("columns")), Integer.parseInt(element.getAttribute("rows")));

        //creates and returns card
        Scheme front = new Scheme(frontName,frontFavors,completeSchemeFront);
        Scheme rear = new Scheme(rearName,rearFavors,completeSchemeRear);

        return new SchemeCard(front, rear);
    }

    public ArrayList<SchemeCard> readCards(){
        NodeList nl;
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.doc = db.parse(new File(Paths.get(System.getProperty("user.home"), ".serversagrada").toAbsolutePath().toString(), "UserCard.xml"));
        } catch (SAXException | IOException | ParserConfigurationException i){
            return null;
        }
        doc.getDocumentElement().normalize();

        nl = doc.getElementsByTagName("card");

        ArrayList<SchemeCard> list = new ArrayList<>();
        boolean frontDone = false;
        Scheme front = null, rear;
        if(nl != null){
            for(int i=0; i<nl.getLength(); i ++){
                if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    NodeList schemes = nl.item(i).getChildNodes();
                    for(int j=0; j<schemes.getLength(); j++) {
                        if(schemes.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Element element = (Element) schemes.item(j);
                            if(!frontDone) {
                                String frontName = element.getAttribute("name");
                                int frontFavors = Integer.parseInt(element.getAttribute("favors"));
                                SchemeCell[][] frontSchemeCells = createScheme(element.getElementsByTagName("cell"), Integer.parseInt(element.getAttribute("columns")), Integer.parseInt(element.getAttribute("rows")));
                                front = new Scheme(frontName, frontFavors, frontSchemeCells);
                                frontDone = true;
                            }
                            else {
                                String rearName = element.getAttribute("name");
                                int rearFavors = Integer.parseInt(element.getAttribute("favors"));
                                SchemeCell[][] rearSchemeCells = createScheme(element.getElementsByTagName("cell"), Integer.parseInt(element.getAttribute("columns")), Integer.parseInt(element.getAttribute("rows")));

                                rear = new Scheme(rearName, rearFavors, rearSchemeCells);
                                list.add(new SchemeCard(front, rear));
                                frontDone = false;
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * creates scheme from file read
     * @param cells elements to be added to the scheme
     * @return scheme read from file
     * @author Fabrizio Siciliano
     * */
    private SchemeCell[][] createScheme(NodeList cells, int columns, int rows){
        int cellNode, i = 0, j = 0;
        SchemeCell[][] completeScheme = new SchemeCell[rows][columns];

        for (cellNode = 0; cellNode < cells.getLength(); cellNode++) {
            Node cell = cells.item(cellNode);
            if(cell.getNodeType() == Node.ELEMENT_NODE) {
                Element el = (Element) cell;
                if (j == columns) {
                    j = 0;
                    i++;
                }
                if (el.getAttribute("color").equals("W") && el.getAttribute("number").equals("0")) {
                    completeScheme[i][j] = new SchemeCell();
                } else {
                    if (!el.getAttribute("color").equals("W")) {
                        completeScheme[i][j] = new SchemeCell(Color.stringToColor(el.getAttribute("color")));
                    } else {
                        if (el.getAttribute("color").equals("W") && !el.getAttribute("number").equals("0")) {
                            completeScheme[i][j] = new SchemeCell(Integer.parseInt(el.getAttribute("number")));
                        }
                    }
                }
                j++;
            }
        }
        return completeScheme;
    }

    private boolean exists(NodeList cards, String name){
        for(int i=0; i<cards.getLength(); i++){
            if(cards.item(i).getNodeType() == Node.ELEMENT_NODE){
                NodeList schemes = cards.item(i).getChildNodes();
                for(int j=0; j<schemes.getLength(); j++){
                    if(schemes.item(j).getNodeType() == Node.ELEMENT_NODE){
                        Element el = (Element) schemes.item(j);
                        if(el.getAttribute("name").equals(name))
                            return true;
                    }
                }
            }
        }
        return false;
    }
}
