package ru.itmo.lab5.file;

import ru.itmo.lab5.manager.CollectionManager;

import java.io.IOException;

/**
 * Collection manager writer (to external destination)
 * @param <T> Collection manager type
 */
public interface CollectionManagerWriter<T extends CollectionManager<?>> {
    /**
     * Write collection manager to external destination
     * @param collectionManager Collection manager to write
     * @throws IOException If error during IO operations occurred
     */
    void writeCollection(T collectionManager) throws IOException;
}
