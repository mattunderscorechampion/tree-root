package com.mattunderscore.trees.tree;

/**
 * Represents a node of the tree. This node is closed, specifies itself as the type of its child nodes. It is suitable
 * for immutable nodes.
 *
 * @author Matt Champion on 13/06/2015
 */
public interface Node<E> extends OpenNode<E, Node<E>> {
}
