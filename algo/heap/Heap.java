import java.util.Arrays;

public class Heap {

    private int[] heap;

    private int n; // max capacity of lower layer

    private int k = 0; // elements

    public Heap(int capacity) {
        n = 1;
        while (n < capacity) {
            n <<= 1;
        }
        this.heap = new int[2 * n];
    }

    // extract minimum
    public int poll() {
        if (k < 1) {
            throw new RuntimeException("cannot poll from empty heap");
        }
        int top = heap[1];
        heap[1] = heap[1 + k];
        drown(1);
        --k;
        return top;
    }

    // get the minimum
    public int peek() {
        if (k < 1) {
            throw new RuntimeException("cannot peek from empty heap");
        }
        return heap[1];
    }

    // add element to heap
    public void push(int value) {
        heap[k] = value;
        popup(k);
        ++k;
    }

    private void swap(int a, int b) {
        int tmp = heap[a];
        heap[a] = heap[b];
        heap[b] = tmp;
    }

    private void drown(int i) {
        while (i < n) {
            if (Integer.min(heap[left(i)], heap[right(i)]) > heap[i]) {
                return;
            }
            if (heap[left(i)] < heap[right(i)]) {
                swap(i, left(i));
                i = left(i);
            } else {
                swap(i, right(i));
                i = right(i);
            }
        }
    }

    private void popup(int i) {
        while (i > 1 && heap[parent(i)] > heap[i]) {
            swap(i, parent(i));
            i = parent(i);
        }
    }

    private int parent(int i) {
        return i >>> 1;
    }

    private int left(int i) {
        return i << 1;
    }

    private int right(int i) {
        return (i << 1) + 1;
    }
}