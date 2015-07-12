/* Copyright © 2015 Matthew Champion
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
 * Neither the name of mattunderscore.com nor the
      names of its contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL MATTHEW CHAMPION BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

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
import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
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
    private TreeWalker<MutableSettableStructuredNode<String>> walker;
    private InOrder inOrder;

    @BeforeClass
    public static void setUpClass() {
        final LinkedTree.Constructor<String> constructor = new LinkedTree.Constructor<>();
        tree = constructor.build(
            "f",
            constructor.build(
                "b",
                constructor.build(
                    "a"),
                constructor.build(
                    "d",
                    constructor.build(
                        "c"),
                    constructor.build(
                        "e")
                )
            ),
            constructor.build(
                "i",
                constructor.build(
                    "h",
                    constructor.build(
                        "g"))));

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
