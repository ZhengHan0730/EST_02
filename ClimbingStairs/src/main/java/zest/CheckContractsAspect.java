package zest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface CheckContracts {
}

@Aspect
public class CheckContractsAspect {

    @Pointcut("@annotation(CheckContracts)")
    public void methods() {
    }

    @Before("methods()")
    public void checkPreCondition(JoinPoint point) {
        assert point.getArgs().length == 1: "method must have only one argument";
        var n = point.getArgs()[0];
        assert n instanceof Integer: "type of argument must be Integer";
        assert (Integer) n > 0: "number of stairs must be a positive integer";
    }

    @AfterReturning(pointcut = "methods()", returning = "allWays")
    public void checkPostCondition(Object allWays) throws Throwable {
        assert allWays instanceof BigDecimal: "return value must be BigDecimal";
        assert ((BigDecimal) allWays).compareTo(new BigDecimal(0)) > 0 : "number of ways to climb the stairs must be a non-negative number";
    }
}
