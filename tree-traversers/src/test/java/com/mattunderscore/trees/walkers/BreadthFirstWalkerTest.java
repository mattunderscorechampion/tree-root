package com.mattunderscore.trees.walkers;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.traversal.Walker;
import com.mattunderscore.trees.tree.Node;

public final class BreadthFirstWalkerTest {
    private static BreadthFirstWalker walker;
    private static LinkedTree<String> tree;
    private static LinkedTree<String> emptyTree;

    @Mock
    private Walker<LinkedTree<String>> nodeWalker;
    @Mock
    private Walker<String> elementWalker;

    @BeforeClass
    public static void setUpClass() {
        final TreeConstructor<String, LinkedTree<String>> constructor = new LinkedTree.Constructor<>();
        tree = constructor.build(
            "f",
            new LinkedTree[]{
                constructor.build(
                    "b",
                    new LinkedTree[]{
                        constructor.build(
                            "a",
                            new LinkedTree[]{}),
                        constructor.build(
                            "d",
                            new LinkedTree[]{
                                constructor.build(
                                    "c",
                                    new LinkedTree[]{}),
                                constructor.build(
                                    "e",
                                    new LinkedTree[]{})
                            })
                    }),
                constructor.build(
                    "i",
                    new LinkedTree[]{
                        constructor.build(
                            "h",
                            new LinkedTree[]{
                                constructor.build(
                                    "g",
                                    new LinkedTree[]{})})})});

        emptyTree = new LinkedTree.EmptyConstructor<String>().build();

        walker = new BreadthFirstWalker();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void empty() {
        walker.accept(emptyTree, nodeWalker);
        verify(nodeWalker).onEmpty();
        verify(nodeWalker).onCompleted();
    }

    @Test
    public void emptyElements() {
        walker.accept(emptyTree, new NodeToElementWalker<>(elementWalker));
        verify(elementWalker).onEmpty();
        verify(elementWalker).onCompleted();
    }

    @Test
    public void elements() {
        when(elementWalker.onNext(Matchers.isA(String.class))).thenReturn(true);
        walker.accept(tree, new NodeToElementWalker<>(elementWalker));
        verify(elementWalker).onNext("f");
        verify(elementWalker).onNext("b");
        verify(elementWalker).onNext("i");
        verify(elementWalker).onNext("a");
        verify(elementWalker).onNext("d");
        verify(elementWalker).onNext("h");
        verify(elementWalker).onNext("c");
        verify(elementWalker).onNext("e");
        verify(elementWalker).onNext("g");
        verify(elementWalker).onCompleted();
        verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void firstElement() {
        when(elementWalker.onNext(Matchers.isA(String.class))).thenReturn(false);
        walker.accept(tree, new NodeToElementWalker<>(elementWalker));
        verify(elementWalker).onNext("f");
        verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void nodes() {
        when(nodeWalker.onNext(Matchers.isA(LinkedTree.class))).thenReturn(true);
        walker.accept(tree, nodeWalker);
        verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("f")));
        verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("b")));
        verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("i")));
        verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("a")));
        verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("d")));
        verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("h")));
        verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("c")));
        verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("e")));
        verify(nodeWalker).onNext(Matchers.argThat(new ElementMatcher("g")));
        verify(nodeWalker).onCompleted();
        verifyNoMoreInteractions(nodeWalker);
    }

    public static final class ElementMatcher extends ArgumentMatcher<LinkedTree<String>> {
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
