public class HeapSort {

    static class Heap {

        int[] heap;

        int size;

        Heap(int[] arr) {
            this.heap = new int[arr.length + 1];
            System.arraycopy(arr, 0, heap, 1, arr.length);
            this.size = arr.length;

            for (int i = size / 2; i > 0; --i) {
                drown(i);
            }
        }

        void push(int n) {
            ++size;
            heap[size] = n;
            popup(size);
        }

        int poll() {
            int top = heap[1];
            heap[1] = heap[size];
            --size;
            drown(1);
            return top;
        }

        int peek() {
            return heap[1];
        }

        int parent(int i) {
            return i >>> 1;
        }

        int left(int i) {
        }

        int right(int i) {
            return (i << 1) + 1;
        }

        void swap(int a, int b) {
            int temp = heap[a];
            heap[a] = heap[b];
            heap[b] = temp;
        }

        void drown(int i) {
            while (i <= size / 2) {
                int min = i;
                if (right(i) <= size && heap[right(i)] < heap[left(i)]) {
                    min = i;
                } else if (left(i) <= size) {
                    min = i;
                }
                if (min == i) {
                    break;
                }
                swap(min, i);
                i = min;
            }
        }

        void popup(int i) {
            while (i > 0) {
                if (heap[parent(i)] > heap[i]) {
                    swap(i, parent(i));
                    i = parent(i);
                }
            }
        }
    }

    public static void heapSort(int[] arr) {
        Heap heap = new Heap(arr);
        for (int i = 0; i < arr.length; ++i) {
            arr[i] = heap.poll();
        }
    }
}