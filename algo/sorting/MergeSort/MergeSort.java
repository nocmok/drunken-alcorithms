import java.lang.System;

public class MergeSort {

    /**
     * merge two consequetive subarrays a and b
     */
    private static void merge(int arr[], int[] buf, int a, int aLen, int b, int bLen) {
        int from = a;

        int aEnd = a + aLen;
        int bEnd = b + bLen;

        int i = 0;

        while (a < aEnd || b < bEnd) {
            if (b >= bEnd || (a < aEnd && arr[a] < arr[b])) {
                buf[i] = arr[a];
                ++a;
            } else {
                buf[i] = arr[b];
                ++b;
            }
            ++i;
        }

        System.arraycopy(buf, 0, arr, from, aLen + bLen);
    }

    public static void mergeSort(int[] arr, int[] buf, int from, int len) {
        if (len < 2) {
            return;
        }
        int mid = from + len / 2;
        mergeSort(arr, buf, from, len / 2);
        mergeSort(arr, buf, mid, (len + 1) / 2);
        merge(arr, buf, from, len / 2, mid, (len + 1) / 2);
    }
}