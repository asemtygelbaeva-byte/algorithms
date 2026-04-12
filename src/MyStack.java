public class MyStack<T> {
    private final MyArrayList<T> data;

    public MyStack() {
        this.data = new MyArrayList<>();
    }

    public void push(T item) {
        data.addLast(item);
    }

    public T pop() {
        T value = data.getLast();
        data.removeLast();
        return value;
    }

    public T peek() {
        return data.getLast();
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
