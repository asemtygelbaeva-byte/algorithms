public class Assignment3Test {
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void runAll() {
        testsPassed = 0;
        testsFailed = 0;

        testHashTableBasicOperations();
        testHashDistribution();
        testBSTBasicOperations();
        testBSTIterator();

        System.out.println("Assignment 3 passed: " + testsPassed);
        System.out.println("Assignment 3 failed: " + testsFailed);

        if (testsFailed > 0) {
            throw new IllegalStateException("Some Assignment 3 tests failed");
        }
    }

    private static void testHashTableBasicOperations() {
        MyHashTable<String, Integer> table = new MyHashTable<>();

        table.put("one", 1);
        table.put("two", 2);
        table.put("three", 3);
        table.put("two", 22);

        assertEquals("hash size after update", 3, table.size());
        assertEquals("hash get existing", 22, table.get("two"));
        assertEquals("hash get missing", null, table.get("missing"));
        assertTrue("hash contains value", table.contains(3));
        assertTrue("hash does not contain value", !table.contains(404));
        assertEquals("hash getKey", "one", table.getKey(1));
        assertEquals("hash remove", 22, table.remove("two"));
        assertEquals("hash removed value", null, table.get("two"));
        assertEquals("hash size after remove", 2, table.size());
    }

    private static void testHashDistribution() {
        MyHashTable<MyTestingClass, Student> table = new MyHashTable<>(11);

        for (int i = 0; i < 10000; i++) {
            MyTestingClass key = new MyTestingClass(i, "Student" + i, i % 31);
            table.put(key, new Student("Student" + i, 18 + i % 8));
        }

        int[] bucketSizes = table.getBucketSizes();
        int min = bucketSizes[0];
        int max = bucketSizes[0];
        for (int i = 1; i < bucketSizes.length; i++) {
            if (bucketSizes[i] < min) {
                min = bucketSizes[i];
            }
            if (bucketSizes[i] > max) {
                max = bucketSizes[i];
            }
        }

        assertEquals("hash 10000 elements size", 10000, table.size());
        assertTrue("hash distribution is uniform enough", max - min < 150);

        System.out.println("HashTable bucket sizes:");
        for (int i = 0; i < bucketSizes.length; i++) {
            System.out.println("bucket " + i + ": " + bucketSizes[i]);
        }
    }

    private static void testBSTBasicOperations() {
        BST<Integer, String> tree = new BST<>();

        tree.put(5, "five");
        tree.put(3, "three");
        tree.put(7, "seven");
        tree.put(4, "four");
        tree.put(3, "updated");

        assertEquals("bst size after update", 4, tree.size());
        assertEquals("bst get existing", "updated", tree.get(3));
        assertEquals("bst get missing", null, tree.get(10));

        tree.delete(3);
        assertEquals("bst size after delete", 3, tree.size());
        assertEquals("bst deleted value", null, tree.get(3));

        tree.delete(5);
        assertEquals("bst delete root", null, tree.get(5));
        assertEquals("bst size after root delete", 2, tree.size());
    }

    private static void testBSTIterator() {
        BST<Integer, String> tree = new BST<>();
        tree.put(8, "eight");
        tree.put(4, "four");
        tree.put(10, "ten");
        tree.put(2, "two");
        tree.put(6, "six");

        String keys = "";
        String values = "";
        for (BST.Entry<Integer, String> element : tree) {
            keys += element.getKey() + " ";
            values += element.getValue() + " ";
        }

        assertEquals("bst in-order keys", "2 4 6 8 10 ", keys);
        assertEquals("bst iterator values", "two four six eight ten ", values);
    }

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            testsPassed++;
        } else {
            testsFailed++;
            System.out.println("FAIL: " + testName);
        }
    }

    private static void assertEquals(String testName, Object expected, Object actual) {
        boolean equal;
        if (expected == actual) {
            equal = true;
        } else if (expected == null || actual == null) {
            equal = false;
        } else {
            equal = expected.equals(actual);
        }

        if (equal) {
            testsPassed++;
        } else {
            testsFailed++;
            System.out.println("FAIL: " + testName + ". Expected " + expected + ", got " + actual);
        }
    }

    private static class Student {
        private final String name;
        private final int age;

        private Student(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return name + " (" + age + ")";
        }
    }
}
