package com.mattunderscore.trees.tests.walkers;

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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void empty() {
        walkers.walkInOrder(emptyTree, nodeWalker);
        Mockito.verify(nodeWalker).onEmpty();
        Mockito.verify(nodeWalker).onCompleted();
    }

    @Test
    public void emptyElements() {
        walkers.walkElementsInOrder(emptyTree, elementWalker);
        Mockito.verify(elementWalker).onEmpty();
        Mockito.verify(elementWalker).onCompleted();
    }

    @Test
    public void elements() {
        Mockito.when(elementWalker.onNext(Matchers.isA(String.class))).thenReturn(true);
        walkers.walkElementsInOrder(tree, elementWalker);
        Mockito.verify(elementWalker).onNext("a");
        Mockito.verify(elementWalker).onNext("b");
        Mockito.verify(elementWalker).onNext("c");
        Mockito.verify(elementWalker).onNext("d");
        Mockito.verify(elementWalker).onNext("e");
        Mockito.verify(elementWalker).onNext("f");
        Mockito.verify(elementWalker).onNext("g");
        Mockito.verify(elementWalker).onNext("h");
        Mockito.verify(elementWalker).onNext("i");
        Mockito.verify(elementWalker).onCompleted();
        Mockito.verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void firstElement() {
        Mockito.when(elementWalker.onNext(Matchers.isA(String.class))).thenReturn(false);
        walkers.walkElementsInOrder(tree, elementWalker);
        Mockito.verify(elementWalker).onNext("a");
        Mockito.verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void nodes() {
        Mockito.when(nodeWalker.onNext(Matchers.isA(Node.class))).thenReturn(true);
        walkers.walkInOrder(tree, nodeWalker);
        Mockito.verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("a")));
        Mockito.verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("b")));
        Mockito.verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("c")));
        Mockito.verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("d")));
        Mockito.verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("e")));
        Mockito.verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("f")));
        Mockito.verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("g")));
        Mockito.verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("h")));
        Mockito.verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("i")));
        Mockito.verify(nodeWalker).onCompleted();
        Mockito.verifyNoMoreInteractions(nodeWalker);
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
