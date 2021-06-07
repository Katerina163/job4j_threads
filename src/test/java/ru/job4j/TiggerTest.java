package ru.job4j;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class TiggerTest {
    @Test
    public void test() {
        assertThat(new Tigger().rrr(), is(1));
    }
}