package rocks.zipcode.FTS;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class ReadXMLFile {

    public static void main(String argv[]) {
//        if  (argv.length != 2) {
//            throw new Exception("You forgot the filename...");
//        }
        String fname = "/Volumes/Terabyte/kristofer/Downloads/enwiki-latest-abstracts.xml";
        new ReadXMLFile().readFile(fname, true);
    }

    public NodeList readFile(String filename, boolean printFlag) {
        try {
            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("doc");

            if (printFlag) {
                System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
                System.out.println("----------------------------");

                for (int temp = 0; temp < nList.getLength(); temp++) {
                    if (temp > 10) break;
                    Node nNode = nList.item(temp);
                    System.out.println("\nCurrent Element :" + nNode.getNodeName());
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        System.out.println("title : "
                                + eElement.getElementsByTagName("title")
                                .item(0).getTextContent());
                        System.out.println("url : "
                                + eElement.getElementsByTagName("url")
                                .item(0).getTextContent());
                        System.out.println("abstract : "
                                + eElement.getElementsByTagName("abstract")
                                .item(0).getTextContent());
                    }
                }
            }
            return nList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}