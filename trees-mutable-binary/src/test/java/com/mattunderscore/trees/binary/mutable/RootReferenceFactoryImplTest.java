package com.mattunderscore.trees.binary.mutable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.spi.Rotator;

/**
 * Unit tests for {@link RootReferenceFactoryImpl}.
 *
 * @author Matt Champion on 20/08/2015
 */
public final class RootReferenceFactoryImplTest {
    @Test(expected = IllegalStateException.class)
    public void unableToReplaceUnknown() {
        final RootReferenceFactoryImpl<String> factory = new RootReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final Rotator.RootReference<MutableBinaryTreeNode<String>> reference = factory.wrapNode(a);
        reference.replaceRoot(b, c);
    }

    @Test
    public void forClass() {
        final RootReferenceFactoryImpl<String> factory = new RootReferenceFactoryImpl<>();
        assertEquals(MutableBinaryTreeNodeImpl.class, factory.forClass());
    }
}
