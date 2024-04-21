package zest;

public class uniquePaths {
    public int uniquePaths(int m, int n) {
        // Preconditions: Check if grid dimensions are within acceptable range
        if (m < 1 || m > 100) {
            throw new IllegalArgumentException("`1 <= m <= 100`");
        }
        if (n < 1 || n > 100) {
            throw new IllegalArgumentException("`1 <= n <= 100`");
        }

        // Initialize the dynamic programming (DP) table with default values
        int[][] dp = new int[m][n];

        // Invariant: Grid boundaries
        // Initialize the first row and column to represent paths along the edges
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1; // Only one way to reach any cell in the first column
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1; // Only one way to reach any cell in the first row
        }

        // Invariant: Uniqueness of paths
        // Calculate the number of unique paths for the rest of the grid
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1]; // Sum of paths from top and left

                // Ensure that the result doesn't go negative
                if (dp[i][j] < 0) {
                    dp[i][j] = 0; // This shouldn't occur with valid inputs, just a safeguard
                }
            }
        }

        // Postconditions: Check that the final result is valid
        int result = dp[m - 1][n - 1]; // Number of unique paths to reach the bottom-right corner

        if (result < 0) {
            result = 0; // If somehow negative, correct it
        }

        return result; // Return the computed number of unique paths
    }

}
