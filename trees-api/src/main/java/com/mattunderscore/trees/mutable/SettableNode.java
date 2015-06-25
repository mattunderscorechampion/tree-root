package com.mattunderscore.trees.mutable;

/**
 * A node that allows the element it contains to be modified without changing the structure of the tree. This node is
 * closed, it specifies itself as the type of its child nodes. It is suitable for settable nodes that cannot change the
 * structure of the tree.
 *
 * @author Matt Champion on 13/06/2015
 */
public interface SettableNode<E> extends OpenSettableNode<E, SettableNode<E>> {
}
