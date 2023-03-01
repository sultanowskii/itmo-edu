package ru.itmo.lab5.command;

import ru.itmo.lab5.manager.Context;

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
    public String getHelp() {
        return "Do nothing. Syntax: Just hit the Enter.";
    }
}
