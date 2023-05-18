package client.file;

import lib.form.validation.InclusiveBoundsValidator;
import client.runtime.Config;
import lib.file.util.JdomDocumentCreator;
import lib.form.field.Field;
import lib.form.field.IntegerField;
import lib.form.field.StringField;
import org.jdom2.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ConfigReader {
    protected PrintWriter printWriter;
    protected Scanner xmlScanner;

    /**
     * Deafult constructor
     * @param printWriter Global output (to print something to user)
     * @param xmlScanner XML scanner to read data from
     */
    public ConfigReader(PrintWriter printWriter, Scanner xmlScanner) {
        this.printWriter = printWriter;
        this.xmlScanner = xmlScanner;
    }

    public Config read() throws NoSuchFieldException, IllegalAccessException {
        org.jdom2.Document document;

        try {
            document = JdomDocumentCreator.createJDOMDocumentwithDOMParser(this.xmlScanner);
        } catch (IOException e) {
            throw new RuntimeException("Unexpected error occurred during config file parse. Details: " + e.getMessage());
        } catch (SAXException e) {
            throw new RuntimeException("Config file seems to be invalid.");
        } catch (ParserConfigurationException e) {
            throw new RuntimeException("Programming error during config file parse. Details: " + e.getMessage());
        }

        Element root = document.getRootElement();

        var config = new Config();

        Field<String> hostnameField = new StringField("hostname", printWriter);
        hostnameField.parseAndSetValue(config, root.getChild("hostname").getValue());

        Field<Integer> portField = new IntegerField("port", printWriter);
        portField.addValueValidator(new InclusiveBoundsValidator<>(0, 0xFFFF));
        portField.parseAndSetValue(config, root.getChild("port").getValue());

        return config;
    }
}
