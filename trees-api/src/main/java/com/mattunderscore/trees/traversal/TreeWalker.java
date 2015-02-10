package com.mattunderscore.trees.traversal;

/**
 * Interface for walking over a tree. Unlike a {@link Walker} it provides information on a nodes children. Provides
 * internal iterator.
 * @param <E> The type of objects to walk over
 * @author Matt Champion on 31/01/15
 */
public interface TreeWalker<E> {
    /**
     * Called on the start of the iteration.
     */
    void onStarted();

    /**
     * Called on visiting a node.
     * @param node The node
     */
    void onNode(E node);

    /**
     * Called before the first child of a node is visited.
     * @param node The node
     */
    void onNodeChildrenStarted(E node);

    /**
     * Called after a child node (and its children) have been visited and before the next child node is visited.
     * @param node The parent node
     */
    void onNodeChildrenRemaining(E node);

    /**
     * Called after all child nodes (and their children) have been visited and before the next sibling node is visited.
     * @param node The node
     */
    void onNodeChildrenCompleted(E node);

    /**
     * Called when the last visited node has no children.
     * @param node The node with no children
     */
    void onNodeNoChildren(E node);

    /**
     * Called at the end of the iteration.
     */
    void onCompleted();
}
