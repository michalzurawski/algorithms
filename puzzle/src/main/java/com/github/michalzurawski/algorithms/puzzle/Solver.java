package com.github.michalzurawski.algorithms.puzzle;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class responsible for solving the puzzle.
 * Uses A* algorithm to find the solution in minimum possible moves.
 * Tries to solve given board and its twin (board obtained by swapping any two, adjacent, non-empty blocks)
 * simultaneously to check if board is solvable.
 */
public class Solver {
  /**
   * Sequence of boards in the shortest solution.
   */
  private final Stack<Board> solution;

  /**
   * Finds a solution to the given board.
   *
   * @param board board to find the solution
   */
  public Solver(final Board board) {
    final Board twinBoard = board.twin();
    final MinPQ<BoardScore> minPQ = new MinPQ<>();
    final MinPQ<BoardScore> twinMinPQ = new MinPQ<>();
    minPQ.insert(new BoardScore(0, board, null));
    twinMinPQ.insert(new BoardScore(0, twinBoard, null));
    BoardScore finalScore = null;

    while (true) {
      final BoardScore boardScore = minPQ.delMin();
      if (boardScore.board.isGoal()) {
        finalScore = boardScore;
        break;
      }
      for (final Board neighbor : boardScore.board.neighbors()) {
        if (boardScore.parent == null || !neighbor.equals(boardScore.parent.board)) {
          minPQ.insert(new BoardScore(boardScore.steps + 1, neighbor, boardScore));
        }
      }

      final BoardScore twinBoardScore = twinMinPQ.delMin();
      if (twinBoardScore.board.isGoal()) {
        break;
      }
      for (final Board neighbor : twinBoardScore.board.neighbors()) {
        if (twinBoardScore.parent == null || !neighbor.equals(twinBoardScore.parent.board)) {
          twinMinPQ.insert(new BoardScore(twinBoardScore.steps + 1, neighbor, twinBoardScore));
        }
      }
    }
    this.solution = restoreSolution(finalScore);
  }

  /**
   * Returns true if board is solvable.
   *
   * @return true if solvable
   */
  public boolean isSolvable() {
    return !solution.isEmpty();
  }

  /**
   * Returns minimum number of moves to solve the puzzle or <i>-1</i> if unsolvable.
   *
   * @return minimum number of moves
   */
  public int moves() {
    if (!isSolvable()) {
      return -1;
    }
    return solution.size() - 1;
  }

  /**
   * Sequence of boards in the shortest solution or <i>null</i> if unsolvable.
   *
   * @return sequence of boards
   */
  public Iterable<Board> solution() {
    if (!isSolvable()) {
      return null;
    }
    final Collection<Board> boards = new ArrayList<>(solution.size());
    for (final Board board : solution) {
      boards.add(board);
    }
    return boards;
  }

  /**
   * Restores solution from given board score.
   *
   * @param boardScore board score from which solution is restored
   * @return restored solution
   */
  private Stack<Board> restoreSolution(final BoardScore boardScore) {
    final Stack<Board> stack = new Stack<>();
    BoardScore current = boardScore;
    while (current != null) {
      stack.push(current.board);
      current = current.parent;
    }
    return stack;
  }

  /**
   * Uses manhattan heuristic function plus number of steps so far.
   */
  private static final class BoardScore implements Comparable<BoardScore> {
    /**
     * Heuristic score.
     */
    private final int score;
    /**
     * Steps made so far.
     */
    private final int steps;
    /**
     * Current board.
     */
    private final Board board;
    /**
     * Previous step board score.
     */
    private final BoardScore parent;

    /**
     * @param steps  steps made so far
     * @param board  board used to compute score
     * @param parent previous step board score
     */
    private BoardScore(final int steps, final Board board, final BoardScore parent) {
      this.score = board.manhattan();
      this.steps = steps;
      this.board = board;
      this.parent = parent;
    }

    @Override
    public int compareTo(final BoardScore other) {
      return Integer.compare(score + steps, other.score + other.steps);
    }
  }

  /**
   * Reads a puzzle from a file (specified as a command-line argument) and prints the solution to standard output.
   *
   * @param args args[0] - file name
   */
  public static void main(final String[] args) {
    // create initial board from file
    final In in = new In(args[0]);
    final int n = in.readInt();
    final int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        blocks[i][j] = in.readInt();
      }
    }
    final Board initial = new Board(blocks);

    // solve the puzzle
    final Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable()) {
      StdOut.println("No solution possible");
    } else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution()) {
        StdOut.println(board);
      }
    }
  }
}
