import java.util.Iterator;

public class MyLinkedList<T> implements MyList<T> {
    private static final class MyNode<E> {
        private E item;
        private MyNode<E> next;
        private MyNode<E> prev;

        private MyNode(E item) {
            this.item = item;
        }
    }

    private MyNode<T> head;
    private MyNode<T> tail;
    private int size;

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    @Override
    public void add(T item) {
        addLast(item);
    }

    @Override
    public void set(int index, T item) {
        MyNode<T> node = getNode(index);
        node.item = item;
    }

    @Override
    public void add(int index, T item) {
        checkPosition(index);
        if (index == size) {
            addLast(item);
            return;
        }
        if (index == 0) {
            addFirst(item);
            return;
        }

        MyNode<T> target = getNode(index);
        MyNode<T> newNode = new MyNode<>(item);
        MyNode<T> previous = target.prev;

        newNode.prev = previous;
        newNode.next = target;
        previous.next = newNode;
        target.prev = newNode;
        size++;
        ensureAcyclic();
    }

    @Override
    public void addFirst(T item) {
        MyNode<T> newNode = new MyNode<>(item);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
        ensureAcyclic();
    }

    @Override
    public void addLast(T item) {
        MyNode<T> newNode = new MyNode<>(item);
        if (size == 0) {
            head = newNode;
            tail = newNode;
        } else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        ensureAcyclic();
    }

    @Override
    public T get(int index) {
        return getNode(index).item;
    }

    @Override
    public T getFirst() {
        if (size == 0) {
            throw new IllegalStateException("List is empty");
        }
        return head.item;
    }

    @Override
    public T getLast() {
        if (size == 0) {
            throw new IllegalStateException("List is empty");
        }
        return tail.item;
    }

    @Override
    public void remove(int index) {
        MyNode<T> node = getNode(index);
        unlink(node);
        ensureAcyclic();
    }

    @Override
    public void removeFirst() {
        if (size == 0) {
            throw new IllegalStateException("List is empty");
        }
        unlink(head);
        ensureAcyclic();
    }

    @Override
    public void removeLast() {
        if (size == 0) {
            throw new IllegalStateException("List is empty");
        }
        unlink(tail);
        ensureAcyclic();
    }

    @Override
    public void sort() {
        if (size < 2) {
            return;
        }
        for (MyNode<T> i = head.next; i != null; i = i.next) {
            T value = i.item;
            MyNode<T> j = i.prev;
            while (j != null && compare(j.item, value) > 0) {
                j.next.item = j.item;
                j = j.prev;
            }
            if (j == null) {
                head.item = value;
            } else {
                j.next.item = value;
            }
        }
    }

    @Override
    public int indexOf(Object object) {
        int index = 0;
        for (MyNode<T> current = head; current != null; current = current.next) {
            if (areEqual(current.item, object)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        int index = size - 1;
        for (MyNode<T> current = tail; current != null; current = current.prev) {
            if (areEqual(current.item, object)) {
                return index;
            }
            index--;
        }
        return -1;
    }

    @Override
    public boolean exists(Object object) {
        return indexOf(object) != -1;
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[size];
        int i = 0;
        for (MyNode<T> current = head; current != null; current = current.next) {
            result[i++] = current.item;
        }
        return result;
    }

    @Override
    public void clear() {
        MyNode<T> current = head;
        while (current != null) {
            MyNode<T> next = current.next;
            current.next = null;
            current.prev = null;
            current.item = null;
            current = next;
        }
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private MyNode<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new IllegalStateException("No more elements");
                }
                T value = current.item;
                current = current.next;
                return value;
            }
        };
    }

    private void unlink(MyNode<T> node) {
        MyNode<T> previous = node.prev;
        MyNode<T> next = node.next;

        if (previous == null) {
            head = next;
        } else {
            previous.next = next;
        }

        if (next == null) {
            tail = previous;
        } else {
            next.prev = previous;
        }

        node.next = null;
        node.prev = null;
        node.item = null;
        size--;
    }

    private MyNode<T> getNode(int index) {
        checkIndex(index);
        if (index < size / 2) {
            MyNode<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }

        MyNode<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.prev;
        }
        return current;
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private void checkPosition(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    private boolean areEqual(Object left, Object right) {
        if (left == right) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        return left.equals(right);
    }

    @SuppressWarnings("unchecked")
    private int compare(Object left, Object right) {
        if (left == right) {
            return 0;
        }
        if (left == null) {
            return -1;
        }
        if (right == null) {
            return 1;
        }
        if (!(left instanceof Comparable<?>)) {
            throw new IllegalStateException("Elements must implement Comparable");
        }
        return ((Comparable<Object>) left).compareTo(right);
    }

    private void ensureAcyclic() {
        MyNode<T> slow = head;
        MyNode<T> fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                throw new IllegalStateException("Loop detected in linked list");
            }
        }

        if (size == 0) {
            if (head != null || tail != null) {
                throw new IllegalStateException("Invalid empty state");
            }
            return;
        }

        if (head == null || tail == null) {
            throw new IllegalStateException("Invalid boundary nodes");
        }
        if (head.prev != null || tail.next != null) {
            throw new IllegalStateException("Broken boundary links");
        }
    }
}
