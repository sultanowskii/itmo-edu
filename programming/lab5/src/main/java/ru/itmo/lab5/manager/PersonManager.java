package ru.itmo.lab5.manager;

import ru.itmo.lab5.schema.Person;

import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

public class PersonManager {
    private LinkedHashSet<Person> storage;
    private ZonedDateTime initDateTime;

    public PersonManager(LinkedHashSet<Person> storage) {
        this.storage = storage;
    }

    public LinkedHashSet<Person> getStorage() {
        return this.storage;
    }

    public ZonedDateTime getInitDateTime() {
        return this.initDateTime;
    }

    public void setStorage(LinkedHashSet<Person> storage) {
        this.storage = storage;
    }

    public void setInitDateTime(ZonedDateTime initDateTime) {
        this.initDateTime = initDateTime;
    }

    public Person getByID(int id) throws NoSuchElementException {
        return storage.stream().filter((person) -> person.getId() == id).findFirst().get();
    }

    public void removeByID(int id) throws NoSuchElementException {
        Person personToRemove = this.getByID(id);
        this.storage.remove(personToRemove);
    }

    public void add(Person person) {
        this.storage.add(person);
    }
}
