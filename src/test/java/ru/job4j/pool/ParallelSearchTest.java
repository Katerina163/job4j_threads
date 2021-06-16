package ru.job4j.pool;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class ParallelSearchTest {
    @Test
    public void when10element() {
        Integer[] array = {1, 24, 2, 16, 3, 48, 9, 7, 8, 45};
        assertThat(ParallelSearch.search(array, 2), is(2));
    }

    @Test
    public void whenMoreThen10element() {
        Integer[] array = {1, 24, 2, 16, 3, 48, 9, 7, 8, 45, 22, 18};
        assertThat(ParallelSearch.search(array, 18), is(11));
    }
}