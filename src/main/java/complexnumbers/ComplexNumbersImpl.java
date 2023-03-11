package complexnumbers;

public class ComplexNumbersImpl implements ComplexNumbers {
    private double a;
    private double b;

    public ComplexNumbersImpl(double a) {
        this.a = a;
        b = 0;
    }

    public ComplexNumbersImpl(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public ComplexNumbersImpl plus(ComplexNumbersImpl X) {
        return new ComplexNumbersImpl(this.a + X.getA(), this.b + X.getB());
    }

    public ComplexNumbersImpl minus(ComplexNumbersImpl X) {
        return new ComplexNumbersImpl(this.a - X.getA(), this.b - X.getB());
    }

    public ComplexNumbersImpl multiply(ComplexNumbersImpl X) {
        return new ComplexNumbersImpl(this.a * X.getA() - this.b * X.getB(), this.a * X.getB() + this.b * X.getA());
    }

    public double abs() {
        return Math.hypot(this.a, this.b);
    }

    public String complexNumberToString() {
        if (b < 0) {
            return a + " - " + Math.abs(b) + "i";
        } else if (b == 0) {
            return a + "";
        } else {
            return a + " + " + b + "i";
        }
    }
}
