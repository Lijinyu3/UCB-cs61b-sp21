package tester;

import edu.princeton.cs.algs4.StdRandom;

import static org.junit.Assert.*;

import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    private static final String[] OPERATIONS =
            {"addFirst(%s)", "addLast(%s)", "removeFirst()", "removeLast()"};

    private void updateSequences(StringBuilder sequence, int opNum, String opVal) {
        if (opNum <= 1) {
            sequence.append(String.format(OPERATIONS[opNum], opVal));
        } else {
            sequence.append(String.format(OPERATIONS[opNum]));
        }
        sequence.append(System.lineSeparator());
    }

    @Test
    public void comparisonTest() {
        StudentArrayDeque<Integer> actualDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> expectedDeque = new ArrayDequeSolution<>();
        StringBuilder operationsSequence = new StringBuilder(System.lineSeparator());
        final int N = (int) 1e5;
        for (int i = 0; i < N; i++) {
            int opNum = StdRandom.uniform(4);
            String opVal = String.valueOf(i);
            String resVal = "";
            String resExpected = "";
            if (opNum == 0) {
                actualDeque.addFirst(i);
                expectedDeque.addFirst(i);
            } else if (opNum == 1) {
                actualDeque.addLast(i);
                expectedDeque.addLast(i);
            }
            if (opNum >= 2 && expectedDeque.isEmpty()) {
                continue;
            }
            if (opNum == 2) {
                Integer tmp = actualDeque.removeFirst();
                resVal = tmp == null ? "null" : String.valueOf(tmp);
                tmp = expectedDeque.removeFirst();
                resExpected = tmp == null ? "null" : String.valueOf(tmp);
            } else if (opNum == 3) {
                Integer tmp = actualDeque.removeLast();
                resVal = tmp == null ? "null" : String.valueOf(tmp);
                tmp = expectedDeque.removeLast();
                resExpected = tmp == null ? "null" : String.valueOf(tmp);
            }
            updateSequences(operationsSequence, opNum, opVal);
            assertEquals(operationsSequence.toString(), resExpected, resVal);
        }
    }
}
