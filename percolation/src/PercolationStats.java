/*************************************************************************
 * Name: Dmytro Allximik
 * Email: dima@ua2012.org.ua
 *
 * Compilation:  javac PercolationStats.java
 * Execution:
 * Dependencies: Percolation.java
 *
 * Description: Class in charge of running the simulation.
 *
 *************************************************************************/

/**
 * Created by dima on 2/9/2015.
 */
public class PercolationStats {
    private int experimentCount;
    private int rowLen; //number of sites in row
    private int total; //number of sites in matrix
    private int runCount; //number of rows
    private double[] results; //matrix of results

    /**
     * Perform T independent computational experiments on an N-by-N grid
     * @param N number of rows
     * @param T number of tests
     */
    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        rowLen = N;

        //-- Run the experiment T times
        experimentCount = T;
        int i = T;
        total = 0;
        runCount = 0;
        results = new double[T];
        while (0 < i--) {
            int c = monteCarloSimulation(N);
            results[i] = (double) c/N/N;
            total += c;
            runCount++;
        }
    }

    // run the Monte Carlo simulation on an N-by-N grid
    private int monteCarloSimulation(int N) {
        int c = 0;
        Percolation p = new Percolation(N);
        while (!p.percolates()) {
            int i = 1+StdRandom.uniform(N);
            int j = 1+StdRandom.uniform(N);
            if (!p.isOpen(i, j)) {
                c++;
                p.open(i, j);
            }
        }
        return c;
    }

    /**
     * Evaluates sample mean of percolation threshold
     * @return sample mean
     */
    public double mean() {
        return (double) total/runCount/rowLen/rowLen;
    }

    /**
     * Evaluates sample standard deviation of percolation threshold
     * @return sample standard deviation
     */
    public double stddev() {
        if (1 == runCount) {
            return Double.NaN;
        }
        return StdStats.stddev(results);
    }

    /**
     * Evaluate low confidence level
     * @return low confidence level
     */
    public double confidenceLo(){
        return mean() - ((1.96 * stddev()) / Math.sqrt(experimentCount));
    }

    /**
     * Evaluate high confidence level
     * @return high confidence level
     */
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(experimentCount));
    }

    /**
     *
     * @param args matrix dimension, number of tests
     */
    public static void main(String[] args) {
        int N = StdIn.readInt();
        int T = StdIn.readInt();
        PercolationStats ps = new PercolationStats(N, T);

        // Print out the results
        double m = ps.mean();
        double s = ps.stddev();
        double l = ps.confidenceLo();
        double h = ps.confidenceHi();

        StdOut.println("mean                    = "+ m);
        StdOut.println("stddev                  = "+ s);
        StdOut.println("95% confidence interval = "+ l +", "+ h);
    }

}