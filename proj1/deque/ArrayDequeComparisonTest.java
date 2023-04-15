package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArrayDequeComparisonTest {
    private static class ADListNoResizing<T> {
        private final T[] array;
        private int size;
        private int nextFirst;
        private int nextLast;
        private static final int N = (int) 1e5;

        public ADListNoResizing() {
            this.array = (T[]) new Object[N];
            this.size = 0;
            this.nextFirst = N / 2;
            this.nextLast = this.nextFirst + 1;
        }

        public void addFirst(T item) {
            this.array[nextFirst] = item;
            nextFirst--;
            size++;
        }

        public void addLast(T item) {
            this.array[nextLast] = item;
            nextLast++;
            size++;
        }

        public boolean isEmpty() {
            return this.size == 0;
        }

        public int size() {
            return this.size;
        }

        public T removeLast() {
            if (this.isEmpty()) {
                return null;
            }
            T item = this.array[nextLast - 1];
            nextLast--;
            size--;
            return item;
        }

        public T removeFirst() {
            if (this.isEmpty()) {
                return null;
            }
            T item = this.array[nextFirst + 1];
            nextFirst++;
            size--;
            return item;
        }

        private T get(int index, int actualIndex) {
            actualIndex = actualIndex % N;
            if (index == 0) {
                return this.array[actualIndex];
            }
            return get(index - 1, actualIndex + 1);
        }

        public T get(int index) {
            if (index < 0 || index >= this.size()) {
                return null;
            }
            return get(index, this.nextFirst + 1);
        }
    }

    @Test
    public void randomizedComparison() {
        ArrayDeque<Integer> actualList = new ArrayDeque<>();
        ADListNoResizing<Integer> expectedList = new ADListNoResizing<>();
        final int N = (int) 3e5;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 7);
            if (operationNumber == 0) {
                actualList.addFirst(i);
                expectedList.addFirst(i);
            } else if (operationNumber == 1) {
                actualList.addLast(i);
                expectedList.addLast(i);
            } else if (operationNumber == 2) {
                assertEquals(expectedList.isEmpty(), actualList.isEmpty());
            } else if (operationNumber == 3) {
                assertEquals(expectedList.size(), actualList.size());
            } else if (operationNumber == 4) {
                assertEquals(expectedList.removeFirst(), actualList.removeFirst());
            } else if (operationNumber == 5) {
                assertEquals(expectedList.removeLast(), actualList.removeLast());
            } else if (operationNumber == 6) {
                int index = StdRandom.uniform(0, N);
                assertEquals(expectedList.get(index), actualList.get(index));
            }
        }
    }

    @Test
    public void randomizedComparison2() {
        ArrayDeque<Integer> actualList = new ArrayDeque<>();
        java.util.Deque<Integer> expectedList = new java.util.ArrayDeque<>();
        final int N = (int) 1e7;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 7);
            if (operationNumber == 0) {
                actualList.addFirst(i);
                expectedList.addFirst(i);
            } else if (operationNumber == 1) {
                actualList.addLast(i);
                expectedList.addLast(i);
            } else if (operationNumber == 2) {
                assertEquals(expectedList.isEmpty(), actualList.isEmpty());
            } else if (operationNumber == 3) {
                assertEquals(expectedList.size(), actualList.size());
            } else if (operationNumber == 4) {
                if (expectedList.isEmpty()) {
                    assertNull(actualList.removeFirst());
                } else {
                    assertEquals(expectedList.removeFirst(), actualList.removeFirst());
                }
            } else if (operationNumber == 5) {
                if (expectedList.isEmpty()) {
                    assertNull(actualList.removeLast());
                } else {
                    assertEquals(expectedList.removeLast(), actualList.removeLast());
                }
            } else if (operationNumber == 6) {
                int index = StdRandom.uniform(0, N);
            }
        }
    }
}
