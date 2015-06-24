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

import java.util.Iterator;

import com.mattunderscore.trees.mutable.ClosedMutableNode;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.traversal.DefaultElementWalker;
import com.mattunderscore.trees.traversal.DefaultTreeWalker;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;
import com.mattunderscore.trees.traversal.TreeWalkerFactory;
import com.mattunderscore.trees.tree.ClosedNode;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Traversal examples.
 * @author Matt Champion
 */
public final class TraversalExamples {
    public void nodeIterator(TreeIteratorFactory iterators, MutableTree<String, ClosedMutableNode<String>> tree) {
        final Iterator<ClosedMutableNode<String>> iterator = iterators.breadthFirstIterator(tree);
        while (iterator.hasNext()) {
            final ClosedMutableNode<String> node = iterator.next();
        }
    }

    public void elementIterator(TreeIteratorFactory iterators, Tree<String, ClosedNode<String>> tree) {
        final Iterator<String> iterator = iterators.breadthFirstElementsIterator(tree);
        while (iterator.hasNext()) {
            final String element = iterator.next();
            System.out.println(element);
        }
    }

    public void iterateAsSubtypeOfNode(TreeIteratorFactory iterators, MutableTree<String, ClosedMutableNode<String>> tree) {
        final Iterator<ClosedMutableNode<String>> iterator = iterators.breadthFirstIterator(tree);
        while (iterator.hasNext()) {
            final ClosedMutableNode<String> node = iterator.next();
        }
    }

    public void elementWalker(TreeWalkerFactory walkers, Tree<String, ClosedNode<String>> tree) {
        walkers.walkElementsInOrder(tree, new DefaultElementWalker<String>() {
            @Override
            public boolean onNext(String element) {
                System.out.println(element);
                return true;
            }
        });
    }

    public void elementTreeWalker(TreeWalkerFactory walkers, Tree<String, ClosedNode<String>> tree) {
        walkers.walkPreOrder(tree, new DefaultTreeWalker<ClosedNode<String>>() {
            @Override
            public void onStarted() {
                System.out.print("(");
            }

            @Override
            public void onNode(ClosedNode<String> node) {
                System.out.print(node.getElement());
            }

            @Override
            public void onNodeChildrenStarted(ClosedNode<String> node) {
                System.out.print(" (");
            }

            @Override
            public void onNodeChildrenRemaining(ClosedNode<String> node) {
                System.out.print(" ");
            }

            @Override
            public void onNodeChildrenCompleted(ClosedNode<String> node) {
                System.out.print(")");
            }

            @Override
            public void onCompleted() {
                System.out.println(")");
            }
        });
    }
}
