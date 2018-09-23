import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    public static Integer expected;
    public static Integer actual;
    @Test
    public void testStudentArrayDequeAndArrayDequeSolution(){
        StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> sad2 = new ArrayDequeSolution<>();

        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                sad1.addLast(i);
                sad2.addLast(i);
            } else {
                sad1.addFirst(i);
                sad2.addFirst(i);
            }
        }

        for (int i = 0; i < 10; i += 1) {
            double numberBetweenZeroAndOne = StdRandom.uniform();

            if (numberBetweenZeroAndOne < 0.5) {
                expected = sad1.removeLast();
                actual = sad2.removeLast();
            } else {
                expected = sad1.removeFirst();
                actual = sad2.removeFirst();
            }
            assertEquals(expected +" not equal to "+ actual, expected, actual);
        }
    }


}
