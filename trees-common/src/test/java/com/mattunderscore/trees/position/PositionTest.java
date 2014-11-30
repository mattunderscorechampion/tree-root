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

package com.mattunderscore.trees.position;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.common.LinkedTree;
import com.mattunderscore.trees.common.TreesImpl;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Position unit tests.
 * @author Matt Champion on 25/10/14.
 */
public class PositionTest {
    @Test
    public void rootEmptyTree() {
        final Trees trees = new TreesImpl();
        final LinkedTree<String> tree = trees.treeBuilders().<String>bottomUpBuilder()
            .build(LinkedTree.<String>typeKey());

        final Position position = new PositionBuilder().build();
        final LinkedTree<String> node = position.lookup(tree);
        assertNull(node);
    }

    @Test
    public void root() {
        final Trees trees = new TreesImpl();
        final LinkedTree<String> tree = trees.treeBuilders().<String>bottomUpBuilder()
            .create("a").build(LinkedTree.<String>typeKey());

        final Position position = new PositionBuilder().build();
        final LinkedTree<String> node = position.lookup(tree);
        assertEquals("a", node.getElement());
    }

    @Test
    public void leftChild() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String> treeBuilder = trees.treeBuilders().bottomUpBuilder();
        final LinkedTree<String> tree = treeBuilder
            .create(
                "a",
                treeBuilder.create("l"),
                treeBuilder.create("r")).build(LinkedTree.<String>typeKey());

        final Position position = new PositionBuilder().child(0).build();
        final LinkedTree<String> node = position.lookup(tree);
        assertEquals("l", node.getElement());
    }

    @Test
    public void rightChild() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String> treeBuilder = trees.treeBuilders().bottomUpBuilder();
        final LinkedTree<String> tree = treeBuilder
            .create(
                "a",
                    treeBuilder.create("l"),
                    treeBuilder.create("r")).build(LinkedTree.<String>typeKey());

        final Position position = new PositionBuilder().child(1).build();
        final LinkedTree<String> node = position.lookup(tree);
        assertEquals("r", node.getElement());
    }

    @Test(expected = IllegalStateException.class)
    public void wrongDepth() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String> treeBuilder = trees.treeBuilders().bottomUpBuilder();
        final LinkedTree<String> tree = treeBuilder
            .create(
                "a",
                treeBuilder.create("l"),
                treeBuilder.create("r")).build(LinkedTree.<String>typeKey());

        final Position position = new PositionBuilder().child(0).child(1).build();
        position.lookup(tree);
    }

    @Test(expected = IllegalStateException.class)
    public void wrongBreadth() {
        final Trees trees = new TreesImpl();
        final BottomUpTreeBuilder<String> treeBuilder = trees.treeBuilders().bottomUpBuilder();
        final LinkedTree<String> tree = treeBuilder
                .create(
                        "a",
                        treeBuilder.create("l"),
                        treeBuilder.create("r")).build(LinkedTree.<String>typeKey());

        final Position position = new PositionBuilder().child(2).build();
        position.lookup(tree);
    }
}
