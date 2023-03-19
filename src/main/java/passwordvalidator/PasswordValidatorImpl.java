package passwordvalidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidatorImpl {
    public static boolean validateLogPass(String login, String password, String confirmPassword) {
        boolean answer = false;
        /*
         * не совсем понятно является ли отсутствие символов в логине/пароле ошибкой
         * реализовал вариант что не является, в ином случае можно заменить [a-zA-Z1-9_]* на [a-zA-Z1-9_]+
         */
        Pattern pattern = Pattern.compile( "[a-zA-Z1-9_]*" );
        Matcher matchLogin = pattern.matcher(login);
        Matcher matchPassword = pattern.matcher(password);

        try {
            if (!matchLogin.matches()) {
                throw new WrongLoginException("Логин содержит недопустимые символы");
            }
            if (!(login.length() < 20)) {
                throw new WrongLoginException("Логин слишком длинный");
            }
            if (!matchPassword.matches()) {
                throw new WrongPasswordException("Пароль содержит недопустимые символы");
            }
            if (!(password.length() < 20)) {
                throw new WrongPasswordException("Пароль слишком длинный");
            }
            if (!password.equals(confirmPassword)) {
                throw new WrongPasswordException("Пароль и подтверждение не совпадают");
            }
            answer = true;
        } catch (WrongLoginException e) {
            System.out.println(e.getMessage());
            return answer;
        } catch (WrongPasswordException e) {
            System.out.println(e.getMessage());
            return answer;
        }

        return answer;
    }
}
