package com.github.michalzurawski.algorithms.queues;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
  public static void main(String[] args) {
    final int k = Integer.parseInt(args[0]);
    final RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
    int n = 0;
    while (!StdIn.isEmpty()) {
      final String value = StdIn.readString();
      final int probability = StdRandom.uniform(100);
      ++n;
      if (n <= k) {
        randomizedQueue.enqueue(value);
      } else {
        if (probability * n < 100 * k) {
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
