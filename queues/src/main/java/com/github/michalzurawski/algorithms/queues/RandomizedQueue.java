package com.github.michalzurawski.algorithms.queues;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class representing randomized queue.
 * Based on the resizing array provides all operation in constant amortized time.
 *
 * @param <Item> the type of elements held in this collection
 */
public final class RandomizedQueue<Item> implements Iterable<Item> {
  /**
   * Stored values.
   */
  private Item[] values = (Item[]) new Object[2];
  /**
   * Number of stored values.
   */
  private int size = 0;

  /**
   * Returns true if queue is empty.
   *
   * @return true if empty
   */
  public boolean isEmpty() {
    return size == 0;
  }

  /**
   * Return the number of items on the queue.
   *
   * @return number of items
   */
  public int size() {
    return size;
  }

  /**
   * Adds item to the queue.
   *
   * @param item item to be added
   */
  public void enqueue(final Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    if (size == values.length) {
      resize(size * 2);
    }
    values[size] = item;
    ++size;
  }

  /**
   * Removes and returns a random item from the queue.
   *
   * @return removed item
   */
  public Item dequeue() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    final int index = StdRandom.uniform(size);
    final Item temp = values[index];
    --size;
    values[index] = values[size];
    values[size] = null;
    if (size > 2 && size * 2 == values.length) {
      resize(size * 2);
    }
    return temp;
  }

  /**
   * Returns but does not remove a random item from the queue.
   *
   * @return random item from the queue
   */
  public Item sample() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    final int index = StdRandom.uniform(size);
    return values[index];
  }

  /**
   * Class representing randomized queue iterator.
   * Iterates over values in random order.
   * Each iterator is independent.
   *
   * @param <Item> the type of elements held in this collection
   */
  private static final class RandomQueueIterator<Item> implements Iterator<Item> {
    /**
     * Values to iterate.
     */
    private final Item[] values;
    /**
     * Index of current item.
     */
    private int index;

    /**
     * Creates independent random iterator.
     *
     * @param size   number of elements
     * @param values elements to iterate
     */
    private RandomQueueIterator(final int size, final Item[] values) {
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

  /**
   * Resize the array to expected size.
   *
   * @param expectedSize size to resize the array
   */
  private void resize(final int expectedSize) {
    final Item[] copy = (Item[]) new Object[expectedSize];
    System.arraycopy(values, 0, copy, 0, size);
    values = copy;
  }
}
