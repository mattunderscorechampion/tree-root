package com.mattunderscore.trees.binary.mutable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.spi.ParentReference;

/**
 * Unit tests for {@link ParentReferenceFactoryImpl}.
 *
 * @author Matt Champion on 20/08/2015
 */
public final class ParentReferenceFactoryImplTest {
    @Test(expected = IllegalArgumentException.class)
    public void unableToReplaceUnknown() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final ParentReference<MutableBinaryTreeNode<String>> reference = factory.wrap(a);
        reference.replace(b, c);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unableToReplaceUnknownRoot() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(a);

        final ParentReference<MutableBinaryTreeNode<String>> reference = factory.wrap(tree);
        reference.replace(b, c);
    }

    @Test
    public void replaceRoot() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(a);

        final ParentReference<MutableBinaryTreeNode<String>> reference = factory.wrap(tree);
        reference.replace(a, b);

        assertEquals(b, tree.getRoot());
    }

    @Test
    public void replaceLeft() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b", a, null);
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");

        final ParentReference<MutableBinaryTreeNode<String>> reference = factory.wrap(b);
        reference.replace(a, c);

        assertEquals(c, b.childIterator().next());
    }

    @Test
    public void replaceRight() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b", null, a);
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");

        final ParentReference<MutableBinaryTreeNode<String>> reference = factory.wrap(b);
        reference.replace(a, c);

        assertEquals(c, b.childIterator().next());
    }

    @Test
    public void forClass() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        assertEquals(MutableBinaryTreeNodeImpl.class, factory.forClass());
    }
}
