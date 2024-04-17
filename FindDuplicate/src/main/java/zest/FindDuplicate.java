package zest;

public class FindDuplicate {
    public static int findDuplicate(int[] nums) {

        // Pre-conditions
        assert nums != null : "Input array cannot be null";
        assert nums.length > 1 : "Array must contain at least two elements";
        assert isWithinRange(nums): "Array elements have gone out of specified range";

        int tortoise = nums[0];
        int hare = nums[0];
        int[] original = nums.clone();

        // Phase 1: Finding the intersection point of the two runners.
        do {
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
        } while (tortoise != hare);

        // Phase 2: Finding the "entrance" to the cycle.
        tortoise = nums[0];
        while (tortoise != hare) {
            tortoise = nums[tortoise];
            hare = nums[hare];
        }

        // Post-conditions
        assert isElementOf(nums, hare) : "The found duplicate is not a valid element in the array";
        //invariant
        assert isUnchanged(original, nums) : "The input array has been modified";

        return hare;
    }

    protected static boolean isElementOf(int[] arr, int val) {
        for (int num : arr) {
            if (num == val) {
                return true;
            }
        }
        return false;
    }

    protected static boolean isWithinRange(int[] arr) {
        for (int num : arr) {
            if (num < 1 || num > arr.length - 1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isUnchanged(int[] original, int[] current) {
        if (original.length != current.length) {
            return false;
        }
        for (int i = 0; i < original.length; i++) {
            if (original[i] != current[i]) {
                return false;
            }
        }
        return true;
    }
}

