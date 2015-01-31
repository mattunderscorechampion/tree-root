package com.mattunderscore.trees.tree;

import com.mattunderscore.trees.collection.SimpleCollection;

/**
 * Node that has children in specific places.
 * @author Matt Champion on 31/01/15
 */
public interface StructuralNode<E> extends Node<E> {
    @Override
    SimpleCollection<? extends StructuralNode<E>> getChildren();

    /**
     * Get the nth child node.
     * @param nChild The nth value
     * @return The node
     */
    StructuralNode<E> getChild(int nChild);
}
