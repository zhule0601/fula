package com.fula.demo.leetcode;

import java.util.HashMap;
import java.util.Map;

public class L1TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> sumMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (sumMap.containsKey(target - nums[i])) {
                return new int[]{sumMap.get(target - nums[i]),i};
            } else {
                sumMap.put(nums[i], i);
            }
        }
        return null;
    }
}
