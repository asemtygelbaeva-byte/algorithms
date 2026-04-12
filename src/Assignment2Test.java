public class Assignment2Test {
    private static int testsPassed = 0;
    private static int testsFailed = 0;

    public static void runAll() {
        testsPassed = 0;
        testsFailed = 0;

        testArrayList();
        testLinkedList();
        testStack();
        testQueue();
        testMinHeap();

        System.out.println("Passed: " + testsPassed);
        System.out.println("Failed: " + testsFailed);

        if (testsFailed > 0) {
            throw new IllegalStateException("Some tests failed");
        }
    }

    private static void testArrayList() {
        MyArrayList<Integer> list = new MyArrayList<>();
        assertEquals("array initial size", 0, list.size());

        list.add(2);
        list.addFirst(1);
        list.addLast(3);
        list.add(3, 4);
        list.add(2, 99);
        assertArrayEquals("array add operations", list.toArray(), new Object[]{1, 2, 99, 3, 4});

        list.set(2, 5);
        assertEquals("array get", 5, list.get(2));
        assertEquals("array getFirst", 1, list.getFirst());
        assertEquals("array getLast", 4, list.getLast());

        list.add(5);
        list.add(1);
        assertEquals("array indexOf", 0, list.indexOf(1));
        assertEquals("array lastIndexOf", 6, list.lastIndexOf(1));
        assertTrue("array exists true", list.exists(5));
        assertTrue("array exists false", !list.exists(777));

        list.sort();
        assertArrayEquals("array sort", list.toArray(), new Object[]{1, 1, 2, 3, 4, 5, 5});

        list.remove(1);
        list.removeFirst();
        list.removeLast();
        assertArrayEquals("array remove operations", list.toArray(), new Object[]{2, 3, 4, 5});

        int sum = 0;
        for (Integer value : list) {
            sum += value;
        }
        assertEquals("array iterator", 14, sum);

        list.clear();
        assertEquals("array clear", 0, list.size());
        expectException("array getFirst on empty", new Runnable() {
            @Override
            public void run() {
                list.getFirst();
            }
        });
        expectException("array removeLast on empty", new Runnable() {
            @Override
            public void run() {
                list.removeLast();
            }
        });
    }

    private static void testLinkedList() {
        MyLinkedList<Integer> list = new MyLinkedList<>();
        assertEquals("linked initial size", 0, list.size());

        list.add(2);
        list.addFirst(1);
        list.addLast(4);
        list.add(2, 3);
        list.add(4, 5);
        assertArrayEquals("linked add operations", list.toArray(), new Object[]{1, 2, 3, 4, 5});

        list.set(1, 99);
        assertEquals("linked set/get", 99, list.get(1));
        assertEquals("linked getFirst", 1, list.getFirst());
        assertEquals("linked getLast", 5, list.getLast());

        list.add(99);
        assertEquals("linked indexOf", 1, list.indexOf(99));
        assertEquals("linked lastIndexOf", 5, list.lastIndexOf(99));
        assertTrue("linked exists", list.exists(4));
        assertTrue("linked not exists", !list.exists(777));

        list.sort();
        assertArrayEquals("linked sort", list.toArray(), new Object[]{1, 3, 4, 5, 99, 99});

        list.remove(1);
        list.removeFirst();
        list.removeLast();
        assertArrayEquals("linked remove operations", list.toArray(), new Object[]{4, 5, 99});

        int sum = 0;
        for (Integer value : list) {
            sum += value;
        }
        assertEquals("linked iterator", 108, sum);

        list.clear();
        assertEquals("linked clear", 0, list.size());
        expectException("linked getLast on empty", new Runnable() {
            @Override
            public void run() {
                list.getLast();
            }
        });
        expectException("linked removeFirst on empty", new Runnable() {
            @Override
            public void run() {
                list.removeFirst();
            }
        });
    }

    private static void testStack() {
        MyStack<String> stack = new MyStack<>();
        assertTrue("stack starts empty", stack.isEmpty());

        stack.push("A");
        stack.push("B");
        stack.push("C");
        assertEquals("stack size", 3, stack.size());
        assertEquals("stack peek", "C", stack.peek());
        assertEquals("stack pop 1", "C", stack.pop());
        assertEquals("stack pop 2", "B", stack.pop());
        assertEquals("stack pop 3", "A", stack.pop());
        assertTrue("stack empty after pops", stack.isEmpty());

        expectException("stack pop empty", new Runnable() {
            @Override
            public void run() {
                stack.pop();
            }
        });
    }

    private static void testQueue() {
        MyQueue<Integer> queue = new MyQueue<>();
        assertTrue("queue starts empty", queue.isEmpty());

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        assertEquals("queue size", 3, queue.size());
        assertEquals("queue peek", 10, queue.peek());
        assertEquals("queue dequeue 1", 10, queue.dequeue());
        assertEquals("queue dequeue 2", 20, queue.dequeue());
        assertEquals("queue dequeue 3", 30, queue.dequeue());
        assertTrue("queue empty after dequeues", queue.isEmpty());

        expectException("queue dequeue empty", new Runnable() {
            @Override
            public void run() {
                queue.dequeue();
            }
        });
    }

    private static void testMinHeap() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        assertTrue("heap starts empty", heap.isEmpty());

        heap.add(7);
        heap.add(2);
        heap.add(9);
        heap.add(1);
        heap.add(4);

        assertEquals("heap size", 5, heap.size());
        assertEquals("heap peek", 1, heap.peek());
        assertEquals("heap poll 1", 1, heap.poll());
        assertEquals("heap poll 2", 2, heap.poll());
        assertEquals("heap poll 3", 4, heap.poll());
        assertEquals("heap poll 4", 7, heap.poll());
        assertEquals("heap poll 5", 9, heap.poll());
        assertTrue("heap empty after polls", heap.isEmpty());

        expectException("heap poll empty", new Runnable() {
            @Override
            public void run() {
                heap.poll();
            }
        });
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

    private static void assertArrayEquals(String testName, Object[] actual, Object[] expected) {
        if (actual.length != expected.length) {
            testsFailed++;
            System.out.println("FAIL: " + testName + ". Different length");
            return;
        }

        for (int i = 0; i < actual.length; i++) {
            Object left = actual[i];
            Object right = expected[i];
            boolean equal;
            if (left == right) {
                equal = true;
            } else if (left == null || right == null) {
                equal = false;
            } else {
                equal = left.equals(right);
            }

            if (!equal) {
                testsFailed++;
                System.out.println("FAIL: " + testName + ". Difference at index " + i);
                return;
            }
        }

        testsPassed++;
    }

    private static void expectException(String testName, Runnable action) {
        try {
            action.run();
            testsFailed++;
            System.out.println("FAIL: " + testName + ". Exception was expected");
        } catch (RuntimeException ex) {
            testsPassed++;
        }
    }
}
