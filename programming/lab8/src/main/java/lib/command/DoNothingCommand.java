package lib.command;

import server.runtime.Context;
import server.schema.User;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.List;
import java.util.Scanner;

public class DoNothingCommand extends Command {
    public DoNothingCommand() {
        super("");
    }

    @Override
    public boolean isClientSide() {
        return true;
    }

    @Override
    public void exec(PrintWriter printWriter, String[] args, Serializable objectArgument, Context context, User user) {

    }

    @Override
    public String getDescription() {
        return "Do nothing.";
    }

    @Override
    public String getSyntax() {
        return "Just hit the Enter.";
    }
}
