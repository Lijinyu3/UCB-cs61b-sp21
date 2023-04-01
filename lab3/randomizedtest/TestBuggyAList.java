package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
    @Test
    public void testThreeAddThreeRemove() {
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        AListNoResizing<Integer> aListNoResizing = new AListNoResizing<>();
        int[] nums = new int[] {4, 5, 6};
        for (int i : nums) {
            buggyAList.addLast(i);
            aListNoResizing.addLast(i);
        }
        assertEquals(aListNoResizing.size(), buggyAList.size());
        for (int i = 0; i < nums.length; i++) {
            assertEquals(aListNoResizing.removeLast(), buggyAList.removeLast());
        }
    }


    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();

        int N = 500;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                System.out.println("size: " + size);
            } else if (operationNumber == 2 && L.size() > 0) {
                // removeLast
                int removedItem = L.removeLast();
                System.out.println("removed last: " + removedItem);
            } else if (operationNumber == 3 && L.size() > 0) {
                // getLast
                int lastItem = L.getLast();
                System.out.println("last item: " + lastItem);
            }
        }
    }


    @Test
    public void randomizedComparison() {
        AListNoResizing<Integer> expectedList = new AListNoResizing<>();
        BuggyAList<Integer> actualList = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                expectedList.addLast(randVal);
                actualList.addLast(randVal);
                System.out.println("addLast(" + randVal + ")");
            } else if (operationNumber == 1) {
                // size
                int expectedSize = expectedList.size();
                int actualSize = actualList.size();
                assertEquals(expectedSize, actualSize);
            } else if (operationNumber == 2 && expectedList.size() > 0) {
                // removeLast
                int expectedRemovedItem = expectedList.removeLast();
                int actualRemovedItem = actualList.removeLast();;
                assertEquals(expectedRemovedItem, actualRemovedItem);
            } else if (operationNumber == 3 && expectedList.size() > 0) {
                // getLast
                int expectedLastItem = expectedList.getLast();
                int actualLastItem = actualList.getLast();
                assertEquals(expectedLastItem, actualLastItem);
            }
        }
    }
}
