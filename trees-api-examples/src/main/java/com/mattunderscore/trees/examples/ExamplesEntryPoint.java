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

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Entry point for examples module.
 * @author Matt Champion on 29/01/15
 */
public final class ExamplesEntryPoint {
    private ExamplesEntryPoint() {
    }

    /**
     * Entry point.
     * @param args Command line arguments
     */
    public static void main(String[] args) {

        System.out.println("Readme Examples:");
        final DocumentationExamples documentationExamples = new DocumentationExamples();
        System.out.println("Immutable tree example:");
        documentationExamples.immutableTree();

        System.out.println("Binary search tree example:");
        documentationExamples.binarySearchTree();

        final Trees trees = Trees.get();

        System.out.println("Available implementations:");
        trees.availableTreeImplementations().forEach(c -> System.out.println(c.getSimpleName()));

        System.out.println("General examples:");
        final ImmutableTreeExamples immutableTreeExamples = new ImmutableTreeExamples();
        final TraversalExamples traversalExamples = new TraversalExamples();
        final Tree<String, Node<String>> tree = immutableTreeExamples.createTreeFromTheBottomUp(trees.treeBuilders().<String, Node<String>>bottomUpBuilder());
        immutableTreeExamples.createTreeFromTopDown(trees.treeBuilders().<String, Node<String>>topDownBuilder());

        traversalExamples.elementTreeWalker(trees.treeWalkers(), tree);

        System.out.println("Stream examples");
        final StreamExamples streamExamples = new StreamExamples();
        streamExamples.streamElements(trees.nodeStreams(), tree);
    }
}
