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

package com.mattunderscore.trees.binary.mutable;

import static org.mockito.Mockito.isA;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.construction.TreeBuilderFactory;

/**
 * Test for {@link NodeConverter}.
 * @author Matt Champion on 04/05/15
 */
public final class MutableBinaryTreeNodeConverterTest {
    @Mock
    private TreeBuilderFactory builderFactory;
    @Mock
    private TopDownTreeRootBuilder rootBuilder;
    @Mock
    private TopDownTreeRootBuilder.TopDownTreeBuilder builder;

    @Before
    public void setUp() {
        initMocks(this);

        when(builderFactory.topDownBuilder()).thenReturn(rootBuilder);
        when(rootBuilder.root(isA(String.class))).thenReturn(builder);
    }

    @Test
    public void convertToSelf() {
        final MutableBinaryTreeImpl<String> tree = new MutableBinaryTreeImpl<>(
            new MutableBinaryTreeNodeImpl<>(
                "a",
                new MutableBinaryTreeNodeImpl<>("b"),
                new MutableBinaryTreeNodeImpl<>("c")));

        final NodeConverter<String> converter = new NodeConverter<>();
        converter.setTreeBuilderFactory(builderFactory);

        converter.treeFromRootNode(tree.getRoot());

        verify(rootBuilder).root("a");
        verify(builder).addChild("b");
        verify(builder).addChild("c");
        verify(builder).build(MutableBinaryTreeImpl.class);
    }
}
