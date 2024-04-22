package zest;

public class uniquePaths {

    public long uniquePaths(int m, int n) {
        // check Preconditions
        if (m < 1 || m > 100 || n < 1 || n > 100) {
            throw new IllegalArgumentException("check preconditions");
        }


        long[][] dp = new long[m][n];


        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }

        // Calculates the number of paths and prevents negative numbers and overflows
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                long sum = dp[i - 1][j] + dp[i][j - 1];

                //
                if (sum < 0 || sum > Long.MAX_VALUE) {
                    dp[i][j] = 0; // If overflow or negative, set it to 0
                } else {
                    dp[i][j] = sum;
                }
            }
        }

        long result = dp[m - 1][n - 1];

        // check Postconditions
        if (result < 0) {
            result = 0;
        }

        return result;
    }



}
