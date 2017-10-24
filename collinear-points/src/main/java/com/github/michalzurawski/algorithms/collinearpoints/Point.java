package com.github.michalzurawski.algorithms.collinearpoints;

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

/**
 * An immutable data type for points in the plane.
 */
public final class Point implements Comparable<Point> {

  /**
   * x-coordinate of this point.
   */
  private final int x;
  /**
   * y-coordinate of this point.
   */
  private final int y;

  /**
   * Initializes a new point.
   *
   * @param x the <em>x</em>-coordinate of the point
   * @param y the <em>y</em>-coordinate of the point
   */
  public Point(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Draws this point to standard draw.
   */
  void draw() {
    final double radius = 128;
    StdDraw.filledCircle(x, y, radius);
  }

  /**
   * Draws the line segment between this point and the specified point
   * to standard draw.
   *
   * @param that the other point
   */
  void drawTo(final Point that) {
    StdDraw.line(this.x, this.y, that.x, that.y);
  }

  /**
   * Returns the slope between this point and the specified point.
   * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
   * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
   * +0.0 if the line segment connecting the two points is horizontal;
   * Double.POSITIVE_INFINITY if the line segment is vertical;
   * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
   *
   * @param that the other point
   * @return the slope between this point and the specified point
   */
  public double slopeTo(final Point that) {
    if (x == that.x) {
      if (y == that.y) {
        return Double.NEGATIVE_INFINITY;
      }
      return Double.POSITIVE_INFINITY;
    }
    if (y == that.y) {
      return 0;
    }
    return (double) (that.y - y) / (that.x - x);
  }

  /**
   * Compares two points by the slope they make with this point.
   * The slope is defined as in the slopeTo() method.
   *
   * @return the Comparator that defines this ordering on points
   */
  Comparator<Point> slopeOrder() {
    return new PointsComparator();
  }


  /**
   * Returns a string representation of this point.
   * This method is provide for debugging;
   * your program should not rely on the format of the string representation.
   *
   * @return a string representation of this point
   */
  @Override
  public String toString() {
    return "(" + x + ", " + y + ")";
  }

  /**
   * Compares two points by y-coordinate, breaking ties by x-coordinate.
   * Formally, the invoking point (x0, y0) is less than the argument point
   * (x1, y1) if and only if either y0 &lt; y1 or if y0 = y1 and x0 &lt; x1.
   *
   * @param that the other point
   * @return the value <tt>0</tt> if this point is equal to the argument
   * point (x0 = x1 and y0 = y1);
   * a negative integer if this point is less than the argument
   * point; and a positive integer if this point is greater than the
   * argument point
   */
  @Override
  public int compareTo(final Point that) {
    if (y != that.y) {
      return y - that.y;
    }
    return x - that.x;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Point)) {
      return false;
    }
    final Point that = (Point) o;
    return x == that.x && y == that.y;
  }

  @Override
  public int hashCode() {
    int result = x;
    final int prime = 31;
    result = prime * result + y;
    return result;
  }

  /**
   * Compares two argument points by the slope they make with current object.
   */
  private class PointsComparator implements Comparator<Point> {

    @Override
    public int compare(final Point p0, final Point p1) {
      final double slope0 = slopeTo(p0);
      final double slope1 = slopeTo(p1);
      return Double.compare(slope0, slope1);
    }
  }
}
