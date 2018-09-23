package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;


import java.util.Random;

public class PercolationStats {
    private Percolation[] p;
    private Random RANDDOM  = new Random(565);
    private static double[] x;
    private double sum = 0;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0){
            throw new IllegalArgumentException("java.lang.IllegalArgumentException");
        }
         p = new Percolation[T];
         x = new double[T];
         for (int i =0; i < T; i++){
             p[i] = pf.make(N);
             while(!p[i].percolates()){
                 int a = RANDDOM.nextInt(N);
                 int b = RANDDOM.nextInt(N);
                 if (!p[i].isOpen(a,b)){
                     p[i].open(a,b);
                 }else{
                     continue;
                 }
             }
             x[i] = ((double)p[i].numberOfOpenSites()) / (T * T);
             sum = sum + x[i];
         }
    }

    // sample mean of percolation threshold
    public double mean() {
        return sum / x.length;
    }
    // sample standard deviation of percolation threshold
    public double stddev(){
        double u = mean();
        double sum2 = 0;
        for (int i = 0; i < x.length; i++){
            sum2 = sum2 + (x[i] - u) * (x[i] - u);
        }
        double s = sum2 / (x.length - 1);
        return Math.pow(s,0.5);
    }
    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double u = mean();
        double tmep = 1.96 * stddev() / (Math.pow(x.length,0.5));
        return u - tmep;
    }
    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double u = mean();
        double tmep = 1.96 * stddev() / (Math.pow(x.length,0.5));
        return u + tmep;
    }

    public static void main(String[] args){
        PercolationFactory pf = new PercolationFactory();
        PercolationStats ps = new PercolationStats(100,100,pf);
        System.out.println(ps.mean());
        System.out.println(ps.stddev());
        System.out.println(ps.confidenceLow());
        System.out.println(ps.confidenceHigh());
    }
}
