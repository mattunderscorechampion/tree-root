package com.mattunderscore.trees.common.walkers;

import static org.mockito.Matchers.argThat;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mock;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.common.LinkedTree;
import com.mattunderscore.trees.common.TreesImpl;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.traversal.TreeWalkerFactory;
import com.mattunderscore.trees.traversal.Walker;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

public final class InOrderWalkerTest {
    private static TreeWalkerFactory walkers;
    private static Tree<String, Node<String>> tree;
    private static Tree<String, Node<String>> emptyTree;

    @Mock
    private Walker<Node<String>> nodeWalker;
    @Mock
    private Walker<String> elementWalker;

    @BeforeClass
    public static void setUpClass() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        tree = builder.create("f",
            builder.create("b",
                builder.create("a"),
                builder.create("d",
                    builder.create("c"),
                    builder.create("e"))),
            builder.create("i",
                builder.create("h",
                    builder.create("g")))).build(LinkedTree.class);

        walkers = trees.treeWalkers();

        emptyTree = trees.treeBuilders().bottomUpBuilder().build(LinkedTree.class);
    }

    @Before
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void empty() {
        walkers.walkInOrder(emptyTree, nodeWalker);
        verify(nodeWalker).onEmpty();
        verify(nodeWalker).onCompleted();
    }

    @Test
    public void emptyElements() {
        walkers.walkElementsInOrder(emptyTree, elementWalker);
        verify(elementWalker).onEmpty();
        verify(elementWalker).onCompleted();
    }

    @Test
    public void elements() {
        when(elementWalker.onNext(isA(String.class))).thenReturn(true);
        walkers.walkElementsInOrder(tree, elementWalker);
        verify(elementWalker).onNext("a");
        verify(elementWalker).onNext("b");
        verify(elementWalker).onNext("c");
        verify(elementWalker).onNext("d");
        verify(elementWalker).onNext("e");
        verify(elementWalker).onNext("f");
        verify(elementWalker).onNext("g");
        verify(elementWalker).onNext("h");
        verify(elementWalker).onNext("i");
        verify(elementWalker).onCompleted();
        verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void firstElement() {
        when(elementWalker.onNext(isA(String.class))).thenReturn(false);
        walkers.walkElementsInOrder(tree, elementWalker);
        verify(elementWalker).onNext("a");
        verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void nodes() {
        when(nodeWalker.onNext(isA(Node.class))).thenReturn(true);
        walkers.walkInOrder(tree, nodeWalker);
        verify(nodeWalker).onNext(argThat(new ElementMatcher("a")));
        verify(nodeWalker).onNext(argThat(new ElementMatcher("b")));
        verify(nodeWalker).onNext(argThat(new ElementMatcher("c")));
        verify(nodeWalker).onNext(argThat(new ElementMatcher("d")));
        verify(nodeWalker).onNext(argThat(new ElementMatcher("e")));
        verify(nodeWalker).onNext(argThat(new ElementMatcher("f")));
        verify(nodeWalker).onNext(argThat(new ElementMatcher("g")));
        verify(nodeWalker).onNext(argThat(new ElementMatcher("h")));
        verify(nodeWalker).onNext(argThat(new ElementMatcher("i")));
        verify(nodeWalker).onCompleted();
        verifyNoMoreInteractions(nodeWalker);
    }

    public static final class ElementMatcher extends ArgumentMatcher<Node<String>> {
        private final String element;

        public ElementMatcher(String element) {
            this.element = element;
        }

        @Override
        public boolean matches(Object o) {
            final Node<String> node = (Node<String>)o;
            return element.equals(node.getElement());
        }
    }
}
