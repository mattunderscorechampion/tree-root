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

package com.mattunderscore.trees.base;

import java.util.Iterator;

import com.mattunderscore.trees.collection.SimpleCollection;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.utilities.collections.DuplicateOnWriteSimpleCollection;
import com.mattunderscore.trees.utilities.collections.FixedUncheckedSimpleCollection;

/** 
 * @author Matt Champion on 21/09/14.
 */
public abstract class ImmutableNode<E> extends FixedNode<E> {
    protected final SimpleCollection<? extends ImmutableNode<E>> children;

    public ImmutableNode(E element, ImmutableNode<E>[] childNodes) {
        super(element);
        children = new FixedUncheckedSimpleCollection<>(childNodes);
    }

    public ImmutableNode(E element, Object[] childNodes) {
        super(element);
        children = new FixedUncheckedSimpleCollection<>(childNodes);
    }

    public ImmutableNode(E element, DuplicateOnWriteSimpleCollection<? extends ImmutableNode<E>> children) {
        super(element);
        this.children = children;
    }

    @Override
    public int getNumberOfChildren() {
        return children.size();
    }

    @Override
    public Iterator<? extends ImmutableNode<E>> childIterator() {
        return children.iterator();
    }
}
