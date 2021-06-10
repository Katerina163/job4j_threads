package ru.job4j.collect;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CASCountTest {
    @Test
    public void when3PushThen3Poll() {
        CASCount count = new CASCount();
        int[] array = new int[3];
        count.increment();
        array[0] = count.get();
        count.increment();
        array[1] = count.get();
        count.increment();
        array[2] = count.get();
        assertThat(array[0], is(1));
        assertThat(array[1], is(2));
        assertThat(array[2], is(3));
    }

    @Test
    public void when1PushThen1Poll() {
        CASCount count = new CASCount();
        count.increment();
        assertThat(count.get(), is(1));
    }
}