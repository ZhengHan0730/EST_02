# Problem: Merge k Sorted Lists (Juncai Bao)
## Code Coverage
95% lines are covered, 1 lines are not covered.
![Coverage Result.png](Assets%2FCoverage%20Result.png)
In other words, it cannot be covered by normal test suites, while other lines are fully covered.

## Designing Contracts
1. Pre-conditions:

   1）The input to the function, lists, must not be null. If it is null, an IllegalArgumentException is thrown.

   2）The size of lists must be within the range [0, 10^4]. If it exceeds this range, an IllegalArgumentException is thrown.

   3）Each ListNode in lists must have a val within the range [-10^4, 10^4]. If a ListNode has a val outside this range, an IllegalArgumentException is thrown

2. Post-conditions:

   1）If lists is empty, the function returns empty.

   2）If lists is not empty, the function returns a ListNode that is the result of merging all the ListNodes in lists in ascending order of their vals.
   
   3）The returned ListNode must be sorted in ascending order. If it is not sorted, an IllegalStateException is thrown.

3. Invariants:

   The val of each ListNode in lists does not change throughout the execution of the function.

   The relative order of ListNodes with equal vals is preserved in the merged list

## Updated Java Code with Contracts

## Key Properties
properties that should hold true:

1.Deterministic Output: 
For a given input, the function always produces the same output. This means if we input the same array of ListNodes, we will always get the same merged list.

2.Order Preservation: 
The function preserves the relative order of ListNodes with equal vals from the input lists in the merged list. 

3.Sort Preservation: 
The function ensures that the output list is sorted in ascending order. If the input lists are sorted, the output list will also be sorted.

4.Element Preservation: 
Every element that appears in the input lists will appear in the output list and vice versa. This means the function does not add or remove elements; it only rearranges them.

5.Size Preservation: 
The size of the output list is equal to the total size of all input lists. This means no nodes are lost or created during the merging process.


Result of Property-based Test:

The mergeKLists function has passed all property-based tests. This means that for a wide range of inputs, the function correctly merges the lists into a single sorted list, preserving the order of equal elements and maintaining the size of the input. The function also correctly handles edge cases such as null and empty inputs.

100% tries (1000 times) of jqwik tests passed, therefore the test is considered successful

![Test Result.png](Assets%2FTest%20Result.png)