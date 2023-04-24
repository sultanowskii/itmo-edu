package lib.command;

import server.runtime.Context;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class DoNothingCommand extends Command {

    public DoNothingCommand() {
        super("");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
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
