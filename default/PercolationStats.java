import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCECONSTANT = 1.96;
    private final int trials;
    private final double[] fractionOfOpenSites;

    public PercolationStats(int n, int trials) {
        if (trials <= 0) {
            throw new IllegalArgumentException("trials have to be positive.");
        }
        fractionOfOpenSites = new double[trials];
        this.trials = trials;
        int spaces = n * n;
        int tests = 0;
        while (tests < trials) {
            Percolation testField = new Percolation(n);
            while (!testField.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);

                testField.open(row, col);

            }
            fractionOfOpenSites[tests++] = (double) testField.numberOfOpenSites() / spaces;
        }
    }

    public double mean() {
        return StdStats.mean(fractionOfOpenSites);
    }

    public double stddev() {
        return StdStats.stddev(fractionOfOpenSites);
    }

    public double confidenceLo() {
        return mean() - (CONFIDENCECONSTANT * stddev()) / Math.sqrt(trials);
    }

    public double confidenceHi() {
        return mean() + (CONFIDENCECONSTANT * stddev()) / Math.sqrt(trials);
    }

    public static void main(String[] args) {
        PercolationStats test = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        System.out.println("mean                    = " + test.mean());
        System.out.println("stddev                  = " + test.stddev());
        System.out.println("95% confidence interval = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
    }

}