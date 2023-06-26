package lib.manager;

import lib.schema.Person;
import server.manager.PersonManager;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class PersonManagerDiff {
    private PersonManager oldPersonManager;
    private PersonManager newPersonManager;
    private Map<Integer, Person> addedPersons;
    private Map<Integer, Person> editedPersons;
    private Map<Integer, Person> deletedPersons;

    public PersonManagerDiff(PersonManager oldPersonManager, PersonManager newPersonManager) {
        this.oldPersonManager = oldPersonManager;
        this.newPersonManager = newPersonManager;

        this.addedPersons = new HashMap<>();
        this.editedPersons = new HashMap<>();
        this.deletedPersons = new HashMap<>();

        this.fillAddedPersons();
        this.fillEditedPersons();
        this.fillDeletedPersons();
    }

    private void fillAddedPersons() {
        var newStorage = this.newPersonManager.getStorage();
        for (var newPerson : newStorage) {
            int personID = newPerson.getID();
            try {
                this.oldPersonManager.getByID(personID);
            } catch (NoSuchElementException e) {
                this.addedPersons.put(personID, newPerson);
            }
        }
    }

    private void fillEditedPersons() {
        var oldStorage = this.oldPersonManager.getStorage();
        for (var oldPerson : oldStorage) {
            int personID = oldPerson.getID();

            Person newPerson;
            try {
                newPerson = this.newPersonManager.getByID(personID);
            } catch (NoSuchElementException e) {
                continue;
            }

            if (!oldPerson.equals(newPerson)) {
                this.editedPersons.put(personID, newPerson);
            }
        }
    }

    private void fillDeletedPersons() {
        var oldStorage = this.oldPersonManager.getStorage();
        for (var oldPerson : oldStorage) {
            int personID = oldPerson.getID();
            try {
                this.newPersonManager.getByID(personID);
            } catch (NoSuchElementException e) {
                this.deletedPersons.put(personID, oldPerson);
            }
        }
    }

    public Map<Integer, Person> getAddedPersons() {
        return this.addedPersons;
    }

    public Map<Integer, Person> getEditedPersons() {
        return this.editedPersons;
    }

    public Map<Integer, Person> getDeletedPersons() {
        return this.deletedPersons;
    }
}
