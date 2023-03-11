package snilsvalidator;

public class SnilsValidatorImpl implements SnilsValidator {
    public boolean validate(String snils) {
        boolean ans = false;
        boolean stop = false;
        int checksumSnils = 0;
        int checksumCalc = 0;

        try {
            long snilsID = Long.valueOf(snils);
            if (snils.length() != 11) {
                throw new NumberFormatException("Неверное количество цифр в СНИЛС (должно быть 11)");
            }
        } catch (NumberFormatException e) {
            System.out.println("Неверный формат СНИЛС - присутствуют нецифровые символы либо неверное количество цифр");
            stop = true;
        }

        if (stop) {
            return ans;
        } else {
            int sumOfMultiply = 0;
            checksumSnils = Character.digit(snils.charAt(9), 10) * 10 + Character.digit(snils.charAt(10), 10);
            for (int i = 0; i < 9; i++) {
                sumOfMultiply += Character.digit(snils.charAt(i), 10) * (9 - i);
            }
            if (sumOfMultiply < 100) {
                checksumCalc = sumOfMultiply;
            } else if (sumOfMultiply == 100) {
                checksumCalc = 0;
            } else {
                if (sumOfMultiply % 101 == 100) {
                    checksumCalc = 0;
                } else {
                    checksumCalc = sumOfMultiply % 101;
                }
            }
        }

        if (checksumCalc == checksumSnils) {
            ans = true;
        }

        return ans;
    }
}
