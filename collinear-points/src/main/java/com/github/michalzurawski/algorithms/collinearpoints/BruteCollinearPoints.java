package com.github.michalzurawski.algorithms.collinearpoints;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Brute force algorithm for finding 4 collinear points on a plane.
 * Complexity: O(n^4) where n is the number of points.
 */
public class BruteCollinearPoints {
  /**
   * Line segments containing all 4 collinear points.
   */
  private final LineSegment[] lineSegments;

  /**
   * Finds all line segments containing 4 collinear points.
   * Complexity: O(n^4) where n is the number of points.
   *
   * @param points Points to check.
   */
  public BruteCollinearPoints(final Point[] points) {
    final Point[] pointsCopy = Arrays.copyOf(points, points.length);
    Arrays.sort(pointsCopy);
    final int n = pointsCopy.length;
    for (int i = 1; i < n; ++i) {
      if (pointsCopy[i].compareTo(pointsCopy[i - 1]) == 0) {
        throw new IllegalArgumentException();
      }
    }
    final ArrayList<LineSegment> segments = new ArrayList<>();
    for (int i = 0; i < n; ++i) {
      final Point p = pointsCopy[i];
      for (int j = i + 1; j < n; ++j) {
        final Point q = pointsCopy[j];
        final double slopeQ = p.slopeTo(q);
        for (int k = j + 1; k < n; ++k) {
          final Point r = pointsCopy[k];
          final double slopeR = p.slopeTo(r);
          if (slopeQ != slopeR) {
            continue;
          }
          for (int m = k + 1; m < n; ++m) {
            final Point s = pointsCopy[m];
            final double slopeS = p.slopeTo(s);
            if (slopeQ == slopeS) {
              segments.add(new LineSegment(p, s));
            }
          }
        }
      }
    }
    lineSegments = new LineSegment[segments.size()];
    segments.toArray(lineSegments);
  }

  /**
   * Returns the number of line segments containing 4 collinear points.
   * Complexity: O(1)
   *
   * @return the number of line segments containing 4 collinear points
   */
  public int numberOfSegments() {
    return lineSegments.length;
  }

  /**
   * Returns all line segments containing 4 collinear points.
   * Complexity: O(1)
   *
   * @return line segments containing 4 collinear points
   */
  public LineSegment[] segments() {
    return Arrays.copyOf(lineSegments, lineSegments.length);
  }

  /**
   * Finds all 4th collinear points on the two-dimensional plane based on the input from file.
   *
   * @param args Args[0] name of the file
   */
  public static void main(final String[] args) {

    // read the n points from a file
    final In in = new In(args[0]);
    final int n = in.readInt();
    final Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      final int x = in.readInt();
      final int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    final int screenSize = 32768;
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, screenSize);
    StdDraw.setYscale(0, screenSize);
    for (final Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    final BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (final LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
