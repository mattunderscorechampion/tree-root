package com.mattunderscore.trees.common;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.traversal.TreeWalkerFactory;
import com.mattunderscore.trees.traversal.Walker;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public final class TreeWalkerFactoryImplTest {
    private static TreeWalkerFactory walkerFactory;
    private static LinkedTree<String> tree;

    @BeforeClass
    public static void setUp() {
        final Trees trees = new TreesImpl();
        walkerFactory = trees.treeWalkers();
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        tree = builder.create("root", builder.create("a")).build(LinkedTree.<String>typeKey());
    }

    @Test
    public void testWalkPreOrder() {
        final TestWalker<LinkedTree<String>> walker = new TestWalker<>();
        walkerFactory.walkPreOrder(tree, walker);
        assertEquals(2, walker.count);
    }

    @Test
    public void testWalkInOrder() {
        final TestWalker<LinkedTree<String>> walker = new TestWalker<>();
        walkerFactory.walkInOrder(tree, walker);
        assertEquals(2, walker.count);
    }

    @Test
    public void testWalkPostOrder() {
        final TestWalker<LinkedTree<String>> walker = new TestWalker<>();
        walkerFactory.walkPostOrder(tree, walker);
        assertEquals(2, walker.count);
    }

    @Test
    public void testWalkBreadthFirst() {
        final TestWalker<LinkedTree<String>> walker = new TestWalker<>();
        walkerFactory.walkBreadthFirst(tree, walker);
        assertEquals(2, walker.count);
    }

    @Test
    public void testWalkElementsPreOrder() {
        final TestWalker<String> walker = new TestWalker<>();
        walkerFactory.walkElementsPreOrder(tree, walker);
        assertEquals(2, walker.count);
    }

    @Test
    public void testWalkElementsInOrder() {
        final TestWalker<String> walker = new TestWalker<>();
        walkerFactory.walkElementsInOrder(tree, walker);
        assertEquals(2, walker.count);
    }

    @Test
    public void testWalkElementsPostOrder() {
        final TestWalker<String> walker = new TestWalker<>();
        walkerFactory.walkElementsPostOrder(tree, walker);
        assertEquals(2, walker.count);
    }

    @Test
    public void testWalkElementsBreadthFirst() {
        final TestWalker<String> walker = new TestWalker<>();
        walkerFactory.walkElementsBreadthFirst(tree, walker);
        assertEquals(2, walker.count);
    }

    public static final class TestWalker<N> implements Walker<N> {
        private int count = 0;

        @Override
        public void onEmpty() {

        }

        @Override
        public boolean onNext(N node) {
            count++;
            return true;
        }

        @Override
        public void onCompleted() {
        }
    }
}