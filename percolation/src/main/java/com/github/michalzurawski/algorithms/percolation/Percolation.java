package com.github.michalzurawski.algorithms.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Class representing percolation system.
 * It is designed as <i>n</i>-by-<i>n</i> grid with all sites either blocked or open.
 * Each site can be connected to any of four neighbouring sites.
 * The system percolates when there is a connection between the top and the bottom of the grid.
 * Uses Weighted Quick Union with Path Compression algorithm.
 */
public final class Percolation {

  /**
   * Site is closed.
   */
  static final byte CLOSED = 0;
  /**
   * Site is open.
   */
  static final byte OPEN = 1;
  /**
   * Site is open and connected to the top.
   */
  static final byte CONNECTED_TOP = maxState(OPEN, (byte) 2);
  /**
   * Site is open and connected to the bottom.
   */
  static final byte CONNECTED_BOTTOM = maxState(OPEN, (byte) 4);
  /**
   * Site is open and connected to the top and to the bottom.
   */
  static final byte CONNECTED_BOTH = maxState(CONNECTED_TOP, CONNECTED_BOTTOM);

  /**
   * 1-D representation of the grid.
   */
  private final WeightedQuickUnionUF weightedQuickUnionUF;
  /**
   * State of each site.
   */
  private final byte[] states;
  /**
   * Grid length.
   */
  private final int n;
  /**
   * Whether system percolates.
   */
  private boolean percolates = false;
  /**
   * Number of open sites.
   */
  private int openCount = 0;

  /**
   * Creates <i>n</i>-by-<i>n</i> grid with all sites blocked.
   * Complexity: O(n^2)
   *
   * @param n length of the grid
   * @throws IllegalArgumentException when <i>n</i> is less or equal 0
   */
  public Percolation(final int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("grid size should be greater than 0");
    }
    weightedQuickUnionUF = new WeightedQuickUnionUF(n * n);
    this.n = n;
    states = new byte[n * n];
  }

  /**
   * Opens site if it is not already open.
   * Complexity: O(log n) where n is number of sites.
   *
   * @param row 1-indexed row number
   * @param col 1-indexed column number
   * @throws IndexOutOfBoundsException when row or col is not between [1 and n]
   */
  public void open(final int row, final int col) {
    validateRowAndCol(row, col);
    final int coordinates = xyTo1D(row, col);
    if (states[coordinates] != CLOSED) {
      return;
    }
    openCount++;
    byte maxState = OPEN;
    if (row == 1) {
      maxState = maxState(CONNECTED_TOP, maxState);
    }
    if (row == n) {
      maxState = maxState(CONNECTED_BOTTOM, maxState);
    }
    for (int i : new int[]{-1, 1, n, -n}) {
      if (coordinatesAreValid(coordinates, i)) {
        final int neighbourCoordinates = coordinates + i;
        final byte neighbourState = states[neighbourCoordinates];
        if (neighbourState != CLOSED) {
          final byte parentState = states[weightedQuickUnionUF.find(neighbourCoordinates)];
          maxState = maxState(maxState, parentState);
          weightedQuickUnionUF.union(coordinates, neighbourCoordinates);
        }
      }
    }
    final int parentCoordinates = weightedQuickUnionUF.find(coordinates);
    states[coordinates] = maxState;
    states[parentCoordinates] = maxState;
    if (maxState == CONNECTED_BOTH) {
      percolates = true;
    }
  }

  /**
   * Returns true if given site is open.
   * Complexity: O(1)
   *
   * @param row 1-indexed row number
   * @param col 1-indexed column number
   * @return true if site is open
   * @throws IndexOutOfBoundsException when row or col is not between [1 and n]
   */
  public boolean isOpen(final int row, final int col) {
    validateRowAndCol(row, col);
    final byte parentState = states[xyTo1D(row, col)];
    return parentState != CLOSED;
  }

  /**
   * Returns true if given site is full (connected to top).
   * Complexity: O(log n) where n is the number of sites.
   *
   * @param row 1-indexed row number
   * @param col 1-indexed column number
   * @return true if site is open
   * @throws IndexOutOfBoundsException when row or col is not between [1 and n]
   */
  public boolean isFull(final int row, final int col) {
    validateRowAndCol(row, col);
    final int coordinates = xyTo1D(row, col);
    final byte parentState = states[weightedQuickUnionUF.find(coordinates)];
    return isConnectedToTop(parentState);
  }

  /**
   * Returns number of open sites.
   * Complexity: O(1).
   *
   * @return number of open sites
   */
  public int numberOfOpenSites() {
    return openCount;
  }

  /**
   * Returns true if given system percolates.
   * Complexity: O(1).
   *
   * @return true if system percolates
   */
  public boolean percolates() {
    return percolates;
  }

  /**
   * Returns maximum state.
   * States order: CONNECTED_BOTH > (CONNECTED_BOTTOM == CONNECTED_TOP) > OPEN > CLOSED
   * If one state is CONNECTED_TOP and second is CONNECTED_BOTTOM then CONNECTED_BOTH is returned.
   *
   * @param state0 state to check
   * @param state1 state to check
   * @return maximum state
   */
  static byte maxState(final byte state0, final byte state1) {
    return (byte) (state0 | state1);
  }

  /**
   * Return 1-D representation of grid coordinates.
   *
   * @param x row of the grid
   * @param y column of the grid
   * @return 1-D representation
   */
  int xyTo1D(final int x, final int y) {
    return (x - 1) * n + y - 1;
  }

  /**
   * Checks wheter coordinates moved by offset are valid.
   *
   * @param coordinates coordinates to check
   * @param offset      offset to moved by coordinates
   * @return true if coordinates are valid
   */
  private boolean coordinatesAreValid(final int coordinates, final int offset) {
    return coordinates + offset >= 0 && coordinates + offset < n * n && !(coordinates / n != (coordinates + offset)
        / n && coordinates % n != (coordinates + offset) % n);
  }

  /**
   * Checks whether site is connected to top (i.e. its state is either CONNECTED_BOTH or CONNECTED_TOP).
   *
   * @param state state of the site
   * @return true is site is connected to top
   */
  private boolean isConnectedToTop(final byte state) {
    return (state & CONNECTED_TOP) == CONNECTED_TOP;
  }

  /**
   * Validates coordinates.
   *
   * @param row 1-indexed row number
   * @param col 1-indexed column number
   * @throws IndexOutOfBoundsException when row or col is not between [1 and n]
   */
  private void validateRowAndCol(final int row, final int col) {
    if (row <= 0 || row > n || col <= 0 || col > n) {
      throw new IndexOutOfBoundsException("row and column should be between 1 and " + n);
    }
  }
}
