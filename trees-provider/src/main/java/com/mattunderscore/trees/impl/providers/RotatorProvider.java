package com.mattunderscore.trees.impl.providers;

import com.mattunderscore.trees.binary.OpenMutableBinaryTreeNode;
import com.mattunderscore.trees.spi.Rotator;
import com.mattunderscore.trees.transformation.RotationDirection;

/**
 * Provider for {@link Rotator} components.
 * @author Matt Champion on 26/08/2015
 */
public interface RotatorProvider {
    <E, N extends OpenMutableBinaryTreeNode<E, N>> Rotator<E, N> get(N node, RotationDirection direction);
}
