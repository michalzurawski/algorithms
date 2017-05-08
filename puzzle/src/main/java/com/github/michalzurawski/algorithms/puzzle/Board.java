package com.github.michalzurawski.algorithms.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Class representing board for the puzzle game.
 * Each block of the {@code n-by-n} board contains the unique number between <i>0</i> and <i>n^2 - 1</i>
 * where 0 represents empty block.
 */
public class Board {
  /**
   * Board blocks.
   */
  private final int[][] blocks;

  /**
   * Constructs the board from an {@code n-by-n} array of array.
   * For {@code blocks[i][j]} <i>i</i> indicates <i>row</i>
   * and <i>j</i> indicates <i>column</i>.
   *
   * @param blocks blocks to create board
   */
  public Board(final int[][] blocks) {
    this.blocks = cloneArray(blocks);
  }

  /**
   * Return board dimension <i>n</i>.
   *
   * @return board dimension
   */
  public int dimension() {
    return blocks.length;
  }

  /**
   * Heuristic function computing the number of blocks in the wrong position.
   *
   * @return hamming heuristic for current board
   */
  public int hamming() {
    int score = 0;
    final int n = dimension();
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (blocks[i][j] != i * n + j + 1) {
          score++;
        }
      }
    }
    return score - 1;
  }

  /**
   * Heuristic function computing the sum of the Manhattan distances
   * (sum of the vertical and horizontal distance) from the blocks to their goal positions.
   *
   * @return manhattan heuristic for current board
   */
  public int manhattan() {
    int score = 0;
    final int n = dimension();
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        final int block = blocks[i][j] - 1;
        final int expected = i * n + j;
        if (block >= 0 && block != expected) {
          score += Math.abs(block / n - i) + Math.abs(block % n - j);
        }
      }
    }
    return score;
  }

  /**
   * Returns true if this board is equal to the goal board.
   *
   * @return true if this board the goal board
   */
  public boolean isGoal() {
    return hamming() == 0;
  }

  /**
   * Returns the board obtained by exchanging one pair of blocks.
   * It exchanges first two blocks unless one of them is zero (empty block).
   * In such case nth and nth + 1 blocks are exchanged.
   *
   * @return twin board
   */
  public Board twin() {
    final int[][] twinBlocks = cloneArray(blocks);
    if (twinBlocks[0][0] != 0 && twinBlocks[0][1] != 0) {
      swap(twinBlocks, 0, 0, 0, 1);
    } else {
      final int n = dimension();
      swap(twinBlocks, n - 1, 0, n - 1, 1);
    }
    return new Board(twinBlocks);
  }

  /**
   * Returns all neighbouring boards.
   * A board is a neighbour if it is obtainable by a single move.
   *
   * @return all neighbouring boards
   */
  public Iterable<Board> neighbors() {
    int row = -1;
    int col = -1;
    final int n = dimension();
    for (int i = 0; i < n; ++i) {
      for (int j = 0; j < n; ++j) {
        if (blocks[i][j] == 0) {
          row = i;
          col = j;
          break;
        }
      }
    }
    assert row >= 0 && row < n;
    assert col >= 0 && col < n;
    final Collection<Board> neighbors = new ArrayList<>();
    for (int i : new int[]{-1, 1}) {
      if (row + i >= 0 && row + i < n) {
        final int[][] copy = cloneArray(blocks);
        swap(copy, row, col, row + i, col);
        neighbors.add(new Board(copy));
      }
      if (col + i >= 0 && col + i < n) {
        final int[][] copy = cloneArray(blocks);
        swap(copy, row, col, row, col + i);
        neighbors.add(new Board(copy));
      }
    }
    assert neighbors.size() >= 2;
    return neighbors;
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Board)) {
      return false;
    }
    final Board that = (Board) o;
    final int n = dimension();
    return n == that.dimension() && Arrays.deepEquals(blocks, that.blocks);
  }

  @Override
  public String toString() {
    final StringBuilder stringBuilder = new StringBuilder();
    final int n = dimension();
    stringBuilder.append(n).append("\n");
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        stringBuilder.append(String.format("%2d ", blocks[i][j]));
      }
      stringBuilder.append("\n");
    }
    return stringBuilder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    final int n = dimension();
    return prime * n + Arrays.deepHashCode(blocks);
  }

  /**
   * Clones the provided array.
   *
   * @param src array to copy
   * @return a new clone of the provided array
   */
  private static int[][] cloneArray(final int[][] src) {
    final int length = src.length;
    final int[][] target = new int[length][length];
    for (int i = 0; i < length; i++) {
      System.arraycopy(src[i], 0, target[i], 0, length);
    }
    return target;
  }

  /**
   * Swaps two elements of the array.
   *
   * @param array array to swap
   * @param fromX row from
   * @param fromY column from
   * @param toX   row to
   * @param toY   column to
   */
  private static void swap(final int[][] array, final int fromX, final int fromY, final int toX, final int toY) {
    final int tmp = array[fromX][fromY];
    array[fromX][fromY] = array[toX][toY];
    array[toX][toY] = tmp;
  }
}
