package com.mattunderscore.trees.mutable;

/**
 * Represents a mutable node of a tree. This node is closed, it specifies itself as the type of its child nodes. It is
 * suitable for mutable, fixed nodes.
 *
 * @author Matt Champion on 13/06/2015
 */
public interface MutableNode<E> extends OpenMutableNode<E, MutableNode<E>> {
}
