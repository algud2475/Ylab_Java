package snilsvalidator;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        SnilsValidatorImpl snilsValidator = new SnilsValidatorImpl();
        System.out.println(snilsValidator.validate("90114404441"));
    }
}
