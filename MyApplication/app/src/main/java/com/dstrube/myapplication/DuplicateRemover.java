package com.dstrube.myapplication;

/*
# Remove Duplicates from Sorted Array
# Given a sorted array nums, remove the duplicates in-place such that each element appear only once and return the new length.
# Do not allocate extra space for another array, you must do this by modifying the input array in-place
# Example:
# Given nums = [0,0,1,1,1,2,2,3,3,4],
# Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively.
# It doesn't matter what values are set beyond the returned length.
*/
public class DuplicateRemover {
    public static void main(String[] args){
        //try one set of code
        int[] nums1 = {0,0,1,1,1,2,2,3,3,4};
        int len1 = removeDuplicates1(nums1);
        System.out.println("Length found = " + len1);
        int[] newNums1 = new int[len1];
        for(int i = 0; i < len1; i++){
            newNums1[i] = nums1[i];
            System.out.print(nums1[i] + " ");
        }
        System.out.println();

        //reset, try other code
        int[] nums2 = {0,0,1,1,1,2,2,3,3,4};
        int len2 = removeDuplicates2(nums2);
        System.out.println("Length found = " + len2);
        int[] newNums2 = new int[len2];
        for(int i = 0; i < len2; i++){
            newNums2[i] = nums2[i];
            System.out.print(nums2[i] + " ");
        }
        System.out.println();
    }

    public static int removeDuplicates1(int[] nums) {
        if (nums == null ) return 0;
        if (nums.length < 2) return nums.length;

        //https://www.programcreek.com/2013/01/leetcode-remove-duplicates-from-sorted-array-java/
        int j = 0;
        int i = 1;

        while (i < nums.length) {
            if (nums[i] != nums[j]) {
                j++;
                nums[j] = nums[i];
            }
            i++;
        }
        return j + 1;
    }

    public static int removeDuplicates2(int[] nums) {
        if (nums == null ) return 0;
        if (nums.length < 2) return nums.length;

        //https://www.geeksforgeeks.org/remove-duplicates-sorted-array/
        // To store index of next unique element
        int j = 0;

        // Doing same as done in Method 1
        // Just maintaining another updated index i.e. j
        for (int i = 0; i < nums.length-1; i++) {
            if (nums[i] != nums[i+1]) {
                nums[j++] = nums[i];
            }
        }
        nums[j++] = nums[nums.length-1];

        return j;
    }
}
