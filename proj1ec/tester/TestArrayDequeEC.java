package tester;

import edu.princeton.cs.algs4.StdRandom;

import static org.junit.Assert.*;

import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    private static final String[] OPERATIONS =
            {"addFirst(%s)", "addLast(%s)", "size(): %s", "isEmpty(): %s",
                    "removeFirst(): %s", "removeLast(): %s", "get(%s): %s"};

    private void updateSequences(StringBuilder sequence, int opNum, String opVal, String resVal) {
        if (opNum <= 1) {
            sequence.append(String.format(OPERATIONS[opNum], opVal));
        } else if (opNum <= 5) {
            sequence.append(String.format(OPERATIONS[opNum], resVal));
        } else {
            sequence.append(String.format(OPERATIONS[opNum], opVal, resVal));
        }
        sequence.append(System.lineSeparator());
    }

    @Test
    public void comparisonTest() {
        StudentArrayDeque<Integer> actualDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> expectedDeque = new ArrayDequeSolution<>();
        StringBuilder operationsSequence = new StringBuilder();
        final int N = (int) 1e5;
        for (int i = 0; i < N; i++) {
            int opNum = StdRandom.uniform(7);
            String opVal = String.valueOf(i);
            String resVal = "";
            String resExpected = "";
            if (opNum == 0) {
                actualDeque.addFirst(i);
                expectedDeque.addFirst(i);
            } else if (opNum == 1) {
                actualDeque.addLast(i);
                expectedDeque.addLast(i);
            } else if (opNum == 2) {
                assertEquals(expectedDeque.size(), actualDeque.size());
                resVal = String.valueOf(actualDeque.size());
                resExpected = String.valueOf(expectedDeque.size());
            } else if (opNum == 3) {
                resVal = actualDeque.isEmpty() ? "true" : "false";
                resExpected = expectedDeque.isEmpty() ? "true" : "false";
            }
            if (opNum >= 4 && expectedDeque.isEmpty()) {
                continue;
            }
            if (opNum == 4) {
                Integer tmp = actualDeque.removeFirst();
                resVal = tmp == null ? "null" : String.valueOf(tmp);
                tmp = expectedDeque.removeFirst();
                resExpected = tmp == null ? "null" : String.valueOf(tmp);
            } else if (opNum == 5) {
                Integer tmp = actualDeque.removeLast();
                resVal = tmp == null ? "null" : String.valueOf(tmp);
                tmp = expectedDeque.removeLast();
                resExpected = tmp == null ? "null" : String.valueOf(tmp);
            } else if (opNum == 6) {
                int randomIndex = StdRandom.uniform(expectedDeque.size());
                opVal = String.valueOf(randomIndex);
                resVal = String.valueOf(actualDeque.get(randomIndex));
                resExpected = String.valueOf(expectedDeque.get(randomIndex));
            }
            updateSequences(operationsSequence, opNum, opVal, resVal);
            assertEquals(operationsSequence.toString(), resExpected, resVal);
        }
    }
}
