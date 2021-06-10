package ru.job4j.collect;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        Integer increment;
        Integer ref;
        do {
            ref = count.get();
            increment = ref + 1;
        } while (!count.compareAndSet(ref, increment));
    }

    public int get() {
        return count.get();
    }
}
