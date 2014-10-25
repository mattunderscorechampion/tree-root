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

package com.mattunderscore.trees.common.synchronised;

import com.mattunderscore.trees.mutable.ModifiableTree;
import com.mattunderscore.trees.tree.Node;

/**
 * Synchronises a {@link com.mattunderscore.trees.mutable.ModifiableTree}.
 * @author Matt Champion on 09/10/14.
 */
final class SynchronisedModifiableTree<E> implements ModifiableTree<E> {
    private final ModifiableTree<E> delegateTree;

    public SynchronisedModifiableTree(ModifiableTree<E> tree) {
        delegateTree = tree;
    }

    @Override
    public synchronized Node<E> setRoot(E rootElement) {
        return new SynchronisedNode<>(this, delegateTree.setRoot(rootElement));
    }

    @Override
    public synchronized Node<E> addChild(Node<E> parent, E newElement) {
        return new SynchronisedNode<>(this, delegateTree.addChild(parent, newElement));
    }

    @Override
    public synchronized boolean removeChild(Node<E> parent, Node<E> node) {
        final SynchronisedNode<E> syncNode = (SynchronisedNode<E>)node;
        return delegateTree.removeChild(parent, syncNode.delegateNode);
    }

    @Override
    public synchronized Node<E> getRoot() {
        return new SynchronisedNode<>(this, delegateTree.getRoot());
    }

    @Override
    public synchronized boolean isEmpty() {
        return delegateTree.isEmpty();
    }
}