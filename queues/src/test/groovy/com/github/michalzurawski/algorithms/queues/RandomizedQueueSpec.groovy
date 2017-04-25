package com.github.michalzurawski.algorithms.queues

import spock.lang.Specification

class RandomizedQueueSpec extends Specification {
  def "should be initially empty"() {
    given:
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>()
    when:
    boolean isEmpty = randomizedQueue.isEmpty()
    then:
    isEmpty
  }

  def "should has size 0 initially"() {
    given:
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>()
    when:
    int size = randomizedQueue.size()
    then:
    size == 0
  }

  def "should not be empty after element is added"() {
    given:
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>()
    randomizedQueue.enqueue(1)
    when:
    boolean isEmpty = randomizedQueue.isEmpty()
    then:
    !isEmpty
  }

  def "should has size 3 after 3 elements are added"() {
    given:
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>()
    randomizedQueue.enqueue(2)
    randomizedQueue.enqueue(4)
    randomizedQueue.enqueue(8)
    when:
    int size = randomizedQueue.size()
    then:
    size == 3
  }

  def "should enqueue the element"() {
    given:
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>()
    when:
    randomizedQueue.enqueue(2)
    randomizedQueue.enqueue(4)
    randomizedQueue.enqueue(8)
    then:
    randomizedQueue.size() == 3
  }

  def "should randomizedQueueue the element"() {
    given:
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>()
    randomizedQueue.enqueue(2)
    randomizedQueue.enqueue(4)
    randomizedQueue.enqueue(8)
    when:
    int result = randomizedQueue.dequeue()
    then:
    randomizedQueue.size() == 2
    result in [2, 4, 8]
  }

  def "should get sample element"() {
    given:
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>()
    randomizedQueue.enqueue(2)
    randomizedQueue.enqueue(4)
    randomizedQueue.enqueue(8)
    when:
    int result = randomizedQueue.sample()
    then:
    randomizedQueue.size() == 3
    result in [2, 4, 8]
  }

  def "should throw an error when enqueue null"() {
    given:
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>()
    when:
    randomizedQueue.enqueue(null)
    then:
    thrown(NullPointerException)
  }

  def "should throw an error when randomizedQueueue on the empty queue"() {
    given:
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>()
    when:
    randomizedQueue.dequeue()
    then:
    thrown(NoSuchElementException)
  }

  def "should throw an error when getting sample on the empty queue"() {
    given:
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>()
    when:
    randomizedQueue.sample()
    then:
    thrown(NoSuchElementException)
  }

  def "should throw an error when removing from iterator"() {
    given:
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>()
    Iterator<Integer> iterator = randomizedQueue.iterator()
    when:
    iterator.remove()
    then:
    thrown(UnsupportedOperationException)
  }

  def "should throw an error when getting next value from the end"() {
    given:
    RandomizedQueue<Integer> randomizedQueue = new RandomizedQueue<>()
    Iterator<Integer> iterator = randomizedQueue.iterator()
    when:
    iterator.next()
    then:
    thrown(NoSuchElementException)
  }
}
