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

import com.mattunderscore.trees.*;
import com.mattunderscore.trees.ITopDownTreeBuilder.ITopDownTreeBuilderAppender;

import java.util.Iterator;

/**
 * @author matt on 08/08/14.
 */
public class MutableTreeExamples {
    public void createTreeFromTheBottomUp(IBottomUpTreeBuilder<String> builder, ITreeTraverser traverser)
    {
        final IMutableTree<String, IMutableNode<String>> tree = builder.create("root",
                builder.create("a",
                        builder.create("1"),
                        builder.create("2"),
                        builder.create("3")),
                builder.create("b",
                        builder.create("+"),
                        builder.create("-"))).build(IMutableTree.class);

        final Iterator<IMutableNode<String>> iterator = traverser.preOrderIterator(tree);
        final IMutableNode<String> mutableNode = iterator.next();
        final IMutableNode<String> newNode = mutableNode.addChild("more");
    }

    public void createTreeFromRoot(IBottomUpTreeBuilder<String> builder, ITreeTraverser traverser)
    {
        final IMutableTree<String, IMutableNode<String>> tree = builder.create("root").build(IMutableTree.class);
        final IMutableNode<String> root = tree.getRoot();
        final IMutableNode<String> left = root.addChild("a");
        final IMutableNode<String> right = root.addChild("b");
        left.addChild("1");
        left.addChild("2");
        left.addChild("3");
        right.addChild("+");
        right.addChild("-");
    }

    public void createTreeFromTopDown(ITopDownTreeBuilder<String> builder, ITreeTraverser traverser)
    {
        final ITopDownTreeBuilderAppender<String> b0 = builder.create("root");
        final ITopDownTreeBuilderAppender<String> left = b0.addChild("a");
        final ITopDownTreeBuilderAppender<String> right = b0.addChild("b");
        left.addChild("1");
        left.addChild("2");
        left.addChild("3");
        right.addChild("+");
        right.addChild("-");
        final IMutableTree<String, IMutableNode<String>> tree = builder.build(IMutableTree.class);
    }
}
