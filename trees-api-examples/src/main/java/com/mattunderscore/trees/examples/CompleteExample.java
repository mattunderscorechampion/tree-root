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

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.matchers.EqualityMatcher;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.selection.NodeSelector;
import com.mattunderscore.trees.selection.NodeSelectorFactory;
import com.mattunderscore.trees.selection.TreeSelector;
import com.mattunderscore.trees.selection.TreeSelectorFactory;
import com.mattunderscore.trees.traversal.DefaultElementWalker;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * @author Matt Champion on 16/08/14.
 */
public final class CompleteExample {

    public void completeExampleServiceLoaderEntry() {
        final ServiceLoader<Trees> serviceLoader = ServiceLoader.load(Trees.class);
        final Iterator<Trees> iterator = serviceLoader.iterator();
        if (iterator.hasNext()) {
            createTree(iterator.next());
        }
    }

    public void createTree(Trees trees) {
        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        final Tree<String, Node<String>> tree = builder.create("a",
                builder.create("b"),
                builder.create("c"))
            .build(new TypeKey<Tree<String, Node<String>>>(){});

        nodeSelector(trees, tree);
        treeSelector(trees, tree);

        final BottomUpTreeBuilder<Integer> intBuilder = trees.treeBuilders().bottomUpBuilder();
        final Tree<Integer, Node<Integer>> intTree = intBuilder.create(3,
                intBuilder.create(7),
                intBuilder.create(9))
            .build(new TypeKey<Tree<Integer, Node<Integer>>>(){});
        sum(trees, intTree);
    }

    public void nodeSelector(Trees trees, Tree<String, Node<String>> tree) {
        final NodeSelectorFactory selectorFactory = trees.nodeSelectors();
        final NodeSelector<String> selector = selectorFactory.newSelector(new EqualityMatcher("a"));
        final Iterator<Node<String>> iterator = selector.select(tree);
    }

    public void treeSelector(Trees trees, Tree<String, Node<String>> tree) {
        final TreeSelectorFactory selectorFactory = trees.treeSelectors();
        final TreeSelector<String> selector = selectorFactory.newSelector(new EqualityMatcher("a"));
        final Iterator<Tree<String, Node<String>>> iterator = selector.select(tree);
    }

    /**
     * Sum all the values in an integer tree.
     */
    public void sum(Trees trees, Tree<Integer, Node<Integer>> tree) {
        final SumWalker walker = new SumWalker();
        trees.treeWalkers().walkElementsInOrder(tree, walker);
    }

    /**
     * Walker that sums the elements in an integer tree.
     */
    private static final class SumWalker extends DefaultElementWalker<Integer> {
        int sum = 0;

        @Override
        public boolean onNext(Integer integer) {
            sum += integer;
            return true;
        }
    }
}
