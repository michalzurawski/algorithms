package com.github.michalzurawski.algorithms.queues;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  // construct an empty deque
  private static class Node<Item> {
    private Node<Item> next;
    private Node<Item> prev;
    private Item value;

    private Node(Item value) {
      this.value = value;
    }
  }

  private Node<Item> first;
  private Node<Item> last;
  private int size = 0;

  public Deque() {
  }

  // is the deque empty?
  public boolean isEmpty() {
    return first == null;
  }

  // return the number of items on the deque
  public int size() {
    return size;
  }

  // add the item to the front
  public void addFirst(Item item) {
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

  // add the item to the end
  public void addLast(Item item) {
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

  // remove and return the item from the front
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

  // remove and return the item from the end
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

  private static class DequeIterator<Item> implements Iterator<Item> {
    private Node<Item> node;

    private DequeIterator(Node<Item> node) {
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
