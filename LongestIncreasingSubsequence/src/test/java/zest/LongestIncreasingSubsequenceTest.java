package zest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import net.jqwik.api.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

class LongestIncreasingSubsequenceTest {

    @Test
    void testNormalOperation() {
        LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();
        assertEquals(4, lis.lengthOfLIS(new int[]{10, 9, 2, 5, 3, 7, 101, 18}));
        assertEquals(4, lis.lengthOfLIS(new int[]{0, 1, 0, 3, 2, 3}));
        assertEquals(1, lis.lengthOfLIS(new int[]{7, 7, 7, 7, 7, 7, 7}));
    }

    @Test
    void testEmptyArray() {
        LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();
        assertEquals(0, lis.lengthOfLIS(new int[]{}), "Empty array should return 0");
    }

    @Test
    void testNullInput() {
        LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();
        // Using Throwable to ensure that any subclass of Throwable, including Error and Exception, can be caught
        Throwable error = assertThrows(Throwable.class, () -> lis.lengthOfLIS(null));
        assertTrue(error instanceof AssertionError, "The thrown error should be an AssertionError.");
        assertTrue(error.getMessage().contains("Input array cannot be null"), "Expected message about null input array not found.");
    }

    @Test
    void testArrayUnchanged() {
        int[] original = {10, 22, 9, 33, 21, 50, 41, 60, 80};
        int[] testArray = original.clone();
        LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();
        lis.lengthOfLIS(testArray);
        assertArrayEquals(original, testArray, "The input array should not be modified");
    }
    @Provide
    Arbitrary<int[]> arrays() {
        return Arbitraries.integers().array(Integer[].class)
                .ofMinSize(0).ofMaxSize(100)
                .map(Arrays::stream)
                .map(stream -> stream.mapToInt(i -> i).toArray());
    }

    @Property
    void testLISLengthIsCorrect(@ForAll int[] array) {
        LongestIncreasingSubsequence lis = new LongestIncreasingSubsequence();
        int lengthOfLIS = lis.lengthOfLIS(array);
        assertTrue(lengthOfLIS >= 0, "Length of LIS should be non-negative");
        assertTrue(lengthOfLIS <= array.length, "Length of LIS should not exceed the array length");
        assertTrue(isValidLIS(array, lengthOfLIS), "The computed LIS length should correspond to a valid subsequence");
    }

    private boolean isValidLIS(int[] nums, int expectedLength) {
        List<Integer> lis = new ArrayList<>();
        for (int num : nums) {
            if (lis.isEmpty() || num > lis.get(lis.size() - 1)) {
                lis.add(num);
            } else {
                int pos = Collections.binarySearch(lis, num);
                if (pos < 0) pos = -(pos + 1);
                lis.set(pos, num);
            }
        }
        return lis.size() == expectedLength;
    }

}