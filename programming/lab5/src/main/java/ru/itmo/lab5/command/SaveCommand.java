package ru.itmo.lab5.command;

import ru.itmo.lab5.command.exception.InvalidCommandArgumentException;
import ru.itmo.lab5.file.PersonCollectionXMLWriter;
import ru.itmo.lab5.runtime.Context;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class SaveCommand extends Command {

    public SaveCommand() {
        super("save");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) throws IOException {
        BufferedOutputStream outputStream;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(context.getCollectionFilename()));
        } catch (FileNotFoundException e) {
            throw new InvalidCommandArgumentException("File `" + context.getCollectionFilename() + "` is inaccessible (is a directory or is unwritable due to permissions).");
        }
        PersonCollectionXMLWriter personCollectionXMLWriter = new PersonCollectionXMLWriter(scanner, printWriter, outputStream);

        personCollectionXMLWriter.writeCollection(context.getPersonManager());
    }

    @Override
    public String getDescription() {
        return "Save collection to the file.";
    }

    @Override
    public String getSyntax() {
        return this.getName();
    }
}
