package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (i, m) -> {
            if (m.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            m = new Base(i, m.getVersion() + 1);
            m.setName(model.getName());
            return m;
        }).getVersion() > model.getVersion();
    }

    public void delete(Base model) {
        memory.putIfAbsent(model.getId(), null);
    }
}
