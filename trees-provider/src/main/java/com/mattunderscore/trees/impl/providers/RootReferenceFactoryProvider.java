package com.mattunderscore.trees.impl.providers;

import com.mattunderscore.trees.binary.OpenMutableBinaryTreeNode;
import com.mattunderscore.trees.spi.ParentReferenceFactory;

/**
 * Provider for {@link ParentReferenceFactory} components.
 * @author Matt Champion on 26/08/2015
 */
public interface RootReferenceFactoryProvider {
    /**
     * Get a {@link ParentReferenceFactory}.
     * @param node The node to get a reference factory for
     * @param <E> The element type
     * @param <N> The node type
     * @return The factory
     */
    <E, N extends OpenMutableBinaryTreeNode<E, N>> ParentReferenceFactory<E, N> get(N node);
}
