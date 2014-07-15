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

package com.mattunderscore.trees.internal;

import com.mattunderscore.trees.MutableNode;
import com.mattunderscore.trees.MutableTree;
import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.utilities.FixedUncheckedList;
import javafx.collections.transformation.FilteredList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author matt on 15/07/14.
 */
public class MutableTreeNodeImpl<E> implements MutableTree<E>, MutableNode<E> {
    private volatile List<Node<E>> elementList;
    private final TreeNodeImpl<E> node;

    public MutableTreeNodeImpl(E element) {
        elementList = new FixedUncheckedList<>(new Object[0]);
        node = new TreeNodeImpl<>(element, elementList);
    }

    @Override
    public MutableNode<E> addChild(E e) {
        final MutableTreeNodeImpl child = new MutableTreeNodeImpl(e);
        synchronized (this) {
            final List<Node<E>> listProxy = elementList;
            final Object[] newArray = new Object[listProxy.size() + 1];
            for (int i = 0; i < listProxy.size(); i++) {
                newArray[i] = listProxy.get(i);
            }
            elementList = new FixedUncheckedList<Node<E>>(newArray);
        }
        return child;
    }

    @Override
    public MutableNode<E> getRoot() {
        return this;
    }

    @Override
    public E getElement() {
        return node.getElement();
    }

    @Override
    public Class<E> getElementClass() {
        return node.getElementClass();
    }

    @Override
    public Collection<Node<E>> getChildren() {
        return Collections.unmodifiableList(elementList);
    }
}
