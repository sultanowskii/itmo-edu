package ru.itmo.lab5.file;

import ru.itmo.lab5.manager.CollectionManager;

public interface CollectionManagerWriter<T> {
    void writeCollection(CollectionManager<T> collectionManager);
}
