package ru.itmo.lab5.command.parse;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Command input parser.
 */
public class CommandParser {
    /**
     * Parses string into a command input information
     * @param rawString String to be parsed
     * @return Command input information
     */
    public static CommandInputInfo parseString(String rawString) {
        ArrayList<String> tokens = new ArrayList<>(Arrays.asList(rawString.split(" ")));
        String cmdName = tokens.get(0);
        tokens.remove(0);
        return new CommandInputInfo(cmdName, tokens);
    }
}
