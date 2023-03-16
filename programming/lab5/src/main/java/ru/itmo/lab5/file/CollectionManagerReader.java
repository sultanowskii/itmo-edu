package ru.itmo.lab5.file;

import ru.itmo.lab5.manager.CollectionManager;

/**
 * Collection manager reader (from external source)
 * @param <T> Collection manager type
 */
public interface CollectionManagerReader<T extends CollectionManager<?>> {
    /**
     * Read collection manager from external source
     * @return Resulting collection manager
     * @throws NoSuchFieldException If something failed during object reading (during reflection)
     * @throws IllegalAccessException If field of some object is inaccessible (during reflection)
     */
    T readCollectionManager() throws NoSuchFieldException, IllegalAccessException;
}
