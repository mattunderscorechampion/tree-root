package com.mattunderscore.trees.pathcopy.holder;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Test for {@link Constructor}.
 * @author Matt Champion on 07/07/2015.
 */
public class ConstructorTest {

    @Test
    public void key() {
        final Constructor<String> constructor = new Constructor<>();
        assertEquals(PathCopyTree.class, constructor.forClass());
    }

    @Test
    public void build0() {
        final Constructor<String> constructor = new Constructor<>();
        final PathCopyTree<String> tree = constructor.build("root", new PathCopyTree[] {});
        assertEquals("root", tree.getRoot().getElement());
        assertTrue(tree.getRoot().isLeaf());
        assertFalse(tree.isEmpty());
        assertTrue(tree instanceof PathCopyTree);
    }

    @Test
    public void build1() {
        final Constructor<String> constructor = new Constructor<>();
        final PathCopyTree<String> tree = constructor.build("root", new PathCopyTree[] {
            constructor.build("left", new PathCopyTree[] {})});
        assertEquals("root", tree.getRoot().getElement());
        assertFalse(tree.getRoot().isLeaf());
        assertFalse(tree.isEmpty());
        assertTrue(tree instanceof PathCopyTree);
        final Iterator<MutableNode<String>> iterator = tree.getRoot().childIterator();
        assertEquals("left", iterator.next().getElement());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void build2() {
        final Constructor<String> constructor = new Constructor<>();
        final PathCopyTree<String> tree = constructor.build("root", new PathCopyTree[] {
            constructor.build("left", new PathCopyTree[] {
                constructor.build("left-left", new PathCopyTree[] {})
            })});
        assertEquals("root", tree.getRoot().getElement());
        assertFalse(tree.getRoot().isLeaf());
        assertFalse(tree.isEmpty());
        assertTrue(tree instanceof PathCopyTree);
        final Iterator<MutableNode<String>> iterator = tree.getRoot().childIterator();
        final MutableNode<String> child = iterator.next();
        assertEquals("left", child.getElement());
        assertFalse(child.isLeaf());
        assertFalse(iterator.hasNext());
        final Iterator<? extends MutableNode<String>> deeperIterator = child.childIterator();
        final MutableNode<String> leaf = deeperIterator.next();
        assertEquals("left-left", leaf.getElement());
        assertTrue(leaf.isLeaf());
        assertFalse(deeperIterator.hasNext());
    }
}
