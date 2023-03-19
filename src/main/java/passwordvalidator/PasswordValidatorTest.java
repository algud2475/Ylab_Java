package passwordvalidator;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        String login = "";
        String password = "";
        String confirmPassword = "";
        boolean validation = PasswordValidatorImpl.validateLogPass(login, password, confirmPassword);
        if (validation) {
            System.out.println("Логин и пароль успешно зарегистрированы!");
        } else {
            System.out.println("Логин и пароль не зарегистрированы! Попробуйте другую комбинацию");
        }
    }
}
