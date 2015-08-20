package com.mattunderscore.trees.binary.mutable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.spi.Rotator.Direction;

/**
 * Unit tests for {@link LeftRotator}.
 *
 * @author Matt Champion on 17/08/2015
 */
public final class LeftRotatorTest {

    @Test
    public void rotateAtRoot() {
        final RootReferenceFactoryImpl<String> factory = new RootReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", b, c);
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, q);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(p);

        final LeftRotator<String> rotator = new LeftRotator<>();
        rotator.rotate(factory.wrapTree(tree), tree.getRoot());

        assertEquals("q", tree.getRoot().getElement());
        assertEquals("p", tree.getRoot().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getElement());
        assertEquals("b", tree.getRoot().getLeft().getRight().getElement());
    }

    @Test
    public void rotateAtNode() {
        final RootReferenceFactoryImpl<String> factory = new RootReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", b, c);
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, q);
        final MutableBinaryTreeNodeImpl<String> node = new MutableBinaryTreeNodeImpl<>("node", p, null);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(node);

        final LeftRotator<String> rotator = new LeftRotator<>();
        rotator.rotate(factory.wrapNode(node), tree.getRoot().getLeft());

        assertEquals("node", tree.getRoot().getElement());
        assertEquals("q", tree.getRoot().getLeft().getElement());
        assertEquals("p", tree.getRoot().getLeft().getLeft().getElement());
        assertEquals("c", tree.getRoot().getLeft().getRight().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getLeft().getElement());
        assertEquals("b", tree.getRoot().getLeft().getLeft().getRight().getElement());
    }

    @Test
    public void rotateSimpleAtRoot() {
        final RootReferenceFactoryImpl<String> factory = new RootReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", null, c);
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, q);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(p);

        final LeftRotator<String> rotator = new LeftRotator<>();
        rotator.rotate(factory.wrapTree(tree), tree.getRoot());

        assertEquals("q", tree.getRoot().getElement());
        assertEquals("p", tree.getRoot().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getElement());
    }

    @Test
    public void rotateSimpleAtNodeOnLeft() {
        final RootReferenceFactoryImpl<String> factory = new RootReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", null, c);
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, q);
        final MutableBinaryTreeNodeImpl<String> node = new MutableBinaryTreeNodeImpl<>("node", p, null);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(node);

        final LeftRotator<String> rotator = new LeftRotator<>();
        rotator.rotate(factory.wrapNode(node), tree.getRoot().getLeft());

        assertEquals("node", tree.getRoot().getElement());
        assertEquals("q", tree.getRoot().getLeft().getElement());
        assertEquals("p", tree.getRoot().getLeft().getLeft().getElement());
        assertEquals("c", tree.getRoot().getLeft().getRight().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getLeft().getElement());
    }

    @Test
    public void rotateSimpleAtRight() {
        final RootReferenceFactoryImpl<String> factory = new RootReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", null, c);
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, q);
        final MutableBinaryTreeNodeImpl<String> node = new MutableBinaryTreeNodeImpl<>("node", null, p);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(node);

        final LeftRotator<String> rotator = new LeftRotator<>();
        rotator.rotate(factory.wrapNode(node), tree.getRoot().getRight());

        assertEquals("node", tree.getRoot().getElement());
        assertEquals("q", tree.getRoot().getRight().getElement());
        assertEquals("p", tree.getRoot().getRight().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getRight().getElement());
        assertEquals("a", tree.getRoot().getRight().getLeft().getLeft().getElement());
    }

    @Test(expected = IllegalStateException.class)
    public void attemptWithoutPivot() {
        final RootReferenceFactoryImpl<String> factory = new RootReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, null);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(p);

        final LeftRotator<String> rotator = new LeftRotator<>();
        rotator.rotate(factory.wrapTree(tree), tree.getRoot());
    }

    @Test
    public void forDirection() {
        final LeftRotator<String> rotator = new LeftRotator<>();
        assertEquals(Direction.LEFT, rotator.forDirection());
    }

    @Test
    public void forClass() {
        final LeftRotator<String> rotator = new LeftRotator<>();
        assertEquals(MutableBinaryTreeNodeImpl.class, rotator.forClass());
    }
}
