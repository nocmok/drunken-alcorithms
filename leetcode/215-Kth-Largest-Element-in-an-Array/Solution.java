// Given an integer array nums and an integer k, return the kth largest element in the array.
// 
// Note that it is the kth largest element in the sorted order, not the kth distinct element.

class Solution {
    public int findKthLargest(int[] nums, int k) {

        // we are searching for algorithm, with time complexity O(n)

        // let consider random element A in an array
        // let move all elements < A to the left part of array, and rest elements to the
        // right
        // this can be done in O(n)

        // if considered element is at index K then we return it

        // if considered element is at index < K then A < answer (and we can drop left
        // part of array safely)
        // That mean we should find Kth element in left part of array

        // if considered element is at index > K then A > answer (and we can drop right
        // part of array safely)
        // That mean we should find element with index K - sizeof(left part of array) -
        // 1 in right part of array

        // So on each level of recursion we solve 1 subtask with size evenly distributed
        // from 1 to N - 1 (where N is size of source array)
        // in worst case we decresase source array each time only by one and perform on
        // each step O(n) operations
        // so in worst case complexity is O(n^2)

        // but in average we decrease size of source array by 2, and perform O(n)
        // operations on each step
        // so average complexity is O(n) (see Master theorem)

        return findKthSmallest(nums, 0, nums.length, nums.length - 1 - k + 1);
    }

    /**
     * array after reordering : [... less ... | ... equal ... | ... greater ... ]
     * 
     * @return [0] - number of elements < value, [1] - number of elements == value
     */
    public int[] reorder(int[] nums, int start, int len, int value) {
        /** how much elements < value was visited */
        int less = 0;
        /** how much elements == value was visited */
        int equ = 0;

        for (int i = start; i < start + len; ++i) {
            if (nums[i] == value) {
                // swaps current element with first element in array > value
                int tmp = nums[start + less + equ];
                nums[start + less + equ] = nums[i];
                nums[i] = tmp;

                ++equ;
            } else if (nums[i] < value) {
                // swaps current element with first element in array > value
                int tmp = nums[start + less + equ];
                nums[start + less + equ] = nums[i];
                nums[i] = tmp;

                tmp = nums[start + less];
                nums[start + less] = nums[start + less + equ];
                nums[start + less + equ] = tmp;

                ++less;
            }
        }
        return new int[] { less, equ };
    }

    public int findKthSmallest(int[] nums, int start, int len, int k) {
        if (len < 2) {
            return nums[start];
        }
        int mid = start + len / 2;
        int[] lessEqu = reorder(nums, start, len, nums[mid]);
        // if mid element == answer
        if (k >= lessEqu[0] && k < lessEqu[0] + lessEqu[1]) {
            return nums[start + lessEqu[0]];
        }
        // if mid element > answer
        if (k < lessEqu[0]) {
            return findKthSmallest(nums, start, lessEqu[0], k);
        }
        // if mid element < answer
        return findKthSmallest(nums, start + lessEqu[0] + lessEqu[1], len - lessEqu[0] - lessEqu[1], k - lessEqu[0] - lessEqu[1]);
    }
}