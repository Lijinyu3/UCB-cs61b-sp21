package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
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
}