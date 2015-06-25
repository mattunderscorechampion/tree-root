package com.mattunderscore.trees.binary;

/**
 * A mutable, binary tree node. This node is closed, it specifies itself as the type of its child nodes. It is suitable
 * for mutable binary nodes.
 *
 * @author Matt Champion on 13/06/2015
 */
public interface MutableBinaryTreeNode<E> extends OpenMutableBinaryTreeNode<E, MutableBinaryTreeNode<E>> {
}
