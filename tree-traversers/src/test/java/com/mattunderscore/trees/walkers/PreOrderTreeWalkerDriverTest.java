package com.mattunderscore.trees.walkers;

import static com.mattunderscore.trees.walkers.MatcherUtilities.linkedTreeElementMatcher;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.traversal.TreeWalker;

/**
 * Unit tests for {@link PreOrderTreeWalkerDriver}.
 * @author Matt Champion on 09/05/2015.
 */
public final class PreOrderTreeWalkerDriverTest {
    private static PreOrderTreeWalkerDriver driver;
    private static LinkedTree<String> tree;
    private static LinkedTree<String> emptyTree;

    @Mock
    private TreeWalker<LinkedTree<String>> walker;
    private InOrder inOrder;

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

        driver = new PreOrderTreeWalkerDriver();
    }

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        inOrder = inOrder(walker);
    }

    @Test
    public void empty() {
        driver.accept(emptyTree, walker);
        inOrder.verify(walker).onStarted();
        inOrder.verify(walker).onCompleted();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void nodes() {
        driver.accept(tree, walker);
        inOrder.verify(walker).onStarted();
        inOrder.verify(walker).onNode(linkedTreeElementMatcher("f"));
        inOrder.verify(walker).onNodeChildrenStarted(linkedTreeElementMatcher("f"));
        inOrder.verify(walker).onNode(linkedTreeElementMatcher("b"));
        inOrder.verify(walker).onNodeChildrenStarted(linkedTreeElementMatcher("b"));
        inOrder.verify(walker).onNode(linkedTreeElementMatcher("a"));
        inOrder.verify(walker).onNodeNoChildren(linkedTreeElementMatcher("a"));
        inOrder.verify(walker).onNodeChildrenRemaining(linkedTreeElementMatcher("b"));
        inOrder.verify(walker).onNode(linkedTreeElementMatcher("d"));
        inOrder.verify(walker).onNodeChildrenStarted(linkedTreeElementMatcher("d"));
        inOrder.verify(walker).onNode(linkedTreeElementMatcher("c"));
        inOrder.verify(walker).onNodeNoChildren(linkedTreeElementMatcher("c"));
        inOrder.verify(walker).onNodeChildrenRemaining(linkedTreeElementMatcher("d"));
        inOrder.verify(walker).onNode(linkedTreeElementMatcher("e"));
        inOrder.verify(walker).onNodeNoChildren(linkedTreeElementMatcher("e"));
        inOrder.verify(walker).onNodeChildrenCompleted(linkedTreeElementMatcher("d"));
        inOrder.verify(walker).onNodeChildrenCompleted(linkedTreeElementMatcher("b"));
        inOrder.verify(walker).onNodeChildrenRemaining(linkedTreeElementMatcher("f"));
        inOrder.verify(walker).onNode(linkedTreeElementMatcher("i"));
        inOrder.verify(walker).onNodeChildrenStarted(linkedTreeElementMatcher("i"));
        inOrder.verify(walker).onNode(linkedTreeElementMatcher("h"));
        inOrder.verify(walker).onNodeChildrenStarted(linkedTreeElementMatcher("h"));
        inOrder.verify(walker).onNode(linkedTreeElementMatcher("g"));
        inOrder.verify(walker).onNodeNoChildren(linkedTreeElementMatcher("g"));
        inOrder.verify(walker).onNodeChildrenCompleted(linkedTreeElementMatcher("h"));
        inOrder.verify(walker).onNodeChildrenCompleted(linkedTreeElementMatcher("i"));
        inOrder.verify(walker).onNodeChildrenCompleted(linkedTreeElementMatcher("f"));
        inOrder.verify(walker).onCompleted();
        inOrder.verifyNoMoreInteractions();
        verifyNoMoreInteractions(walker);
    }
}
