package com.github.michalzurawski.algorithms.percolation;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

/**
 * This program takes the grid size n as a command-line argument.
 * Then, the user repeatedly clicks sites to open with the mouse.
 * After each site is opened, it draws full sites in light blue,
 * open sites (that aren't full) in white, and blocked sites in black.
 */
public final class InteractivePercolationVisualizer {

  /**
   * Utility class.
   */
  private InteractivePercolationVisualizer() {
  }

  /**
   * This program takes the grid size n as a command-line argument.
   * Then, the user repeatedly clicks sites to open with the mouse.
   * After each site is opened, it draws full sites in light blue,
   * open sites (that aren't full) in white, and blocked sites in black.
   *
   * @param args args[0] length of the grid
   */
  public static void main(final String[] args) {
    // n-by-n percolation system (read from command-line, default = 10)
    final int defaultSize = 10;
    final int pauseTime = 20;
    int n = defaultSize;
    if (args.length == 1) {
      n = Integer.parseInt(args[0]);
    }

    // repeatedly open site specified my mouse click and draw resulting system
    StdOut.println(n);

    StdDraw.enableDoubleBuffering();
    final Percolation percolation = new Percolation(n);
    PercolationVisualizer.draw(percolation, n);
    StdDraw.show();

    while (true) {

      // detected mouse click
      if (StdDraw.mousePressed()) {

        // screen coordinates
        final double x = StdDraw.mouseX();
        final double y = StdDraw.mouseY();

        // convert to row i, column j
        final int i = (int) (n - Math.floor(y));
        final int j = (int) (1 + Math.floor(x));

        // open site (i, j) provided it's in bounds
        if (i >= 1 && i <= n && j >= 1 && j <= n) {
          if (!percolation.isOpen(i, j)) {
            StdOut.println(i + " " + j);
          }
          percolation.open(i, j);
        }

        // draw n-by-n percolation system
        PercolationVisualizer.draw(percolation, n);
        StdDraw.show();
      }

      StdDraw.pause(pauseTime);
    }
  }
}
