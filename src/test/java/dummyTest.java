import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for Dummy class
 * Checks if the area calculation is correct for a given base and height
 */
public class dummyTest {
    
    /**
     * Verifies that the dummy method returns the correct mathematical result.
     * This test specifically checks a standard triangle with base 10 and height 5.
     */
    @Test
    public void testCalculateArea() {
        dummy dummy = new dummy();

        double expected = 25.0;
        double actual = dummy.calculateArea(10.0, 5.0);

        /**
         * Asserts that the calculated value is within a 0.001 margin of error.
         * This delta is necessary for floating-point comparisons in Java.
         */
        assertEquals(expected, actual, 0.001);
    }
}