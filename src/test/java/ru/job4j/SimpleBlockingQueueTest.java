package ru.job4j;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

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

    @Test
    public void whenFetchAllThenGetIt() throws InterruptedException {
        final CopyOnWriteArrayList<Integer> buffer = new CopyOnWriteArrayList<>();
        final SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(
                () -> IntStream.range(0, 5).forEach(queue::offer));
        producer.start();
        Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        buffer.add(queue.poll());
                    }
                }
        );
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList(0, 1, 2, 3, 4)));
    }

    @Test
    public void testTwo() throws InterruptedException {
        final Queue<String> buffer = new LinkedList<>();
        final SimpleBlockingQueue<String> queue = new SimpleBlockingQueue<>();
        Thread producer = new Thread(() -> {
            queue.offer("0");
            queue.offer("1");
            queue.offer("2");
        });
        producer.start();
        Thread consumer = new Thread(() -> {
            while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                buffer.add(queue.poll());
            }
        });
        consumer.start();
        producer.join();
        consumer.interrupt();
        consumer.join();
        assertThat(buffer, is(Arrays.asList("0", "1", "2")));
    }
}