package com.github.michalzurawski.algorithms.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class representing double-ended queue (deque).
 * All operations run in constant time.
 * Implemented as a list.
 *
 * @param <Item> the type of elements held in this collection
 */
public final class Deque<Item> implements Iterable<Item> {
  /**
   * Node of the list.
   *
   * @param <Item> the type of elements held in this collection
   */
  private static final class Node<Item> {
    /**
     * Next node on the list.
     */
    private Node<Item> next;
    /**
     * Previous node on the list.
     */
    private Node<Item> prev;
    /**
     * Value stored in the current node.
     */
    private Item value;

    /**
     * Constructs the node for given value.
     *
     * @param value value to be stored
     */
    private Node(final Item value) {
      this.value = value;
    }
  }

  /**
   * First node on the list.
   */
  private Node<Item> first;
  /**
   * Last node on the list.
   */
  private Node<Item> last;
  /**
   * Number of elements on the list.
   */
  private int size = 0;

  /**
   * Returns true if this deque contains no elements.
   *
   * @return true if deque is empty
   */
  public boolean isEmpty() {
    return first == null;
  }

  /**
   * Returns number of elements stored in this deque.
   *
   * @return number of elements in this deque
   */
  public int size() {
    return size;
  }

  /**
   * Adds element to the front of the deque.
   *
   * @param item value to be stored
   */
  public void addFirst(final Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    final Node<Item> node = new Node<>(item);
    node.next = first;
    if (first != null) {
      first.prev = node;
    } else {
      last = node;
    }
    first = node;
    ++size;
  }

  /**
   * Adds element to the end of the deque.
   *
   * @param item value to be stored
   */
  public void addLast(final Item item) {
    if (item == null) {
      throw new NullPointerException();
    }
    final Node<Item> node = new Node<>(item);
    node.prev = last;
    if (last != null) {
      last.next = node;
    } else {
      first = node;
    }
    last = node;
    ++size;
  }

  /**
   * Removes element from the front of the deque.
   *
   * @return value of removed element
   * @throws NoSuchElementException when deque is empty
   */
  public Item removeFirst() {
    if (first == null) {
      throw new NoSuchElementException();
    }
    final Item value = first.value;
    first = first.next;
    if (first != null) {
      first.prev = null;
    } else {
      last = null;
    }
    --size;
    return value;
  }

  /**
   * Removes element from the end of the deque.
   *
   * @return value of removed element
   * @throws NoSuchElementException when deque is empty
   */
  public Item removeLast() {
    if (last == null) {
      throw new NoSuchElementException();
    }
    final Item value = last.value;
    last = last.prev;
    if (last != null) {
      last.next = null;
    } else {
      first = null;
    }
    --size;
    return value;
  }

  /**
   * Iterator from the beginning of the list.
   *
   * @param <Item> the type of elements held in this collection
   */
  private static final class DequeIterator<Item> implements Iterator<Item> {
    /**
     * Current node.
     */
    private Node<Item> node;

    /**
     * Constructs an iterator starting at node.
     *
     * @param node node to start iteration
     */
    private DequeIterator(final Node<Item> node) {
      this.node = node;
    }

    @Override
    public boolean hasNext() {
      return node != null;
    }

    @Override
    public Item next() {
      if (node == null) {
        throw new NoSuchElementException();
      }
      final Item value = node.value;
      node = node.next;
      return value;
    }
  }

  @Override
  public Iterator<Item> iterator() {
    return new DequeIterator<>(first);
  }
}
