package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimpleOne() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesSimpleTwo() {
        IntList lst = IntList.of();
        boolean changed = IntListExercises.squarePrimes(lst);
        assertNull(lst);
        assertFalse(changed);
    }

    @Test
    public void testSquarePrimesSimplesThree() {
        IntList lst = IntList.of(14, 17, 15);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals(lst.toString(), "14 -> 289 -> 15");
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesSimplesFour() {
        IntList lst = IntList.of(12, 14, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals(lst.toString(), "12 -> 14 -> 18");
        assertFalse(changed);
    }

    @Test
    public void testSquarePrimesSimplesFive() {
        IntList lst = IntList.of(12, 14, 3, 5, 7);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals(lst.toString(), "12 -> 14 -> 9 -> 25 -> 49");
        assertTrue(changed);
    }
}
