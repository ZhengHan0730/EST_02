package zest;
import net.jqwik.api.constraints.IntRange;
import org.junit.jupiter.api.Test;

import static java.lang.Character.MAX_VALUE;
import static org.junit.jupiter.api.Assertions.*;
import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;


class SumOfTwoIntegersTest {
    private final SumOfTwoIntegers calculator = new SumOfTwoIntegers();

    /**
     * Validate normal operation when pre-conditions are met.
     */
    @Test
    public void testNormalOperation() {
        // Valid inputs within 32-bit integer range
        assertEquals(5, calculator.getSum(2, 3));
        assertEquals(-1, calculator.getSum(2, -3));
        assertEquals(0, calculator.getSum(-2, 2));
    }

    /**
     * Confirm that appropriate exceptions or errors are thrown when pre-conditions are violated.
     */
    @Test
    public void testBoundaryConditions() {
        int result = calculator.getSum(Integer.MAX_VALUE - 1, 1);
        assertEquals(Integer.MAX_VALUE, result);

        result = calculator.getSum(Integer.MIN_VALUE + 1, -1);
        assertEquals(Integer.MIN_VALUE, result);  
    }

    /**
     * Ensure post-conditions hold after the execution of functions under various conditions.
     */
    @Test
    public void testPostConditionCheck() {
        // This function should return a value equal to sum of a and b without using + or -
        assertEquals(7, calculator.getSum(3, 4));  // 3 + 4 = 7
        assertEquals(-5, calculator.getSum(-2, -3));  // -2 + -3 = -5
        assertEquals(2, calculator.getSum(-1, 3));  // -1 + 3 = 2
    }

    /**
     * Verify that invariants are maintained throughout the software module's lifecycle.
     */
    @Test
    public void testInvariantCarry() {
        // Test the invariants for the carry value
        int a = Integer.MAX_VALUE - 1;
        int b = 1;
        int result = calculator.getSum(a, b);

        // Carry should be within Integer range
        int carry = (a & b) << 1;
        assertTrue(carry >= Integer.MIN_VALUE && carry <= Integer.MAX_VALUE,
                "Invariant failed: 'carry' out of range.");

        assertEquals(Integer.MAX_VALUE, result,
                "Expected Integer.MAX_VALUE, but got " + result);
    }

    @Test
    public void testInvariantStateChange() {
        // Test invariants after state change
        int a = 5;
        int b = 10;
        int result = calculator.getSum(a, b);

        assertTrue(result >= Integer.MIN_VALUE && result <= Integer.MAX_VALUE,
                "Invariant failed: 'result' out of range.");

        int expected = a + b;
        assertEquals(expected, result,
                "Expected " + expected + ", but got " + result);
    }

    @Test
    public void testInvariantBoundary() {
        // Test boundary cases
        int a = Integer.MAX_VALUE;
        int b = -1;
        int result = calculator.getSum(a, b);

        assertTrue(result >= Integer.MIN_VALUE && result <= Integer.MAX_VALUE,
                "Invariant failed: 'result' out of range.");

        int expected = a + b;
        assertEquals(expected, result,
                "Expected " + expected + ", but got " + result);
    }


    // Commutativity Property: the sum of two numbers should be the same regardless of the order.
    @Property
    void commutativity(@ForAll @IntRange(min = Integer.MIN_VALUE, max = Integer.MAX_VALUE) int a,
                       @ForAll @IntRange(min = Integer.MIN_VALUE, max = Integer.MAX_VALUE) int b) {
        assertEquals(calculator.getSum(a, b), calculator.getSum(b, a));
    }

    // Associativity Property: changing the grouping of numbers being added should not change the result.
    @Property
    void associativity(@ForAll @IntRange(min = Integer.MIN_VALUE, max = Integer.MAX_VALUE) int a,
                       @ForAll @IntRange(min = Integer.MIN_VALUE, max = Integer.MAX_VALUE) int b,
                       @ForAll @IntRange(min = Integer.MIN_VALUE, max = Integer.MAX_VALUE) int c) {
        int expected = calculator.getSum(a, calculator.getSum(b, c));
        int actual = calculator.getSum(calculator.getSum(a, b), c);

        assertEquals(expected, actual);
    }

    // Identity Element Property: adding zero to a number should not change the number.
    @Property
    void identityElement(@ForAll @IntRange(min = Integer.MIN_VALUE, max = Integer.MAX_VALUE) int a) {
        assertEquals(calculator.getSum(a, 0), a);
    }

    // Negation Property: adding a number to its negation should result in zero.
    @Property
    void negation(@ForAll @IntRange(min = Integer.MIN_VALUE, max = Integer.MAX_VALUE) int a) {
        assertEquals(calculator.getSum(a, -a),0);
    }

    // Consistency with Conventional Addition Property: the result should match with standard arithmetic if within 32-bit integer bounds.
    @Property
    void consistencyWithConventionalAddition(@ForAll @IntRange(min = Integer.MIN_VALUE, max = Integer.MAX_VALUE) int a,
                                             @ForAll @IntRange(min = Integer.MIN_VALUE, max = Integer.MAX_VALUE) int b) {

        long longSum = (long) a + (long) b;
        if (longSum >= Integer.MIN_VALUE && longSum <= Integer.MAX_VALUE) {
            assertEquals(calculator.getSum(a, b),calculator.getSumWithoutOperators(a,b));
        }
    }


}
