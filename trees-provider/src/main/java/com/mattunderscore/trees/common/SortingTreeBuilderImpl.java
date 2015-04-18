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

package com.mattunderscore.trees.common;

import com.mattunderscore.trees.*;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.sorted.SortingTree;
import com.mattunderscore.trees.sorted.SortingTreeBuilder;
import com.mattunderscore.trees.tree.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Matt Champion on 06/09/14.
 */
public final class SortingTreeBuilderImpl<E> implements SortingTreeBuilder<E> {
    private final SPISupport helper;
    private final Comparator<E> comparator;
    private final List<E> elements = new ArrayList<>();

    public SortingTreeBuilderImpl(SPISupport helper, Comparator<E> comparator) {
        this.helper = helper;
        this.comparator = comparator;
    }

    @Override
    public SortingTreeBuilder<E> addElement(E element) {
        elements.add(element);
        return this;
    }

    @Override
    public <T extends SortingTree<E, ? extends Node<E>>> T build(Class<T> klass) throws OperationNotSupportedForType {
        final T tree = helper.createEmptyTree(klass, comparator);
        for (final E element : elements) {
            tree.addElement(element);
        }
        return tree;
    }

    @Override
    public <T extends SortingTree<E, ? extends Node<E>>> T build(TypeKey<T> type) throws OperationNotSupportedForType {
        return build(type.getType());
    }
}
