package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {

    @Test
    public void max() {
        Comparator<Integer> comparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        };
        MaxArrayDeque<Integer> maxArrayDeque = new MaxArrayDeque<>(comparator);
        final int N = (int) 1e5;
        for (int i = 0; i < N; i++) {
            int operationNum = StdRandom.uniform(0, 2);
            if (operationNum == 0) {
                maxArrayDeque.addFirst(i);
            } else if (operationNum == 1) {
                maxArrayDeque.addLast(i);
            }
        }
        int maxItem = maxArrayDeque.max();
        assertEquals(N - 1, maxItem);

        Comparator<Integer> reversedComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        };
        assertEquals(0, maxArrayDeque.max(reversedComparator).intValue());

        Comparator<Integer> digitSumComp = new Comparator<Integer>() {
            private int digitSum(int item) {
                if (item == 0) {
                    return 0;
                }
                return item % 10 + digitSum(item / 10);
            }
            @Override
            public int compare(Integer o1, Integer o2) {
                return digitSum(o1) - digitSum(o2);
            }
        };
        assertEquals(N - 1, maxArrayDeque.max(digitSumComp).intValue());
        maxArrayDeque = new MaxArrayDeque<>(digitSumComp);
        for (int i = 0; i < (int) 91e2; i++) {
            int operationNum = StdRandom.uniform(0, 2);
            if (operationNum == 0) {
                maxArrayDeque.addFirst(i);
            } else if (operationNum == 1) {
                maxArrayDeque.addLast(i);
            }
        }
        assertEquals(8999, maxArrayDeque.max(digitSumComp).intValue());
    }
}