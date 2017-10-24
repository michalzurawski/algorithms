package com.github.michalzurawski.algorithms.collinearpoints;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;

/**
 * An immutable data type for Line segments in the plane.
 */
public final class LineSegment {
  /**
   * One endpoint of this line segment.
   */
  private final Point p;
  /**
   * The other endpoint of this line segment.
   */
  private final Point q;

  /**
   * Initializes a new line segment.
   *
   * @param from one endpoint
   * @param to   the other endpoint
   * @throws NullPointerException if either <tt>p</tt> or <tt>q</tt>
   *                              is <tt>null</tt>
   */
  LineSegment(final Point from, final Point to) {
    if (from == null || to == null) {
      throw new NullPointerException("argument is null");
    }
    this.p = from;
    this.q = to;
  }


  /**
   * Draws this line segment to standard draw.
   */
  void draw() {
    StdDraw.setPenColor(Color.GREEN);
    p.draw();
    q.draw();
    StdDraw.setPenColor(Color.BLACK);
    p.drawTo(q);
  }

  /**
   * Returns a string representation of this line segment
   * This method is provide for debugging;
   * your program should not rely on the format of the string representation.
   *
   * @return a string representation of this line segment
   */
  @Override
  public String toString() {
    return p + " -> " + q;
  }

  /**
   * Throws an exception if called. The hashCode() method is not supported because
   * hashing has not yet been introduced in this course. Moreover, hashing does not
   * typically lead to good *worst-case* performance guarantees, as required on this
   * assignment.
   *
   * @return Unsupported operation
   * @throws UnsupportedOperationException if called
   */
  @Override
  public int hashCode() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final LineSegment that = (LineSegment) o;
    return p.equals(that.p) && q.equals(that.q);
  }
}

