package ru.job4j.pool;

import ru.job4j.SimpleBlockingQueue;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final List<Thread> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>();

    public void work(Runnable job) throws InterruptedException {
        tasks.offer(job);
        Thread thread = new Thread(job);
        threads.add(thread);
        thread.start();
    }

    public void shutdown() throws InterruptedException {
        for (Thread t : threads) {
            t.interrupt();
        }
    }
}
