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

package com.mattunderscore.trees.linked.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.mattunderscore.iterators.NonNullIterator;
import com.mattunderscore.trees.base.AbstractSettableNode;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.mutable.MutableSettableStructuredNode;
import com.mattunderscore.trees.mutable.MutableTree;

import net.jcip.annotations.NotThreadSafe;

/**
 * A simple tree implementation. Commonly used for temporary trees.
 * @author Matt Champion on 07/08/14.
 */
@NotThreadSafe
public final class LinkedTree<E> extends AbstractSettableNode<E, MutableSettableStructuredNode<E>> implements MutableTree<E, MutableSettableStructuredNode<E>>, MutableSettableStructuredNode<E> {
    private final ArrayList<LinkedTree<E>> children;

    public LinkedTree(E root) {
        super(root);
        children = new ArrayList<>();
    }

    @SuppressWarnings("unchecked")
    /*package*/ LinkedTree(E root, LinkedTree[] subtrees) {
        super(root);
        children = new ArrayList<>(subtrees.length);
        Collections.addAll(children, (LinkedTree<E>[])subtrees);
    }

    @SuppressWarnings("unchecked")
    /*package*/ LinkedTree(E root, ArrayList<LinkedTree<E>> subtrees) {
        super(root);
        children = subtrees;
    }

    @Override
    public boolean isEmpty() {
        return elementReference.get() == null;
    }

    @Override
    public LinkedTree<E> getRoot() {
        if (isEmpty()) {
            return null;
        }
        else {
            return this;
        }
    }

    @Override
    public Iterator<LinkedTree<E>> childStructuralIterator() {
        return children.iterator();
    }

    @Override
    public LinkedTree<E> getChild(int nChild) {
        return children.get(nChild);
    }

    @Override
    public LinkedTree<E> setChild(int nChild, E element) {
        final LinkedTree<E> child = new LinkedTree<>(element);
        if (nChild < children.size()) {
            children.set(nChild, child);
        }
        else {
            children.ensureCapacity(nChild);
            for (int i = children.size(); i < nChild; i++) {
                children.add(null);
            }
            children.add(child);
        }
        return child;
    }

    @Override
    public LinkedTree<E> addChild(E e) {
        if (e == null) {
            throw new NullPointerException("You cannot add a child to an empty tree");
        }
        final LinkedTree<E> child = new LinkedTree<>(e);
        children.add(child);
        return child;
    }

    @Override
    public boolean removeChild(MutableSettableStructuredNode<E> child) {
        return child != null && child.getClass().equals(getClass()) && children.remove(child);
    }

    @Override
    public LinkedTree<E> setRoot(E root) {
        setElement(root);
        return this;
    }

    @Override
    public int getNumberOfChildren() {
        return children.size();
    }

    @Override
    public Iterator<LinkedTree<E>> childIterator() {
        return new NonNullIterator<>(children.iterator());
    }

    /**
     * Construct a TypeKey for a specific element type.
     * @param <E> The element type
     * @return The type key
     */
    public static <E> TypeKey<LinkedTree<E>> typeKey() {
        return new TypeKey<LinkedTree<E>>() {};
    }
}
