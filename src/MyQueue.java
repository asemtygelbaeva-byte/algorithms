public class MyQueue<T> {
    private final MyLinkedList<T> data;

    public MyQueue() {
        this.data = new MyLinkedList<>();
    }

    public void enqueue(T item) {
        data.addLast(item);
    }

    public T dequeue() {
        T value = data.getFirst();
        data.removeFirst();
        return value;
    }

    public T peek() {
        return data.getFirst();
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.size() == 0;
    }

    public void clear() {
        data.clear();
    }
}
