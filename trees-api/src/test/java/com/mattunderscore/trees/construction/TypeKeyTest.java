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

package com.mattunderscore.trees.construction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests for TypeKey.
 * @author Matt Champion
 */
public final class TypeKeyTest {

    @Test
    public void type0() {
        final TypeKey<Tree<String, Node<String>>> key0 = new TypeKey<Tree<String, Node<String>>>() { };

        assertEquals(Tree.class, key0.getType());
    }

    @Test
    public void type1() {
        final TypeKey<TestTree> key0 = new TypeKey<TestTree>() { };

        assertEquals(TestTree.class, key0.getType());
    }

    @Test
    public void testToString() {
        final TypeKey<Tree<String, Node<String>>> key0 = new TypeKey<Tree<String, Node<String>>>() { };

        assertTrue(key0.toString().startsWith("TypeKey"));
        assertTrue(key0.toString().contains(TypeKey.class.getSimpleName()));
    }

    @Test
    public void equals0() {
        final TypeKey<Tree<String, Node<String>>> key0 = new TypeKey<Tree<String, Node<String>>>() { };
        final TypeKey<Tree<String, Node<String>>> key1 = new TypeKey<Tree<String, Node<String>>>() { };

        assertTrue(key0.equals(key1));
        assertTrue(key1.equals(key0));
        assertEquals(key0.hashCode(), key1.hashCode());
    }

    @Test
    public void equals1() {
        final TypeKey<Tree<String, Node<String>>> key0 = new TypeKey<Tree<String, Node<String>>>() { };

        assertTrue(key0.equals(key0));
        assertEquals(key0.hashCode(), key0.hashCode());
    }

    @Ignore("The types of the parameters of the tree are erased")
    @Test
    public void notEquals0() {
        final TypeKey<Tree<String, Node<String>>> key0 = new TypeKey<Tree<String, Node<String>>>() { };
        final TypeKey<Tree<Integer, Node<Integer>>> key1 = new TypeKey<Tree<Integer, Node<Integer>>>() { };

        assertFalse(key0.equals(key1));
    }

    @Test
    public void notEquals1() {
        final TypeKey<Tree<String, Node<String>>> key0 = new TypeKey<Tree<String, Node<String>>>() { };

        assertFalse(key0.equals(null));
    }

    @Test
    public void notEquals2() {
        final TypeKey<Tree<String, Node<String>>> key0 = new TypeKey<Tree<String, Node<String>>>() { };

        assertFalse(key0.equals(new Object()));
    }

    public static final class TestTree implements Tree<String, Node<String>> {

        @Override
        public Node<String> getRoot() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }
}