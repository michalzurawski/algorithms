package com.github.michalzurawski.algorithms.queues

import spock.lang.Specification

class DequeSpec extends Specification {
  def "should be initially empty"() {
    given:
    Deque<Integer> deque = new Deque<>()
    when:
    boolean isEmpty = deque.isEmpty()
    then:
    isEmpty
  }

  def "should has size 0 initially"() {
    given:
    Deque<Integer> deque = new Deque<>()
    when:
    int size = deque.size()
    then:
    size == 0
  }

  def "should not be empty after element is added"() {
    given:
    Deque<Integer> deque = new Deque<>()
    deque.addFirst(1)
    when:
    boolean isEmpty = deque.isEmpty()
    then:
    !isEmpty
  }

  def "should has size 3 after 3 elements are added"() {
    given:
    Deque<Integer> deque = new Deque<>()
    deque.addFirst(2)
    deque.addFirst(4)
    deque.addFirst(8)
    when:
    int size = deque.size()
    then:
    size == 3
  }

  def "should add element to the front"() {
    given:
    Deque<Integer> deque = new Deque<>()
    when:
    deque.addFirst(2)
    deque.addFirst(4)
    deque.addFirst(8)
    then:
    deque.size() == 3
    Iterator<Integer> iterator = deque.iterator()
    iterator.next() == 8
    iterator.next() == 4
    iterator.next() == 2
  }

  def "should add element to the end"() {
    given:
    Deque<Integer> deque = new Deque<>()
    when:
    deque.addLast(2)
    deque.addLast(4)
    deque.addLast(8)
    then:
    deque.size() == 3
    Iterator<Integer> iterator = deque.iterator()
    iterator.next() == 2
    iterator.next() == 4
    iterator.next() == 8
  }

  def "should remove element from the front"() {
    given:
    Deque<Integer> deque = new Deque<>()
    deque.addFirst(2)
    deque.addFirst(4)
    deque.addFirst(8)
    when:
    int result = deque.removeFirst()
    then:
    result == 8
    deque.size() == 2
    Iterator<Integer> iterator = deque.iterator()
    iterator.next() == 4
    iterator.next() == 2
  }

  def "should remove element from the end"() {
    given:
    Deque<Integer> deque = new Deque<>()
    deque.addLast(2)
    deque.addLast(4)
    deque.addLast(8)
    when:
    int result = deque.removeLast()
    then:
    result == 8
    deque.size() == 2
    Iterator<Integer> iterator = deque.iterator()
    iterator.next() == 2
    iterator.next() == 4
  }

  def "should remove element from the end when added to the front"() {
    given:
    Deque<Integer> deque = new Deque<>()
    deque.addFirst(2)
    deque.addFirst(4)
    deque.addFirst(8)
    when:
    int result0 = deque.removeLast()
    int result1 = deque.removeLast()
    int result2 = deque.removeLast()
    then:
    result0 == 2
    result1 == 4
    result2 == 8
    deque.isEmpty()
  }

  def "should remove element from the front when added to the end"() {
    given:
    Deque<Integer> deque = new Deque<>()
    deque.addLast(2)
    deque.addLast(4)
    deque.addLast(8)
    when:
    int result0 = deque.removeFirst()
    int result1 = deque.removeFirst()
    int result2 = deque.removeFirst()
    then:
    result0 == 2
    result1 == 4
    result2 == 8
    deque.isEmpty()
  }

  def "should throw an error when adding null to the front"() {
    given:
    Deque<Integer> deque = new Deque<>()
    when:
    deque.addFirst(null)
    then:
    thrown(NullPointerException)
  }

  def "should throw an error when adding null to the end"() {
    given:
    Deque<Integer> deque = new Deque<>()
    when:
    deque.addLast(null)
    then:
    thrown(NullPointerException)
  }

  def "should throw an error when removing from the front on the empty deque"() {
    given:
    Deque<Integer> deque = new Deque<>()
    when:
    deque.removeFirst()
    then:
    thrown(NoSuchElementException)
  }

  def "should throw an error when removing from the end on the empty deque"() {
    given:
    Deque<Integer> deque = new Deque<>()
    when:
    deque.removeLast()
    then:
    thrown(NoSuchElementException)
  }

  def "should throw an error when removing from iterator"() {
    given:
    Deque<Integer> deque = new Deque<>()
    Iterator<Integer> iterator = deque.iterator()
    when:
    iterator.remove()
    then:
    thrown(UnsupportedOperationException)
  }

  def "should throw an error when getting next value from the end"() {
    given:
    Deque<Integer> deque = new Deque<>()
    Iterator<Integer> iterator = deque.iterator()
    when:
    iterator.next()
    then:
    thrown(NoSuchElementException)
  }
}
