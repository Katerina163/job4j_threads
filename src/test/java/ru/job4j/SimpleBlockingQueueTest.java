package ru.job4j;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Integer[] i = new Integer[2];
        Thread first = new Thread(() -> {
            i[0] = queue.poll();
            i[1] = queue.poll();
        });
        Thread second = new Thread(() -> {
            queue.offer(1);
            queue.offer(2);
        });
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(i[0], is(1));
        assertThat(i[1], is(2));
    }
}