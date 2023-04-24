package server.file.util;

import org.jdom2.input.DOMBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Jdom document creator (used for reading)
 */
public class JdomDocumentCreator {
    /**
     * Create Jdom document with DOM parser.
     * @param xmlScanner Scanner used to read the raw data
     * @return Jdom document
     * @throws IOException If something bad happens during file reading
     * @throws SAXException On internal Jdom exception
     * @throws ParserConfigurationException On internal Jdom exception
     */
    public static org.jdom2.Document createJDOMDocumentwithDOMParser(Scanner xmlScanner) throws IOException, SAXException, ParserConfigurationException {
        StringBuilder xmlString = new StringBuilder();
        while (xmlScanner.hasNextLine()) {
            xmlString.append(xmlScanner.nextLine());
        }
        InputStream is = new ByteArrayInputStream(xmlString.toString().getBytes(StandardCharsets.UTF_8));

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbFactory.newDocumentBuilder();

        Document doc = documentBuilder.parse(is);

        DOMBuilder domBuilder = new DOMBuilder();

        return domBuilder.build(doc);
    }
}
