package deque;

public class LinkedListDeque<T> {
    private class Node {
        T val;
        Node tail;
        Node prev;
    }

    private int size;
    private final Node sentinel;

    public LinkedListDeque() {
        this.size = 0;
        this.sentinel = new Node();
        this.sentinel.prev = this.sentinel;
        this.sentinel.tail = this.sentinel;
    }

    public void addFirst(T item) {
        Node newNode = new Node();
        newNode.val = item;
        newNode.prev = this.sentinel;
        newNode.tail = this.sentinel.tail;
        this.sentinel.tail = newNode;
        newNode.tail.prev = newNode;
        this.size++;
    }

    public void addLast(T item) {
        Node newNode = new Node();
        newNode.val = item;
        newNode.tail = this.sentinel;
        newNode.prev = this.sentinel.prev;
        this.sentinel.prev = newNode;
        newNode.prev.tail = newNode;
        this.size++;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    public int size() {
        return this.size;
    }

    public void printDeque() {
        for (Node node = this.sentinel.tail; node != this.sentinel; node = node.tail) {
            System.out.print(node.val + " ");
        }
        System.out.println();
    }

    public T removeFirst() {
        if (this.size() == 0) {
            return null;
        }
        Node toBeDeleted = this.sentinel.tail;
        this.sentinel.tail = toBeDeleted.tail;
        toBeDeleted.tail.prev = this.sentinel;
        this.size--;
        return toBeDeleted.val;
    }

    public T removeLast() {
        if (this.size() == 0) {
            return null;
        }
        Node toBeDeleted = this.sentinel.prev;
        this.sentinel.prev = toBeDeleted.prev;
        toBeDeleted.prev.tail = this.sentinel;
        this.size--;
        return toBeDeleted.val;
    }

    public T get(int index) {
        if (index + 1 >= this.size()) {
            return null;
        }
        Node node = this.sentinel.tail;
        while (index > 0) {
            node = node.tail;
            index--;
        }
        return node.val;
    }

    public T getRecursive(int index) {
        if (index + 1 >= this.size()) {
            return null;
        }
        return getRecursiveHelper(index, this.sentinel.tail);
    }

    private T getRecursiveHelper(int index, Node curNode) {
        if (index == 0) {
            return curNode.val;
        }
        return getRecursiveHelper(index - 1, curNode.tail);
    }
}
