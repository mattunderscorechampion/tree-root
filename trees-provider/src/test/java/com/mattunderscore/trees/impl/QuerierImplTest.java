package com.mattunderscore.trees.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.mattunderscore.simple.collections.SimpleCollection;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.binary.mutable.Constructor;
import com.mattunderscore.trees.binary.mutable.EmptyConstructor;
import com.mattunderscore.trees.binary.mutable.MutableBinaryTreeImpl;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.query.Querier;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link QuerierImpl}.
 *
 * @author Matt Champion on 27/08/2015
 */
public final class QuerierImplTest {

    @Test(expected = IllegalArgumentException.class)
    public void heightEmpty() {
        final Tree<String, MutableSettableStructuredNode<String>> node = new LinkedTree<>(null);

        final Querier querier = new QuerierImpl();

        querier.height(node);
    }

    @Test(expected = NullPointerException.class)
    public void heightNull() {
        final Querier querier = new QuerierImpl();

        querier.height((Node<String>)null);
    }

    @Test
    public void heightLeaf() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");

        final Querier querier = new QuerierImpl();

        assertEquals(0, querier.height(node));
    }

    @Test
    public void heightSimple() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");
        node.addChild("b");

        final Querier querier = new QuerierImpl();

        assertEquals(1, querier.height(node));
    }

    @Test
    public void heightManyPaths() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");
        node.addChild("b");
        node.addChild("c");

        final Querier querier = new QuerierImpl();

        assertEquals(1, querier.height(node));
    }

    @Test
    public void heightSinglePath() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");
        node.addChild("b");
        node
            .addChild("c")
            .addChild("d");

        final Querier querier = new QuerierImpl();

        assertEquals(2, querier.height(node));
    }

    @Test
    public void pathsEmpty() {
        final Tree<String, MutableSettableStructuredNode<String>> tree = new LinkedTree<>(null);

        final Querier querier = new QuerierImpl();

        assertEquals(0, querier.pathsToLeaves(tree).size());
    }

    @Test(expected = NullPointerException.class)
    public void pathsNull() {
        final Querier querier = new QuerierImpl();

        querier.pathsToLeaves((Node<String>) null);
    }

    @Test
    public void pathSelf() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");

        final Querier querier = new QuerierImpl();

        final SimpleCollection<List<MutableSettableStructuredNode<String>>> paths = querier.pathsToLeaves(node);

        assertEquals(1, paths.size());

        final List<MutableSettableStructuredNode<String>> path = paths.iterator().next();

        assertEquals(1, path.size());
        assertEquals("a", path.get(0).getElement());
    }

    @Test
    public void simplePath() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");
        node.addChild("b");

        final Querier querier = new QuerierImpl();

        final SimpleCollection<List<MutableSettableStructuredNode<String>>> paths = querier.pathsToLeaves(node);

        assertEquals(1, paths.size());

        final List<MutableSettableStructuredNode<String>> path = paths.iterator().next();

        assertEquals(2, path.size());
        assertEquals("a", path.get(0).getElement());
        assertEquals("b", path.get(1).getElement());
    }

    @Test
    public void twoPaths() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");
        node.addChild("b");
        node.addChild("c");

        final Querier querier = new QuerierImpl();

        final SimpleCollection<List<MutableSettableStructuredNode<String>>> paths = querier.pathsToLeaves(node);

        assertEquals(2, paths.size());

        final Iterator<List<MutableSettableStructuredNode<String>>> iterator = paths.iterator();
        final List<MutableSettableStructuredNode<String>> path0 = iterator.next();

        assertEquals(2, path0.size());
        assertEquals("a", path0.get(0).getElement());
        // Cannot tell if next element is b or c

        final List<MutableSettableStructuredNode<String>> path1 = iterator.next();

        assertEquals(2, path1.size());
        assertEquals("a", path1.get(0).getElement());
        // Cannot tell if next element is b or c
    }

    @Test
    public void differentLengthPaths() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");
        node.addChild("b");
        node
            .addChild("c")
            .addChild("d");

        final Querier querier = new QuerierImpl();

        final SimpleCollection<List<MutableSettableStructuredNode<String>>> paths = querier.pathsToLeaves(node);

        assertEquals(2, paths.size());

        final Iterator<List<MutableSettableStructuredNode<String>>> iterator = paths.iterator();
        final List<MutableSettableStructuredNode<String>> path0 = iterator.next();
        final List<MutableSettableStructuredNode<String>> path1 = iterator.next();

        if (path0.size() == 2 && path1.size() == 3) {
            assertEquals(2, path0.size());
            assertEquals("a", path0.get(0).getElement());
            assertEquals("b", path0.get(1).getElement());

            assertEquals("a", path1.get(0).getElement());
            assertEquals("c", path1.get(1).getElement());
            assertEquals("d", path1.get(2).getElement());
        }
        else if (path0.size() == 3 && path1.size() == 2) {
            assertEquals("a", path0.get(0).getElement());
            assertEquals("c", path0.get(1).getElement());
            assertEquals("d", path0.get(2).getElement());

            assertEquals(2, path1.size());
            assertEquals("a", path1.get(0).getElement());
            assertEquals("b", path1.get(1).getElement());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyTreeBalanced() {
        final Querier querier = new QuerierImpl();

        querier.isBalanced(new EmptyConstructor<>().build());
    }

    @Test
    public void leafBalanced() {
        final Querier querier = new QuerierImpl();

        assertTrue(querier.isBalanced(new Constructor<String>().build("a", new MutableBinaryTreeImpl[] {})));
    }

    @Test
    public void offByOneBalanced() {
        final Constructor<String> constructor = new Constructor<>();
        final Tree<String, MutableBinaryTreeNode<String>> tree =
            constructor.build("a", new MutableBinaryTreeImpl[] {
                constructor.build("b", new MutableBinaryTreeImpl[]{})});

        final Querier querier = new QuerierImpl();

        assertTrue(querier.isBalanced(tree));
    }

    @Test
    public void unbalanced() {
        final Constructor<String> constructor = new Constructor<>();
        final Tree<String, MutableBinaryTreeNode<String>> tree =
            constructor.build("a", new MutableBinaryTreeImpl[] {
                constructor.build("b", new MutableBinaryTreeImpl[]{
                    constructor.build("c", new MutableBinaryTreeImpl[]{
                        constructor.build("d", new MutableBinaryTreeImpl[] {})})})});

        final Querier querier = new QuerierImpl();

        assertFalse(querier.isBalanced(tree));
    }

    @Test
    public void balanced() {
        final Constructor<String> constructor = new Constructor<>();
        final Tree<String, MutableBinaryTreeNode<String>> tree =
            constructor.build("a", new MutableBinaryTreeImpl[] {
                constructor.build("b", new MutableBinaryTreeImpl[]{}),
                constructor.build("b", new MutableBinaryTreeImpl[]{})});

        final Querier querier = new QuerierImpl();

        assertTrue(querier.isBalanced(tree));
    }
}
