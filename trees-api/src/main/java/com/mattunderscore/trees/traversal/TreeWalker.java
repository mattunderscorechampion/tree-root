package com.mattunderscore.trees.traversal;

import com.mattunderscore.trees.tree.Node;

/**
 * Interface for walking over a tree. Unlike a {@link Walker} it provides information on a nodes children. Provides
 * internal iterator.
 * @param <E> The type of objects to walk over
 * @author Matt Champion on 31/01/15
 */
public interface TreeWalker<E> {
    void onStarted();

    void onNode(E node);

    void onNodeChildrenStarted(E node);

    void onNodeChildrenCompleted(E node);

    void onNodeNoChildren(E node);

    void onCompleted();
}
