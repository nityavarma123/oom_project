package ExtraFunctions;

public class Quadrilateral extends PolygonShape {
    private double[] lengths = new double[4];
    private double[] angles = new double[4];
    private double area;

    public Quadrilateral() {
        super(4);
        // Default square shape
        int[] x = {100, 100, 400, 400};
        int[] y = {100, 400, 400, 100};
        setCoordinates(x, y);
    }

    @Override
    public void calculateProperties() {
        // 1. Area (Shoelace formula)
        double sum = 0;
        for (int i = 0; i < 4; i++) {
            sum += (double)xPoints[i] * yPoints[(i + 1) % 4] - (double)xPoints[(i + 1) % 4] * yPoints[i];
        }
        this.area = Math.abs(sum) / 2.0;

        // 2. Lengths
        for (int i = 0; i < 4; i++) {
            lengths[i] = Math.hypot(xPoints[(i + 1) % 4] - xPoints[i], yPoints[(i + 1) % 4] - yPoints[i]);
        }

        // 3. Angles (using Law of Cosines with diagonals)
        double d1 = Math.hypot(xPoints[2] - xPoints[0], yPoints[2] - yPoints[0]); // AC diagonal
        double d2 = Math.hypot(xPoints[3] - xPoints[1], yPoints[3] - yPoints[1]); // BD diagonal

        angles[0] = calculateAngle(lengths[0], lengths[3], d2); // A
        angles[1] = calculateAngle(lengths[0], lengths[1], d1); // B
        angles[2] = calculateAngle(lengths[1], lengths[2], d2); // C
        angles[3] = calculateAngle(lengths[2], lengths[3], d1); // D
    }

    private double calculateAngle(double a, double b, double c) {
        return Math.toDegrees(Math.acos((a * a + b * b - c * c) / (2 * a * b)));
    }

    @Override
    public double getArea() { return Math.round(area * 100.0) / 100.0; }
    @Override
    public double getPerimeter() {
        return Math.round((lengths[0] + lengths[1] + lengths[2] + lengths[3]) * 100.0) / 100.0;
    }
    @Override
    public double getAngle(int i) { return Math.round(angles[i] * 100.0) / 100.0; }
    @Override
    public double getLength(int i) { return Math.round(lengths[i] * 100.0) / 100.0; }
}