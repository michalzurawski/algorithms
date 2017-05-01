package com.github.michalzurawski.algorithms.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Class estimating threshold value of Percolation System using Monte Carlo simulation.
 * In each experiment new {@code n-by-n} Percolation System is created and the sites
 * are randomly opened until system percolates.
 */
public class PercolationStats {
  /**
   * Estimated mean value of percolation threshold.
   */
  private final double mean;
  /**
   * Estimated standard deviation of percolation threshold.
   */
  private final double stddev;
  /**
   * Estimated lower 95% confidence interval of percolation threshold.
   */
  private final double confidenceLo;
  /**
   * Estimated upper 95% confidence interval of percolation threshold.
   */
  private final double confidenceHi;

  /**
   * Performs {@code trials} independent experiments on {@code n-by-by} grid.
   * Complexity: O(n^2 * trials)
   *
   * @param n      grid length
   * @param trials number of experiments to run
   */
  public PercolationStats(final int n, final int trials) {
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

  /**
   * Returns mean value of percolation threshold.
   *
   * @return mean value
   */
  public double mean() {
    return mean;
  }

  /**
   * Returns standard deviation of percolation threshold.
   *
   * @return standard deviation
   */
  public double stddev() {
    return stddev;
  }

  /**
   * Returns low endpoint of 95% confidence interval.
   *
   * @return low endpoint of 95% confidence interval
   */
  public double confidenceLo() {
    return confidenceLo;
  }

  /**
   * Returns high endpoint of 95% confidence interval.
   *
   * @return high endpoint of 95% confidence interval
   */
  public double confidenceHi() {
    return confidenceHi;
  }

  /**
   * Computes mean value from given numbers.
   *
   * @param values used to computation
   * @return mean value
   */
  static double mean(final double[] values) {
    double sumOfFractions = 0;
    for (double value : values) {
      sumOfFractions += value;
    }
    return sumOfFractions / values.length;
  }

  /**
   * Computes standard deviation from given numbers.
   *
   * @param values used to computation
   * @param mean   mean value
   * @return standard deviation
   */
  static double stddev(final double[] values, final double mean) {
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

  /**
   * Estimates mean value and standard deviation of threshold value of Percolation System.
   *
   * @param args args[0] - grid size; args[1] number of trials
   */
  public static void main(final String[] args) {
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
