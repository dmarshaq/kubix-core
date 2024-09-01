package org.dmarshaq.kubix.core.util;

public class LoopedCollection<E> {
    private final E[] array;
    private int size;
    private int zeroIndexPointer;

    public LoopedCollection(int capacity) {
        this.array = (E[]) new Object[capacity];
        size = 0;
        zeroIndexPointer = 0;
    }

    public int size() {
        return size;
    }

    public void add(E element) {
        array[zeroIndexPointer++] = element;
        size = size < array.length ? size + 1 : array.length;
        zeroIndexPointer = zeroIndexPointer % array.length;
    }

    public E get(int index) {
        return array[(index + zeroIndexPointer) % size];
    }
}
