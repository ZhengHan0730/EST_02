package zest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import net.jqwik.api.*;

import java.util.List;

class FindDuplicateTest {

    @Test
    void testNormalOperation() {
        assertEquals(2, FindDuplicate.findDuplicate(new int[]{1, 3, 4, 2, 2}));
    }

    @Test
    void testWithNullArray() {
        // Using Throwable as the catch type to encompass all Throwable types, including Error and Exception
        Throwable error = assertThrows(AssertionError.class, () -> {
            FindDuplicate.findDuplicate(null);
        });
        assertTrue(error.getMessage().contains("Input array cannot be null"), "Expected message about null input array not found.");
    }

    @Test
    void testArrayTooShort() {
        Throwable error = assertThrows(AssertionError.class, () -> {
            FindDuplicate.findDuplicate(new int[]{1});
        });
        assertTrue(error.getMessage().contains("Array must contain at least two elements"), "Expected message about array size not found.");
    }

    @Test
    void testArrayElementRange() {
        int[] input = {1, 2, 3, 4, 5, 2};
        FindDuplicate.findDuplicate(input);  // This should pass as elements are within range

        Throwable error = assertThrows(AssertionError.class, () -> {
            FindDuplicate.findDuplicate(new int[]{0, 1, 2, 3});
        });
        assertTrue(error.getMessage().contains("Array elements have gone out of specified range"), "Expected message about element range not found.");
    }

    @Test
    void testElementBelowRange() {
        // Test case with an element below the range should return false
        int[] arrayWithElementBelowRange = {0, 2, 3, 4, 5};
        assertFalse(FindDuplicate.isWithinRange(arrayWithElementBelowRange), "Contains an element below 1");
    }

    @Test
    void testElementAboveRange() {
        // Test case with an element above the range should return false
        int[] arrayWithElementAboveRange = {1, 2, 3, 4, 6};
        assertFalse(FindDuplicate.isWithinRange(arrayWithElementAboveRange), "Contains an element above arr.length - 1");
    }

    @Test
    void testIsElementOfWithNonExistingElement() {
        int[] array = {1, 2, 3, 4, 5};
        assertFalse(FindDuplicate.isElementOf(array, 6), "The element should not exist in the array.");
    }

    // Jqwik property-based tests
    @Provide
    Arbitrary<int[]> arraysWithDuplicates() {
        return Arbitraries.integers().between(2, 1000)  // Ensures at least 2 elements, thus n+1 can be achieved
                .map(size -> generateArrayWithDuplicates(size));
    }

    private int[] generateArrayWithDuplicates(int size) {
        // Ensure the range of elements chosen is [1, size-1]
        List<Integer> elements = Arbitraries.integers().between(1, size - 1)
                .list().ofSize(size).sample();

        // Add a duplicate and shuffle
        elements.add(elements.get(0));
        java.util.Collections.shuffle(elements);

        return elements.stream().mapToInt(i -> i).toArray();
    }

    @Property
    void alwaysFindsADuplicate(@ForAll("arraysWithDuplicates") int[] nums) {
        FindDuplicate finder = new FindDuplicate();
        int duplicate = finder.findDuplicate(nums);
        assertTrue(isDuplicate(nums, duplicate), "The output number must be a duplicate in the input array");
    }

    private boolean isDuplicate(int[] array, int number) {
        boolean found = false;
        for (int num : array) {
            if (num == number) {
                if (found) {
                    return true;
                }
                found = true;
            }
        }
        return false;
    }
}