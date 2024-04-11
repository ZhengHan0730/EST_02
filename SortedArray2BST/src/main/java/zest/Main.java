package zest;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SortedArrayToBST converter = new SortedArrayToBST();

        // Input array
        int[] nums = {-10, -3, 0, 5, 9};

        // Constructing the BST from the sorted array
        TreeNode root = converter.sortedArrayToBST(nums);

        // Performing a level-order traversal and printing the result
        List<Integer> traversalResult = converter.levelOrderTraversal(root);
        System.out.println("Level-order traversal of the BST: " + traversalResult);
    }
}
