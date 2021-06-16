package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static ru.job4j.pool.RolColSum.*;

public class RolColSumTest {
    @Test
    public void testSum() {
        int[][] array = {{1, 1, 1}, {1, 2, 3}, {2, 2, 2}};
        Sums[] s = sum(array);
        assertThat(s[0].getRowSum(), is(4));
        assertThat(s[0].getColSum(), is(3));
        assertThat(s[1].getRowSum(), is(5));
        assertThat(s[1].getColSum(), is(6));
        assertThat(s[2].getRowSum(), is(6));
        assertThat(s[2].getColSum(), is(6));
    }

    @Test
    public void testAsyncSum() throws ExecutionException, InterruptedException {
        int[][] array = {{1, 1, 1}, {1, 1, 9}, {2, 2, 2}};
        Sums[] s = asyncSum(array);
        assertThat(s[0].getRowSum(), is(4));
        assertThat(s[0].getColSum(), is(3));
        assertThat(s[1].getRowSum(), is(4));
        assertThat(s[1].getColSum(), is(11));
        assertThat(s[2].getRowSum(), is(12));
        assertThat(s[2].getColSum(), is(6));
    }
}