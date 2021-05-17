public class QuickSort {

    private static void swap(int[] arr, int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /*
     * return index i in subarray, such that for each index j < i arr[j] <= arr[i]
     * and and for each j > i arr[j] >= arr[i]
     */
    private static int partition(int[] arr, int from, int len, int pivot) {
        int a = from;
        int b = from + len - 1;

        while (a < b) {
            while (a < b && arr[a] <= pivot) {
                ++a;
            }
            while (b > a && arr[b] >= pivot) {
                --b;
            }

            swap(arr, a, b);
            if (a < b) {
                ++a;
                --b;
            }
        }

        return a;
    }

    public static void quickSort(int[] arr, int from, int len) {
        if (len < 2) {
            return;
        }
        int pivot = arr[from + len / 2];
        int mid = partition(arr, from, len, pivot);
        quickSort(arr, from, mid - from);
        quickSort(arr, mid + 1, from + len - mid - 1);
    }
}