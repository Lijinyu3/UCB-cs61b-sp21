package deque;

public class LinkedListDeque<T> {
    private int size;
    private final Node sentinel;

    /**
     * Naked data structure Node
     */
    private class Node {
        public Node prev;
        public Node next;
        public T item;

        public Node() {
        }
        public Node(T item, Node prev, Node next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * Constructor Method
     * Create an empty linked list deque
     */
    public LinkedListDeque() {
        sentinel = new Node();
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    /**
     * Returns if the index is valid
     */
    private boolean checkIndex(int index) {
        return !isEmpty() && 0 >= index && index < size();
    }

    /**
     * Adds an item of type TypeName to the front of the deque
     * item cannot be null.
     */
    public void addFirst(T item) {
        Node newNode = new Node(item, sentinel, sentinel.next);
        sentinel.next = newNode;
        newNode.next.prev = newNode;
        size += 1;
    }

    /**
     * Adds an item of type TypeName to the back of the deque
     * item cannot be null
     */
    public void addLast(T item) {
        Node newNode = new Node(item, sentinel.prev, sentinel);
        sentinel.prev = newNode;
        newNode.prev.next = newNode;
        size += 1;
    }

    /**
     * Returns the number of items in the deque
     */
    public int size() {
        return size;
    }

    /**
     * Returns if deque is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Prints the items in the deque from first to last,
     * separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        Node curNode = sentinel.next;
        while (curNode != sentinel) {
            System.out.print(curNode.item);
            System.out.print(' ');
            curNode = curNode.next;
        }
        System.out.println();
    }

    /**
     * Removes and returns the items at the front of the deque.
     * If no such item exists, return null
     */
    public T removeFirst() {
        Node firstNode = sentinel.next;
        if (firstNode == sentinel) {
            return null;
        }
        sentinel.next = firstNode.next;
        firstNode.next.prev = sentinel;
        size -= 1;
        return firstNode.item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, return null
     */
    public T removeLast() {
        Node lastNode = sentinel.prev;
        if (lastNode == sentinel) {
            return null;
        }
        sentinel.prev = lastNode.prev;
        lastNode.prev.next = sentinel;
        size -= 1;
        return lastNode.item;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     * This method will not alter the deque.
     */
    public T get(int index) {
        if (!checkIndex(index)) {
            return null;
        }
        Node curNode = sentinel.next;
        for (int i = 0; i < index; i++) {
            curNode = curNode.next;
        }
        return curNode.item;
    }

    /**
     * Same as get method, but uses recursion.
     */
    public T getRecursion(int index) {
        if (!checkIndex(index)) {
            return null;
        }
        return getRecursionHelper(index, sentinel.next);
    }

    /**
     * helper function for getRecursion method
     */
    private T getRecursionHelper(int index, Node curNode) {
        if (index == 0) {
            return curNode.item;
        }
        return getRecursionHelper(index - 1, curNode.next);
    }
}
