/* Copyright © 2016 Matthew Champion
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

package com.mattunderscore.tree.root.querying;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.mattunderscore.simple.collections.SimpleCollection;
import com.mattunderscore.trees.binary.BinaryTreeNode;
import com.mattunderscore.trees.binary.MutableBinaryTreeNode;
import com.mattunderscore.trees.binary.mutable.Constructor;
import com.mattunderscore.trees.binary.mutable.EmptyConstructor;
import com.mattunderscore.trees.binary.mutable.MutableBinaryTreeImpl;
import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.query.Querier;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * Unit tests for {@link QuerierImpl}.
 *
 * @author Matt Champion on 27/08/2015
 */
public final class QuerierImplTest {

    @Test(expected = IllegalArgumentException.class)
    public void heightEmpty() {
        final Tree<String, MutableSettableStructuredNode<String>> node = new LinkedTree<>(null);

        final Querier querier = new QuerierImpl();

        querier.height(node);
    }

    @Test(expected = NullPointerException.class)
    public void heightNull() {
        final Querier querier = new QuerierImpl();

        querier.height((Node<String>)null);
    }

    @Test
    public void heightLeaf() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");

        final Querier querier = new QuerierImpl();

        assertEquals(0, querier.height(node));
    }

    @Test
    public void heightSimple() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");
        node.addChild("b");

        final Querier querier = new QuerierImpl();

        assertEquals(1, querier.height(node));
    }

    @Test
    public void heightManyPaths() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");
        node.addChild("b");
        node.addChild("c");

        final Querier querier = new QuerierImpl();

        assertEquals(1, querier.height(node));
    }

    @Test
    public void heightSinglePath() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");
        node.addChild("b");
        node
            .addChild("c")
            .addChild("d");

        final Querier querier = new QuerierImpl();

        assertEquals(2, querier.height(node));
    }

    @Test
    public void pathsEmpty() {
        final Tree<String, MutableSettableStructuredNode<String>> tree = new LinkedTree<>(null);

        final Querier querier = new QuerierImpl();

        assertEquals(0, querier.pathsToLeaves(tree).size());
    }

    @Test(expected = NullPointerException.class)
    public void pathsNull() {
        final Querier querier = new QuerierImpl();

        querier.pathsToLeaves((Node<String>) null);
    }

    @Test
    public void pathSelf() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");

        final Querier querier = new QuerierImpl();

        final SimpleCollection<List<MutableSettableStructuredNode<String>>> paths = querier.pathsToLeaves(node);

        assertEquals(1, paths.size());

        final List<MutableSettableStructuredNode<String>> path = paths.iterator().next();

        assertEquals(1, path.size());
        assertEquals("a", path.get(0).getElement());
    }

    @Test
    public void simplePath() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");
        node.addChild("b");

        final Querier querier = new QuerierImpl();

        final SimpleCollection<List<MutableSettableStructuredNode<String>>> paths = querier.pathsToLeaves(node);

        assertEquals(1, paths.size());

        final List<MutableSettableStructuredNode<String>> path = paths.iterator().next();

        assertEquals(2, path.size());
        assertEquals("a", path.get(0).getElement());
        assertEquals("b", path.get(1).getElement());
    }

    @Test
    public void twoPaths() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");
        node.addChild("b");
        node.addChild("c");

        final Querier querier = new QuerierImpl();

        final SimpleCollection<List<MutableSettableStructuredNode<String>>> paths = querier.pathsToLeaves(node);

        assertEquals(2, paths.size());

        final Iterator<List<MutableSettableStructuredNode<String>>> iterator = paths.iterator();
        final List<MutableSettableStructuredNode<String>> path0 = iterator.next();

        assertEquals(2, path0.size());
        assertEquals("a", path0.get(0).getElement());
        // Cannot tell if next element is b or c

        final List<MutableSettableStructuredNode<String>> path1 = iterator.next();

        assertEquals(2, path1.size());
        assertEquals("a", path1.get(0).getElement());
        // Cannot tell if next element is b or c
    }

    @Test
    public void differentLengthPaths() {
        final MutableSettableStructuredNode<String> node = new LinkedTree<>("a");
        node.addChild("b");
        node
            .addChild("c")
            .addChild("d");

        final Querier querier = new QuerierImpl();

        final SimpleCollection<List<MutableSettableStructuredNode<String>>> paths = querier.pathsToLeaves(node);

        assertEquals(2, paths.size());

        final Iterator<List<MutableSettableStructuredNode<String>>> iterator = paths.iterator();
        final List<MutableSettableStructuredNode<String>> path0 = iterator.next();
        final List<MutableSettableStructuredNode<String>> path1 = iterator.next();

        if (path0.size() == 2 && path1.size() == 3) {
            assertEquals(2, path0.size());
            assertEquals("a", path0.get(0).getElement());
            assertEquals("b", path0.get(1).getElement());

            assertEquals("a", path1.get(0).getElement());
            assertEquals("c", path1.get(1).getElement());
            assertEquals("d", path1.get(2).getElement());
        }
        else if (path0.size() == 3 && path1.size() == 2) {
            assertEquals("a", path0.get(0).getElement());
            assertEquals("c", path0.get(1).getElement());
            assertEquals("d", path0.get(2).getElement());

            assertEquals(2, path1.size());
            assertEquals("a", path1.get(0).getElement());
            assertEquals("b", path1.get(1).getElement());
        }
    }

    @Test(expected = NullPointerException.class)
    public void balancedNull() {
        final Querier querier = new QuerierImpl();

        querier.isBalanced((BinaryTreeNode<String>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyTreeBalanced() {
        final Querier querier = new QuerierImpl();

        querier.isBalanced(new EmptyConstructor<>().build());
    }

    @Test(expected = NullPointerException.class)
    public void perfectlyBalancedNull() {
        final Querier querier = new QuerierImpl();

        querier.isPerfectlyBalanced((BinaryTreeNode<String>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void emptyTreePerfectlyBalanced() {
        final Querier querier = new QuerierImpl();

        querier.isPerfectlyBalanced(new EmptyConstructor<>().build());
    }

    @Test
    public void leafBalanced() {
        final Querier querier = new QuerierImpl();

        assertTrue(querier.isBalanced(new Constructor<String>().build("a", new MutableBinaryTreeImpl[] {})));
    }

    @Test
    public void leafPerfectlyBalanced() {
        final Querier querier = new QuerierImpl();

        assertTrue(querier.isPerfectlyBalanced(new Constructor<String>().build("a", new MutableBinaryTreeImpl[] {})));
    }

    @Test
    public void offByOneBalanced() {
        final Constructor<String> constructor = new Constructor<>();
        final Tree<String, MutableBinaryTreeNode<String>> tree =
            constructor.build("a", new MutableBinaryTreeImpl[] {
                constructor.build("b", new MutableBinaryTreeImpl[]{})});

        final Querier querier = new QuerierImpl();

        assertTrue(querier.isBalanced(tree));
    }

    @Test
    public void offByOneNotPerfectlyBalanced() {
        final Constructor<String> constructor = new Constructor<>();
        final Tree<String, MutableBinaryTreeNode<String>> tree =
                constructor.build("a", new MutableBinaryTreeImpl[] {
                        constructor.build("b", new MutableBinaryTreeImpl[]{})});

        final Querier querier = new QuerierImpl();

        assertFalse(querier.isPerfectlyBalanced(tree));
    }

    @Test
    public void unbalanced() {
        final Constructor<String> constructor = new Constructor<>();
        final Tree<String, MutableBinaryTreeNode<String>> tree =
            constructor.build("a", new MutableBinaryTreeImpl[] {
                constructor.build("b", new MutableBinaryTreeImpl[]{
                    constructor.build("c", new MutableBinaryTreeImpl[]{
                        constructor.build("d", new MutableBinaryTreeImpl[] {})})})});

        final Querier querier = new QuerierImpl();

        assertFalse(querier.isBalanced(tree));
    }

    @Test
    public void balanced() {
        final Constructor<String> constructor = new Constructor<>();
        final Tree<String, MutableBinaryTreeNode<String>> tree =
            constructor.build("a", new MutableBinaryTreeImpl[] {
                constructor.build("b", new MutableBinaryTreeImpl[]{}),
                constructor.build("b", new MutableBinaryTreeImpl[]{})});

        final Querier querier = new QuerierImpl();

        assertTrue(querier.isBalanced(tree));
    }

    @Test
    public void largeBalanced() {
        final Constructor<String> constructor = new Constructor<>();
        final Tree<String, MutableBinaryTreeNode<String>> tree =
                constructor.build("a", new MutableBinaryTreeImpl[] {
                        constructor.build("b", new MutableBinaryTreeImpl[]{
                                constructor.build("c", new MutableBinaryTreeImpl[]{
                                        constructor.build("d", new MutableBinaryTreeImpl[]{}),
                                        constructor.build("d", new MutableBinaryTreeImpl[]{})
                                }),
                                constructor.build("c", new MutableBinaryTreeImpl[]{
                                        constructor.build("d", new MutableBinaryTreeImpl[]{}),
                                        constructor.build("d", new MutableBinaryTreeImpl[]{})
                                })
                        }),
                        constructor.build("b", new MutableBinaryTreeImpl[]{
                                constructor.build("c", new MutableBinaryTreeImpl[]{
                                        constructor.build("d", new MutableBinaryTreeImpl[]{}),
                                        constructor.build("d", new MutableBinaryTreeImpl[]{})
                                }),
                                constructor.build("c", new MutableBinaryTreeImpl[]{
                                        constructor.build("d", new MutableBinaryTreeImpl[]{}),
                                        constructor.build("d", new MutableBinaryTreeImpl[]{})
                                })
                        })
                });

        final Querier querier = new QuerierImpl();

        assertTrue(querier.isBalanced(tree));
    }

    @Test(expected = IllegalArgumentException.class)
    public void leafBalanceFactor() {
        final Constructor<String> constructor = new Constructor<>();
        final MutableBinaryTreeImpl<String> node = constructor.build("a", new MutableBinaryTreeImpl[]{});

        final Querier querier = new QuerierImpl();

        querier.balanceFactor(node.getRoot());
    }

    @Test(expected = IllegalArgumentException.class)
    public void noRightBalanceFactor() {
        final Constructor<String> constructor = new Constructor<>();
        final MutableBinaryTreeImpl<String> node = constructor.build("a", new MutableBinaryTreeImpl[]{
                constructor.build("b", new MutableBinaryTreeImpl[]{})
        });

        final Querier querier = new QuerierImpl();

        querier.balanceFactor(node.getRoot());
    }

    @Test(expected = IllegalArgumentException.class)
    public void noLeftBalanceFactor() {
        final EmptyConstructor<String> emptyConstructor = new EmptyConstructor<>();
        final Constructor<String> constructor = new Constructor<>();
        final MutableBinaryTreeImpl<String> node = constructor.build("a", new MutableBinaryTreeImpl[]{
                emptyConstructor.build(),
                constructor.build("b", new MutableBinaryTreeImpl[]{})
        });

        final Querier querier = new QuerierImpl();

        querier.balanceFactor(node.getRoot());
    }

    @Test
    public void leftBalanceFactor() {
        final Constructor<String> constructor = new Constructor<>();
        final MutableBinaryTreeImpl<String> node = constructor.build("a", new MutableBinaryTreeImpl[]{
                constructor.build("b", new MutableBinaryTreeImpl[]{
                        constructor.build("d", new MutableBinaryTreeImpl[]{})
                }),
                constructor.build("c", new MutableBinaryTreeImpl[]{})
        });

        final Querier querier = new QuerierImpl();

        assertEquals(1, querier.balanceFactor(node.getRoot()));
    }

    @Test
    public void rightBalanceFactor() {
        final Constructor<String> constructor = new Constructor<>();
        final MutableBinaryTreeImpl<String> node = constructor.build("a", new MutableBinaryTreeImpl[]{
                constructor.build("b", new MutableBinaryTreeImpl[]{}),
                constructor.build("c", new MutableBinaryTreeImpl[]{
                        constructor.build("d", new MutableBinaryTreeImpl[]{})
                })
        });

        final Querier querier = new QuerierImpl();

        assertEquals(-1, querier.balanceFactor(node.getRoot()));
    }

    @Test
    public void evenBalanceFactor() {
        final Constructor<String> constructor = new Constructor<>();
        final MutableBinaryTreeImpl<String> node = constructor.build("a", new MutableBinaryTreeImpl[]{
                constructor.build("b", new MutableBinaryTreeImpl[]{
                        constructor.build("d", new MutableBinaryTreeImpl[]{})
                }),
                constructor.build("c", new MutableBinaryTreeImpl[]{
                        constructor.build("d", new MutableBinaryTreeImpl[]{})
                })
        });

        final Querier querier = new QuerierImpl();

        assertEquals(0, querier.balanceFactor(node.getRoot()));
    }
}
