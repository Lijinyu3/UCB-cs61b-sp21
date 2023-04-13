package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T>{
    private final Comparator<T> defaultComparator;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        this.defaultComparator = c;
    }

    public T max() {
        T maxItem = null;
        for (int i = 0; i < this.size(); i++) {
            if (maxItem == null || this.defaultComparator.compare(this.get(i), maxItem) > 0) {
                maxItem = this.get(i);
            }
        }
        return maxItem;
    }

    public T max(Comparator<T> c) {
        T maxItem = null;
        for (int i = 0; i < this.size(); i++) {
            if (maxItem == null || c.compare(this.get(i), maxItem) > 0) {
                maxItem = this.get(i);
            }
        }
        return maxItem;
    }
}
