package zest;

import org.junit.jupiter.api.Test;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

class ClimbingStairsTest {

    private static final String RANDOM_SEED = "114514";

    private final ClimbingStairs instance = new ClimbingStairs();

    @Property(seed = RANDOM_SEED, tries = 20)
    void testPreConditions(@ForAll @IntRange(min = Integer.MIN_VALUE, max = 0) int numStairs) {
        assertThrows(AssertionError.class, () -> {
            instance.climbStairs(numStairs);
        });
    }

    @Property(seed = RANDOM_SEED, tries = 20)
    void testPostConditions(@ForAll @IntRange(min = 1, max = (int) 1e6) int numStairs) {
        assertTrue(instance.climbStairs(numStairs).compareTo(new BigDecimal(0)) >= 0);
    }

    @Test
    void exampleBasedTests() {
        assertEquals(0, new BigDecimal(1).compareTo(instance.climbStairs(1)));
        assertEquals(0, new BigDecimal(2).compareTo(instance.climbStairs(2)));
    }

    @Property(seed = RANDOM_SEED, tries = 20)
    void equalsToOneBeforePlusTwoBefore(@ForAll @IntRange(min = 3, max = (int) 1e6) int numStairs) {
        assertEquals(
                instance.climbStairs(numStairs - 1).add(instance.climbStairs(numStairs - 2)),
                instance.climbStairs(numStairs));
    }
}
