package client.file;

import client.runtime.ClientContext;
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
    protected ClientContext context;

    /**
     * Deafult constructor
     * @param printWriter Global output (to print something to user)
     * @param xmlScanner XML scanner to read data from
     */
    public ConfigReader(PrintWriter printWriter, Scanner xmlScanner, ClientContext context) {
        this.printWriter = printWriter;
        this.xmlScanner = xmlScanner;
        this.context = context;
    }

    public Config read() throws NoSuchFieldException, IllegalAccessException {
        org.jdom2.Document document;

        var messageBundle = context.getMessageBundle();

        try {
            document = JdomDocumentCreator.createJDOMDocumentwithDOMParser(this.xmlScanner);
        } catch (IOException e) {
            throw new RuntimeException(messageBundle.getString("error.parsing") + ": " + e.getMessage());
        } catch (SAXException e) {
            throw new RuntimeException(messageBundle.getString("error.invalidConfig"));
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(messageBundle.getString("error.programming") + ": " + e.getMessage());
        }

        Element root = document.getRootElement();

        var config = new Config();

        Field<String> hostnameField = new StringField("hostname", printWriter);
        hostnameField.parseAndSetValue(config, root.getChild("hostname").getValue(), messageBundle);

        Field<Integer> portField = new IntegerField("port", printWriter);
        portField.addValueValidator(new InclusiveBoundsValidator<>(0, 0xFFFF));
        portField.parseAndSetValue(config, root.getChild("port").getValue(), messageBundle);

        return config;
    }
}
