package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T>{
    private T[] array;
    private int size;
    private int nextFirst;
    private int nextLast;
    private static final int STARTING_SIZE = 8;
    private static final double MIN_USAGE_RATIO = 0.25;

    public ArrayDeque() {
        this.array = (T[]) new Object[STARTING_SIZE];
        this.size = 0;
        this.nextFirst = STARTING_SIZE - 1;
        this.nextLast = 0;
    }

    private void copyArray(T[] newArray) {
        int firstPos = (this.nextFirst + 1) % this.array.length;
        int lastPos = Math.floorMod(this.nextLast - 1, this.array.length);
        if (firstPos < lastPos) {
            System.arraycopy(this.array, firstPos, newArray, 1, this.size);
            this.nextFirst = 0;
            this.nextLast = this.size + 1;
        } else {
            int diff = newArray.length - this.array.length;
            int firstLength = this.array.length - 1 - this.nextFirst;
            int newFirstDest = diff + this.nextFirst + 1;
            int lastLength = this.nextLast;
            if (firstLength > 0) {
                System.arraycopy(this.array, this.nextFirst + 1, newArray, newFirstDest, firstLength);
            }
            if (lastLength > 0) {
                System.arraycopy(this.array, 0, newArray, 0, lastLength);
            }
            this.nextFirst += diff;
        }
    }

    private void resize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
        this.copyArray(newArray);
        this.array = newArray;
    }

    @Override
    public void addFirst(T item) {
        if (this.size == this.array.length) {
            this.resize(this.size * 2);
        }
        this.array[nextFirst] = item;
        this.size++;
        this.nextFirst--;
        this.nextFirst = Math.floorMod(this.nextFirst, this.array.length);
    }

    @Override
    public void addLast(T item) {
        if (this.size == this.array.length) {
            this.resize(this.size * 2);
        }
        this.array[nextLast] = item;
        this.size++;
        this.nextLast++;
        this.nextLast = Math.floorMod(this.nextLast, this.array.length);
    }

//    public boolean isEmpty() {
//        return this.size == 0;
//    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void printDeque() {
        int i = Math.floorMod((this.nextFirst + 1) ,this.array.length);
        int l = this.size();
        while (l > 0) {
            System.out.print(this.array[i] + " ");
            l--;
            i = (i + 1) % this.array.length;
        }
        System.out.println();
    }

    private double usageRatio() {
        return (double) this.size / this.array.length;
    }

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        if (this.usageRatio() < MIN_USAGE_RATIO && this.array.length > STARTING_SIZE) {
            this.resize(this.array.length / 2);
        }
        int toBeDeletedIndex = (this.nextFirst + 1) % this.array.length;
        T toBeDeletedItem = this.array[toBeDeletedIndex];
        this.size--;
        this.nextFirst = toBeDeletedIndex;
        return toBeDeletedItem;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        if (this.usageRatio() < MIN_USAGE_RATIO && this.array.length > STARTING_SIZE) {
            this.resize(this.array.length / 2);
        }
        int toBeDeletedIndex = Math.floorMod((this.nextLast - 1) ,this.array.length);
        T toBeDeletedItem = this.array[toBeDeletedIndex];
        this.size--;
        this.nextLast = toBeDeletedIndex;
        return toBeDeletedItem;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size()) {
            return null;
        }
        int i = Math.floorMod((this.nextFirst + 1 + index) ,this.array.length);
        return this.array[i];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Deque otherAD) {
            if (this.size() != otherAD.size()) {
                return false;
            }
            for (int i = 0; i < this.size(); i++) {
                if (this.get(i) != otherAD.get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;

        public ArrayDequeIterator() {
            this.pos = 0;
        }

        @Override
        public boolean hasNext() {
            return this.pos < size();
        }

        @Override
        public T next() {
            T item = get(pos);
            pos++;
            return item;
        }
    }
}
