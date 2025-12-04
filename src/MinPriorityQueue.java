// Time: O(n)
// Space: O(n)

// DATA STRUCTURE:
//     A : array representing heap
//     A.heap_size : number of elements currently stored
// HELPER FUNCTIONS:
//     PARENT(i) = ⌊i/2⌋
//     LEFT(i)   = 2*i
//     RIGHT(i)  = 2*i+1
// PROCEDURE MIN-HEAPIFY(A, i)
//     l ← LEFT(i)
//     r ← RIGHT(i)
//     if l ≤ A.heap_size and A[l] < A[i]
//         smallest ← l
//     else smallest ← i
//     if r ≤ A.heap_size and A[r] < A[smallest]
//         smallest ← r
//     if smallest ≠ i
//         exchange A[i] ↔ A[smallest]
//         MIN-HEAPIFY(A, smallest)
// PROCEDURE HEAP-MINIMUM(A)
//     return A[1]
// PROCEDURE HEAP-EXTRACT-MIN(A)
//     if A.heap_size < 1
//         error "HEAP UNDERFLOW"
//     min ← A[1]
//     A[1] ← A[A.heap_size]
//     A.heap_size ← A.heap_size - 1
//     MIN-HEAPIFY(A, 1)
//     return min
// PROCEDURE HEAP-DECREASE-KEY(A, i, key)
//     if key > A[i]
//         error "NEW KEY IS GREATER THAN CURRENT KEY"
//     A[i] ← key
//     while i > 1 and A[PARENT(i)] > A[i]
//         exchange A[i] ↔ A[PARENT(i)]
//         i ← PARENT(i)
// PROCEDURE MIN-HEAP-INSERT(A, key)
//     A.heap_size ← A.heap_size + 1
//     A[A.heap_size] ← +∞
//     HEAP-DECREASE-KEY(A, A.heap_size, key)
public class MinPriorityQueue {

    private int[] heap;
    private int size;
    private int capacity;

    public MinPriorityQueue(int capacity) {
        this.capacity = capacity;
        heap = new int[capacity + 1]; // index 1-based
        size = 0;
    }

    private int parent(int i) {
        return i / 2;
    }

    private int left(int i) {
        return 2 * i;
    }

    private int right(int i) {
        return 2 * i + 1;
    }

    public int heapMinimum() {
        if (size < 1) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap[1];
    }

    private void minHeapify(int i) {
        int l = left(i), r = right(i);
        int smallest = i;

        if (l <= size && heap[l] < heap[smallest]) {
            smallest = l;
        }
        if (r <= size && heap[r] < heap[smallest]) {
            smallest = r;
        }

        if (smallest != i) {
            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    public int heapExtractMin() {
        if (size < 1) {
            throw new IllegalStateException("Heap underflow");
        }

        int min = heap[1];
        heap[1] = heap[size];
        size--;
        minHeapify(1);

        return min;
    }

    public void heapDecreaseKey(int i, int key) {
        if (key > heap[i]) {
            throw new IllegalArgumentException("New key is greater than current key");
        }

        heap[i] = key;
        while (i > 1 && heap[parent(i)] > heap[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    public void minHeapInsert(int key) {
        if (size == capacity) {
            throw new IllegalStateException("Heap is full");
        }

        size++;
        heap[size] = Integer.MAX_VALUE;
        heapDecreaseKey(size, key);
    }

    private void swap(int a, int b) {
        int temp = heap[a];
        heap[a] = heap[b];
        heap[b] = temp;
    }

    public static void main(String[] args) {
        MinPriorityQueue pq = new MinPriorityQueue(10);
        pq.minHeapInsert(5);
        pq.minHeapInsert(2);
        pq.minHeapInsert(9);
        pq.minHeapInsert(1);

        System.out.println("Minimum: " + pq.heapMinimum()); // 1
        System.out.println("Extracted Min: " + pq.heapExtractMin()); // 1
        System.out.println("New Minimum: " + pq.heapMinimum()); // 2
    }
}
