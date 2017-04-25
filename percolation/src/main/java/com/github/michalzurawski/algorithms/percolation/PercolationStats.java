package com.github.michalzurawski.algorithms.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
  private final double mean;
  private final double stddev;
  private final double confidenceLo;
  private final double confidenceHi;

  // perform trials independent experiments on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("Trials and n must be greater than 0");
    }
    final double[] fractions = new double[trials];
    for (int i = 0; i < trials; ++i) {
      final Percolation percolation = new Percolation(n);
      int openSites = 0;
      while (!percolation.percolates()) {
        final int row = StdRandom.uniform(n);
        final int col = StdRandom.uniform(n);
        if (percolation.isOpen(row + 1, col + 1)) {
          continue;
        }
        percolation.open(row + 1, col + 1);
        openSites++;
      }
      fractions[i] = (double) openSites / (n * n);
    }
    mean = mean(fractions);
    stddev = stddev(fractions, mean);
    final double stddevConfidence = 1.96 * stddev / Math.sqrt(trials);
    confidenceLo = mean - stddevConfidence;
    confidenceHi = mean + stddevConfidence;
  }

  // sample mean of percolation threshold
  public double mean() {
    return mean;
  }

  // sample standard deviation of percolation threshold
  public double stddev() {
    return stddev;
  }

  // low  endpoint of 95% confidence interval
  public double confidenceLo() {
    return confidenceLo;
  }

  // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return confidenceHi;
  }

  static double mean(double[] values) {
    double sumOfFractions = 0;
    for (double value : values) {
      sumOfFractions += value;
    }
    return sumOfFractions / values.length;
  }

  static double stddev(double[] values, double mean) {
    double sumOfDeviations = 0;
    for (double value : values) {
      sumOfDeviations += (value - mean) * (value - mean);
    }
    if (values.length > 1) {
      return Math.sqrt(sumOfDeviations / (values.length - 1));
    } else {
      return Double.NaN;
    }
  }

  public static void main(String[] args) {
    if (args.length != 2) {
      StdOut.println("Usage: program gridSize trialsNumber");
      return;
    }
    final Integer gridSize;
    final Integer trials;
    try {
      gridSize = Integer.parseInt(args[0]);
      trials = Integer.parseInt(args[1]);
    } catch (NumberFormatException ex) {
      StdOut.println("Arguments have to be integers");
      return;
    }
    final PercolationStats percolationStats = new PercolationStats(gridSize, trials);
    StdOut.println("mean\t\t\t= " + percolationStats.mean());
    StdOut.println("stddev\t\t\t= " + percolationStats.stddev());
    StdOut.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats
        .confidenceHi() + "]");
  }
}
