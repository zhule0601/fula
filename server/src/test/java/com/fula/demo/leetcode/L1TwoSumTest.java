package com.fula.demo.leetcode;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class L1TwoSumTest {

    @Test
    public void testTwoSum() {
        int[] result = L1TwoSum.twoSum(new int[]{2, 7, 11, 15}, 9);
        assert result != null;
        assert result[0] == 0;
        assert result[1] == 1;

        int[] result2 = L1TwoSum.twoSum(new int[]{2, 7, 11, 15}, 13);
        assert result2 != null;
        assert result2[0] == 0;
        assert result2[1] == 2;
    }
}