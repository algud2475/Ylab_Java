import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) {
        int number = new Random().nextInt(100); // здесь загадывается число от 1 до 99
        int maxAttempts = 10; // здесь задается количество попыток
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");

        Scanner scan = new Scanner(System.in);
        int userNumber;
        for (int i = maxAttempts; i > 0; i--) {
            userNumber = scan.nextInt();
            if (i == 1 && userNumber != number) {
                System.out.println("Ты не угадал");
                break;
            } else if (userNumber == number) {
                System.out.println("Ты угадал с " + (maxAttempts + 1 - i) + " попытки!");
                break;
            } else if (userNumber < number) {
                System.out.println("Моё число больше! Осталось " + (i - 1) + " попыток");
            } else if (userNumber > number) {
                System.out.println("Моё число меньше! Осталось " + (i - 1) + " попыток");
            }
        }
    }
}

