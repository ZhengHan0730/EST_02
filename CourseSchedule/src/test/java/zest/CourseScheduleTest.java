package zest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;

import static org.junit.jupiter.api.Assertions.*;

class CourseScheduleTest {

    private final static String RANDOM_SEED = "114514";
    private final Random random = new Random(114514);
    private final CourseSchedule instance = new CourseSchedule();

    @Property(seed = RANDOM_SEED)
    void testPreConditionsNumCourses(@ForAll @IntRange(min = Integer.MIN_VALUE, max = 0) int numCourses) {
        assertThrows(AssertionError.class, () -> instance.canFinish(numCourses, new int[][]{}));
    }

    @Property(seed = RANDOM_SEED, tries = 1000)
    void testPreConditionsPrerequisitesNegativeValue(@ForAll @IntRange(min = Integer.MIN_VALUE, max = 0) int from, @ForAll @IntRange(min = Integer.MIN_VALUE, max = 0) int to) {
        assertThrows(AssertionError.class, () -> instance.canFinish(10, new int[][]{new int[]{from, to}}));
    }

    @Property(seed = RANDOM_SEED)
    void testPreConditionsPrerequisitesNonExistCourse(@ForAll @IntRange(min = 10) int from, @ForAll @IntRange(min = 10) int to) {
        assertThrows(AssertionError.class, () -> instance.canFinish(10, new int[][]{new int[]{from, to}}));
    }

    @Property(seed = RANDOM_SEED)
    void testPreConditionsPrerequisitesInvalidShape(@ForAll("positiveIntegerOtherThanTwo") int prerequisiteSize) {
        int[] prerequisite = new int[prerequisiteSize];
        for (int i = 0; i < prerequisiteSize; i++) {
            prerequisite[i] = i;
        }
        assertThrows(AssertionError.class, () -> instance.canFinish(prerequisiteSize + 1, new int[][]{prerequisite}));
    }

    @Provide
    Arbitrary<Integer> positiveIntegerOtherThanTwo() {
        return Arbitraries.integers().filter(n -> n >= 0 && n != 2 && n <= 1e6);
    }

    @Property(seed = RANDOM_SEED)
    void testPreConditionsPrerequisitesDistinctCourse(@ForAll @IntRange(max = 10) int course) {
        assertThrows(AssertionError.class, () -> instance.canFinish(10, new int[][]{new int[]{course, course}}));
    }

    @Property(seed = RANDOM_SEED)
    void forestsCanBeScheduled(@ForAll @IntRange(min = 1, max = 1000) int numCourses) {
        List<GraphNode> courses = new ArrayList<GraphNode>();
        for (int i = 0; i < numCourses; i++) {
            courses.add(new GraphNode(i));
        }
        Collections.shuffle(courses, random);

        // dummy root
        courses.add(0, new GraphNode(-1));
        GraphNode root = courses.get(0);
        assert root.value == -1;

        int cursor = 1;
        for (int i = 0; i < numCourses; i++) {
            GraphNode curr = courses.get(i);
            int numChildren = random.nextInt(courses.size() - cursor);
            for (int j = 0; j < numChildren; j++) {
                GraphNode next = courses.get(cursor + j);
                curr.next.add(next);
                next.prev.add(curr);
            }
            cursor += numChildren;
            if (cursor == courses.size()) {
                break;
            }
        }

        List<int[]> dependencies = new ArrayList<>();
        for (GraphNode sub : root.next) {
            Queue<GraphNode> buffer = new LinkedList<>();
            buffer.add(sub);
            while (!buffer.isEmpty()) {
                GraphNode node = buffer.poll();
                for (GraphNode child : node.next) {
                    dependencies.add(new int[]{child.value, node.value});
                    buffer.add(child);
                }
            }
        }

        int[][] prerequisites = new int[dependencies.size()][];
        for (int i = 0; i < prerequisites.length; i++) {
            prerequisites[i] = dependencies.get(i);
        }
        assertTrue(instance.canFinish(numCourses, prerequisites));
    }

    @Property(seed = RANDOM_SEED)
    void graphsWithCycleCannotBeScheduled(@ForAll @IntRange(min = 2, max = 1000) int numCourses) {
        List<GraphNode> courses = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            courses.add(new GraphNode(i));
        }

        int cycleSize = 2;
        if (numCourses > 2) {
            cycleSize += random.nextInt(numCourses - 2);
        }
        List<GraphNode> cycle = new ArrayList<>();
        List<Integer> indices = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            indices.add(i);
        }
        for (int i = 0; i < cycleSize; i++) {
            int indexIndex = random.nextInt(indices.size());
            int index = indices.get(indexIndex);
            cycle.add(courses.get(index));
            indices.remove(indexIndex);
        }
        cycle.get(cycleSize - 1).next.add(cycle.get(0));
        cycle.get(0).prev.add(cycle.get(cycleSize - 1));
        for (int i = 1; i < cycleSize; i++) {
            cycle.get(i - 1).next.add(cycle.get(i));
            cycle.get(i).prev.add(cycle.get(i - 1));
        }

        int numEdges = random.nextInt(numCourses * (numCourses - 1));
        for (int i = 0; i < numEdges; i++) {
            GraphNode from = courses.get(random.nextInt(numCourses));
            GraphNode to = courses.get(random.nextInt(numCourses));
            if (from == to || from.next.contains(to)) {
                continue;
            }
            from.next.add(to);
            to.prev.add(from);
        }

        int numPrerequisites = 0;
        for (var course : courses) {
            numPrerequisites += course.next.size();
        }
        int[][] prerequisites = new int[numPrerequisites][];
        int index = 0;
        for (var from : courses) {
            for (var to : from.next) {
                prerequisites[index++] = new int[]{from.value, to.value};
            }
        }

        assertFalse(instance.canFinish(numCourses, prerequisites));
    }

    private static class GraphNode {
        public GraphNode(Integer value) {
            this.value = value;
            this.next = new ArrayList<>();
            this.prev = new ArrayList<>();
        }

        public Integer value;
        public List<GraphNode> next;
        public List<GraphNode> prev;
    }
}
