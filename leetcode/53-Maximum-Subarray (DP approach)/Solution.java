// Given an integer array nums, find the contiguous subarray (containing at least one number) 
// which has the largest sum and return its sum.

class Solution {
    public int maxSubArray(int[] nums) {
        // dp[i] - max sum of contiguous subarray, which ends at index i
        // dp[i] = max(dp[i - 1] + arr[i], arr[i])

        int maxSum = nums[0];
        // dp
        int currMaxSum = 0;

        for(int i = 0; i < nums.length; ++i){
            currMaxSum = Integer.max(currMaxSum + nums[i], nums[i]);
            maxSum = Integer.max(maxSum, currMaxSum);
        }

        return maxSum;
    }
}