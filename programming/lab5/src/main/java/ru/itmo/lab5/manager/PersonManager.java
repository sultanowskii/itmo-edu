package ru.itmo.lab5.manager;

import ru.itmo.lab5.schema.Person;

import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

public class PersonManager extends CollectionManager<Person> {
    public PersonManager() {

    }
    public PersonManager(LinkedHashSet<Person> storage) {
        super(storage);
    }

    @Override
    public Person getByID(int id) throws NoSuchElementException {
        return storage.stream().filter((person) -> person.getID() == id).findFirst().get();
    }

    @Override
    public void removeByID(int id) throws NoSuchElementException {
        Person personToRemove = this.getByID(id);
        this.storage.remove(personToRemove);
    }

    @Override
    public void add(Person person) {
        person.setID(getNextIDAndIncrement());
        this.storage.add(person);
    }

    @Override
    public void addWithoutAutoID(Person person) {
        this.storage.add(person);
    }
}
