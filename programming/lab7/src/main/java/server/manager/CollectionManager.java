package server.manager;

import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

/**
 * Collection elements manager.
 * @param <T> Type of element
 */
public abstract class CollectionManager<T> {
    protected LinkedHashSet<T> storage;
    protected ZonedDateTime initDateTime;

    /**
     * Default constructor
     */
    public CollectionManager() {
        this.initDateTime = ZonedDateTime.now();
    }

    /**
     * Constructor with storage of elements.
     * @param storage Storage, containing elements
     */
    public CollectionManager(LinkedHashSet<T> storage) {
        this.initDateTime = ZonedDateTime.now();
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
