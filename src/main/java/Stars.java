import java.util.Scanner;

public class Stars {
    public static void main(String[] args) throws Exception {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            int m = scanner.nextInt();
            String template = scanner.next();

            StringBuilder answer = new StringBuilder();

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < m; j++) {
                    answer.append(template);
                }
                answer.append("\n");
            }

            System.out.println(answer);

        }
    }
}

