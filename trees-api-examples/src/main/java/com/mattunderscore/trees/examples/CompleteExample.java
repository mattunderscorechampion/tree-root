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
import com.mattunderscore.trees.common.matchers.EqualityMatcher;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author matt on 16/08/14.
 */
public final class CompleteExample {

    public void completeExampleServiceLoaderEntry() {
        final ServiceLoader<ITrees> serviceLoader = ServiceLoader.load(ITrees.class);
        final Iterator<ITrees> iterator = serviceLoader.iterator();
        if (iterator.hasNext()) {
            createTree(iterator.next());
        }
    }

    public void createTree(ITrees trees) {
        final IBottomUpTreeBuilder<String> builder = trees.bottomUpBuilder();
        final ITree<String, INode<String>> tree = builder.create("a",
                builder.create("b"),
                builder.create("c"))
            .build(ITree.class);

        nodeSelector(trees, tree);
        treeSelector(trees, tree);
    }

    public void nodeSelector(ITrees trees, ITree<String, INode<String>> tree) {
        final INodeSelectorFactory selectorFactory = trees.nodeSelectorFactory();
        final INodeSelector selector = selectorFactory.newSelector(new EqualityMatcher("a"));
        final Iterator<INode<String>> iterator = selector.select(tree);
    }

    public void treeSelector(ITrees trees, ITree<String, INode<String>> tree) {
        final ITreeSelectorFactory selectorFactory = trees.treeSelectorFactory();
        final ITreeSelector selector = selectorFactory.newSelector(new EqualityMatcher("a"));
        final Iterator<ITree<String, INode<String>>> iterator = selector.select(tree);
    }
}
