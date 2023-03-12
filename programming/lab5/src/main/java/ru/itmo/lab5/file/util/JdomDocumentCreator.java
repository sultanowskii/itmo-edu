package ru.itmo.lab5.file.util;

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

public class JdomDocumentCreator {
    public static org.jdom2.Document createJDOMwithDOMParser(Scanner xmlScanner) throws IOException, SAXException, ParserConfigurationException {
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
