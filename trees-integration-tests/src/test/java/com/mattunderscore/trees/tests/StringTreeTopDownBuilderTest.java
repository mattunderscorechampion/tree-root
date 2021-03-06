/* Copyright © 2014 Matthew Champion
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

package com.mattunderscore.trees.tests;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.construction.NodeAppender;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.immutable.TreeNodeImpl;
import com.mattunderscore.trees.impl.TreesImpl;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.mutable.MutableTreeImpl;
import com.mattunderscore.trees.pathcopy.holder.PathCopyTree;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Tests for top down builders.
 * Builds a tree using the top down tree builder and verifies its structure.
 * @author Matt Champion on 21/06/14.
 */
@RunWith(Parameterized.class)
public final class StringTreeTopDownBuilderTest {
    private static final Trees trees = Trees.get();
    private final Class treeClass;

    public StringTreeTopDownBuilderTest(Class<? extends Tree<String, ? extends OpenNode<String, ?>>> treeClass) {
        this.treeClass = treeClass;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
            {Tree.class}, // 0
            {TreeNodeImpl.class}, // 1, repeats 0 with different key
            {LinkedTree.class}, // 2
            {MutableTree.class}, // 3
            {MutableTreeImpl.class}, // 4, repeats 3 with different key
            {PathCopyTree.class} // 5
        });
    }

    @Test
    public void build() {
        final TopDownTreeRootBuilder<String, ? extends OpenNode<String, ?>> builder = trees.treeBuilders().topDownBuilder();
        final TopDownTreeRootBuilder.TopDownTreeBuilder<String, ? extends OpenNode<String, ?>> nodeApp0 = builder.root("a");
        final NodeAppender<String, ?> nodeApp1 = nodeApp0.addChild("b");
        nodeApp1.addChild("c");
        nodeApp0.addChild("d");
        @SuppressWarnings("unchecked")
        final Tree<String, ? extends OpenNode<String, ?>> tree = nodeApp0.build(treeClass);

        final OpenNode<String, ?> root = tree.getRoot();
        Assert.assertEquals(String.class, root.getElementClass());
        Assert.assertEquals("a", root.getElement());
        final Iterator<? extends OpenNode<String, ?>> iterator0 = root.childIterator();
        Assert.assertEquals(2, root.getNumberOfChildren());
        Assert.assertEquals("a", root.getElement());
        Assert.assertTrue(iterator0.hasNext());
        final OpenNode<String, ?> bNode = iterator0.next();
        Assert.assertTrue(iterator0.hasNext());
        final OpenNode<String, ?> dNode = iterator0.next();
        Assert.assertFalse(iterator0.hasNext());
        Assert.assertEquals("b", bNode.getElement());
        Assert.assertEquals("d", dNode.getElement());
        Assert.assertEquals(1, bNode.getNumberOfChildren());
        final Iterator<? extends OpenNode<String, ?>> iterator1 = bNode.childIterator();
        Assert.assertTrue(iterator1.hasNext());
        final OpenNode<String, ?> cNode = iterator1.next();
        Assert.assertFalse(iterator1.hasNext());
        Assert.assertEquals("c", cNode.getElement());
    }
}
