package com.panda.greeter;

/**
 * Created by twer on 6/16/13.
 */
public class Greeter {
    public String greetToWorld() {
        return "Hello World";
    }

    public String greetToPerson(Person person) {
        return "Hello " + person.getName();
    }
}
