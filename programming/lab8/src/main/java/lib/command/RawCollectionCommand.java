package lib.command;

import server.runtime.Context;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;

public class RawCollectionCommand extends Command {

    public RawCollectionCommand() {
        super("raw_collection");
    }

    @Override
    public boolean isClientSide() {
        return false;
    }

    @Override
    public boolean exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {
        return true;
    }

    @Override
    public String getDescription() {
        return "Get collection in a raw view.";
    }

    @Override
    public String getSyntax() {
        return this.getName();
    }
}
