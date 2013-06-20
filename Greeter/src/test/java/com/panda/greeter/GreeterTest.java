package com.panda.greeter;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Created by twer on 6/19/13.
 */
public class GreeterTest {
    @Test
    public void greets_to_the_world() {
        Greeter greeter = new Greeter();
        assertThat(greeter.greetToWorld(), is("Hello World."));
    }
}
