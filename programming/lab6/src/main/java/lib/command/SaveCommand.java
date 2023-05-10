package lib.command;

import lib.command.exception.InvalidCommandArgumentException;
import server.file.PersonCollectionXMLWriter;
import server.runtime.Context;

import java.io.*;

public class SaveCommand extends Command {

    public SaveCommand() {
        super("save");
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context) throws IOException {
        BufferedOutputStream outputStream;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(context.getCollectionFilename()));
        } catch (FileNotFoundException e) {
            throw new InvalidCommandArgumentException("File `" + context.getCollectionFilename() + "` is inaccessible (is a directory or is unwritable due to permissions).");
        }
        PersonCollectionXMLWriter personCollectionXMLWriter = new PersonCollectionXMLWriter(printWriter, outputStream);

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
