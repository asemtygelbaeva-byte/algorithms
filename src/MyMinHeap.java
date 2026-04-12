public class MyMinHeap<T extends Comparable<? super T>> {
    private final MyArrayList<T> heap;

    public MyMinHeap() {
        this.heap = new MyArrayList<>();
    }

    public void add(T item) {
        heap.addLast(item);
        siftUp(heap.size() - 1);
    }

    public T peek() {
        if (heap.size() == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap.getFirst();
    }

    public T poll() {
        if (heap.size() == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        T min = heap.getFirst();
        T last = heap.getLast();
        heap.removeLast();

        if (heap.size() > 0) {
            heap.set(0, last);
            siftDown(0);
        }

        return min;
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.size() == 0;
    }

    public void clear() {
        heap.clear();
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            T current = heap.get(index);
            T parentValue = heap.get(parent);
            if (current.compareTo(parentValue) >= 0) {
                return;
            }
            heap.set(index, parentValue);
            heap.set(parent, current);
            index = parent;
        }
    }

    private void siftDown(int index) {
        int size = heap.size();
        while (true) {
            int left = 2 * index + 1;
            int right = left + 1;
            int smallest = index;

            if (left < size && heap.get(left).compareTo(heap.get(smallest)) < 0) {
                smallest = left;
            }
            if (right < size && heap.get(right).compareTo(heap.get(smallest)) < 0) {
                smallest = right;
            }

            if (smallest == index) {
                return;
            }

            T temp = heap.get(index);
            heap.set(index, heap.get(smallest));
            heap.set(smallest, temp);
            index = smallest;
        }
    }
}
