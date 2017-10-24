package com.github.michalzurawski.algorithms.wordnet

import edu.princeton.cs.algs4.Digraph
import edu.princeton.cs.algs4.In
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class SAPTest extends Specification {
    static Digraph digraph

    def setupSpec() {
        In input = new In("digraph1.txt")
        digraph = new Digraph(input)
    }

    def "should throw exception when creating SAP with null object"() {
        when:
        new SAP(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should compute length of shortest path for vertices #v and #w"(int v, int w, int expected) {
        given:
        SAP sap = new SAP(digraph)

        when:
        int result = sap.length(v, w)

        then:
        result == expected

        where:
        v | w  || expected
        0 | 0  || 0
        0 | 1  || 1
        0 | 12 || 4
        1 | 2  || 2
        3 | 5  || 2
        3 | 11 || 4
        7 | 12 || 5
        6 | 0  || -1
        4 | 6  || -1
    }

    def "should throw exception when calling length for incorrect vertices #v and #w"(int v, int w) {
        given:
        SAP sap = new SAP(digraph)

        when:
        sap.length(v, w)

        then:
        thrown(IllegalArgumentException)

        where:
        v  | w
        0  | -1
        0  | 13
        -1 | 13
    }

    def "should compute ancestor of shortest path for vertices #v and #w"(int v, int w, int expected) {
        given:
        SAP sap = new SAP(digraph)

        when:
        int result = sap.ancestor(v, w)

        then:
        result == expected

        where:
        v | w  || expected
        0 | 0  || 0
        0 | 1  || 0
        0 | 12 || 0
        1 | 2  || 0
        3 | 5  || 1
        3 | 11 || 1
        9 | 12 || 5
        6 | 0  || -1
        4 | 6  || -1
    }

    def "should throw exception when calling ancestor for incorrect vertices #v and #w"(int v, int w) {
        given:
        SAP sap = new SAP(digraph)

        when:
        sap.length(v, w)

        then:
        thrown(IllegalArgumentException)

        where:
        v  | w
        0  | -1
        0  | 13
        -1 | 13
    }

    def "should compute length of shortest path for list of vertices #v and #w"(Iterable<Integer> v, Iterable<Integer> w, int expected) {
        given:
        SAP sap = new SAP(digraph)

        when:
        int result = sap.length(v, w)

        then:
        result == expected

        where:
        v       | w         || expected
        [0]     | [0]       || 0
        [0]     | [1]       || 1
        [0]     | [12]      || 4
        [1]     | [2]       || 2
        [3]     | [5]       || 2
        [3]     | [11]      || 4
        [7]     | [12]      || 5
        [6]     | [0]       || -1
        [4]     | [6]       || -1
        []      | [0]       || -1
        [0, 3]  | [0, 4, 5] || 0
        [1, 3]  | [2, 11]   || 2
        [3, 6]  | [5, 7]    || 1
        [3, 12] | [11, 9]   || 2
    }

    def "should throw exception when calling length for incorrect list of vertices #v and #w"(Iterable<Integer> v, Iterable<Integer> w) {
        given:
        SAP sap = new SAP(digraph)

        when:
        sap.length(v, w)

        then:
        thrown(IllegalArgumentException)

        where:
        v          | w
        null       | [0]
        [0]        | null
        null       | null
        [1, 2]     | [3, 13]
        [1, 2, -1] | [3, 12]
    }

    def "should compute ancestor of shortest path for list of vertices #v and #w"(Iterable<Integer> v, Iterable<Integer> w, int expected) {
        given:
        SAP sap = new SAP(digraph)

        when:
        int result = sap.ancestor(v, w)

        then:
        result == expected

        where:
        v       | w         || expected
        [0]     | [0]       || 0
        [0]     | [1]       || 0
        [0]     | [12]      || 0
        [1]     | [2]       || 0
        [3]     | [5]       || 1
        [3]     | [11]      || 1
        [9]     | [12]      || 5
        [6]     | [0]       || -1
        [4]     | [6]       || -1
        []      | [0]       || -1
        [0, 3]  | [0, 4, 5] || 0
        [1, 3]  | [2, 11]   || 0
        [3, 6]  | [5, 7]    || 3
        [3, 12] | [11, 9]   || 10
    }

    def "should throw exception when calling ancestor for incorrect list of vertices #v and #w"(Iterable<Integer> v, Iterable<Integer> w) {
        given:
        SAP sap = new SAP(digraph)

        when:
        sap.ancestor(v, w)

        then:
        thrown(IllegalArgumentException)

        where:
        v          | w
        null       | [0]
        [0]        | null
        null       | null
        [1, 2]     | [3, 13]
        [1, 2, -1] | [3, 12]
    }
}
