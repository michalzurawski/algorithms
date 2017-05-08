package com.github.michalzurawski.algorithms.collinearpoints

import edu.princeton.cs.algs4.In
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class BruteCollinearPointsSpec extends Specification {
  def "should return 0 when no collinear points are given"() {
    given:
    Point[] points = [new Point(1, 1)]
    BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points)
    when:
    int result = bruteCollinearPoints.numberOfSegments()
    then:
    result == 0
  }

  def "should return empty line segment when no collinear points are given"() {
    given:
    Point[] points = [new Point(1, 1)]
    BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points)
    when:
    LineSegment[] result = bruteCollinearPoints.segments()
    then:
    result.length == 0
  }

  def "should compute number of four collinear points for file #fileName"() {
    given:
    Point[] points = createPoints(fileName)
    BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points)
    when:
    int result = bruteCollinearPoints.numberOfSegments()
    then:
    result == expected

    where:
    fileName          || expected
    "equidistant.txt" || 4
    "input8.txt"      || 2
  }

  def "should throw NullPointerException when creating with null array"() {
    when:
    new BruteCollinearPoints(null)
    then:
    thrown(NullPointerException)
  }

  def "should throw NullPointerException when one of the points is null"() {
    given:
    Point[] points = [new Point(1, 1), null]
    when:
    new BruteCollinearPoints(points)
    then:
    thrown(NullPointerException)
  }

  def "should throw IllegalArgumentException when two points are equal"() {
    given:
    Point[] points = [new Point(1, 1), new Point(0, 0), new Point(1, 1)]
    when:
    new BruteCollinearPoints(points)
    then:
    thrown(IllegalArgumentException)
  }

  private static Point[] createPoints(String fileName) {
    In input = new In(getClass().getResource('/' + fileName))
    int n = input.readInt()
    Point[] points = new Point[n]
    for (int i = 0; i < n; ++i) {
      int x = input.readInt()
      int y = input.readInt()
      points[i] = new Point(x, y)
    }
    return points
  }
}
