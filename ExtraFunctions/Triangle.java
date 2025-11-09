package ExtraFunctions;

public class Triangle extends PolygonShape {
    private double[] lengths = new double[3];
    private double[] angles = new double[3];
    private double area;

    public Triangle() {
        super(3);
        // Default triangle shape
        int[] x = {250, 100, 400};
        int[] y = {100, 400, 400};
        setCoordinates(x, y);
    }

    @Override
    public void calculateProperties() {
        // 1. Area (Shoelace formula for general polygon works for triangle too)
        double sum = 0;
        for (int i = 0; i < 3; i++) {
             sum += (double)xPoints[i] * yPoints[(i + 1) % 3] - (double)xPoints[(i + 1) % 3] * yPoints[i];
        }
        this.area = Math.abs(sum) / 2.0;

        // 2. Lengths
        for (int i = 0; i < 3; i++) {
            lengths[i] = Math.hypot(xPoints[(i + 1) % 3] - xPoints[i], yPoints[(i + 1) % 3] - yPoints[i]);
        }

        // 3. Angles (Law of Cosines: c^2 = a^2 + b^2 - 2ab*cos(C))
        // Angle 0 is opposite length 1.
        angles[0] = calculateAngle(lengths[0], lengths[2], lengths[1]); // Angle at vertex 0
        angles[1] = calculateAngle(lengths[0], lengths[1], lengths[2]); // Angle at vertex 1
        angles[2] = calculateAngle(lengths[1], lengths[2], lengths[0]); // Angle at vertex 2
    }

    private double calculateAngle(double adj1, double adj2, double opposite) {
        return Math.toDegrees(Math.acos((adj1 * adj1 + adj2 * adj2 - opposite * opposite) / (2 * adj1 * adj2)));
    }

    @Override
    public double getArea() { return Math.round(area * 100.0) / 100.0; }
    @Override
    public double getPerimeter() {
        return Math.round((lengths[0] + lengths[1] + lengths[2]) * 100.0) / 100.0;
    }
    @Override
    public double getAngle(int i) { return Math.round(angles[i] * 100.0) / 100.0; }
    @Override
    public double getLength(int i) { return Math.round(lengths[i] * 100.0) / 100.0; }
}