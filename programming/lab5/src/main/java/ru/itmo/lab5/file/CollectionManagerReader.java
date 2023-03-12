package ru.itmo.lab5.file;

import ru.itmo.lab5.manager.CollectionManager;

public interface CollectionManagerReader<T> {
    CollectionManager<T> readCollectionManager() throws NoSuchFieldException, IllegalAccessException;
}
