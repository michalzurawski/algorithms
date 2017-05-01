package com.github.michalzurawski.algorithms.percolation;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.Font;

/**
 * After each site is opened, it draws full sites in light blue,
 * open sites (that aren't full) in white, and blocked sites in black,
 * with with site (1, 1) in the upper left-hand corner.
 */
public final class PercolationVisualizer {
  /**
   * Utility class.
   */
  private PercolationVisualizer() {
  }

  /**
   * Delay in milliseconds (controls animation speed).
   */
  private static final int DELAY = 100;

  /**
   * Draws n-by-n percolation system.
   *
   * @param percolation percolation system to draw
   * @param n           length of the grid
   */
  public static void draw(final Percolation percolation, final int n) {
    StdDraw.clear();
    StdDraw.setPenColor(StdDraw.BLACK);
    final double minValueOfScale = -0.05;
    final double maxValueOfScale = 1.05;
    StdDraw.setXscale(minValueOfScale * n, maxValueOfScale * n);
    StdDraw.setYscale(minValueOfScale * n, maxValueOfScale * n);   // leave a border to write text
    StdDraw.filledSquare(n / 2.0, n / 2.0, n / 2.0);
    final double scale = 0.25;

    // draw n-by-n grid
    int opened = 0;
    for (int row = 1; row <= n; row++) {
      for (int col = 1; col <= n; col++) {
        if (percolation.isFull(row, col)) {
          StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
          opened++;
        } else if (percolation.isOpen(row, col)) {
          StdDraw.setPenColor(StdDraw.WHITE);
          opened++;
        } else {
          StdDraw.setPenColor(StdDraw.BLACK);
        }
        StdDraw.filledSquare(col - scale * 2, n - row + scale * 2, scale * 2 + minValueOfScale);
      }
    }

    // write status text
    final int fontSize = 12;
    final double scalePart = -0.025;
    final double scaleTriple = 0.75;
    StdDraw.setFont(new Font("SansSerif", Font.PLAIN, fontSize));
    StdDraw.setPenColor(StdDraw.BLACK);
    StdDraw.text(scale * n, scalePart * n, opened + " open sites");
    if (percolation.percolates()) {
      StdDraw.text(scaleTriple * n, scalePart * n, "percolates");
    } else {
      StdDraw.text(scaleTriple * n, scalePart * n, "does not percolate");
    }

  }

  /**
   * After each site is opened, it draws full sites in light blue,
   * open sites (that aren't full) in white, and blocked sites in black,
   * with with site (1, 1) in the upper left-hand corner.
   *
   * @param args args[0] - input file
   */
  public static void main(final String[] args) {
    final In in = new In(args[0]);      // input file
    final int n = in.readInt();         // n-by-n percolation system

    // turn on animation mode
    StdDraw.enableDoubleBuffering();

    // repeatedly read in sites to open and draw resulting system
    final Percolation percolation = new Percolation(n);
    draw(percolation, n);
    StdDraw.show();
    StdDraw.pause(DELAY);
    while (!in.isEmpty()) {
      final int row = in.readInt();
      final int col = in.readInt();
      percolation.open(row, col);
      draw(percolation, n);
      StdDraw.show();
      StdDraw.pause(DELAY);
    }
  }
}
