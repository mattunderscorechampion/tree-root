package com.mattunderscore.trees.impl.providers;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.spi.EmptySortedTreeConstructor;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Provider for {@link EmptySortedTreeConstructor}.
 * @author Matt Champion on 25/07/2015
 */
public interface EmptySortedTreeConstructorProvider {
    /**
     * @param klass The key to lookup
     * @param <E> The type of element
     * @param <N> The type of node
     * @param <T> The type of tree
     * @return The empty tree constructor
     * @throws OperationNotSupportedForType If the key is not supported
     */
    @SuppressWarnings("unchecked")
    <E, N extends OpenNode<E, N>, T extends Tree<E, N>> EmptySortedTreeConstructor<E, N, T> get(Class<T> klass);
}
