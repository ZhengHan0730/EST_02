package zest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
        assert point.getArgs().length == 2 : "method must have exactly 2 arguments";
        Object numCoursesObj = point.getArgs()[0];
        assert numCoursesObj instanceof Integer : "type of numCourse must be Integer";
        int numCourses = (Integer) numCoursesObj;
        assert numCourses > 0 : "number of courses must be a positive integer";

        Object prerequisitesObj = point.getArgs()[1];
        assert prerequisitesObj instanceof int[][] : "type of prerequisites must be int[][]";
        int[][] prerequisites = (int[][]) prerequisitesObj;
        for (int[] prerequisite : prerequisites) {
            assert prerequisite.length == 2 : "length of each prerequisite must be 2";
            assert prerequisite[0] != prerequisite[1] : "two courses in a prerequisite pair must be distinct";
            for (int course : prerequisite) {
                assert course >= 0 : "course number must be non-negative";
                assert course < numCourses : "course number must be less than numCourse";
            }
        }
    }

    @AfterReturning(pointcut = "methods()", returning = "canScheduleObj")
    public void checkPostCondition(Object canScheduleObj) throws Throwable {
        assert canScheduleObj instanceof Boolean : "return value must be Boolean";
    }
}
