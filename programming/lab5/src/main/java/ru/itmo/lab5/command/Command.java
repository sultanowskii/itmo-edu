package ru.itmo.lab5.command;

import ru.itmo.lab5.command.exception.InvalidCommandArgumentException;
import ru.itmo.lab5.form.validation.ValidationException;
import ru.itmo.lab5.manager.Context;

import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public abstract class Command {
    protected Scanner scanner;
    protected PrintWriter printWriter;
    protected String name;

    public Command(String name, Scanner scanner, PrintWriter printWriter) {
        this.name = name;
        this.scanner = scanner;
        this.printWriter = printWriter;
    }

    public abstract void exec(List<String> args, Context context) throws InvalidCommandArgumentException, ValidationException;

    public String getName() {
        return this.name;
    }

    public abstract String getHelp();
}
