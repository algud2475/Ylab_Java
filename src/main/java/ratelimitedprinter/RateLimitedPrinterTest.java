package ratelimitedprinter;

public class RateLimitedPrinterTest {
    public static void main(String[] args) {
        RateLimitedPrinterImpl rateLimitedPrinterImpl = new RateLimitedPrinterImpl(1000);
        for (int i = 0; i < 1000000000; i++) {
            rateLimitedPrinterImpl.print(String.valueOf(i));
        }
    }
}
