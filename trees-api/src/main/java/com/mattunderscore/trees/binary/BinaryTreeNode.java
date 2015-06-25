package com.mattunderscore.trees.binary;

/**
 * A binary tree node. This node is open, it accepts a generic parameter for the type of child nodes it has. This node
 * is closed, it specifies itself as the type of its child nodes. It is suitable for immutable binary nodes.
 *
 * @author Matt Champion on 13/06/2015
 */
public interface BinaryTreeNode<E> extends OpenBinaryTreeNode<E, BinaryTreeNode<E>> {
}
