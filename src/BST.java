import java.util.Iterator;
import java.util.NoSuchElementException;

public class BST<K extends Comparable<K>, V> implements Iterable<BST.Entry<K, V>> {
    public static class Entry<K, V> {
        private final K key;
        private final V value;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    private class Node {
        private K key;
        private V val;
        private Node left;
        private Node right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    private Node root;
    private int size;

    public void put(K key, V val) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        root = put(root, key, val);
    }

    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }

        Node current = root;
        while (current != null) {
            int comparison = key.compareTo(current.key);
            if (comparison < 0) {
                current = current.left;
            } else if (comparison > 0) {
                current = current.right;
            } else {
                return current.val;
            }
        }

        return null;
    }

    public void delete(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null");
        }
        root = delete(root, key);
    }

    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            private final MyStack<Node> stack = new MyStack<>();

            {
                pushLeft(root);
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public Entry<K, V> next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No more elements");
                }

                Node node = stack.pop();
                pushLeft(node.right);
                return new Entry<>(node.key, node.val);
            }

            private void pushLeft(Node node) {
                Node current = node;
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }
            }
        };
    }

    private Node put(Node node, K key, V val) {
        if (node == null) {
            size++;
            return new Node(key, val);
        }

        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.left = put(node.left, key, val);
        } else if (comparison > 0) {
            node.right = put(node.right, key, val);
        } else {
            node.val = val;
        }

        return node;
    }

    private Node delete(Node node, K key) {
        if (node == null) {
            return null;
        }

        int comparison = key.compareTo(node.key);
        if (comparison < 0) {
            node.left = delete(node.left, key);
        } else if (comparison > 0) {
            node.right = delete(node.right, key);
        } else {
            size--;
            if (node.right == null) {
                return node.left;
            }
            if (node.left == null) {
                return node.right;
            }

            Node replacement = min(node.right);
            replacement.right = deleteMin(node.right);
            replacement.left = node.left;
            return replacement;
        }

        return node;
    }

    private Node deleteMin(Node node) {
        if (node.left == null) {
            return node.right;
        }
        node.left = deleteMin(node.left);
        return node;
    }

    private Node min(Node node) {
        Node current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
}
