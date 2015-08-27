package com.mattunderscore.trees.binary.mutable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.transformation.RotationDirection;

/**
 * Unit tests for {@link InPlaceRightRotator}.
 *
 * @author Matt Champion on 17/08/2015
 */
public final class InPlaceRightRotatorTest {

    @Test
    public void rotateAtRoot() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, b);
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", p, c);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(q);

        final InPlaceRightRotator<String> rotator = new InPlaceRightRotator<>();
        rotator.rotate(factory.wrap(tree), tree.getRoot());

        assertEquals("p", tree.getRoot().getElement());
        assertEquals("a", tree.getRoot().getLeft().getElement());
        assertEquals("q", tree.getRoot().getRight().getElement());
        assertEquals("b", tree.getRoot().getRight().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getRight().getElement());
    }

    @Test
    public void rotateAtNode() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, b);
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", p, c);
        final MutableBinaryTreeNodeImpl<String> node = new MutableBinaryTreeNodeImpl<>("node", q, null);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(node);

        final InPlaceRightRotator<String> rotator = new InPlaceRightRotator<>();
        rotator.rotate(factory.wrap(node), tree.getRoot().getLeft());

        assertEquals("node", tree.getRoot().getElement());
        assertEquals("p", tree.getRoot().getLeft().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getElement());
        assertEquals("q", tree.getRoot().getLeft().getRight().getElement());
        assertEquals("b", tree.getRoot().getLeft().getRight().getLeft().getElement());
        assertEquals("c", tree.getRoot().getLeft().getRight().getRight().getElement());
    }

    @Test
    public void rotateSimpleAtRoot() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, null);
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", p, c);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(q);

        final InPlaceRightRotator<String> rotator = new InPlaceRightRotator<>();
        rotator.rotate(factory.wrap(tree), tree.getRoot());

        assertEquals("p", tree.getRoot().getElement());
        assertEquals("a", tree.getRoot().getLeft().getElement());
        assertEquals("q", tree.getRoot().getRight().getElement());
        assertEquals("c", tree.getRoot().getRight().getRight().getElement());
    }

    @Test
    public void rotateSimpleAtNodeOnLeft() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, null);
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", p, c);
        final MutableBinaryTreeNodeImpl<String> node = new MutableBinaryTreeNodeImpl<>("node", q, null);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(node);

        final InPlaceRightRotator<String> rotator = new InPlaceRightRotator<>();
        rotator.rotate(factory.wrap(node), tree.getRoot().getLeft());

        assertEquals("node", tree.getRoot().getElement());
        assertEquals("p", tree.getRoot().getLeft().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getElement());
        assertEquals("q", tree.getRoot().getLeft().getRight().getElement());
        assertEquals("c", tree.getRoot().getLeft().getRight().getRight().getElement());
    }

    @Test
    public void rotateSimpleAtRight() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, null);
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", p, c);
        final MutableBinaryTreeNodeImpl<String> node = new MutableBinaryTreeNodeImpl<>("node", null, q);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(node);

        final InPlaceRightRotator<String> rotator = new InPlaceRightRotator<>();
        rotator.rotate(factory.wrap(node), tree.getRoot().getRight());

        assertEquals("node", tree.getRoot().getElement());
        assertEquals("p", tree.getRoot().getRight().getElement());
        assertEquals("a", tree.getRoot().getRight().getLeft().getElement());
        assertEquals("q", tree.getRoot().getRight().getRight().getElement());
        assertEquals("c", tree.getRoot().getRight().getRight().getRight().getElement());
    }

    @Test(expected = IllegalStateException.class)
    public void attemptWithoutPivot() {
        final ParentReferenceFactoryImpl<String> factory = new ParentReferenceFactoryImpl<>();
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", null, a);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(p);

        final InPlaceRightRotator<String> rotator = new InPlaceRightRotator<>();
        rotator.rotate(factory.wrap(tree), tree.getRoot());
    }

    @Test
    public void forDirection() {
        final InPlaceRightRotator<String> rotator = new InPlaceRightRotator<>();
        assertEquals(RotationDirection.RIGHT, rotator.forDirection());
    }

    @Test
    public void forClass() {
        final InPlaceRightRotator<String> rotator = new InPlaceRightRotator<>();
        assertEquals(MutableBinaryTreeNodeImpl.class, rotator.forClass());
    }
}
