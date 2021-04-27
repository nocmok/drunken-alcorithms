// Given an array nums of size n, return the majority element.
// 
// The majority element is the element that appears more than ⌊n / 2⌋ times. 
// You may assume that the majority element always exists in the array.

class Solution {
    public int majorityElement(int[] nums) {

        // if we divide nums in two halfs, at least in one half major element still will be major
        // 
        // so, if we compute major element in both halfs, 
        // we just need to check one of them in order to find out which is major in entire array.
        // this check can be performed in O(n) time, so total complexity is O(n * log(n))

        return majorityElement(nums, 0, nums.length);
    }

    public boolean isMajor(int[] nums, int start, int len, int value){
        int count = 0;
        for(int i = start; i < start + len; ++i){
            if(nums[i] == value){
                ++count;
                if(count * 2 > len){
                    return true;
                }
                if(start + len - i - 1 + count < len / 2){
                    return false;
                }
            }
        }
        return false;
    }

    public int majorityElement(int[] nums, int start, int len){
        if(len < 3){
            return nums[start];
        }
        int candidate = majorityElement(nums, start, len / 2);
        if(isMajor(nums, start, len, candidate)){
            return candidate;
        }else{
            return majorityElement(nums, start + len / 2, (len + 1) / 2);
        }
    }
}