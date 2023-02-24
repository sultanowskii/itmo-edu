package ru.itmo.lab5.command;

import ru.itmo.lab5.manager.Context;
import ru.itmo.lab5.schema.Person;

import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class ClearCommand extends Command {

    public ClearCommand(Scanner scanner, PrintWriter printWriter) {
        super("clear", scanner, printWriter);
    }

    @Override
    public void exec(List<String> args, Context context) {
        LinkedHashSet<Person> persons = context.getPersonManager().getStorage();
        persons.clear();
    }


    @Override
    public String getHelp() {
        return "Clear the collection. Syntax: " + this.getName();
    }
}
