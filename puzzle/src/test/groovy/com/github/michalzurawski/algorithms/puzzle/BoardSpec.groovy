package com.github.michalzurawski.algorithms.puzzle

import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class BoardSpec extends Specification {
  def "should compute the dimension for the board #board"() {
    expect:
    board.dimension() == expected

    where:
    board                                                   || expected
    new Board([[1, 2], [3, 0]] as int[][])                  || 2
    new Board([[2, 1], [3, 0]] as int[][])                  || 2
    new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || 3
  }

  def "should compute hamming function for the board #board"() {
    expect:
    board.hamming() == expected

    where:
    board                                                   || expected
    new Board([[1, 2], [3, 0]] as int[][])                  || 0
    new Board([[1, 2], [0, 3]] as int[][])                  || 1
    new Board([[1, 0], [3, 2]] as int[][])                  || 1
    new Board([[2, 1], [3, 0]] as int[][])                  || 2
    new Board([[0, 1], [3, 2]] as int[][])                  || 2
    new Board([[3, 1], [0, 2]] as int[][])                  || 3
    new Board([[3, 1], [2, 0]] as int[][])                  || 3
    new Board([[0, 3], [2, 1]] as int[][])                  || 3
    new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || 0
    new Board([[1, 2, 3], [4, 5, 6], [7, 0, 8]] as int[][]) || 1
    new Board([[1, 2, 3], [4, 5, 6], [0, 7, 8]] as int[][]) || 2
    new Board([[1, 2, 3], [4, 5, 6], [0, 8, 7]] as int[][]) || 1
    new Board([[0, 8, 7], [6, 5, 4], [3, 2, 1]] as int[][]) || 7
  }

  def "should compute manhattan function for the board #board"() {
    expect:
    board.manhattan() == expected

    where:
    board                                                   || expected
    new Board([[1, 2], [3, 0]] as int[][])                  || 0
    new Board([[1, 2], [0, 3]] as int[][])                  || 1
    new Board([[1, 0], [3, 2]] as int[][])                  || 1
    new Board([[2, 1], [3, 0]] as int[][])                  || 2
    new Board([[0, 1], [3, 2]] as int[][])                  || 2
    new Board([[3, 1], [0, 2]] as int[][])                  || 3
    new Board([[3, 1], [2, 0]] as int[][])                  || 4
    new Board([[0, 3], [2, 1]] as int[][])                  || 6
    new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || 0
    new Board([[1, 2, 3], [4, 5, 6], [7, 0, 8]] as int[][]) || 1
    new Board([[1, 2, 3], [4, 5, 6], [0, 7, 8]] as int[][]) || 2
    new Board([[0, 8, 7], [6, 5, 4], [3, 2, 1]] as int[][]) || 20
  }

  def "should check if it is goal board for the board #board"() {
    expect:
    board.isGoal() == expected

    where:
    board                                                   || expected
    new Board([[1, 2], [3, 0]] as int[][])                  || true
    new Board([[1, 2], [0, 3]] as int[][])                  || false
    new Board([[1, 0], [3, 2]] as int[][])                  || false
    new Board([[2, 1], [3, 0]] as int[][])                  || false
    new Board([[0, 1], [3, 2]] as int[][])                  || false
    new Board([[3, 1], [0, 2]] as int[][])                  || false
    new Board([[3, 1], [2, 0]] as int[][])                  || false
    new Board([[0, 3], [2, 1]] as int[][])                  || false
    new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || true
    new Board([[1, 2, 3], [4, 5, 6], [7, 0, 8]] as int[][]) || false
    new Board([[1, 2, 3], [4, 5, 6], [0, 7, 8]] as int[][]) || false
    new Board([[0, 8, 7], [6, 5, 4], [3, 2, 1]] as int[][]) || false
  }

  def "should board #board0 equals board #board1"() {
    expect:
    board0 == board1

    where:
    board0                                                  || board1
    new Board([[1, 2], [3, 0]] as int[][])                  || new Board([[1, 2], [3, 0]] as int[][])
    new Board([[2, 1], [3, 0]] as int[][])                  || new Board([[2, 1], [3, 0]] as int[][])
    new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][])
    new Board([[2, 1, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || new Board([[2, 1, 3], [4, 5, 6], [7, 8, 0]] as int[][])
  }

  def "should board #board0 not equals board #board1"() {
    expect:
    board0 != board1

    where:
    board0                                                  || board1
    new Board([[1, 2], [3, 0]] as int[][])                  || new Board([[1, 2], [0, 3]] as int[][])
    new Board([[1, 2], [3, 0]] as int[][])                  || new Board([[2, 1], [3, 0]] as int[][])
    new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || new Board([[1, 2, 3], [4, 5, 6], [7, 0, 8]] as int[][])
    new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || new Board([[2, 1, 3], [4, 5, 6], [7, 8, 0]] as int[][])
  }

  def "should compute the twin for the board #board"() {
    expect:
    board.twin() == expected

    where:
    board                                                   || expected
    new Board([[1, 2], [3, 0]] as int[][])                  || new Board([[2, 1], [3, 0]] as int[][])
    new Board([[1, 2], [0, 3]] as int[][])                  || new Board([[2, 1], [0, 3]] as int[][])
    new Board([[1, 0], [3, 2]] as int[][])                  || new Board([[1, 0], [2, 3]] as int[][])
    new Board([[2, 1], [3, 0]] as int[][])                  || new Board([[1, 2], [3, 0]] as int[][])
    new Board([[0, 1], [3, 2]] as int[][])                  || new Board([[0, 1], [2, 3]] as int[][])
    new Board([[3, 1], [0, 2]] as int[][])                  || new Board([[1, 3], [0, 2]] as int[][])
    new Board([[3, 1], [2, 0]] as int[][])                  || new Board([[1, 3], [2, 0]] as int[][])
    new Board([[0, 3], [2, 1]] as int[][])                  || new Board([[0, 3], [1, 2]] as int[][])
    new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || new Board([[2, 1, 3], [4, 5, 6], [7, 8, 0]] as int[][])
    new Board([[2, 1, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][])
    new Board([[0, 8, 7], [6, 5, 4], [3, 2, 1]] as int[][]) || new Board([[0, 8, 7], [6, 5, 4], [2, 3, 1]] as int[][])
    new Board([[8, 0, 7], [6, 5, 4], [3, 2, 1]] as int[][]) || new Board([[8, 0, 7], [6, 5, 4], [2, 3, 1]] as int[][])
  }

  def "should compute neighbours for the board #board"() {
    when:
    Iterable<Board> neighbours = board.neighbors()
    then:
    neighbours.size() == expected.size()
    expected.containsAll(neighbours)

    where:
    board                                                   || expected
    new Board([[1, 2], [3, 0]] as int[][])                  || [new Board([[1, 2], [0, 3]] as int[][]), new Board([[1, 0], [3, 2]] as int[][])]
    new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || [new Board([[1, 2, 3], [4, 5, 6], [7, 0, 8]] as int[][]), new Board([[1, 2, 3], [4, 5, 0], [7, 8, 6]] as int[][])]
    new Board([[1, 2, 3], [4, 5, 6], [7, 0, 8]] as int[][]) || [new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]), new Board([[1, 2, 3], [4, 5, 6], [0, 7, 8]] as int[][]), new Board([[1, 2, 3], [4, 0, 6], [7, 5, 8]] as int[][])]
    new Board([[1, 2, 3], [4, 0, 6], [7, 5, 8]] as int[][]) || [new Board([[1, 0, 3], [4, 2, 6], [7, 5, 8]] as int[][]), new Board([[1, 2, 3], [0, 4, 6], [7, 5, 8]] as int[][]), new Board([[1, 2, 3], [4, 6, 0], [7, 5, 8]] as int[][]), new Board([[1, 2, 3], [4, 5, 6], [7, 0, 8]] as int[][])]
  }
}
