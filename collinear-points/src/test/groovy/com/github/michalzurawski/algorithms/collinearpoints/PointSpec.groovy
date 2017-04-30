package com.github.michalzurawski.algorithms.collinearpoints

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class PointSpec extends Specification {
  def "should point #p0 equals #p1"() {
    expect:
    p0 == p1;

    where:
    p0              | p1
    new Point(0, 0) | new Point(0, 0)
    new Point(2, 3) | new Point(2, 3)
  }

  def "should point #p0 be less than #p1"() {
    expect:
    p0 < p1

    where:
    p0              | p1
    new Point(0, 0) | new Point(0, 1)
    new Point(1, 3) | new Point(2, 3)
    new Point(3, 3) | new Point(2, 4)
  }

  def "should point #p0 be greater than #p1"() {
    expect:
    p0 > p1

    where:
    p0              | p1
    new Point(0, 1) | new Point(0, 0)
    new Point(2, 3) | new Point(1, 3)
    new Point(2, 4) | new Point(3, 3)
  }

  def "should slope for horizontal points #p0 and #p1 be positive zero"() {
    expect:
    p0.slopeTo(p1) == 0

    where:
    p0               | p1
    new Point(1, 4)  | new Point(2, 4)
    new Point(1, -3) | new Point(-1, -3)
  }

  def "should slope for vertical points #p0 and #p1 be infinity"() {
    expect:
    p0.slopeTo(p1) == Double.POSITIVE_INFINITY

    where:
    p0               | p1
    new Point(2, 2)  | new Point(2, 4)
    new Point(-2, 4) | new Point(-2, -3)
  }

  def "should slope for equal points #p0 and #p1 be minus infinity"() {
    expect:
    p0.slopeTo(p1) == Double.NEGATIVE_INFINITY

    where:
    p0               | p1
    new Point(2, 2)  | new Point(2, 2)
    new Point(-2, 4) | new Point(-2, 4)
  }

  def "should slope for points #p0 and #p1 be #expected"() {
    expect:
    p0.slopeTo(p1).round(10) == expected

    where:
    p0               | p1                | expected
    new Point(0, 0)  | new Point(1, 1)   | 1.0
    new Point(1, 1)  | new Point(0, 0)   | 1.0
    new Point(1, 0)  | new Point(0, 1)   | -1.0
    new Point(0, 1)  | new Point(1, 0)   | -1.0
    new Point(3, -5) | new Point(-8, 13) | (13 - -5) / (-8 - 3)
  }
}
