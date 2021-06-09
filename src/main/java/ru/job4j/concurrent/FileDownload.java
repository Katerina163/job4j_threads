package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class FileDownload implements Runnable {
    private static volatile int i = 0;
    private final String url;
    private final int speed;

    public FileDownload(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("new_file" + i + ".xml")) {
            i++;
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            Long start = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
            Long end = System.nanoTime();
            if (1024 / (end - start) < speed) {
                Thread.sleep(end - start);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 2) {
            throw new IllegalArgumentException();
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new FileDownload(url, speed));
        wget.start();
        wget.join();
    }
}
