package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;
import java.lang.IllegalArgumentException;
import java.util.Random;

public class PercolationStats {
    private int N;
    private int T;
    private Percolation percolation;
    private double[] grade;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N<0 || T<0) {
            throw new IllegalArgumentException("T and N need to over 0");
        }
        percolation = pf.make(N);
        this.N = N;
        this.T = T;
    }
    public double mean() {

        return StdStats.mean(grade);
    }
    public double stddev() {
        return StdStats.stddev(grade);
    }
    public double confidenceLow() {
        return mean()-1.96*stddev()/Math.sqrt(T);
    }
    public double confidenceHigh() {
        return mean()+1.96*stddev()/Math.sqrt(T);
    }
    private void getXt() {
        double[] x_nums = new double[T];
        for (int i=0; i<T; i++) {
            int x_num = getOpenSites();

            x_nums[i] = (double) x_num/(double) (N*N);
        }
        grade = x_nums;
    }
    private int getOpenSites() {

        while (!percolation.percolates()) {
            int row = StdRandom.uniform(percolation.getN());
            int col = StdRandom.uniform(percolation.getN());
            if (percolation.isOpen(row, col)) {
                continue;
            }
            percolation.open(row,col);

        }
        return percolation.getOpen_nums();
    }
}
