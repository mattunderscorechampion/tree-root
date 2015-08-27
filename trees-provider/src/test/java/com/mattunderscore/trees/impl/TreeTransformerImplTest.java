package com.mattunderscore.trees.impl;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.isA;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.binary.MutableBinaryTree;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.impl.suppliers.RootReferenceFactorySupplier;
import com.mattunderscore.trees.impl.suppliers.RotatorSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.KeyMappingSupplier;
import com.mattunderscore.trees.impl.suppliers.impl.RootReferenceFactorySupplierImpl;
import com.mattunderscore.trees.impl.suppliers.impl.RotatorSupplierImpl;
import com.mattunderscore.trees.spi.ParentReference;
import com.mattunderscore.trees.spi.ParentReferenceFactory;
import com.mattunderscore.trees.spi.Rotator;
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
    @Mock
    private RootReferenceFactorySupplier referenceFactorySupplier;
    @Mock
    private ParentReferenceFactory<String, MutableBinaryTreeNode<String>> referenceFactory;
    @Mock
    private RotatorSupplier rotatorSupplier;
    @Mock
    private Rotator rotator;
    @Mock
    private ParentReference<MutableBinaryTreeNode<String>> reference;

    @Before
    public void setUp() {
        initMocks(this);
        when(tree.getRoot()).thenReturn(rootParent);
        when(rotatorSupplier.get(isA(MutableBinaryTreeNode.class), isA(RotationDirection.class))).thenReturn(rotator);
        when(referenceFactorySupplier.get(rootParent)).thenReturn(referenceFactory);
        when(referenceFactorySupplier.get(root)).thenReturn(referenceFactory);
        when(referenceFactory.wrap(tree)).thenReturn(reference);
        when(referenceFactory.wrap(rootParent)).thenReturn(reference);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rotateEmptyTree() {
        final KeyMappingSupplier keyMappingSupplier = KeyMappingSupplier.get();
        final TreeTransformerImpl transformer = new TreeTransformerImpl(
            new RootReferenceFactorySupplierImpl(keyMappingSupplier),
            new RotatorSupplierImpl(keyMappingSupplier));

        when(tree.isEmpty()).thenReturn(true);

        transformer.rotateRootInPlace(tree, RotationDirection.LEFT);
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void rotateRootInPlaceNoneFound() {
        final KeyMappingSupplier keyMappingSupplier = KeyMappingSupplier.get();
        final TreeTransformerImpl transformer = new TreeTransformerImpl(
            new RootReferenceFactorySupplierImpl(keyMappingSupplier),
            new RotatorSupplierImpl(keyMappingSupplier));

        transformer.rotateRootInPlace(tree, RotationDirection.LEFT);
    }

    @Test(expected = OperationNotSupportedForType.class)
    public void rotateInPlaceNoneFound() {
        final KeyMappingSupplier keyMappingSupplier = KeyMappingSupplier.get();
        final TreeTransformerImpl transformer = new TreeTransformerImpl(
            new RootReferenceFactorySupplierImpl(keyMappingSupplier),
            new RotatorSupplierImpl(keyMappingSupplier));

        transformer.rotateInPlace(rootParent, root, RotationDirection.LEFT);
    }

    @Test
    public void rotateInPlaceWithMock() {
        final TreeTransformerImpl transformer = new TreeTransformerImpl(
            referenceFactorySupplier,
            rotatorSupplier);

        transformer.rotateInPlace(rootParent, root, RotationDirection.LEFT);

        verify(referenceFactory).wrap(rootParent);
        verify(rotator).rotate(reference, root);
    }

    @Test
    public void rotateRootInPlaceWithMock() {
        when(tree.getRoot()).thenReturn(root);
        final TreeTransformerImpl transformer = new TreeTransformerImpl(
            referenceFactorySupplier,
            rotatorSupplier);

        transformer.rotateRootInPlace(tree, RotationDirection.LEFT);

        verify(referenceFactory).wrap(tree);
        verify(rotator).rotate(reference, root);
    }
}
