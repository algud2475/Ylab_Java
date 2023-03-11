package complexnumbers;

public class ComplexNumbersTest {
    public static void main(String[] args) {
        ComplexNumbersImpl A = new ComplexNumbersImpl(7);
        ComplexNumbersImpl B = new ComplexNumbersImpl(3, -2);

        ComplexNumbersImpl AmB = A.minus(B);
        ComplexNumbersImpl ApB = A.plus(B);
        ComplexNumbersImpl AmBApB = AmB.multiply(ApB);

        System.out.println("A = " + A.complexNumberToString());
        System.out.println("B = " + B.complexNumberToString());
        System.out.println("C = A - B = " + AmB.complexNumberToString());
        System.out.println("D = A + B = " + ApB.complexNumberToString());
        System.out.println("E = C * D = " + AmBApB.complexNumberToString());
        System.out.printf("module E = %.2f", AmBApB.abs());
    }
}
