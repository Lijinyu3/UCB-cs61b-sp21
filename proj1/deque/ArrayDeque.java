package deque;

import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
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

    @Deprecated
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

    /**
     * This method implements a more concise version of the copyArray method,
     * which uses the get method and for loop to copy elements from this array to a new array.
     * inspired by the specification in the 23sp Proj1B.*
     */
    private void copyArrayPlanB(T[] newArray) {
        for (int i = 0; i < this.size; i++) {
            newArray[i] = this.get(i);
        }
        this.nextFirst = newArray.length - 1;
        this.nextLast = this.size;
    }

    private int getActualIndex(int index, int offset) {
        index += offset;
        return Math.floorMod(index, this.array.length);
    }

    private void resize(int newSize) {
        T[] newArray = (T[]) new Object[newSize];
//        this.copyArray(newArray);
        this.copyArrayPlanB(newArray);
        this.array = newArray;
    }

    @Override
    public void addFirst(T item) {
        if (this.size == this.array.length) {
            this.resize(this.size * 2);
        }
        this.array[nextFirst] = item;
        this.size++;
        this.nextFirst = this.getActualIndex(this.nextFirst, -1);
    }

    @Override
    public void addLast(T item) {
        if (this.size == this.array.length) {
            this.resize(this.size * 2);
        }
        this.array[nextLast] = item;
        this.size++;
        this.nextLast = this.getActualIndex(this.nextLast, 1);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < this.size; i++) {
            System.out.print(this.get(i) + " ");
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
        int toBeDeletedIndex = this.getActualIndex(this.nextFirst, 1);
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
        int toBeDeletedIndex = this.getActualIndex(this.nextLast, -1);
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
        int i = this.getActualIndex(this.nextFirst + 1, index);
        return this.array[i];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        /*
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
         */
        if (o == null) {
            return false;
        }
        if (!(o instanceof Deque)) {
            return false;
        }
        Deque<?> otherAD = (Deque<?>) o;

        if (this.size() != otherAD.size()) {
            return false;
        }
        for (int i = 0; i < this.size(); i++) {
            if (!(this.get(i).equals(otherAD.get(i)))) {
                return false;
            }
        }
        return true;
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
