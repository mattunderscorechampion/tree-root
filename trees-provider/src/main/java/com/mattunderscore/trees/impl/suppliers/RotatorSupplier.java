package com.mattunderscore.trees.impl.suppliers;

import com.mattunderscore.trees.binary.OpenMutableBinaryTreeNode;
import com.mattunderscore.trees.spi.Rotator;
import com.mattunderscore.trees.transformation.RotationDirection;

/**
 * Supplier for {@link Rotator} components.
 * @author Matt Champion on 26/08/2015
 */
public interface RotatorSupplier {
    <E, N extends OpenMutableBinaryTreeNode<E, N>> Rotator<E, N> get(N node, RotationDirection direction);
}
