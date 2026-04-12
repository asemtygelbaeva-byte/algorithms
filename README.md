# Assignment 2 - ArrayList and LinkedList

## What is implemented

Physical data structures:
- `MyList<T>` interface
- `MyArrayList<T>`
- `MyLinkedList<T>` (doubly linked list)

Logical data structures:
- `MyStack<T>` (based on `MyArrayList<T>`)
- `MyQueue<T>` (based on `MyLinkedList<T>`)
- `MyMinHeap<T extends Comparable<? super T>>` (based on `MyArrayList<T>`)

Tests:
- `Assignment2Test` contains tests for all methods in `MyArrayList` and `MyLinkedList`
- also includes tests for stack, queue, and min-heap operations

## Important constraints

- No `java.util.*` classes are used in data structures except `Iterator`.
- Sorting in list implementations works with elements that implement `Comparable`.
- `MyLinkedList` contains loop-prevention validation after structural changes.

## How to run

From project root:

```powershell
javac src\*.java
java -cp src Main
```

Expected output:
- number of passed/failed tests
- `All Assignment 2 tests passed.`

## Suggested 6-commit plan (for grading)

1. `feat: add MyList interface and project skeleton`
2. `feat: implement MyArrayList with full API`
3. `feat: implement doubly linked MyLinkedList with loop checks`
4. `feat: add MyStack MyQueue MyMinHeap based on custom lists`
5. `test: add Assignment2Test covering all list methods`
6. `docs: add README and usage instructions`

## Complexity (short)

- `MyArrayList`:
  - `get/set`: `O(1)`
  - `add/remove` by index: `O(n)`
  - `addLast`: amortized `O(1)`
- `MyLinkedList`:
  - add/remove first/last: `O(1)`
  - get/set by index: `O(n)`
- `MyStack`: push/pop/peek `O(1)` amortized
- `MyQueue`: enqueue/dequeue/peek `O(1)`
- `MyMinHeap`: add/poll `O(log n)`, peek `O(1)`
