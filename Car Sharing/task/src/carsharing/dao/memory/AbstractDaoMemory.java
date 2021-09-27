package carsharing.dao.memory;

import carsharing.dao.BaseItem;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractDaoMemory<T extends BaseItem> {
    private final Map<Integer, T> itemMap = new LinkedHashMap<>();
    private final AtomicInteger idGen = new AtomicInteger(1);

    protected int nextId() {
        return idGen.getAndIncrement();
    }

    protected List<T> getAll() {
        return new ArrayList<>(itemMap.values());
    }

    protected void saveItem(T item) {
        itemMap.put(item.getId(), item);
    }

    protected T getById(int id) {
        return itemMap.get(id);
    }

}
