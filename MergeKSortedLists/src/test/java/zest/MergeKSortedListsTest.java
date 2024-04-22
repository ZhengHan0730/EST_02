package zest;

import net.jqwik.api.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class MergeKSortedListsTest {
    //T1 input with null
    @Test
    void testNullInput() {
        MergeKSortedLists mergeKSortedLists = new MergeKSortedLists();
        assertThrows(IllegalArgumentException.class, () -> mergeKSortedLists.mergeKLists(null));
    }
    //T2 input with empty list
    @Test
    void testEmptyInput() {
        MergeKSortedLists mergeKSortedLists = new MergeKSortedLists();
        assertNull(mergeKSortedLists.mergeKLists(new ListNode[0]));
    }
    //T3 input with one list
    @Test
    void testSingleNodeLists() {
        MergeKSortedLists mergeKSortedLists = new MergeKSortedLists();
        ListNode[] lists = new ListNode[] {new ListNode(1), new ListNode(2), new ListNode(3)};
        ListNode result = mergeKSortedLists.mergeKLists(lists);
        assertEquals(1, result.val);
        assertEquals(2, result.next.val);
        assertEquals(3, result.next.next.val);
    }
    //T4 input with multiple lists
    @Test
    void testMultipleNodeLists() {
        MergeKSortedLists mergeKSortedLists = new MergeKSortedLists();
        ListNode[] lists = new ListNode[] {
                new ListNode(1, new ListNode(4, new ListNode(5))),
                new ListNode(1, new ListNode(3, new ListNode(4))),
                new ListNode(2, new ListNode(6))
        };
        ListNode result = mergeKSortedLists.mergeKLists(lists);
        int[] expectedValues = new int[] {1, 1, 2, 3, 4, 4, 5, 6};
        for (int expectedValue : expectedValues) {
            assertNotNull(result);
            assertEquals(expectedValue, result.val);
            result = result.next;
        }
        assertNull(result);
    }
    //T5 result out of range
    @Test
    void testNodeValueOutOfRange() {
        MergeKSortedLists mergeKSortedLists = new MergeKSortedLists();
        ListNode[] lists = new ListNode[] {new ListNode(10001)};
        assertThrows(IllegalArgumentException.class, () -> mergeKSortedLists.mergeKLists(lists));
    }
    //T6 result not fit to the requirement
    @Test
    void testResultNotSorted() {
        MergeKSortedLists mergeKSortedLists = new MergeKSortedLists();
        ListNode[] lists = new ListNode[] {new ListNode(2, new ListNode(1))};
        assertThrows(IllegalStateException.class, () -> mergeKSortedLists.mergeKLists(lists));
    }

    //Property Test
    @Property
    boolean testMergeKListsProperty(@ForAll("generateLists") ListNode[] lists) {
        MergeKSortedLists mergeKSortedLists = new MergeKSortedLists();
        ListNode result = mergeKSortedLists.mergeKLists(lists);
        return isSorted(result);
    }
    // Helper function to check if a list is sorted
    private boolean isSorted(ListNode node) {
        while (node != null && node.next != null) {
            if (node.val > node.next.val) {
                return false;
            }
            node = node.next;
        }
        return true;
    }
    // Generator for ListNode[]
    @Provide
    Arbitrary<ListNode[]> generateLists() {
        Arbitrary<Integer> sizes = Arbitraries.integers().between(0, 10000);
        Arbitrary<Integer> values = Arbitraries.integers().between(-10000, 10000);
        return sizes.flatMap(size -> values.list().ofSize(size).map(this::createList));
    }
    // Helper function to create a ListNode from a list of integers
    private ListNode[] createList(List<Integer> values) {
        ListNode[] lists = new ListNode[values.size()];
        for (int i = 0; i < values.size(); i++) {
            lists[i] = new ListNode(values.get(i));
        }
        return lists;
    }

}
