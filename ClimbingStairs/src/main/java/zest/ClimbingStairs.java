package zest;

import java.math.BigDecimal;

public class ClimbingStairs {

    @CheckContracts
    public BigDecimal climbStairs(int n) {
        if (n <= 2) {
            return new BigDecimal(n);
        }
        BigDecimal oneStepBefore = new BigDecimal(2);
        BigDecimal twoStepsBefore = new BigDecimal(1);
        BigDecimal allWays = new BigDecimal(0);

        for (int i = 2; i < n; i++) {
            allWays = oneStepBefore.add(twoStepsBefore);
            twoStepsBefore = oneStepBefore;
            oneStepBefore = allWays;
        }
        return allWays;
    }
}
