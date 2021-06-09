package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        try {
            char[] array = {'|', '/', '-', '\\'};
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                System.out.print("\rLoading ... " + array[i++]);
                if (i == 4) {
                    i = 0;
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException ie) {
            System.out.print("\rDone");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(10000);
        progress.interrupt();
    }
}
