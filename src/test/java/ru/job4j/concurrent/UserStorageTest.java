package ru.job4j.concurrent;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class UserStorageTest {
    @Test
    public void add() {
        UserStorage us = new UserStorage();
        User u = new User(1, 1);
        boolean rsl = us.add(u);
        assertThat(us.getFromId(1), is(u));
        assertTrue(rsl);
    }

    @Test
    public void update() {
        UserStorage us = new UserStorage();
        User u1 = new User(1, 1);
        User u2 = new User(1, 2);
        us.add(u1);
        boolean rsl = us.update(u2);
        assertThat(us.getFromId(1), is(u2));
        assertTrue(rsl);
    }

    @Test
    public void delete() {
        UserStorage us = new UserStorage();
        User u = new User(1, 1);
        us.add(u);
        boolean rsl = us.delete(u);
        assertNull(us.getFromId(1));
        assertTrue(rsl);
    }

    @Test
    public void transfer() {
        UserStorage us = new UserStorage();
        User from = new User(1, 1);
        User to = new User(2, 1);
        us.add(from);
        us.add(to);
        boolean rsl = us.transfer(from.getId(), to.getId(), 1);
        boolean res = us.transfer(from.getId(), to.getId(), 1);
        assertThat(us.getFromId(1).getAmount(), is(0));
        assertThat(us.getFromId(2).getAmount(), is(2));
        assertTrue(rsl);
        assertFalse(res);
    }

    @Test
    public void addSameId() {
        UserStorage us = new UserStorage();
        User u1 = new User(1, 1);
        User u2 = new User(1, 2);
        us.add(u1);
        boolean rsl = us.add(u2);
        assertFalse(rsl);
    }

    @Test
    public void updateNoId() {
        UserStorage us = new UserStorage();
        User u = new User(1, 1);
        boolean rsl = us.update(u);
        assertFalse(rsl);
    }

    @Test
    public void addTwoThread() throws InterruptedException {
        final UserStorage us = new UserStorage();
        Thread first = new Thread(() -> us.add(new User(1, 1)));
        Thread second = new Thread(() -> us.add(new User(1, 1)));
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(us.getFromId(1), is(new User(1, 1)));
    }

    @Test
    public void updateTwoThread() throws InterruptedException {
        final UserStorage us = new UserStorage();
        Thread first = new Thread(() -> {
            us.add(new User(1, 1));
            us.update(new User(1, 3));
        });
        Thread second = new Thread(() -> {
            us.add(new User(1, 1));
            us.update(new User(1, 3));
        });
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(us.getFromId(1), is(new User(1, 3)));
    }

    @Test
    public void deleteTwoThread() throws InterruptedException {
        final UserStorage us = new UserStorage();
        Thread first = new Thread(() -> {
            us.add(new User(1, 1));
            us.delete(new User(1, 1));
        });
        Thread second = new Thread(() -> {
            us.add(new User(1, 1));
            us.delete(new User(1, 1));
        });
        first.start();
        second.start();
        first.join();
        second.join();
        assertNull(us.getFromId(1));
    }

    @Test
    public void transferTwoThread() throws InterruptedException {
        final UserStorage us = new UserStorage();
        User from = new User(1, 1);
        User to = new User(2, 1);
        us.add(from);
        us.add(to);
        Thread first = new Thread(() -> {
            us.transfer(from.getId(), to.getId(), 1);
        });
        Thread second = new Thread(() -> {
            us.transfer(from.getId(), to.getId(), 1);
        });
        first.start();
        second.start();
        first.join();
        second.join();
        assertThat(us.getFromId(1).getAmount(), is(0));
        assertThat(us.getFromId(2).getAmount(), is(2));
    }
}