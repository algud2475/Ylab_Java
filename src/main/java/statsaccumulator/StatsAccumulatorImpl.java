package statsaccumulator;

public class StatsAccumulatorImpl {
    private int min;
    private int max;
    private int count;
    private double avg;

    public StatsAccumulatorImpl() {}

    public StatsAccumulatorImpl(int value) {
        count = 1;
        min = value;
        max = value;
        avg = value;
    }

    public void add(int value) {
        min = Math.min(min, value);
        max = Math.max(max, value);
        count += 1;
        avg = (avg * (count - 1) + value) / count;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public int getCount() {
        return count;
    }

    public Double getAvg() {
        return avg;
    }
}
