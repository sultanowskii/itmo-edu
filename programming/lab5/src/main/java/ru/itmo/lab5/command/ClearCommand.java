package ru.itmo.lab5.command;

import ru.itmo.lab5.manager.Context;
import ru.itmo.lab5.schema.Person;

import java.io.PrintWriter;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;

public class ClearCommand extends Command {

    public ClearCommand() {
        super("clear");
    }

    @Override
    public void exec(Scanner scanner, PrintWriter printWriter, List<String> args, Context context) {
        LinkedHashSet<Person> persons = context.getPersonManager().getStorage();
        int removedElementCounter = persons.size();

        persons.clear();

        printWriter.println("Removed " + removedElementCounter + " element(s).");
    }


    @Override
    public String getHelp() {
        return "Clear the collection. Syntax: " + this.getName();
    }
}
