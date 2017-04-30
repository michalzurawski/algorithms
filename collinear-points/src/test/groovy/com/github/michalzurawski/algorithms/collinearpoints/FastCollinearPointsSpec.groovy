package com.github.michalzurawski.algorithms.collinearpoints

import edu.princeton.cs.algs4.In
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class FastCollinearPointsSpec extends Specification {
  def "should return 0 when no collinear points are given"() {
    given:
    Point[] points = [new Point(1, 1)]
    FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points)
    when:
    int result = fastCollinearPoints.numberOfSegments()
    then:
    result == 0
  }

  def "should return empty line segment when no collinear points are given"() {
    given:
    Point[] points = [new Point(1, 1)]
    FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points)
    when:
    LineSegment[] result = fastCollinearPoints.segments()
    then:
    result.length == 0
  }

  def "should compute number of four collinear points for file #fileName"() {
    given:
    Point[] points = createPoints(fileName)
    FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points)
    when:
    int result = fastCollinearPoints.numberOfSegments()
    then:
    result == expected

    where:
    fileName            || expected
    "equidistant.txt"   || 4
    "grid4x4.txt"       || 16
    "grid5x5.txt"       || 22
    "grid6x6.txt"       || 44
    "horizontal5.txt"   || 5
    "horizontal25.txt"  || 25
    "horizontal50.txt"  || 50
    "horizontal75.txt"  || 75
    "horizontal100.txt" || 100
    "inarow.txt"        || 5
    "input1.txt"        || 0
    "input2.txt"        || 0
    "input3.txt"        || 0
    "input6.txt"        || 1
    "input8.txt"        || 2
    "input9.txt"        || 1
    "input10.txt"       || 2
    "input20.txt"       || 5
    "input40.txt"       || 4
    "input48.txt"       || 6
    "input50.txt"       || 7
    "input56.txt"       || 20
    "input80.txt"       || 31
    "input100.txt"      || 62
    "input150.txt"      || 1
    "input200.txt"      || 4
    "input250.txt"      || 1
    "input299.txt"      || 6
    "input350.txt"      || 4
    "input400.txt"      || 7
    "input1000.txt"     || 0
    "input2000.txt"     || 0
    "input3000.txt"     || 0
    "input4000.txt"     || 3
    "input5000.txt"     || 2
    "input6000.txt"     || 2
    "input8000.txt"     || 15
    "input10000.txt"    || 35
    "kw1260.txt"        || 288
    "mystery10089.txt"  || 34
    "random23.txt"      || 0
    "random38.txt"      || 0
    "random91.txt"      || 0
    "rs1423.txt"        || 443
    "vertical5.txt"     || 5
    "vertical25.txt"    || 25
    "vertical50.txt"    || 50
    "vertical75.txt"    || 75
    "vertical100.txt"   || 100
  }

  def "should compute line segments of four collinear points for file #fileName"() {
    given:
    Point[] points = createPoints(fileName)
    FastCollinearPoints fastCollinearPoints = new FastCollinearPoints(points)
    when:
    LineSegment[] result = fastCollinearPoints.segments()
    then:
    result == expected

    where:
    fileName            || expected
    "input1.txt"        || []
    "input2.txt"        || []
    "input3.txt"        || []
    "input6.txt"        || [new LineSegment(new Point(14000, 10000), new Point(32000, 10000))]
    "input8.txt"        || [new LineSegment(new Point(10000, 0), new Point(0, 10000)), new LineSegment(new Point(3000, 4000), new Point(20000, 21000))]
    "input9.txt"        || [new LineSegment(new Point(1000, 1000), new Point(9000, 9000))]
    "input10.txt"       || [new LineSegment(new Point(28000, 13500), new Point(3000, 26000)), new LineSegment(new Point(1000, 18000), new Point(4000, 30000))]
    "input40.txt"       || [new LineSegment(new Point(1000, 17000), new Point(29000, 17000)), new LineSegment(new Point(1000, 17000), new Point(1000, 31000)), new LineSegment(new Point(2000, 24000), new Point(25000, 24000)), new LineSegment(new Point(2000, 29000), new Point(28000, 29000))]
    "input150.txt"      || [new LineSegment(new Point(29700, 1200), new Point(6200, 15300))]
    "input200.txt"      || [new LineSegment(new Point(14600, 2500), new Point(28600, 16500)), new LineSegment(new Point(10400, 6900), new Point(10400, 25800)), new LineSegment(new Point(10700, 9000), new Point(22400, 20700)), new LineSegment(new Point(24100, 20700), new Point(1900, 28100))]
    "input250.txt"      || [new LineSegment(new Point(7050, 6250), new Point(30200, 29400))]
    "input350.txt"      || [new LineSegment(new Point(29700, 0), new Point(3950, 25750)), new LineSegment(new Point(1300, 650), new Point(28600, 14300)), new LineSegment(new Point(4450, 4050), new Point(4450, 31000)), new LineSegment(new Point(16850, 12850), new Point(16850, 27750))]
    "input1000.txt"     || []
    "input2000.txt"     || []
    "input3000.txt"     || []
    "input4000.txt"     || [new LineSegment(new Point(13778, 4867), new Point(7682, 29251)), new LineSegment(new Point(28934, 9864), new Point(12536, 15330)), new LineSegment(new Point(3100, 10917), new Point(3100, 29341))]
    "input5000.txt"     || [new LineSegment(new Point(29647, 11934), new Point(4991, 24262)), new LineSegment(new Point(19366, 12669), new Point(28450, 20239))]
    "input6000.txt"     || [new LineSegment(new Point(27999, 522), new Point(27999, 28291)), new LineSegment(new Point(8838, 11620), new Point(20147, 22929))]
    "random23.txt"      || []
    "random38.txt"      || []
    "random91.txt"      || []
  }
  def "should throw NullPointerException when creating with null array"() {
    when:
    new FastCollinearPoints(null)
    then:
    thrown(NullPointerException)
  }

  def "should throw NullPointerException when one of the points is null"() {
    given:
    Point[] points = [new Point(1, 1), null]
    when:
    new FastCollinearPoints(points)
    then:
    thrown(NullPointerException)
  }

  def "should throw IllegalArgumentException when two points are equal"() {
    given:
    Point[] points = [new Point(1, 1), new Point(0, 0), new Point(1, 1)]
    when:
    new FastCollinearPoints(points)
    then:
    thrown(IllegalArgumentException)
  }

  private static Point[] createPoints(String fileName) {
    In input = new In("src/test/resources/" + fileName)
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
