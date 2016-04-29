package com.mattunderscore.trees.query;

import com.mattunderscore.trees.tree.OpenNode;

import java.util.Collection;

/**
 * A reducer for a tree. The reducer may be applied to every node of the tree to generate a result or may short cut
 * part of the tree and terminate without traversing the entire tree. The reducer is applied to the children of a node
 * and the result is passed in when reducing the node.
 *
 * @author Matt Champion on 29/04/16
 */
public interface PostOrderPartialTreeReducer<E, N extends OpenNode<E, N>, R> {
    /**
     * Apply the reduction to a node.
     * @param node The node to reduce
     * @param childResults The results of the nodes children
     * @return The result of the reduction
     */
    ReductionResult<R> applyToNode(N node, Collection<R> childResults);

}
