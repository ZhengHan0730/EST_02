package zest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import net.jqwik.api.*;

class uniquePathsTest {
    private final uniquePaths uniquePaths = new uniquePaths();

    // 测试在前置条件满足时的正常操作
    @Test
    public void testNormalOperation() {
        // 3x3 网格的唯一路径应该是 6
        int result = uniquePaths.uniquePaths(3, 3);
        assertEquals(6, result);

        // 2x2 网格的唯一路径应该是 2
        result = uniquePaths.uniquePaths(2, 2);
        assertEquals(2, result);

        // 3x7 网格的唯一路径应该是 28
        result = uniquePaths.uniquePaths(3, 7);
        assertEquals(28, result);
    }

    // 测试在前置条件被违反时的异常处理
    @Test
    public void testPreConditionViolationM() {
        // m 小于 1 应该抛出 IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            uniquePaths.uniquePaths(0, 5);
        });
    }

    @Test
    public void testPreConditionViolationN() {
        // n 大于 100 应该抛出 IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> {
            uniquePaths.uniquePaths(10, 101);
        });
    }

    // 确保后置条件在函数执行后保持
    @Test
    public void testPostConditions() {
        int result = uniquePaths.uniquePaths(1, 1); // 唯一路径是 1
        assertEquals(1, result); // 确保返回的结果是非负整数

        result = uniquePaths.uniquePaths(100, 100); // 测试最大边界
        assertTrue(result >=0); // 确保结果为正
    }

    // 确保不变量在状态变化后保持
    @Test
    public void testInvariants() {
        // 不变量测试：移动的路径必须在网格范围内
        int result = uniquePaths.uniquePaths(3, 3);
        assertTrue(result >= 0); // 路径数不能为负

        result = uniquePaths.uniquePaths(100, 100);
        assertTrue(result >= 0); // 路径数不能为负

        // 结果应该与已知的预期一致
        result = uniquePaths.uniquePaths(7, 3);
        assertEquals(28, result);
    }

 
}