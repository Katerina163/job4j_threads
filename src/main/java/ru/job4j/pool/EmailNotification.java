package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool;

    public EmailNotification() {
        pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
    }

    public void create(String name, String email) {
        pool.submit(() -> emailTo(new User(name, email)));
    }

    private void emailTo(User user) {
        String subject = String.format(
                "Notification %s to email %s",
                user.getName(), user.getEmail());
        String body = String.format(
                "Add a new event to %s", user.getName());
        send(subject, body, user.getEmail());
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
    }

    public static void main(String[] args) {
        EmailNotification en = new EmailNotification();
        en.create("Olga", "olga@ya.ru");
        en.close();
    }
}