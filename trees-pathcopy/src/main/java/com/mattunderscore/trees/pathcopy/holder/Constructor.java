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

package com.mattunderscore.trees.pathcopy.holder;

import java.util.Iterator;

import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.tree.Tree;

/**
 * Implementation of {@link com.mattunderscore.trees.spi.TreeConstructor} for
 * {@link com.mattunderscore.trees.pathcopy.holder.PathCopyTree}.
 * @author Matt Champion on 28/01/15.
 */
public final class Constructor<E> implements TreeConstructor<E, MutableNode<E>, PathCopyTree<E>> {

    @Override
    public PathCopyTree<E> build(E e, PathCopyTree<E>[] subtrees) {
        final PathCopyTree<E> tree = new PathCopyTree<>();
        final PathCopyNode<E> root = tree.setRoot(e);
        for (PathCopyTree<E> subtree : subtrees) {
            final PathCopyNode<E> subRoot = subtree.getRoot();
            final MutableNode<E> newSubRoot = root.addChild(subRoot.getElement());
            copyChildren(newSubRoot, subRoot);
        }
        return tree;
    }

    @Override
    public Class<? extends Tree> forClass() {
        return PathCopyTree.class;
    }

    private void copyChildren(MutableNode<E> newParent, MutableNode<E> parent) {
        final Iterator<? extends MutableNode<E>> iterator = parent.childIterator();
        while (iterator.hasNext()) {
            final MutableNode<E> child = iterator.next();
            final MutableNode<E> newChild = newParent.addChild(child.getElement());
            copyChildren(newChild, child);
        }
    }
}
