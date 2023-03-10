import java.util.Scanner;

public class Pell {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();

            long answer = PeleNumber(n);
            System.out.println(answer);
        }
    }

    public static long PeleNumber(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            long[] P = new long[n + 1];
            P[0] = 0;
            P[1] = 1;
            for (int i = 2; i <= n; i++) {
                P[i] = 2 * P[i - 1] + P[i - 2];
            }
            return P[n];
        }
    }
}
