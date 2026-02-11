import org.junit.Test;
import static org.junit.Assert.*;

public class dummyTest {

    @Test
    public void testCalculateArea() {
        dummy d = new dummy();

        double expected = 25.0; // FIXED: 10*5/2 = 25
        double actual = d.calculateArea(10.0, 5.0);

        assertEquals(expected, actual, 0.001);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeBaseTest() {
        dummy d = new dummy();
        d.calculateArea(-10.0, 5.0);
    }

    @Test
    public void testClassifyTriangle() {
        dummy d = new dummy();

        String acInvalid = d.classifyTriangle(1.0, 2.0, 3.0);
        String acEquilateral = d.classifyTriangle(3.0, 3.0, 3.0);
        String acIsosceles = d.classifyTriangle(5.0, 5.0, 8.0);
        String acScalene = d.classifyTriangle(3.0, 4.0, 5.0);

        assertEquals("Invalid", acInvalid);
        assertEquals("Equilateral", acEquilateral);
        assertEquals("Isosceles", acIsosceles);
        assertEquals("Scalene", acScalene);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negClassifyTriangle() {
        dummy d = new dummy();
        d.classifyTriangle(-2.0, 2.0, 2.0)
    }
}
