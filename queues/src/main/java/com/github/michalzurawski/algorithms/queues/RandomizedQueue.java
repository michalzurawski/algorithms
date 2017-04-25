package com.github.michalzurawski.algorithms.queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  // construct an empty randomized queue
  private Item[] values = (Item[]) new Object[8];
  private int size = 0;

  public RandomizedQueue() {
  }

  // is the queue empty?
  public boolean isEmpty() {
    return size == 0;
  }

  // return the number of items on the queue
  public int size() {
    return size;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    if (size == values.length) {
      resize(size * 2);
    }
    values[size] = item;
    ++size;
  }

  // remove and return a random item
  public Item dequeue() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    final int index = StdRandom.uniform(size);
    final Item temp = values[index];
    --size;
    values[index] = values[size];
    values[size] = null;
    if (size > 4 && size * 4 == values.length) {
      resize(size * 2);
    }
    return temp;
  }

  // return (but do not remove) a random item
  public Item sample() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    final int index = StdRandom.uniform(size);
    return values[index];
  }

  private static class RandomQueueIterator<Item> implements Iterator<Item> {
    private int index;
    private Item[] values;

    private RandomQueueIterator(int size, Item[] values) {
      index = size;
      this.values = (Item[]) new Object[size];
      System.arraycopy(values, 0, this.values, 0, size);
      StdRandom.shuffle(this.values);
    }

    @Override
    public boolean hasNext() {
      return index > 0;
    }

    @Override
    public Item next() {
      if (index == 0) {
        throw new NoSuchElementException();
      }
      --index;
      return values[index];
    }
  }

  @Override
  public Iterator<Item> iterator() {
    return new RandomQueueIterator<>(size, values);
  }

  private void resize(int expectedSize) {
    final Item[] copy = (Item[]) new Object[expectedSize];
    System.arraycopy(values, 0, copy, 0, size);
    values = copy;
  }
}
