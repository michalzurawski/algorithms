package com.github.michalzurawski.algorithms.percolation

import edu.princeton.cs.algs4.In
import spock.lang.Specification
import spock.lang.Unroll

import static com.github.michalzurawski.algorithms.percolation.Percolation.CLOSED
import static com.github.michalzurawski.algorithms.percolation.Percolation.CONNECTED_BOTH
import static com.github.michalzurawski.algorithms.percolation.Percolation.CONNECTED_BOTTOM
import static com.github.michalzurawski.algorithms.percolation.Percolation.CONNECTED_TOP
import static com.github.michalzurawski.algorithms.percolation.Percolation.OPEN;

@Unroll
class PercolationSpec extends Specification {

  def "should not contain open sites at the beginning"() {
    given:
    Percolation percolation = new Percolation(2)
    expect:
    percolation.numberOfOpenSites() == 0
    !percolation.isOpen(1, 1)
    !percolation.isOpen(2, 1)
    !percolation.isOpen(1, 2)
    !percolation.isOpen(2, 2)
  }

  def "should open the site"() {
    given:
    Percolation percolation = new Percolation(2)
    when:
    percolation.open(1, 1)
    percolation.open(1, 2)
    percolation.open(2, 1)
    then:
    percolation.numberOfOpenSites() == 3
    percolation.isOpen(1, 1)
    percolation.isOpen(2, 1)
    percolation.isOpen(1, 2)
    !percolation.isOpen(2, 2)
  }

  def "should be full when open at top"() {
    given:
    Percolation percolation = new Percolation(3)
    when:
    percolation.open(1, 1)
    percolation.open(1, 3)
    then:
    percolation.isFull(1, 1)
    percolation.isFull(1, 3)
  }

  def "should be full when connected to top"() {
    given:
    Percolation percolation = new Percolation(3)
    when:
    percolation.open(1, 2)
    percolation.open(2, 2)
    percolation.open(2, 1)
    percolation.open(3, 1)
    then:
    percolation.isFull(1, 2)
    percolation.isFull(2, 2)
    percolation.isFull(2, 1)
    percolation.isFull(3, 1)
  }

  def "should not contain backwash"() {
    given:
    Percolation percolation = new Percolation(3)
    when:
    percolation.open(1, 2)
    percolation.open(2, 2)
    percolation.open(2, 1)
    percolation.open(3, 1)
    percolation.open(3, 3)
    then:
    !percolation.isFull(3, 3)
  }

  def "should percolate when all sites are opens"() {
    given:
    Percolation percolation = new Percolation(2)
    when:
    percolation.open(1, 1)
    percolation.open(1, 2)
    percolation.open(2, 1)
    percolation.open(2, 2)
    then:
    percolation.percolates()
  }

  def "should not percolate when all sites are closed"() {
    given:
    Percolation percolation = new Percolation(2)
    expect:
    !percolation.percolates()
  }

  def "should not percolate when adjacent sites are opens"() {
    given:
    Percolation percolation = new Percolation(2)
    when:
    percolation.open(1, 1)
    percolation.open(2, 2)
    then:
    !percolation.percolates()
  }

  def "should percolate for file #fileName"() {
    given:
    In input = new In(getClass().getResource('/' + fileName))
    Percolation percolation = new Percolation(input.readInt())
    when:
    while (!input.isEmpty()) {
      int i = input.readInt();
      int j = input.readInt();
      percolation.open(i, j);
    }
    then:
    percolation.percolates()

    where:
    fileName << ["input1.txt", "input2.txt", "input3.txt", "input4.txt", "input5.txt", "input6.txt",
                 "input7.txt", "input8.txt", "input10.txt", "input20.txt", "input50.txt", "jerry47.txt",
                 "sedgewick60.txt", "wayne98.txt"]
  }

  def "should not percolate for file #fileName"() {
    given:
    In input = new In("src/test/resources/" + fileName)
    Percolation percolation = new Percolation(input.readInt())
    when:
    while (!input.isEmpty()) {
      int i = input.readInt();
      int j = input.readInt();
      percolation.open(i, j);
    }
    then:
    !percolation.percolates()

    where:
    fileName << ["input1-no.txt", "input2-no.txt", "input8-no.txt", "input10-no.txt", "greeting57.txt", "heart25.txt"]
  }

  def "should throw an error when open(#a, #b) is outside the range (1..#n)"() {
    given:
    Percolation percolation = new Percolation(n)
    when:
    percolation.open(a, b)
    then:
    thrown(IndexOutOfBoundsException)

    where:
    a  | b  | n
    -1 | 1  | 2
    1  | -1 | 2
    0  | 1  | 2
    1  | 0  | 2
    3  | 1  | 2
    1  | 3  | 2
  }

  def "should throw an error when isFull(#a, #b) is outside the range (1..#n)"() {
    given:
    Percolation percolation = new Percolation(n)
    when:
    percolation.isFull(a, b)
    then:
    thrown(IndexOutOfBoundsException)

    where:
    a  | b  | n
    -1 | 1  | 2
    1  | -1 | 2
    0  | 1  | 2
    1  | 0  | 2
    3  | 1  | 2
    1  | 3  | 2
  }

  def "should throw an error when isOpen(#a, #b) is outside the range (1..#n)"() {
    given:
    Percolation percolation = new Percolation(n)
    when:
    percolation.isOpen(a, b)
    then:
    thrown(IndexOutOfBoundsException)

    where:
    a  | b  | n
    -1 | 1  | 2
    1  | -1 | 2
    0  | 1  | 2
    1  | 0  | 2
    3  | 1  | 2
    1  | 3  | 2
  }

  def "should throw an error when creating percolation with size less or equal zero #n"() {
    when:
    new Percolation(n)
    then:
    thrown(IllegalArgumentException)

    where:
    n << [0, -1]
  }

  def "should calculate max state for states #state0 and #state1"() {
    when:
    byte state = Percolation.maxState((byte) state0, (byte) state1)
    then:
    state == expected

    where:
    state0           | state1           | expected
    CLOSED           | CLOSED           | CLOSED
    CLOSED           | OPEN             | OPEN
    CLOSED           | CONNECTED_BOTTOM | CONNECTED_BOTTOM
    CLOSED           | CONNECTED_TOP    | CONNECTED_TOP
    CLOSED           | CONNECTED_BOTH   | CONNECTED_BOTH
    OPEN             | CLOSED           | OPEN
    OPEN             | OPEN             | OPEN
    OPEN             | CONNECTED_BOTTOM | CONNECTED_BOTTOM
    OPEN             | CONNECTED_TOP    | CONNECTED_TOP
    OPEN             | CONNECTED_BOTH   | CONNECTED_BOTH
    CONNECTED_TOP    | CLOSED           | CONNECTED_TOP
    CONNECTED_TOP    | OPEN             | CONNECTED_TOP
    CONNECTED_TOP    | CONNECTED_TOP    | CONNECTED_TOP
    CONNECTED_TOP    | CONNECTED_BOTTOM | CONNECTED_BOTH
    CONNECTED_TOP    | CONNECTED_BOTH   | CONNECTED_BOTH
    CONNECTED_BOTTOM | CLOSED           | CONNECTED_BOTTOM
    CONNECTED_BOTTOM | OPEN             | CONNECTED_BOTTOM
    CONNECTED_BOTTOM | CONNECTED_TOP    | CONNECTED_BOTH
    CONNECTED_BOTTOM | CONNECTED_BOTTOM | CONNECTED_BOTTOM
    CONNECTED_BOTTOM | CONNECTED_BOTH   | CONNECTED_BOTH
    CONNECTED_BOTH   | CLOSED           | CONNECTED_BOTH
    CONNECTED_BOTH   | OPEN             | CONNECTED_BOTH
    CONNECTED_BOTH   | CONNECTED_TOP    | CONNECTED_BOTH
    CONNECTED_BOTH   | CONNECTED_TOP    | CONNECTED_BOTH
    CONNECTED_BOTH   | CONNECTED_BOTH   | CONNECTED_BOTH
  }

  def "should calculate 1D dimension for row #row and column #column size #n"() {
    given:
    Percolation percolation = new Percolation(n)
    when:
    int coordinate = percolation.xyTo1D(row, col)
    then:
    coordinate == expected

    where:
    n | row | col | expected
    1 | 1   | 1   | 0
    2 | 1   | 1   | 0
    2 | 1   | 2   | 1
    2 | 2   | 1   | 2
    2 | 2   | 2   | 3
  }
}