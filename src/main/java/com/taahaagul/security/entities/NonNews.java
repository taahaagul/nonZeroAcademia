package com.taahaagul.security.entities;

import lombok.Data;
import java.util.*;
import java.util.stream.Stream;

@Data
public class NonNews<T> implements Iterable<T> {
    private final int maxSize;
    private final Queue<T> queue;
    private final Set<T> set;

    public NonNews(int maxSize) {
        this.maxSize = maxSize;
        this.queue = new LinkedList<>();
        this.set = new HashSet<>();
    }

    public void add(T item) {
        if(!set.contains(item)) {
            if(queue.size() == maxSize) {
                T oldestItem = queue.remove();
                set.remove(oldestItem);
            }
            queue.add(item);
            set.add(item);
        }
    }

    public boolean contains(T item) {
        return set.contains(item);
    }

    public void remove(T item) {
        if(set.contains(item)) {
            set.remove(item);
            queue.remove(item);
        }
    }

    public int size() {
        return queue.size();
    }

    @Override
    public Iterator<T> iterator() {
        return queue.iterator();
    }

    public Stream<T> stream() {
        return queue.stream();
    }

    @Override
    public String toString() {
        return queue.toString();
    }
}
