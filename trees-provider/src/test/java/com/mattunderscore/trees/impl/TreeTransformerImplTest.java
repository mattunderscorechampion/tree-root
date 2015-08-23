package com.mattunderscore.trees.impl;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.binary.MutableBinaryTree;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.impl.suppliers.impl.KeyMappingSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.RootReferenceFactorySupplier;
import com.mattunderscore.trees.impl.suppliers.impl.RotatorSupplier;
import com.mattunderscore.trees.transformation.RotationDirection;

/**
 * Unit tests for {@link TreeTransformerImpl}.
 *
 * @author Matt Champion on 22/08/2015
 */
public final class TreeTransformerImplTest {
    @Mock
    private MutableBinaryTree<String, MutableBinaryTreeNode<String>> tree;
    @Mock
    private MutableBinaryTreeNode<String> rootParent;
    @Mock
    private MutableBinaryTreeNode<String> root;

    @Before
    public void setUp() {
        initMocks(this);
        when(tree.getRoot()).thenReturn(rootParent);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rotateEmptyTree() {
        final KeyMappingSupplier keyMappingSupplier = new KeyMappingSupplier();
        final TreeTransformerImpl transformer = new TreeTransformerImpl(
            new RootReferenceFactorySupplier(keyMappingSupplier),
            new RotatorSupplier(keyMappingSupplier));

        when(tree.isEmpty()).thenReturn(true);

        transformer.rotateRootInPlace(tree, RotationDirection.LEFT);
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void rotateRootInPlaceNoneFound() {
        final KeyMappingSupplier keyMappingSupplier = new KeyMappingSupplier();
        final TreeTransformerImpl transformer = new TreeTransformerImpl(
            new RootReferenceFactorySupplier(keyMappingSupplier),
            new RotatorSupplier(keyMappingSupplier));

        transformer.rotateRootInPlace(tree, RotationDirection.LEFT);
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void rotateInPlaceNoneFound() {
        final KeyMappingSupplier keyMappingSupplier = new KeyMappingSupplier();
        final TreeTransformerImpl transformer = new TreeTransformerImpl(
            new RootReferenceFactorySupplier(keyMappingSupplier),
            new RotatorSupplier(keyMappingSupplier));

        transformer.rotateInPlace(rootParent, root, RotationDirection.LEFT);
    }
}
