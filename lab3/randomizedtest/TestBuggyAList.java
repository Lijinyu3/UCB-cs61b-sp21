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
}
