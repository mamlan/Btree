package cse214hw3;
/**
 * This is the interface providing the public contract for any B-tree implementation. It comprises of only
 * two methods:
 * <ol>
 * <li>contains(E element)</li>
 * <li>add(E element)</li>
 * </ol>
 * @param <E> the type of elements in this tree; which must implement the <code>java.lang.Comparable</code>
 * interface (since B-trees require a totally ordered data type).
 */

public interface AbstractBTree<E extends Comparable<E>> {
        /**
         * Follows the search algorithm for B-trees to determine whether the specified element exists in this
         * B-tree. If no no element is found, <code>null</code> is returned.
         *
         * @param element the specified element to be searched.
         * @return a node-index pair that consists of the node in this tree where the specified element is
         * found, and the index of this element in that node; <code>null</code> if the element is
         * not present in this tree.
         */
        NodeIndexPair<E> contains(E element);
        /**
         * Add the specified element to this B-tree, using the insertion algorithm for B-trees. If this element
         * already exists in this tree, then calling this method has no effect on the tree.
         *
         * @param element the specified element to be added to this tree.
         */
        void add(E element);
    }

