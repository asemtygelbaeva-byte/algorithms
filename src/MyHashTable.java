public class MyHashTable<K, V> {
    private static final int DEFAULT_CHAIN_COUNT = 11;

    private static class HashNode<K, V> {
        private K key;
        private V value;
        private HashNode<K, V> next;

        public HashNode(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + key + " " + value + "}";
        }
    }

    private HashNode<K, V>[] chainArray;
    private int M;
    private int size;

    public MyHashTable() {
        this(DEFAULT_CHAIN_COUNT);
    }

    @SuppressWarnings("unchecked")
    public MyHashTable(int M) {
        if (M <= 0) {
            throw new IllegalArgumentException("Number of chains must be positive");
        }
        this.M = M;
        this.chainArray = (HashNode<K, V>[]) new HashNode[M];
        this.size = 0;
    }

    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        return Math.floorMod(key.hashCode(), M);
    }

    public void put(K key, V value) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];

        while (current != null) {
            if (areEqual(current.key, key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        HashNode<K, V> newNode = new HashNode<>(key, value);
        newNode.next = chainArray[index];
        chainArray[index] = newNode;
        size++;
    }

    public V get(K key) {
        HashNode<K, V> node = getNode(key);
        if (node == null) {
            return null;
        }
        return node.value;
    }

    public V remove(K key) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];
        HashNode<K, V> previous = null;

        while (current != null) {
            if (areEqual(current.key, key)) {
                if (previous == null) {
                    chainArray[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }

        return null;
    }

    public boolean contains(V value) {
        for (int i = 0; i < M; i++) {
            HashNode<K, V> current = chainArray[i];
            while (current != null) {
                if (areEqual(current.value, value)) {
                    return true;
                }
                current = current.next;
            }
        }
        return false;
    }

    public K getKey(V value) {
        for (int i = 0; i < M; i++) {
            HashNode<K, V> current = chainArray[i];
            while (current != null) {
                if (areEqual(current.value, value)) {
                    return current.key;
                }
                current = current.next;
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    public int getBucketSize(int index) {
        if (index < 0 || index >= M) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Chains: " + M);
        }

        int count = 0;
        HashNode<K, V> current = chainArray[index];
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    public int[] getBucketSizes() {
        int[] bucketSizes = new int[M];
        for (int i = 0; i < M; i++) {
            bucketSizes[i] = getBucketSize(i);
        }
        return bucketSizes;
    }

    private HashNode<K, V> getNode(K key) {
        int index = hash(key);
        HashNode<K, V> current = chainArray[index];

        while (current != null) {
            if (areEqual(current.key, key)) {
                return current;
            }
            current = current.next;
        }

        return null;
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
}
