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

package com.mattunderscore.trees.examples;

import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.mutable.ClosedMutableNode;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;

import java.util.Iterator;

/**
 * @author Matt Champion on 08/08/14.
 */
public final class MutableTreeExamples {
    public void createTreeFromTheBottomUp(BottomUpTreeBuilder<String, ClosedMutableNode<String>> builder, TreeIteratorFactory traverser)
    {
        final MutableTree<String, ClosedMutableNode<String>> tree = builder.create("root",
                builder.create("a",
                        builder.create("1"),
                        builder.create("2"),
                        builder.create("3")),
                builder.create("b",
                        builder.create("+"),
                        builder.create("-"))).build(MutableTree.class);

        final Iterator<ClosedMutableNode<String>> iterator = traverser.preOrderIterator(tree);
        final ClosedMutableNode<String> mutableNode = iterator.next();
        final ClosedMutableNode<String> newNode = mutableNode.addChild("more");
    }

    public void createTreeFromRoot(BottomUpTreeBuilder<String, ClosedMutableNode<String>> builder)
    {
        final MutableTree<String, ClosedMutableNode<String>> tree = builder.create("root").build(MutableTree.class);
        final ClosedMutableNode<String> root = tree.getRoot();
        final ClosedMutableNode<String> left = root.addChild("a");
        final ClosedMutableNode<String> right = root.addChild("b");
        left.addChild("1");
        left.addChild("2");
        left.addChild("3");
        right.addChild("+");
        right.addChild("-");
    }

    public void createTreeFromTopDown(TopDownTreeRootBuilder<String, ClosedMutableNode<String>> builder)
    {
        final TopDownTreeRootBuilder.TopDownTreeBuilder<String, ClosedMutableNode<String>> b0 = builder.root("root");
        final TopDownTreeRootBuilder.TopDownTreeBuilderAppender<String> left = b0.addChild("a");
        final TopDownTreeRootBuilder.TopDownTreeBuilderAppender<String> right = b0.addChild("b");
        left.addChild("1");
        left.addChild("2");
        left.addChild("3");
        right.addChild("+");
        right.addChild("-");
        final MutableTree<String, ClosedMutableNode<String>> tree = b0.build(MutableTree.class);
    }
}
