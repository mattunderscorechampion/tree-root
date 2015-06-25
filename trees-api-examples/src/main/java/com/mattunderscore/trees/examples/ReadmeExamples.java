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

package com.mattunderscore.trees.examples;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.binary.ClosedBinaryTreeNode;
import com.mattunderscore.trees.binary.search.BinarySearchTree;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.sorted.SortingTreeBuilder;
import com.mattunderscore.trees.traversal.DefaultElementWalker;
import com.mattunderscore.trees.tree.Tree;

/**
 * Examples used in the README.
 * @author Matt Champion on 29/01/15
 */
public final class ReadmeExamples {
    public void immutableTree() {
        final ServiceLoader<Trees> serviceLoader = ServiceLoader.load(Trees.class);
        final Trees trees = serviceLoader.iterator().next();

        final BottomUpTreeBuilder<String, MutableNode<String>> builder = trees.treeBuilders().bottomUpBuilder();
        final Tree<String, MutableNode<String>> tree = builder.create("a",
            builder.create("b"),
            builder.create("c"))
            .build(new TypeKey<Tree<String, MutableNode<String>>>(){});

        trees.treeWalkers().walkElementsInOrder(tree, new DefaultElementWalker<String>() {
            @Override
            public boolean onNext(String node) {
                System.out.println("Element: " + node);
                return true;
            }
        });
    }

    public void binarySearchTree() {
        final ServiceLoader<Trees> serviceLoader = ServiceLoader.load(Trees.class);
        final Trees trees = serviceLoader.iterator().next();

        final SortingTreeBuilder<Integer, ClosedBinaryTreeNode<Integer>> builder = trees.treeBuilders().sortingTreeBuilder();
        final BinarySearchTree<Integer> tree = builder
            .addElement(2)
            .addElement(1)
            .addElement(3)
            .build(BinarySearchTree.<Integer>typeKey());

        tree
            .addElement(4)
            .addElement(6)
            .addElement(5);

        final Iterator<Integer> iterator = trees.treeIterators().inOrderElementsIterator(tree);
        while (iterator.hasNext()) {
            System.out.println("Element: " + iterator.next());
        }
    }
}
