package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> storage = new HashMap<>();

    public synchronized boolean add(User user) {
        if (storage.containsKey(user.getId())) {
            return false;
        }
        storage.putIfAbsent(user.getId(), user);
        return storage.containsValue(user);
    }

    public synchronized User getFromId(int id) {
        return storage.get(id);
    }

    public synchronized boolean update(User user) {
        if (!storage.containsKey(user.getId())) {
            return false;
        }
        storage.replace(user.getId(), user);
        return storage.containsValue(user);
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User from = storage.get(fromId);
        User to = storage.get(toId);
        if (from == null || to == null || from.getAmount() < amount) {
            return false;
        } else {
            from.setAmount(from.getAmount() - amount);
            to.setAmount(to.getAmount() + amount);
            return true;
        }
    }
}