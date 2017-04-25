package com.github.michalzurawski.algorithms.percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

  static final byte CLOSED = 0;
  static final byte OPEN = 1;
  static final byte CONNECTED_TOP = maxState(OPEN, (byte) 2);
  static final byte CONNECTED_BOTTOM = maxState(OPEN, (byte) 4);
  static final byte CONNECTED_BOTH = maxState(CONNECTED_TOP, CONNECTED_BOTTOM);

  private final WeightedQuickUnionUF weightedQuickUnionUF;
  private final byte[] states;
  private final int n;
  private boolean percolates = false;
  private int openCount = 0;

  // create n-by-n grid, with all sites blocked
  public Percolation(int n) {
    if (n <= 0) {
      throw new IllegalArgumentException("grid size should be greater than 0");
    }
    weightedQuickUnionUF = new WeightedQuickUnionUF(n * n);
    this.n = n;
    states = new byte[n * n];
  }

  // open site (row, col) if it is not open already
  public void open(int row, int col) {
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

  // is site (row, col) open?
  public boolean isOpen(int row, int col) {
    validateRowAndCol(row, col);
    final byte parentState = states[xyTo1D(row, col)];
    return parentState != CLOSED;
  }

  // is site (row, col) full?
  public boolean isFull(int row, int col) {
    validateRowAndCol(row, col);
    final int coordinates = xyTo1D(row, col);
    final byte parentState = states[weightedQuickUnionUF.find(coordinates)];
    return isConnectedToTop(parentState);
  }

  // number of open sites
  public int numberOfOpenSites() {
    return openCount;
  }

  // does the system percolate?
  public boolean percolates() {
    return percolates;
  }

  static byte maxState(byte state0, byte state1) {
    return (byte) (state0 | state1);
  }

  int xyTo1D(int x, int y) {
    return (x - 1) * n + y - 1;
  }

  private boolean coordinatesAreValid(int coordinates, int offset) {
    return coordinates + offset >= 0 && coordinates + offset < n * n &&
        !(coordinates / n != (coordinates + offset) / n && coordinates % n != (coordinates + offset) % n);
  }

  private boolean isConnectedToTop(byte state) {
    return (state & CONNECTED_TOP) == CONNECTED_TOP;
  }
  private void validateRowAndCol(int row, int col) {
    if (row <= 0 || row > n || col <= 0 || col > n) {
      throw new IndexOutOfBoundsException("row and column should be between 1 and " + n);
    }
  }
}