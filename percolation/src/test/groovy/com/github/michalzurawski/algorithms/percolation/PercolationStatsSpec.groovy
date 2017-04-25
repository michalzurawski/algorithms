package com.github.michalzurawski.algorithms.percolation

import spock.lang.Specification

class PercolationStatsSpec extends Specification {

  def "should compute mean value"() {
    when:
    double result = PercolationStats.mean((double[]) values.toArray())
    then:
    result == expected

    where:
    values               || expected
    [1.0]                || 1.0
    [1.0, 2.0, 3.0]      || 2.0
    [1.0, 2.0, 3.0, 4.0] || 2.5
  }

  def "should compute standard deviation"() {
    when:
    double result = PercolationStats.stddev((double[]) values.toArray(), mean)
    then:
    result == expected

    where:
    values          | mean || expected
    [1.0, 1.0, 1.0] | 1.0  || 0.0
    [1.0, 2.0, 3.0] | 2.0  || 1.0
    [1.0, 3.0, 5.0] | 3.0  || 2.0
  }

  def "should throw error when trials #trials or size #n is not positive"() {
    when:
    new PercolationStats(n, trials)
    then:
    thrown(IllegalArgumentException)

    where:
    n  | trials
    0  | 1
    -1 | 1
    1  | 0
    1  | -1
    0  | 0
    -1 | -1
  }

  def "should standard deviation be NaN for one trial"() {
    given:
    PercolationStats percolationStats = new PercolationStats(5, 1)
    when:
    double result = percolationStats.stddev()
    then:
    result == Double.NaN
  }
}
