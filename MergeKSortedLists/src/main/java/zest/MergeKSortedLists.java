package zest;

import java.util.PriorityQueue;

class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }
}

public class MergeKSortedLists {
    public ListNode mergeKLists(ListNode[] lists) {

        // Pre-condition: Check if the input is null
//        assert lists != null : "Input cannot be null";
        if (lists == null) {
            throw new IllegalArgumentException("Input cannot be null");
        }

        // Pre-condition: Check if the size of lists is within the range [0, 10^4]
//        assert lists.length > 10000 : "The number of linked lists cannot exceed 10^4";
        if (lists.length > 10000) {
            throw new IllegalArgumentException("The number of linked lists cannot exceed 10^4");
        }

        if (lists.length == 0) return null;

        PriorityQueue<ListNode> queue = new PriorityQueue<>(lists.length, (a, b) -> a.val - b.val);

        ListNode dummy = new ListNode(0);
        ListNode tail = dummy;

        for (ListNode node : lists) {
            if (node != null) {
                // Check if the value of the node is within the range [-10^4, 10^4]
                if (node.val < -10000 || node.val > 10000) {
                    throw new IllegalArgumentException("Node value must be in the range [-10^4, 10^4]");
                }
                queue.add(node);
            }
        }

        while (!queue.isEmpty()) {
            tail.next = queue.poll();
            tail = tail.next;

            if (tail.next != null) {
                queue.add(tail.next);
            }
        }

        // Post-condition: Check if the result is sorted
        ListNode node = dummy.next;
        while (node != null && node.next != null) {
            if (node.val > node.next.val) {
                throw new IllegalStateException("The result linked list is not sorted");
            }
            node = node.next;
        }

        return dummy.next;
    }
}



