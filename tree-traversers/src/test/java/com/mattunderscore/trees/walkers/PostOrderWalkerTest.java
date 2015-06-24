package com.mattunderscore.trees.walkers;

import static com.mattunderscore.trees.walkers.MatcherUtilities.linkedTreeElementMatcher;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.ClosedMutableSettableStructuredNode;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.traversal.Walker;

public final class PostOrderWalkerTest {
    private static PostOrderWalker walker;
    private static LinkedTree<String> tree;
    private static LinkedTree<String> emptyTree;

    @Mock
    private Walker<ClosedMutableSettableStructuredNode<String>> nodeWalker;
    @Mock
    private Walker<String> elementWalker;
    private InOrder elementOrder;
    private InOrder nodeOrder;

    @BeforeClass
    public static void setUpClass() {
        final TreeConstructor<String, ClosedMutableSettableStructuredNode<String>, LinkedTree<String>> constructor = new LinkedTree.Constructor<>();
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

        walker = new PostOrderWalker();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        nodeOrder = inOrder(nodeWalker);
        elementOrder = inOrder(elementWalker);
    }

    @Test
    public void empty() {
        walker.accept(emptyTree, nodeWalker);
        nodeOrder.verify(nodeWalker).onEmpty();
        nodeOrder.verify(nodeWalker).onCompleted();
        nodeOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(nodeWalker);
    }

    @Test
    public void emptyElements() {
        walker.accept(emptyTree, new NodeToElementWalker<>(elementWalker));
        elementOrder.verify(elementWalker).onEmpty();
        elementOrder.verify(elementWalker).onCompleted();
        elementOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void elements() {
        when(elementWalker.onNext(Matchers.isA(String.class))).thenReturn(true);
        walker.accept(tree, new NodeToElementWalker<>(elementWalker));
        elementOrder.verify(elementWalker).onNext("a");
        elementOrder.verify(elementWalker).onNext("c");
        elementOrder.verify(elementWalker).onNext("e");
        elementOrder.verify(elementWalker).onNext("d");
        elementOrder.verify(elementWalker).onNext("b");
        elementOrder.verify(elementWalker).onNext("g");
        elementOrder.verify(elementWalker).onNext("h");
        elementOrder.verify(elementWalker).onNext("i");
        elementOrder.verify(elementWalker).onNext("f");
        elementOrder.verify(elementWalker).onCompleted();
        elementOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void firstElement() {
        when(elementWalker.onNext(Matchers.isA(String.class))).thenReturn(false);
        walker.accept(tree, new NodeToElementWalker<>(elementWalker));
        verify(elementWalker).onNext("a");
        verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void firstTwoElements() {
        when(elementWalker.onNext(Matchers.isA(String.class))).thenReturn(true, false);
        walker.accept(tree, new NodeToElementWalker<>(elementWalker));
        elementOrder.verify(elementWalker).onNext("a");
        elementOrder.verify(elementWalker).onNext("c");
        elementOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(elementWalker);
    }

    @Test
    public void nodes() {
        when(nodeWalker.onNext(Matchers.isA(LinkedTree.class))).thenReturn(true);
        walker.accept(tree, nodeWalker);
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("a"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("c"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("e"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("d"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("b"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("g"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("h"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("i"));
        nodeOrder.verify(nodeWalker).onNext(linkedTreeElementMatcher("f"));
        nodeOrder.verify(nodeWalker).onCompleted();
        nodeOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(nodeWalker);
    }
}
