package sequences;

public class SequencesImpl implements Sequences {
    public void a(int n) {
        if (n > 0) {
            System.out.print("A. ");
            for (int i = 1; i <= n; i++) {
                System.out.print(i * 2 + ", ");
            }
            System.out.println();
        }
    }

    public void b(int n) {
        if (n > 0) {
            System.out.print("B. ");
            for (int i = 1; i <= n; i++) {
                System.out.print(i * 2 - 1 + ", ");
            }
            System.out.println();
        }
    }

    public void c(int n) {
        if (n > 0) {
            System.out.print("C. ");
            for (int i = 1; i <= n; i++) {
                System.out.print(i * i + ", ");
            }
            System.out.println();
        }
    }

    public void d(int n) {
        if (n > 0) {
            System.out.print("D. ");
            for (int i = 1; i <= n; i++) {
                System.out.print(i * i * i + ", ");
            }
            System.out.println();
        }
    }

    public void e(int n) {
        if (n > 0) {
            System.out.print("E. ");
            for (int i = 1; i <= n; i++) {
                if (i % 2 == 0) {
                    System.out.print(-1 + ", ");
                } else {
                    System.out.print(1 + ", ");
                }
            }
            System.out.println();
        }
    }

    public void f(int n) {
        if (n > 0) {
            System.out.print("F. ");
            for (int i = 1; i <= n; i++) {
                if (i % 2 == 0) {
                    System.out.print(-i + ", ");
                } else {
                    System.out.print(i + ", ");
                }
            }
            System.out.println();
        }
    }

    public void g(int n) {
        if (n > 0) {
            System.out.print("G. ");
            for (int i = 1; i <= n; i++) {
                if (i % 2 == 0) {
                    System.out.print(-i * i + ", ");
                } else {
                    System.out.print(i * i + ", ");
                }
            }
            System.out.println();
        }
    }

    public void h(int n) {
        int ans = 1;
        if (n > 0) {
            System.out.print("H. ");
            for (int i = 1; i <= n; i++) {
                if (i % 2 == 0) {
                    System.out.print(0 + ", ");
                } else {
                    System.out.print(ans + ", ");
                    ans += 1;
                }
            }
            System.out.println();
        }
    }

    public void i(int n) {
        int current;
        int prev = 1;
        if (n > 0) {
            System.out.print("I. ");
            for (int i = 1; i <= n; i++) {
                current = prev * i;
                prev = current;
                System.out.print(current + ", ");
            }
            System.out.println();
        }
    }

    public void j(int n) {
        int current = 1;
        int prev = 0;
        int prevPrev = 0;
        if (n > 0) {
            System.out.print("J. ");
            for (int i = 1; i <= n; i++) {
                if (i == 1) {
                    System.out.print(current + ", ");
                    prevPrev = prev;
                    prev = current;
                } else {
                    current = prev + prevPrev;
                    System.out.print(current + ", ");
                    prevPrev = prev;
                    prev = current;
                }
            }
            System.out.println();
        }
    }
}
