package zest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;

class uniquePathsTest {
    private final uniquePaths uniquePaths = new uniquePaths();

    @Test
    public void testNormalOperation() {
        long result = uniquePaths.uniquePaths(3, 3);
        assertEquals(6, result);

        result = uniquePaths.uniquePaths(2, 2);
        assertEquals(2, result);

        result = uniquePaths.uniquePaths(3, 7);
        assertEquals(28, result);
    }

    @Test
    public void testPreConditionViolationM() {
        assertThrows(IllegalArgumentException.class, () -> {
            uniquePaths.uniquePaths(0, 5);
        });
    }

    @Test
    public void testPreConditionViolationN() {
        assertThrows(IllegalArgumentException.class, () -> {
            uniquePaths.uniquePaths(10, 101);
        });
    }

    @Test
    public void testPostConditions() {
        long result = uniquePaths.uniquePaths(1, 1);
        assertEquals(1, result);

        result = uniquePaths.uniquePaths(100, 100);
        assertTrue(result >=0);
    }

    @Test
    public void testInvariants() {
        long result = uniquePaths.uniquePaths(3, 3);
        assertTrue(result >= 0);

        result = uniquePaths.uniquePaths(100, 100);
        assertTrue(result >= 0);

        result = uniquePaths.uniquePaths(7, 3);
        assertEquals(28, result);
    }

    @Test
    public void testBoundaryConditions() {
        assertEquals(1, uniquePaths.uniquePaths(1, 1));

        assertTrue(uniquePaths.uniquePaths(100, 100) >=0);

        assertEquals(1, uniquePaths.uniquePaths(1, 100));

        assertEquals(1, uniquePaths.uniquePaths(100, 1));
    }

    @Property
    public void validGridSize(@ForAll @IntRange(min = 1, max = 100) int m,
                              @ForAll @IntRange(min = 1, max = 100) int n) {

        assertDoesNotThrow(() -> {
            long result = uniquePaths.uniquePaths(m, n);
            assertTrue(result >=0);
        });
    }

    @Property
    public void invalidGridSize(@ForAll @IntRange(min = -100, max = 0) int invalid) {

        assertThrows(IllegalArgumentException.class, () -> {
            uniquePaths.uniquePaths(invalid, 1); // 非法的行数
        });

        assertThrows(IllegalArgumentException.class, () -> {
            uniquePaths.uniquePaths(1, invalid); // 非法的列数
        });
    }

    @Property
    public void overflowCheck(@ForAll @IntRange(min = 1, max = 100) int m,
                              @ForAll @IntRange(min = 1, max = 100) int n) {
        assertDoesNotThrow(() -> {
            uniquePaths.uniquePaths(m, n);
        });
    }






}