package com.mattunderscore.trees.transformation;

import com.mattunderscore.trees.binary.MutableBinaryTree;
import com.mattunderscore.trees.binary.OpenMutableBinaryTreeNode;

/**
 * Provides tree transformation operations.
 * @author Matt Champion on 23/08/2015
 */
public interface TreeTransformer {
    /**
     * Rotate a tree using the tree's root node as the rotation root. The rotation is performed in place.
     * @param tree The tree to rotate
     * @param direction The direction to rotate
     * @param <E> The element type
     * @param <N> The node type
     */
    <E, N extends OpenMutableBinaryTreeNode<E, N>> void rotateRootInPlace(MutableBinaryTree<E, N> tree, RotationDirection direction);

    /**
     * Rotate a tree using some node as rotation root. The rotation is performed in place.
     * @param rootParent The parent of the rotation root
     * @param rotationRoot The rotation root
     * @param direction The direction to rotate
     * @param <E> The element type
     * @param <N> The node type
     */
    <E, N extends OpenMutableBinaryTreeNode<E, N>> void rotateInPlace(N rootParent, N rotationRoot, RotationDirection direction);
}
