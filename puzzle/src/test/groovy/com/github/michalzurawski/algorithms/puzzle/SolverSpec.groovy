package com.github.michalzurawski.algorithms.puzzle

import edu.princeton.cs.algs4.In
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class SolverSpec extends Specification {
  def "should predict if board #board is solvable"() {
    given:
    Solver solver = new Solver(board)
    when:
    boolean solvable = solver.isSolvable()
    then:
    solvable == expected

    where:
    board                                                   || expected
    new Board([[1, 2], [3, 0]] as int[][])                  || true
    new Board([[2, 1], [3, 0]] as int[][])                  || false
    new Board([[0, 3], [2, 1]] as int[][])                  || true
    new Board([[0, 1], [2, 3]] as int[][])                  || false
    new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || true
    new Board([[2, 1, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || false
    new Board([[0, 8, 7], [6, 5, 4], [3, 2, 1]] as int[][]) || true
    new Board([[0, 8, 4], [6, 5, 7], [3, 2, 1]] as int[][]) || false
  }

  def "should compute minimum number of moves for board #board"() {
    given:
    Solver solver = new Solver(board)
    when:
    int moves = solver.moves()
    then:
    moves == expected

    where:
    board                                                   || expected
    new Board([[1, 2], [3, 0]] as int[][])                  || 0
    new Board([[0, 3], [2, 1]] as int[][])                  || 6
    new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || 0
    new Board([[0, 8, 7], [6, 5, 4], [3, 2, 1]] as int[][]) || 28
  }

  def "should minimum return -1 for unsolvable board #board"() {
    given:
    Solver solver = new Solver(board)
    when:
    int moves = solver.moves()
    then:
    moves == expected

    where:
    board                                                   || expected
    new Board([[2, 1], [3, 0]] as int[][])                  || -1
    new Board([[0, 1], [2, 3]] as int[][])                  || -1
    new Board([[2, 1, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || -1
    new Board([[0, 8, 4], [6, 5, 7], [3, 2, 1]] as int[][]) || -1
  }

  def "should provide correct solution for board #board"() {
    given:
    Solver solver = new Solver(board)
    when:
    Iterable<Board> moves = solver.solution()
    then:
    moves == expected

    where:
    board                                                   || expected
    new Board([[1, 2], [3, 0]] as int[][])                  || [new Board([[1, 2], [3, 0]] as int[][])]
    new Board([[0, 3], [2, 1]] as int[][])                  || [new Board([[0, 3], [2, 1]] as int[][]), new Board([[2, 3], [0, 1]] as int[][]), new Board([[2, 3], [1, 0]] as int[][]), new Board([[2, 0], [1, 3]] as int[][]), new Board([[0, 2], [1, 3]] as int[][]), new Board([[1, 2], [0, 3]] as int[][]), new Board([[1, 2], [3, 0]] as int[][])]
    new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || [new Board([[1, 2, 3], [4, 5, 6], [7, 8, 0]] as int[][])]
  }

  def "should solution return null for unsolvable board #board"() {
    given:
    Solver solver = new Solver(board)
    when:
    Iterable<Board> moves = solver.solution()
    then:
    moves == expected

    where:
    board                                                   || expected
    new Board([[2, 1], [3, 0]] as int[][])                  || null
    new Board([[0, 1], [2, 3]] as int[][])                  || null
    new Board([[2, 1, 3], [4, 5, 6], [7, 8, 0]] as int[][]) || null
    new Board([[0, 8, 4], [6, 5, 7], [3, 2, 1]] as int[][]) || null
  }

  def "should find shortest solution for correct board for file #fileName"() {
    given:
    Board board = createBoardFromFile(fileName)
    Solver solver = new Solver(board)
    when:
    int moves = solver.moves()
    then:
    moves == expected

    where:
    fileName           || expected
    "puzzle00.txt"     || 0
    "puzzle01.txt"     || 1
    "puzzle02.txt"     || 2
    "puzzle2x2-00.txt" || 0
    "puzzle2x2-01.txt" || 1
    "puzzle2x2-02.txt" || 2
    "puzzle2x2-03.txt" || 3
    "puzzle2x2-04.txt" || 4
    "puzzle2x2-05.txt" || 5
    "puzzle2x2-06.txt" || 6
    "puzzle03.txt"     || 3
    "puzzle3x3-00.txt" || 0
    "puzzle3x3-01.txt" || 1
    "puzzle3x3-02.txt" || 2
    "puzzle3x3-03.txt" || 3
    "puzzle3x3-04.txt" || 4
    "puzzle3x3-05.txt" || 5
    "puzzle3x3-06.txt" || 6
    "puzzle3x3-07.txt" || 7
    "puzzle3x3-08.txt" || 8
    "puzzle3x3-09.txt" || 9
    "puzzle3x3-10.txt" || 10
    "puzzle3x3-11.txt" || 11
    "puzzle3x3-12.txt" || 12
    "puzzle3x3-13.txt" || 13
    "puzzle3x3-14.txt" || 14
    "puzzle3x3-15.txt" || 15
    "puzzle3x3-16.txt" || 16
    "puzzle3x3-17.txt" || 17
    "puzzle3x3-18.txt" || 18
    "puzzle3x3-19.txt" || 19
    "puzzle3x3-20.txt" || 20
    "puzzle3x3-21.txt" || 21
    "puzzle3x3-22.txt" || 22
    "puzzle3x3-23.txt" || 23
    "puzzle3x3-24.txt" || 24
    "puzzle3x3-25.txt" || 25
    "puzzle3x3-26.txt" || 26
    "puzzle3x3-27.txt" || 27
    "puzzle3x3-28.txt" || 28
    "puzzle3x3-29.txt" || 29
    "puzzle3x3-30.txt" || 30
    "puzzle04.txt"     || 4
    "puzzle4x4-00.txt" || 0
    "puzzle4x4-01.txt" || 1
    "puzzle4x4-02.txt" || 2
    "puzzle4x4-03.txt" || 3
    "puzzle4x4-04.txt" || 4
    "puzzle4x4-05.txt" || 5
    "puzzle4x4-06.txt" || 6
    "puzzle4x4-07.txt" || 7
    "puzzle4x4-08.txt" || 8
    "puzzle4x4-09.txt" || 9
    "puzzle4x4-10.txt" || 10
    "puzzle4x4-11.txt" || 11
    "puzzle4x4-12.txt" || 12
    "puzzle4x4-13.txt" || 13
    "puzzle4x4-14.txt" || 14
    "puzzle4x4-15.txt" || 15
    "puzzle4x4-16.txt" || 16
    "puzzle4x4-17.txt" || 17
    "puzzle4x4-18.txt" || 18
    "puzzle4x4-19.txt" || 19
    "puzzle4x4-20.txt" || 20
    "puzzle4x4-21.txt" || 21
    "puzzle4x4-22.txt" || 22
    "puzzle4x4-23.txt" || 23
    "puzzle4x4-24.txt" || 24
    "puzzle4x4-25.txt" || 25
    "puzzle4x4-26.txt" || 26
    "puzzle4x4-27.txt" || 27
    "puzzle4x4-28.txt" || 28
    "puzzle4x4-29.txt" || 29
    "puzzle4x4-30.txt" || 30
    "puzzle05.txt"     || 5
    "puzzle06.txt"     || 6
    "puzzle07.txt"     || 7
    "puzzle08.txt"     || 8
    "puzzle09.txt"     || 9
    "puzzle10.txt"     || 10
    "puzzle11.txt"     || 11
    "puzzle12.txt"     || 12
    "puzzle13.txt"     || 13
    "puzzle14.txt"     || 14
    "puzzle15.txt"     || 15
    "puzzle16.txt"     || 16
    "puzzle17.txt"     || 17
    "puzzle18.txt"     || 18
    "puzzle19.txt"     || 19
    "puzzle20.txt"     || 20
    "puzzle21.txt"     || 21
    "puzzle22.txt"     || 22
    "puzzle23.txt"     || 23
    "puzzle24.txt"     || 24
    "puzzle25.txt"     || 25
    "puzzle26.txt"     || 26
    "puzzle27.txt"     || 27
    "puzzle28.txt"     || 28
    "puzzle29.txt"     || 29
    "puzzle30.txt"     || 30
  }

  def "should detect unsolvable board for file #fileName"() {
    given:
    Board board = createBoardFromFile(fileName)
    Solver solver = new Solver(board)
    expect:
    !solver.isSolvable()

    where:
    fileName << ["puzzle2x2-unsolvable1.txt", "puzzle2x2-unsolvable2.txt", "puzzle2x2-unsolvable3.txt",
                 "puzzle3x3-unsolvable.txt", "puzzle3x3-unsolvable1.txt", "puzzle3x3-unsolvable2.txt",
                 "puzzle3x3-unsolvable.txt"]
  }

  private static Board createBoardFromFile(String fileName) {
    In input = new In(getClass().getResource('/' + fileName));
    int n = input.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocks[i][j] = input.readInt();
      }
    }
    new Board(blocks);
  }
}
