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

package com.mattunderscore.trees.spi.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder.TopDownTreeBuilder;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;


/**
 * Unit tests for {@link AbstractNodeToRelatedTreeConverter}.
 * @author Matt Champion on 12/07/2015.
 */
public final class AbstractNodeToRelatedTreeConverterTest {
    @Mock
    private TopDownTreeRootBuilder<String, Node<String>> rootBuilder;
    @Mock
    private TopDownTreeBuilder<String, Node<String>> builder;
    @Mock
    private Node<String> root;
    @Mock
    private Node<String> left;
    @Mock
    private Node<String> right;
    @Mock
    private Node<String> bottom;
    @Mock
    private Tree<String, Node<String>> tree;
    @Mock
    private Tree<String, Node<String>> convertedTree;

    @Before
    public void setUp() {
        initMocks(this);

        final List<Node<String>> depth1 = new ArrayList<>();
        depth1.add(left);
        depth1.add(right);
        final List<Node<String>> depth2 = new ArrayList<>();
        depth2.add(bottom);

        when(tree.getRoot()).thenReturn(root);
        when(root.childIterator()).thenAnswer(invocationOnMock -> depth1.iterator());
        when(root.getElement()).thenReturn("root");
        when(root.getNumberOfChildren()).thenReturn(2);
        when(root.isLeaf()).thenReturn(false);
        when(left.childIterator()).thenAnswer(invocationOnMock -> Collections.emptyIterator());
        when(left.getElement()).thenReturn("left");
        when(left.getNumberOfChildren()).thenReturn(0);
        when(left.isLeaf()).thenReturn(true);
        when(right.childIterator()).thenAnswer(invocationOnMock -> depth2.iterator());
        when(right.getElement()).thenReturn("right");
        when(right.getNumberOfChildren()).thenReturn(1);
        when(right.isLeaf()).thenReturn(false);
        when(bottom.childIterator()).thenAnswer(invocationOnMock -> Collections.emptyIterator());
        when(bottom.getElement()).thenReturn("bottom");
        when(bottom.getNumberOfChildren()).thenReturn(0);
        when(bottom.isLeaf()).thenReturn(true);

        when(rootBuilder.root(isA(String.class))).thenReturn(builder);
        when(builder.addChild(isA(String.class))).thenReturn(builder);
        when(builder.build(Tree.class)).thenReturn(convertedTree);
    }

    @Test
    public void treeFromRootNode() {
        final Converter converter = new Converter(Node.class, Tree.class, rootBuilder);
        final Tree<String, Node<String>> newTree = converter.treeFromRootNode(root);

        verify(rootBuilder).root("root");
        verify(builder).addChild("left");
        verify(builder).addChild("right");
        verify(builder).addChild("bottom");

        verify(builder).build(Tree.class);

        assertSame(newTree, convertedTree);
    }

    @Test
    public void forClass() {
        final Converter converter = new Converter(Node.class, Tree.class, rootBuilder);
        assertEquals(Node.class, converter.forClass());
    }

    private final class Converter extends AbstractNodeToRelatedTreeConverter<String, Node<String>, Tree<String, Node<String>>> {
        private final TopDownTreeRootBuilder<String, Node<String>> builder;

        public Converter(Class targetNodeClass, Class targetTreeClass, TopDownTreeRootBuilder<String, Node<String>> builder) {
            super(targetNodeClass, targetTreeClass);
            this.builder = builder;
        }

        @Override
        protected TopDownTreeRootBuilder<String, Node<String>> getBuilder() {
            return builder;
        }
    }
}
