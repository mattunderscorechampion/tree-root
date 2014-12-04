package com.mattunderscore.trees.base;

import com.mattunderscore.trees.mutable.SettableNode;

/**
 * Abstract settable node implementation.
 * @author Matt Champion
 */
public abstract class AbstractSettableNode<E> extends UnfixedNode<E> implements SettableNode<E> {

    public AbstractSettableNode(E element) {
        super(element);
    }

    @Override
    public E setElement(E element) {
        return elementReference.getAndSet(element);
    }
}
