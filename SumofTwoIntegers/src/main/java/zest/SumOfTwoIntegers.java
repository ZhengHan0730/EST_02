package zest;

public class SumOfTwoIntegers {
    public int getSum(int a, int b) {

        // Pre-conditions
        assert ((long) a >= Integer.MIN_VALUE && (long) a <= Integer.MAX_VALUE) : "Parameter 'a' is out of range.";
        assert ((long) b >= Integer.MIN_VALUE && (long) b <= Integer.MAX_VALUE) : "Parameter 'b' is out of range.";



        while (b != 0) {
            int carry = (a & b) << 1;  // Carry now contains common set bits of a and b
            // Check invariants
            assert carry >= Integer.MIN_VALUE && carry <= Integer.MAX_VALUE : "Invariant failed: 'carry' out of range.";
            assert a >= Integer.MIN_VALUE && a <= Integer.MAX_VALUE : "Invariant failed: 'a' out of range.";
            assert b >= Integer.MIN_VALUE && b <= Integer.MAX_VALUE : "Invariant failed: 'b' out of range.";

            a = a ^ b;  // Sum of bits of a and b where at least one of the bits is not set
            b = carry;  // Carry is shifted by one so that adding it to a gives the required sum
        }

        // Check postcondition
        assert a == getSumWithoutOperators(a, b) : "Postcondition failed: incorrect sum.";
        return a;
    }

    public int getSumWithoutOperators(int a, int b) {
        int c = a + b;
        return c;
    }
}
