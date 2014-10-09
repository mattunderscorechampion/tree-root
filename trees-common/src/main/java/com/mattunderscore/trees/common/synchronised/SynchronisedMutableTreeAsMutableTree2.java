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

import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.mutable.MutableTree2;
import com.mattunderscore.trees.tree.Node;

/**
 * Synchronises a {@link MutableTree} as a {@link MutableTree2}.
 * @author Matt Champion on 09/10/14.
 */
final class SynchronisedMutableTreeAsMutableTree2<E> implements MutableTree2<E> {
    private final MutableTree<E, ? extends MutableNode<E>> delegateTree;

    public SynchronisedMutableTreeAsMutableTree2(MutableTree<E, ? extends MutableNode<E>> delegateTree) {
        this.delegateTree = delegateTree;
    }

    @Override
    public synchronized Node<E> addChild(Node<E> parent, E newElement) {
        final MutableNode<E> mutableParent = (MutableNode<E>)parent;
        return mutableParent.addChild(newElement);
    }

    @Override
    public synchronized boolean removeChild(Node<E> parent, Node<E> node) {
        final MutableNode<E> mutableParent = (MutableNode<E>)parent;
        return mutableParent.removeChild((MutableNode<E>)node);
    }

    @Override
    public synchronized Node<E> getRoot() {
        return delegateTree.getRoot();
    }

    @Override
    public synchronized boolean isEmpty() {
        return delegateTree.isEmpty();
    }
}
