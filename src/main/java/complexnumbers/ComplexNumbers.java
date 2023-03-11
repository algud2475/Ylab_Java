package complexnumbers;

public interface ComplexNumbers {
    ComplexNumbersImpl plus(ComplexNumbersImpl X);
    ComplexNumbersImpl minus(ComplexNumbersImpl X);
    ComplexNumbersImpl multiply(ComplexNumbersImpl X);
    double abs();
    String complexNumberToString();
}
