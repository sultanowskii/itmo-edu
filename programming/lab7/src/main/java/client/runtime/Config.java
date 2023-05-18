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

    public static Config readFromfile(String configFilename, PrintWriter printWriter) {
        ConfigReader configReader;

        File configFile = new File(configFilename);
        if (configFile.exists() && !configFile.canRead()) {
            printWriter.println("Can't access file: Permission denied. Exiting...");
            return null;
        }

        try (
            Scanner configFileScanenr = new Scanner(configFile);
        ) {
            configReader = new ConfigReader(printWriter, configFileScanenr);
            try {
                return configReader.read();
            } catch (NoSuchFieldException | IllegalAccessException e) {
                printWriter.println("Programming error. Details: " + e.getMessage() + " Exiting...");
                return null;
            } catch (NullPointerException e) {
                printWriter.println("Config file seems to be invalid. Exiting...");
                return null;
            } catch (ValidationException e) {
                printWriter.println("Config file seems to be invalid. Reason: " + e.getMessage());
                printWriter.println("Exiting...");
                return null;
            } catch (RuntimeException e) {
                printWriter.println(e.getMessage() + " Exiting...");
                return null;
            }
        } catch (FileNotFoundException e) {
            printWriter.println("Config file doesn't exist. Exiting...");
            return null;
        }
    }
}
