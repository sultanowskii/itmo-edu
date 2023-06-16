package client.runtime;

import client.file.ConfigReader;
import lib.form.validation.ValidationException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Config {
    public String hostname;
    public int port;

    public static Config readFromfile(String configFilename, PrintWriter printWriter, ClientContext context) {
        ConfigReader configReader;
        var messageBundle = context.getMessageBundle();

        File configFile = new File(configFilename);
        if (configFile.exists() && !configFile.canRead()) {
            printWriter.println(messageBundle.getString("error.configFilePermission"));
            printWriter.println(messageBundle.getString("message.exiting"));
            return null;
        }

        try (
            Scanner configFileScanenr = new Scanner(configFile);
        ) {
            configReader = new ConfigReader(printWriter, configFileScanenr, context);
            try {
                return configReader.read();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                printWriter.println(messageBundle.getString("error.programming") + ": " + e.getMessage() + " Exiting...");
                return null;
            } catch (NullPointerException e) {
                printWriter.println(messageBundle.getString("error.invalidConfig"));
                return null;
            } catch (ValidationException e) {
                printWriter.println(messageBundle.getString("error.invalidConfig") + ": " + e.getMessage());
                printWriter.println(messageBundle.getString("message.exiting"));
                return null;
            } catch (RuntimeException e) {
                printWriter.println(e.getMessage());
                printWriter.println(messageBundle.getString("message.exiting"));
                return null;
            }
        } catch (FileNotFoundException e) {
            printWriter.println(messageBundle.getString("error.fileNotFound") +": " + e.getMessage());
            printWriter.println(messageBundle.getString("message.exiting"));
            return null;
        }
    }
}
