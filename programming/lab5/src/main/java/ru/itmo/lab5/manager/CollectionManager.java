package ru.itmo.lab5.manager;

import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

public abstract class CollectionManager<T> {
    protected LinkedHashSet<T> storage;
    protected ZonedDateTime initDateTime;

    protected int nextID = 0;

    public CollectionManager() {

    }

    public CollectionManager(LinkedHashSet<T> storage) {
        this.storage = storage;
    }

    public int getNextID() {
        return nextID;
    }

    public int getNextIDAndIncrement() {
        return nextID++;
    }

    // Для случаев, когда загрузили данные из файла и нужно выставить релевантный ID
    public void setNextID(int newNextID) {
        nextID = newNextID;
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

    public abstract void addWithoutAutoID(T object);
}
