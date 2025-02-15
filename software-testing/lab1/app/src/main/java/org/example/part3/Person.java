package org.example.part3;

public class Person extends Actor {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String name() {
        return name;
    }

    public int age() {
        return age;
    }

    public String introduction() {
        return "My name is " + name() + ", I'm " + age() + " years old";
    }
}
