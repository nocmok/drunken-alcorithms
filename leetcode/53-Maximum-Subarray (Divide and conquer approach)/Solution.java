// Given an integer array nums, find the contiguous subarray (containing at least one number) 
// which has the largest sum and return its sum.

class Solution {
    public int maxSubArray(int[] nums) {
        return maxSubArray(nums, 0, nums.length);
    }

    public int maxSubArray(int[] nums, int start, int len){
        // [... ,[max subarray], ... ]
        // [.. | . ,[max subarray], ... ] then result lays out in right half of array
        // [... ,[max subarray], . | .. ] then result lays out in left half of array
        
        // [... ,[max su | barray], ... ] 
        // then we should calculate max sum of array which ends with midpoint and sum of array which starts at midpoint (O(n)) 
        
        // which leads to O(n * log(n)) complexity 

        if(len < 2){
            return nums[start];
        }

        int max = Integer.max(maxSubArray(nums, start, len / 2), maxSubArray(nums, start + len / 2, (len + 1) / 2));
        
        int midpoint = start + len / 2;
        int cumSum;
        int lMax = Integer.MIN_VALUE;
        int rMax = Integer.MIN_VALUE;

        cumSum = 0;

        for(int i = midpoint - 1; i >= start; --i){
            cumSum += nums[i];
            lMax = Integer.max(lMax, cumSum);
        }

        cumSum = 0;

        for(int i = midpoint; i < midpoint + (len + 1) / 2; ++i){
            cumSum += nums[i];
            rMax = Integer.max(rMax, cumSum);            
        }

        return Integer.max(max, rMax + lMax);
    }
}