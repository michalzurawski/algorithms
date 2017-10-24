package com.github.michalzurawski.algorithms.wordnet;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.Collections;

/**
 * Class representing shortest ancestral path.
 * An <i>ancestral path</i> between two vertices <i>v</i> and <i>w</i> in a digraph is a directed path
 * from <i>v</i> to a common ancestor <i>x</i>, together with a directed path from <i>w</i>
 * to the same ancestor <i>x</i>.
 * A <i>shortest ancestral path</i> is an ancestral path of minimum total length.
 */
public class SAP {
    /**
     * Array containing paths from vertex <i>i</i>.
     */
    private final BreadthFirstDirectedPaths[] breadthFirstDirectedPaths;

    /**
     * Constructs the SAP from a graph.
     *
     * @param digraph directed graph, may contains cycles
     * @throws IllegalArgumentException when digraph is null
     */
    public SAP(final Digraph digraph) {
        if (digraph == null) {
            throw new IllegalArgumentException();
        }
        breadthFirstDirectedPaths = new BreadthFirstDirectedPaths[digraph.V()];
        for (int i = 0; i < breadthFirstDirectedPaths.length; ++i) {
            breadthFirstDirectedPaths[i] = new BreadthFirstDirectedPaths(digraph, i);
        }
    }

    /**
     * Returns length of shortest ancestral path between <i>v</i> and <i>w</i>.
     *
     * @param v vertex
     * @param w vertex
     * @return length of shortest ancestral path or -1 if no such path
     * @throws IllegalArgumentException if any of passed vertices is not between 0 and digraph.V() - 1
     */
    public int length(final int v, final int w) {
        final AncestorResult result = compute(Collections.singletonList(v), Collections.singletonList(w));
        return result.length;
    }

    /**
     * Returns common ancestor of v and w that participates in a shortest ancestral path.
     *
     * @param v vertex
     * @param w vertex
     * @return common ancestor or -1 if no such path
     * @throws IllegalArgumentException if any of passed vertices is not between 0 and digraph.V() - 1
     */
    public int ancestor(final int v, final int w) {
        final AncestorResult result = compute(Collections.singletonList(v), Collections.singletonList(w));
        return result.ancestor;
    }

    /**
     * Return length of shortest ancestral path between any vertex in <i>v</i> and any vertex in <i>w</i>.
     *
     * @param v collection of vertices
     * @param w collection of vertices
     * @return length of shortest ancestral path or -1 if no such path
     * @throws IllegalArgumentException if any of passed vertices is not between 0 and digraph.V() - 1
     */
    public int length(final Iterable<Integer> v, final Iterable<Integer> w) {
        final AncestorResult result = compute(v, w);
        return result.length;
    }

    /**
     * Returns a common ancestor that participates in shortest ancestral path.
     *
     * @param v collection of vertices
     * @param w collection of vertices
     * @return common ancestor or -1 if no such path
     */
    public int ancestor(final Iterable<Integer> v, final Iterable<Integer> w) {
        final AncestorResult result = compute(v, w);
        return result.ancestor;
    }

    /**
     * Class representing results of computing SAP.
     */
    private final class AncestorResult {
        /**
         * Computed ancestor.
         */
        private int ancestor;
        /**
         * Computed length.
         */
        private int length;

        /**
         * Creates new results.
         *
         * @param ancestor ancestor
         * @param length   length
         */
        private AncestorResult(final int ancestor, final int length) {
            this.ancestor = ancestor;
            this.length = length;
        }
    }

    /**
     * Computes ancestor and length of SAP for vertices.
     *
     * @param verticesV vertices
     * @param verticesW vertices
     * @return ancestor and length
     */
    private AncestorResult compute(final Iterable<Integer> verticesV, final Iterable<Integer> verticesW) {
        if (verticesV == null || verticesW == null) {
            throw new IllegalArgumentException();
        }
        for (final int v : verticesV) {
            if (v < 0 || v >= breadthFirstDirectedPaths.length) {
                throw new IllegalArgumentException();
            }
        }
        for (final int w : verticesW) {
            if (w < 0 || w >= breadthFirstDirectedPaths.length) {
                throw new IllegalArgumentException();
            }
        }
        int min = Integer.MAX_VALUE;
        int ancestor = -1;
        for (int i = 0; i < breadthFirstDirectedPaths.length; ++i) {
            int min1 = Integer.MAX_VALUE;
            int min2 = Integer.MAX_VALUE;
            for (final int v : verticesV) {
                min1 = Integer.min(min1, breadthFirstDirectedPaths[v].distTo(i));
            }
            for (final int w : verticesW) {
                min2 = Integer.min(min2, breadthFirstDirectedPaths[w].distTo(i));
            }
            if (min1 != Integer.MAX_VALUE && min2 != Integer.MAX_VALUE && min1 + min2 < min) {
                min = min1 + min2;
                ancestor = i;
            }
        }
        if (min == Integer.MAX_VALUE) {
            min = -1;
        }
        return new AncestorResult(ancestor, min);
    }
}
