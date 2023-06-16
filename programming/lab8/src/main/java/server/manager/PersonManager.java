package server.manager;

import lib.schema.Person;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

public class PersonManager extends CollectionManager<Person> implements Serializable {
    public PersonManager() {
        super();
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

    public boolean isPassportIDavailable(String passportID) {
        return this.storage
            .stream()
            .noneMatch(
                p -> p.getPassportID().equals(passportID)
            );
    }

    @Override
    public void add(Person person) {
        this.storage.add(person);
    }
}
