import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

/*
 http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 */
public class PercolationStats {

    private int N; // matrix dimesion
    private int T; // simulation times
    private double[] fractions; //

    public double mean() {
        return StdStats.mean(fractions);
    }

    private double var() {
        return StdStats.var(fractions);
    }

    public double stddev() {
        return StdStats.stddev(fractions);
    }

    public double confidenceLo() {
        return mean() - 1.96 * var() / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + 1.96 * var() / Math.sqrt(T);
    }

    private void print() {
        StdOut.println("mean = " + mean());
        StdOut.println("stddev =" + stddev());
        StdOut.println("95%s confidence interval = " + confidenceLo() + ", "
                + confidenceHi());
        StdOut.println();
    }

    private void simulate() {
        int bound = N * N;
        for (int i = 0; i < T; i++) {
            Percolation node = new Percolation(N);
            int opened = 0;
            while (!node.percolates() && opened < bound) {
                int rand = StdRandom.uniform(0, bound);
                int row = (int) (rand / N) + 1;
                int col = (int) (rand % N) + 1;
                if (!node.isOpen(row, col)) {
                    node.open(row, col);
                    opened++;
                    // node.print();
                }
            }
            fractions[i] = (double) opened / (double) bound;
        }
    }

    public PercolationStats(int N, int T) {
        if (N < 1 || T < 1) {
            throw new IllegalArgumentException();
        }
        this.N = N;
        this.T = T;
        fractions = new double[T];
        simulate();
        print();
    }

    public static void main(String[] args) {
        if (args.length >= 2) {
            int N = Integer.parseInt(args[0]);
            int T = Integer.parseInt(args[1]);
            new PercolationStats(N, T);
        }
    }
}
