package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {
    @Override
    public void run() {
        try {
            int i = 0;
            while (!Thread.currentThread().isInterrupted()) {
                switch (i) {
                    case 0 -> {
                        System.out.print("\rLoading ... |");
                        i++;
                        break;
                    }
                    case 1 -> {
                        System.out.print("\rLoading ... /");
                        i++;
                        break;
                    }
                    case 2 -> {
                        System.out.print("\rLoading ... -");
                        i++;
                        break;
                    }
                    case 3 -> {
                        System.out.print("\rLoading ... \\");
                        i = 0;
                        break;
                    }
                    default -> {
                        System.out.print("\rОй");
                        i = 0;
                        break;
                    }
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
