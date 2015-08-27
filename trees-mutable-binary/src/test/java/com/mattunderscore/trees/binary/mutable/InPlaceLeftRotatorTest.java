package com.mattunderscore.trees.binary.mutable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.transformation.RotationDirection;

/**
 * Unit tests for {@link InPlaceLeftRotator}.
 *
 * @author Matt Champion on 17/08/2015
 */
public final class InPlaceLeftRotatorTest {

    @Test
    public void rotateAtRoot() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", b, c);
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, q);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(p);

        final InPlaceLeftRotator<String> rotator = new InPlaceLeftRotator<>();
        rotator.rotate(factory.wrap(tree), tree.getRoot());

        assertEquals("q", tree.getRoot().getElement());
        assertEquals("p", tree.getRoot().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getElement());
        assertEquals("b", tree.getRoot().getLeft().getRight().getElement());
    }

    @Test
    public void rotateAtNode() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", b, c);
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, q);
        final MutableBinaryTreeNodeImpl<String> node = new MutableBinaryTreeNodeImpl<>("node", p, null);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(node);

        final InPlaceLeftRotator<String> rotator = new InPlaceLeftRotator<>();
        rotator.rotate(factory.wrap(node), tree.getRoot().getLeft());

        assertEquals("node", tree.getRoot().getElement());
        assertEquals("q", tree.getRoot().getLeft().getElement());
        assertEquals("p", tree.getRoot().getLeft().getLeft().getElement());
        assertEquals("c", tree.getRoot().getLeft().getRight().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getLeft().getElement());
        assertEquals("b", tree.getRoot().getLeft().getLeft().getRight().getElement());
    }

    @Test
    public void rotateSimpleAtRoot() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", null, c);
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, q);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(p);

        final InPlaceLeftRotator<String> rotator = new InPlaceLeftRotator<>();
        rotator.rotate(factory.wrap(tree), tree.getRoot());

        assertEquals("q", tree.getRoot().getElement());
        assertEquals("p", tree.getRoot().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getElement());
    }

    @Test
    public void rotateSimpleAtNodeOnLeft() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", null, c);
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, q);
        final MutableBinaryTreeNodeImpl<String> node = new MutableBinaryTreeNodeImpl<>("node", p, null);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(node);

        final InPlaceLeftRotator<String> rotator = new InPlaceLeftRotator<>();
        rotator.rotate(factory.wrap(node), tree.getRoot().getLeft());

        assertEquals("node", tree.getRoot().getElement());
        assertEquals("q", tree.getRoot().getLeft().getElement());
        assertEquals("p", tree.getRoot().getLeft().getLeft().getElement());
        assertEquals("c", tree.getRoot().getLeft().getRight().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getLeft().getElement());
    }

    @Test
    public void rotateSimpleAtRight() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", null, c);
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, q);
        final MutableBinaryTreeNodeImpl<String> node = new MutableBinaryTreeNodeImpl<>("node", null, p);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(node);

        final InPlaceLeftRotator<String> rotator = new InPlaceLeftRotator<>();
        rotator.rotate(factory.wrap(node), tree.getRoot().getRight());

        assertEquals("node", tree.getRoot().getElement());
        assertEquals("q", tree.getRoot().getRight().getElement());
        assertEquals("p", tree.getRoot().getRight().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getRight().getElement());
        assertEquals("a", tree.getRoot().getRight().getLeft().getLeft().getElement());
    }

    @Test(expected = IllegalStateException.class)
    public void attemptWithoutPivot() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, null);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(p);

        final InPlaceLeftRotator<String> rotator = new InPlaceLeftRotator<>();
        rotator.rotate(factory.wrap(tree), tree.getRoot());
    }

    @Test
    public void forDirection() {
        final InPlaceLeftRotator<String> rotator = new InPlaceLeftRotator<>();
        assertEquals(RotationDirection.LEFT, rotator.forDirection());
    }

    @Test
    public void forClass() {
        final InPlaceLeftRotator<String> rotator = new InPlaceLeftRotator<>();
        assertEquals(MutableBinaryTreeNodeImpl.class, rotator.forClass());
    }
}
