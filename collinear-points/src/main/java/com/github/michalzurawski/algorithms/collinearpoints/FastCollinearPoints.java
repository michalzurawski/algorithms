package com.github.michalzurawski.algorithms.collinearpoints;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Fast algorithm for finding 4 collinear points on a plane.
 * Complexity: O(n^2 log n) where n is the number of points.
 */
public class FastCollinearPoints {
  /**
   * Line segments containing all 4 collinear points.
   */
  private final LineSegment[] lineSegments;

  /**
   * Finds all line segments containing 4 collinear points.
   * Complexity: O(n^2 lg n) where n is the number of points.
   *
   * @param points Points to check.
   */
  public FastCollinearPoints(final Point[] points) {
    final Point[] pointsCopy = Arrays.copyOf(points, points.length);
    Arrays.sort(pointsCopy);
    final int n = pointsCopy.length;
    for (int i = 1; i < n; ++i) {
      if (pointsCopy[i].compareTo(pointsCopy[i - 1]) == 0) {
        throw new IllegalArgumentException();
      }
    }
    final Collection<LineSegment> segments = new ArrayList<>();

    for (final Point point : pointsCopy) {
      final Point[] sortedPoints = new Point[n];
      System.arraycopy(pointsCopy, 0, sortedPoints, 0, n);
      Arrays.sort(sortedPoints, point.slopeOrder());
      for (int j = 1; j < n;) {
        int index = j;
        final List<Point> collinearPoints = new ArrayList<>();
        collinearPoints.add(sortedPoints[j - 1]);
        while (index < n && point.slopeTo(sortedPoints[index]) == point.slopeTo(sortedPoints[j - 1])) {
          collinearPoints.add(sortedPoints[index]);
          ++index;
        }
        if (collinearPoints.size() > 2) {
          j += collinearPoints.size() - 1;
          Collections.sort(collinearPoints);
          final Point first = collinearPoints.get(0);
          if (point.compareTo(first) > 0) {
            continue;
          }
          final Point last = collinearPoints.get(collinearPoints.size() - 1);
          final LineSegment lineSegment = new LineSegment(point, last);
          segments.add(lineSegment);
        } else {
          ++j;
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
    StdDraw.setPenColor(Color.RED);
    for (final Point p : points) {
      p.draw();
    }
    StdDraw.setPenColor(Color.BLACK);
    StdDraw.show();

    // print and draw the line segments
    final FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (final LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
  }
}
