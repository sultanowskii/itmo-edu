package ru.itmo.lab5.manager;

import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

public abstract class SchemaCollectionManager<T> {
    protected LinkedHashSet<T> storage;
    protected ZonedDateTime initDateTime;

    public SchemaCollectionManager(LinkedHashSet<T> storage) {
        this.storage = storage;
    }

    public LinkedHashSet<T> getStorage() {
        return this.storage;
    }

    public ZonedDateTime getInitDateTime() {
        return this.initDateTime;
    }

    public void setStorage(LinkedHashSet<T> storage) {
        this.storage = storage;
    }

    public void setInitDateTime(ZonedDateTime initDateTime) {
        this.initDateTime = initDateTime;
    }

    public abstract T getByID(int id) throws NoSuchElementException;

    public abstract void removeByID(int id) throws NoSuchElementException;

    public abstract void add(T object);
}
