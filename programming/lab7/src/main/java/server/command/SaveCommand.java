package server.command;

import lib.command.Command;
import server.manager.PersonCollectionSaver;
import server.runtime.Context;

import java.io.*;

public class SaveCommand extends Command {

    public SaveCommand() {
        super("save");
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context) throws IOException {
        var collectionSaver = new PersonCollectionSaver(context.getPersonManager(), context.getCollectionFilename(), printWriter);
        collectionSaver.saveCollectionToFile();
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
