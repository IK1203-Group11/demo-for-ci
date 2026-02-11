import org.junit.Test;
import static org.junit.Assert.*;

 //Unit tests for Dummy class

 public class dummyTest {
    
    /**
     * Verifies that the calculateArea returns the correct mathematical result.
     * Checks a standard triangle with base 10 and height 5.
     */
    @Test
    public void testCalculateArea() {
        dummy d = new dummy();

        double expected = 20.0;
        double actual = d.calculateArea(10.0, 5.0);

        /**
         * Asserts that the calculated value is within a 0.001 margin of error.
         * This delta is necessary for floating-point comparisons in Java.
         */
        assertEquals(expected, actual, 0.001);
    }
    /**
     * Test for invalid input of negative base 
     *  @throws IllegalArgumentException if any side is less than or equal to zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public void negativeBaseTest() {
        dummy d = new dummy();

        d.calculateArea(-10.0, 5.0);
    }

    //Verifies that classifyTriangle returns the correct mathematical result
    @Test
    public void testClassifyTriangle() {
        dummy d = new dummy();
    
        String acInvalid = d.classifyTriangle(1.0, 2.0, 3.0);
        String acEquilateral = d.classifyTriangle(3.0, 3.0, 3.0);
        String acIsosceles = d.classifyTriangle(5.0, 5.0, 8.0);
        String acScalene = d. classifyTriangle(3.0, 4.0, 5.0);
        
        // Invalid: 1+2 is not greater than 3  
        assertEquals("Invalid", acInvalid);
        
        // Equilateral: All sides same
        assertEquals("Equilateral", acEquilateral);

        // Isosceles: Exactly two sides same (and valid: 5+5 > 8)
        assertEquals("Isosceles", acIsosceles);

        // Scalene: All sides different (and valid: 3+4 > 5)
        assertEquals("Scalene", acScalene);


    }
    /**
     * Test for invalid input of negative side length in classifyTriangle
     * @throws IllegalArgumentException if any side is less than or equal to zero.
     */
    @Test(expected = IllegalArgumentException.class)
    public void negClassifyTriangle() {
        dummy d = new dummy();

        d.classifyTriangle(-2.0, 2.0, 2.0);

    }

}