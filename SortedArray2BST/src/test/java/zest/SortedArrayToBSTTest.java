package zest;

import net.jqwik.api.Assume;
import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SortedArrayToBSTTest {

    @Test
    void testEmptyArray() {
        int[] emptyArray = {};
        SortedArrayToBST solution = new SortedArrayToBST();
        TreeNode result = solution.sortedArrayToBST(emptyArray);
        assertNull(result);
    }

    @Test
    void testSingleElementArray() {
        int[] singleElementArray = {1};
        SortedArrayToBST solution = new SortedArrayToBST();
        TreeNode result = solution.sortedArrayToBST(singleElementArray);
        assertNotNull(result);
        assertEquals(1, result.val);
        assertNull(result.left);
        assertNull(result.right);
    }

    @Test
    void testMultipleElementsArray() {
        int[] multipleElementsArray = {1, 2, 3, 4, 5, 6, 7};
        SortedArrayToBST solution = new SortedArrayToBST();
        TreeNode result = solution.sortedArrayToBST(multipleElementsArray);
        assertNotNull(result);
        // Validate the structure of the BST (e.g., manually check the tree)
    }

    @Test
    void testNegativeElementsArray() {
        int[] negativeElementsArray = {-10, -3, 0, 5, 9};
        SortedArrayToBST solution = new SortedArrayToBST();
        TreeNode result = solution.sortedArrayToBST(negativeElementsArray);
        assertNotNull(result);
        // Validate the structure of the BST
    }

    @Test
    void testDuplicateElementsArray() {
        int[] duplicateElementsArray = {1, 1, 2, 2, 3, 3};
        SortedArrayToBST solution = new SortedArrayToBST();
        TreeNode result = solution.sortedArrayToBST(duplicateElementsArray);
        assertNotNull(result);
        // Validate the structure of the BST
    }

    @Test
    void testLargeArray() {
        // An array containing numbers from 1 to 10,000 (inclusive)
        int[] largeArray = new int[10000];
        for (int i = 0; i < 10000; i++) {
            largeArray[i] = i + 1;
        }
        SortedArrayToBST solution = new SortedArrayToBST();
        TreeNode result = solution.sortedArrayToBST(largeArray);
        assertNotNull(result);
        // Validate the structure of the BST
    }

    @Property
    void testSortedArrayToBST(@ForAll @IntRange(min = -20, max = 20) int[] nums) {
        // Assume that the input array is sorted (property: Between -20 and +20)
        Assume.that(isSorted(nums));

        SortedArrayToBST solution = new SortedArrayToBST();
        TreeNode root = solution.sortedArrayToBST(nums);

        // Validate the BST properties
        assert isValidBST(root);
    }

    // Helper method to check if an array is sorted
    private boolean isSorted(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < nums[i - 1]) {
                return false;
            }
        }
        return true;
    }

    // Helper method to validate BST properties
    private boolean isValidBST(TreeNode root) {
        return isValidBSTHelper(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBSTHelper(TreeNode node, long minVal, long maxVal) {
        if (node == null) {
            return true;
        }
        if (node.val <= minVal || node.val >= maxVal) {
            return false;
        }
        return isValidBSTHelper(node.left, minVal, node.val) && isValidBSTHelper(node.right, node.val, maxVal);
    }
}

