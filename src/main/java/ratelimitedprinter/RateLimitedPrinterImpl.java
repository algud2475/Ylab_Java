package ratelimitedprinter;

public class RateLimitedPrinterImpl implements RateLimitedPrinter {
    private int interval;
    private long lastTime;
    private long currentTime;

    public RateLimitedPrinterImpl(int interval) {
        this.interval = interval;
    }

    public void print(String message) {
        currentTime = System.currentTimeMillis();
        if (currentTime - lastTime > interval) {
            System.out.println(message);
            lastTime = currentTime;
        }
    }
}
