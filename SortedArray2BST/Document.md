# Problem: Convert Sorted Array to Binary Search Tree(Juncai Bao)
## Code Coverage


## Designing Contracts
1. Pre-conditions:

   1）The input array nums must be sorted in ascending order.

   2）The input array nums should not be null.

   3）The input array nums should not be empty

2. Post-conditions:

   1）for every node, its left subtree contains only nodes with values less than the node’s value, and its right subtree contains only nodes with values greater than the node’s value.

3. Invariants:

   1）The range [left, right] should always represent a valid subarray of nums.

   2）The middle element nums[mid] (where mid = left + (right - left) / 2) is the value of the current node being created.

   3）The left subtree of the current node is constructed from the range [left, mid - 1].

   4）The right subtree of the current node is constructed from the range [mid + 1, right].   

## Updated Java Code with Contracts

## Key Properties
properties that should hold true:

The input to the sortedArrayToBST method is a sorted array of integers. The array represents the in-order traversal of the resulting BST.

The algorithm uses a recursive approach to build the BST. It identifies the middle element of the sorted array as the root. Recursively, it constructs the left subtree from the elements before the middle element and the right subtree from the elements after the middle element.

The levelOrderTraversal method performs a breadth-first traversal of the BST. It returns a list of integers representing the level-order sequence of the tree (root, followed by levels from left to right).

Result of Property-based Test:

Tests failed:2 passed:5 of 7 tests, 695 tries, 89 checks 
testDuplicateElementsArray() and testSortedArrayToBST() failed.


**_Note: Unfamiliar with JAVA, using Copilot as assistant to help me understand the algorithm and the grammar of java. 
This single assignment is not qualified and has to be failed, please only fail on me (23-744-246)._**
