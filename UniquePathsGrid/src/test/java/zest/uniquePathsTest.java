package zest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import net.jqwik.api.*;

class uniquePathsTest {
    private final uniquePaths uniquePaths = new uniquePaths();

    // Test for normal operation when preconditions are met
    @Test
    public void testNormalOperation() {

        int result = uniquePaths.uniquePaths(3, 3);
        assertEquals(6, result);

        result = uniquePaths.uniquePaths(2, 2);
        assertEquals(2, result);

        result = uniquePaths.uniquePaths(3, 7);
        assertEquals(28, result);
    }

    // Testing exception handling when preconditions are violated
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

    // Ensure that postconditions are maintained after function execution
    @Test
    public void testPostConditions() {
        int result = uniquePaths.uniquePaths(1, 1);
        assertEquals(1, result);

        result = uniquePaths.uniquePaths(100, 100);
        assertTrue(result >=0);
    }

    // Ensure that invariants remain after a state change
    @Test
    public void testInvariants() {

        int result = uniquePaths.uniquePaths(3, 3);
        assertTrue(result >= 0);

        result = uniquePaths.uniquePaths(100, 100);
        assertTrue(result >= 0);
        
        result = uniquePaths.uniquePaths(7, 3);
        assertEquals(28, result);
    }

 
}