package cse214hw3;

/**
 * A simple pair object, where one item of the pair is a B-tree <code>Node</code> <code>n</code>, and the
 * other item is an integer index. The integer index is meant to specify the position of an element in the
 * node <code>n</code>.
 *
 * @param <E> the element type of the node in this pair.
 * @apiNote The parameter type is not bound by <code>java.lang.Comparable</code> so that any future use of
 * this pair remains possible for other data structures that may not require total ordering.
 */

public class NodeIndexPair<E> {
    public final BTree.Node nodeLocation;
    public final int index;
    public NodeIndexPair(BTree.Node n, int i) {
        this.nodeLocation = n;
        this.index = i;
    }
}
