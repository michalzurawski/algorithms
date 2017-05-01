package com.github.michalzurawski.algorithms.queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Permutation client which chooses k random strings from n.
 */
public final class Permutation {
  /**
   * Utility class.
   */
  private Permutation() {
  }

  /**
   * Permutation client which chooses k random strings from n.
   *
   * @param args args[0] = k
   */
  public static void main(final String[] args) {
    final int k = Integer.parseInt(args[0]);
    final RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
    int n = 0;
    final int percent = 100;
    while (!StdIn.isEmpty()) {
      final String value = StdIn.readString();
      final int probability = StdRandom.uniform(100);
      ++n;
      if (n <= k) {
        randomizedQueue.enqueue(value);
      } else {
        if (probability * n < k * percent) {
          randomizedQueue.dequeue();
          randomizedQueue.enqueue(value);
        }
      }
    }
    for (int i = 0; i < k; ++i) {
      StdOut.println(randomizedQueue.dequeue());
    }
  }
}
