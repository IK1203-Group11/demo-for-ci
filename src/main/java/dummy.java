public class dummy {

    /**
    * Calculates the area of a triangle.
    * @param base the base of the triangle
    * @param height the height of the triangle
    * @return the area as a double
    */
    public double calculateArea(double base, double height) {
        if (base <= 0 || height <= 0) throw new IllegalArgumentException("Base and height must be positive");
            return base * height / 2;
    }

    /**
    * Determines the type of triangle based on its side lengths.
    * @param a side 1
    * @param b side 2
    * @param c side 3
    * @return A string: "Equilateral", "Isosceles", "Scalene", or "Invalid"
    */
    public String classifyTriangle(double a, double b, double c) {
        if (a <= 0 || b <= 0 || c <= 0) {
            throw new IllegalArgumentException("Sides must be positive");
        }
        if (a + b <= c || a + c <= b || b + c <= a) {
            return "Invalid"
        } else if (a == b && b == c) {
            return "Equilateral";
        } else if (a == b || b == c || a == c) {
            return "Isosceles";
        } else {
            return "Scalene";
        }
    }

}
