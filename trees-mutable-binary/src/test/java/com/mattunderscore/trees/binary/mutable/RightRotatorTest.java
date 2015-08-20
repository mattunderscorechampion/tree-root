package com.mattunderscore.trees.binary.mutable;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.spi.Rotator;

/**
 * Unit tests for {@link RightRotator}.
 *
 * @author Matt Champion on 17/08/2015
 */
public final class RightRotatorTest {

    @Test
    public void rotateAtRoot() {
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, b);
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", p, c);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(q);

        final RightRotator<String> rotator = new RightRotator<>();
        rotator.rotate(rotator.wrapTree(tree), tree.getRoot());

        assertEquals("p", tree.getRoot().getElement());
        assertEquals("a", tree.getRoot().getLeft().getElement());
        assertEquals("q", tree.getRoot().getRight().getElement());
        assertEquals("b", tree.getRoot().getRight().getLeft().getElement());
        assertEquals("c", tree.getRoot().getRight().getRight().getElement());
    }

    @Test
    public void rotateAtNode() {
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, b);
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", p, c);
        final MutableBinaryTreeNodeImpl<String> node = new MutableBinaryTreeNodeImpl<>("node", q, null);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(node);

        final RightRotator<String> rotator = new RightRotator<>();
        rotator.rotate(rotator.wrapNode(node), tree.getRoot().getLeft());

        assertEquals("node", tree.getRoot().getElement());
        assertEquals("p", tree.getRoot().getLeft().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getElement());
        assertEquals("q", tree.getRoot().getLeft().getRight().getElement());
        assertEquals("b", tree.getRoot().getLeft().getRight().getLeft().getElement());
        assertEquals("c", tree.getRoot().getLeft().getRight().getRight().getElement());
    }

    @Test
    public void rotateSimpleAtRoot() {
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, null);
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", p, c);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(q);

        final RightRotator<String> rotator = new RightRotator<>();
        rotator.rotate(rotator.wrapTree(tree), tree.getRoot());

        assertEquals("p", tree.getRoot().getElement());
        assertEquals("a", tree.getRoot().getLeft().getElement());
        assertEquals("q", tree.getRoot().getRight().getElement());
        assertEquals("c", tree.getRoot().getRight().getRight().getElement());
    }

    @Test
    public void rotateSimpleAtNodeOnLeft() {
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, null);
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", p, c);
        final MutableBinaryTreeNodeImpl<String> node = new MutableBinaryTreeNodeImpl<>("node", q, null);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(node);

        final RightRotator<String> rotator = new RightRotator<>();
        rotator.rotate(rotator.wrapNode(node), tree.getRoot().getLeft());

        assertEquals("node", tree.getRoot().getElement());
        assertEquals("p", tree.getRoot().getLeft().getElement());
        assertEquals("a", tree.getRoot().getLeft().getLeft().getElement());
        assertEquals("q", tree.getRoot().getLeft().getRight().getElement());
        assertEquals("c", tree.getRoot().getLeft().getRight().getRight().getElement());
    }

    @Test
    public void rotateSimpleAtRight() {
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", a, null);
        final MutableBinaryTreeNodeImpl<String> q = new MutableBinaryTreeNodeImpl<>("q", p, c);
        final MutableBinaryTreeNodeImpl<String> node = new MutableBinaryTreeNodeImpl<>("node", null, q);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(node);

        final RightRotator<String> rotator = new RightRotator<>();
        rotator.rotate(rotator.wrapNode(node), tree.getRoot().getRight());

        assertEquals("node", tree.getRoot().getElement());
        assertEquals("p", tree.getRoot().getRight().getElement());
        assertEquals("a", tree.getRoot().getRight().getLeft().getElement());
        assertEquals("q", tree.getRoot().getRight().getRight().getElement());
        assertEquals("c", tree.getRoot().getRight().getRight().getRight().getElement());
    }

    @Test(expected = IllegalStateException.class)
    public void unableToReplaceUnknown() {
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> b = new MutableBinaryTreeNodeImpl<>("b");
        final MutableBinaryTreeNodeImpl<String> c = new MutableBinaryTreeNodeImpl<>("c");
        final Rotator.RootReference<MutableBinaryTreeNode<String>> reference = new RightRotator<String>().wrapNode(a);
        reference.replaceRoot(b, c);
    }

    @Test(expected = IllegalStateException.class)
    public void attemptWithoutPivot() {
        final MutableBinaryTreeNodeImpl<String> a = new MutableBinaryTreeNodeImpl<>("a");
        final MutableBinaryTreeNodeImpl<String> p = new MutableBinaryTreeNodeImpl<>("p", null, a);

        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(p);

        final RightRotator<String> rotator = new RightRotator<>();
        rotator.rotate(rotator.wrapTree(tree), tree.getRoot());
    }

    @Test
    public void forClass() {
        final RightRotator<String> rotator = new RightRotator<>();
        assertEquals(MutableBinaryTreeNodeImpl.class, rotator.forClass());
    }
}
