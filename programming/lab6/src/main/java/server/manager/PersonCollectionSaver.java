package server.manager;

import lib.command.exception.InvalidCommandArgumentException;
import server.file.PersonCollectionXMLWriter;
import server.manager.PersonManager;

import java.io.*;

public class PersonCollectionSaver implements CollectionSaver {
    private PersonManager collection;
    private String filename;
    private PrintWriter printWriter;

    public PersonCollectionSaver(
        PersonManager collection,
        String filename,
        PrintWriter printWriter
    ) {
        this.collection = collection;
        this.filename = filename;
        this.printWriter = printWriter;
    }

    public void saveCollectionToFile() throws IOException, InvalidCommandArgumentException {
        BufferedOutputStream outputStream;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(this.filename));
        } catch (FileNotFoundException e) {
            throw new InvalidCommandArgumentException("File `" + this.filename + "` is inaccessible (is a directory or is unwritable due to permissions).");
        }

        PersonCollectionXMLWriter personCollectionXMLWriter = new PersonCollectionXMLWriter(this.printWriter, outputStream);

        personCollectionXMLWriter.writeCollection(this.collection);
    }
}
