package com.mattunderscore.trees.walkers;

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

public final class InOrderWalkerTest {
    private static InOrderWalker walker;
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

        walker = new InOrderWalker();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void empty() {
        walker.accept(emptyTree, nodeWalker);
        Mockito.verify(nodeWalker).onEmpty();
        Mockito.verify(nodeWalker).onCompleted();
    }

    @Test
    public void emptyElements() {
        walker.accept(emptyTree, new NodeToElementWalker<>(elementWalker));
        Mockito.verify(elementWalker).onEmpty();
        Mockito.verify(elementWalker).onCompleted();
    }

    @Test
    public void elements() {
        Mockito.when(elementWalker.onNext(Matchers.isA(String.class))).thenReturn(true);
        walker.accept(tree, new NodeToElementWalker<>(elementWalker));
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
        walker.accept(tree, new NodeToElementWalker<>(elementWalker));
        Mockito.verify(elementWalker).onNext("a");
        Mockito.verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void nodes() {
        Mockito.when(nodeWalker.onNext(Matchers.isA(LinkedTree.class))).thenReturn(true);
        walker.accept(tree, nodeWalker);
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
