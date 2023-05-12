package server.manager;

import lib.command.exception.InvalidCommandArgumentException;

import java.io.IOException;

public interface CollectionSaver {
    void saveCollectionToFile() throws IOException, InvalidCommandArgumentException;
}
