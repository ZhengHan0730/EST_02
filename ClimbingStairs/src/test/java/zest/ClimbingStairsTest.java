package zest;

import org.junit.jupiter.api.Test;

import net.jqwik.api.ForAll;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ClimbingStairsTest {

    private ClimbingStairs instance = new ClimbingStairs();

    @Test
    void foo() {
        assertEquals(instance.climbStairs(1), 1);
        assertEquals(instance.climbStairs(2), 2);
    }

    @Property(seed = "114514")
    void equalsToOneBeforePlusTwoBefore(@ForAll @IntRange(min = 3) int numStairs) {
        assertEquals(
                instance.climbStairs(numStairs),
                instance.climbStairs(numStairs - 1) + instance.climbStairs(numStairs - 2));
    }
}
