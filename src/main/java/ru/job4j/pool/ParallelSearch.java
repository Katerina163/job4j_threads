package ru.job4j.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch extends RecursiveTask<Integer> {
    private final int[] array;
    private final int first;
    private final int last;
    private final int number;

    private ParallelSearch(int[] array, int first, int last, int number) {
        this.array = array;
        this.first = first;
        this.last = last;
        this.number = number;
    }

    @Override
    protected Integer compute() {
        if (last - first < 10) {
            for (int i = 0; i < array.length; i++) {
                if (array[i] == number) {
                    return i;
                }
            }
        }
        int mid = (first + last) / 2;
        ParallelSearch pleft = new ParallelSearch(array, first, mid, number);
        ParallelSearch pright = new ParallelSearch(array, mid + 1, last, number);
        pleft.fork();
        pright.fork();
        int left = pleft.join();
        int right = pright.join();
        return Math.max(right, left);
    }

    public static int search(int[] array, int number) {
        ForkJoinPool fJP = new ForkJoinPool();
        return fJP.invoke(new ParallelSearch(array, 0, array.length - 1, number));
    }
}
