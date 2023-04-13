package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;

public class ArrayDequeTest {
    @Test
    public void testAddFirst() {
        ArrayDeque<Integer> adList = new ArrayDeque<>();
        final int N = (int) 1e3;
        for (int i = 0; i < N; i++) {
            adList.addFirst(N - i - 1);
            assertEquals(i + 1, adList.size());
            assertEquals(N - i - 1, adList.get(0).intValue());
        }

        for (int i = 0; i < N; i++) {
            assertEquals(i, adList.get(i).intValue());
        }

//        adList.printDeque();
    }

    @Test
    public void testAddLast() {
        ArrayDeque<Integer> adList = new ArrayDeque<>();
        final int N = (int) 1e3;
        for (int i = 0; i < N; i++) {
            adList.addLast(i);
            assertEquals(i + 1, adList.size());
            assertEquals(i, adList.get(i).intValue());
        }

//        adList.printDeque();
    }

    @Test
    public void testRemoveFirst() {
        ArrayDeque<Integer> adList = new ArrayDeque<>();
        final int N = (int) 1e3;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 2);
            if (operationNumber == 0) {
                adList.addFirst(i);
            } else if (operationNumber == 1) {
                adList.addLast(i);
            }
        }
        assertEquals(N, adList.size());
        for (int i = 0; i < N; i++) {
            Integer x = adList.removeFirst();
            if (x == null) {
                break;
            }
            assertEquals(N - i - 1, adList.size());
        }
    }

    @Test
    public void testRemoveLast() {
        ArrayDeque<Integer> adList = new ArrayDeque<>();
        final int N = (int) 1e3;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 2);
            if (operationNumber == 0) {
                adList.addFirst(i);
            } else if (operationNumber == 1) {
                adList.addLast(i);
            }
        }
        assertEquals(N, adList.size());
        for (int i = 0; i < N; i++) {
            Integer x = adList.removeLast();
            if (x == null) {
                break;
            }
            assertEquals(N - i - 1, adList.size());
        }
    }

    @Test
    public void testIntegration() {
        ArrayDeque<Integer> adList = new ArrayDeque<>();
        final int N = (int) 1e5;
        for (int i = 0; i < N; i++) {
            int operationNumber = StdRandom.uniform(0, 2);
            if (operationNumber == 0) {
                adList.addFirst(i);
            } else if (operationNumber == 1) {
                adList.addLast(i);
            }
        }
        assertEquals(N, adList.size());
        for (int i = 0; i < N; i++) {
            adList.removeFirst();
            adList.removeLast();
        }
        assertTrue(adList.isEmpty());
    }

    @Test
    public void iterationTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        java.util.Deque<Integer> ad2 = new java.util.ArrayDeque<>();

        final int N = (int) 1e5;
        for (int i = 0; i < N; i++) {
            int opNum = StdRandom.uniform(2);
            if (opNum == 0) {
                ad1.addLast(i);
                ad2.addLast(i);
            } else if (opNum == 1) {
                ad1.addFirst(i);
                ad2.addFirst(i);
            }
        }

        Iterator<Integer> iterator1 = ad1.iterator();
        for (int i : ad2) {
            assertEquals(i, iterator1.next().intValue());
        }

        Iterator<Integer> iterator2 = ad2.iterator();
        for (int i : ad1) {
            assertEquals(iterator2.next().intValue(), i);
        }

        iterator1 = ad1.iterator();
        iterator2 = ad2.iterator();
        for (int i = 0; i < N + 10; i++) {
            assertEquals(iterator2.hasNext(), iterator1.hasNext());
            if (iterator2.hasNext()) {
                assertEquals(iterator2.next(), iterator1.next());
            }
        }
    }

    @Test
    public void equalsTestEqual() {
        Deque<Integer> deque1 = new ArrayDeque<>();
        deque1.addLast(2);
        deque1.addLast(3);
        deque1.addLast(4);
        Deque<Integer> deque2 = new ArrayDeque<>();
        deque2.addLast(2);
        deque2.addLast(3);
        deque2.addLast(4);
        assertTrue(deque1.equals(deque2));

        deque1 = new ArrayDeque<>();
        deque2 = new ArrayDeque<>();
        final int N = 10;
        for (int i = 0; i < N; i++) {
            final int n = StdRandom.uniform(N);
            for (int j = 0; j < n; j++) {
                deque1.addLast(j);
                deque2.addLast(j);
            }
            assertTrue(deque1.equals(deque2));
        }

        deque1 = new ArrayDeque<>();
        deque2 = new ArrayDeque<>();
        assertTrue(deque1.equals(deque2));

        deque1 = new ArrayDeque<>();
        deque2 = deque1;
        assertTrue(deque1.equals(deque2));
    }
}