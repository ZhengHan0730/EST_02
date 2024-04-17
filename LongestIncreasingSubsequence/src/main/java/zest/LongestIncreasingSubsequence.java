package zest;

public class LongestIncreasingSubsequence {
    public int lengthOfLIS(int[] nums) {
        // Pre-condition: input must not be null
        assert nums != null : "Input array cannot be null";

        int[] original = nums.clone();

        if (nums.length == 0) {
            return 0;
        }

        int[] dp = new int[nums.length];
        int maxLength = 0;

        for (int i = 0; i < nums.length; i++) {
            dp[i] = 1; // Each element is an increasing subsequence of length 1

            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }

            maxLength = Math.max(maxLength, dp[i]);
        }

        // Post-condition: Ensure the result is non-negative and does not exceed array length
        assert maxLength >= 0 && maxLength <= nums.length : "Invalid subsequence length";
        // Invariant: Array 'nums' must not be altered
        assert isUnchanged(original, nums) : "The input array has been modified";

        return maxLength;
    }

    private static boolean isUnchanged(int[] original, int[] current) {
        if (original.length != current.length) {
            return false;  // Different lengths mean the array was changed
        }
        for (int i = 0; i < original.length; i++) {
            if (original[i] != current[i]) {
                return false;  // Any difference in elements means the array was changed
            }
        }
        return true;  // Arrays are identical
    }
}