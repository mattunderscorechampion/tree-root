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

import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.Tree;
import com.mattunderscore.trees.spi.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Helper class for trees. Loads services to provide extensibility.
 * @author matt on 26/06/14.
 */
final class TreeHelper {
    private final Map<Class<?>, EmptyTreeConstructor<?, ?>> emptyConstructors;
    private final Map<Class<?>, TreeConstructor<?, ?>> treeConstructors;
    private final Map<Class<?>, TreeConverter<?, ?>> treeConverters;
    private final Map<Class<?>, NodeToTreeConverter> converters;

    public TreeHelper() {
        treeConverters = new HashMap<Class<?>, TreeConverter<?, ?>>();
        final ServiceLoader<TreeConverter> treeConvertersLoader =
                ServiceLoader.load(TreeConverter.class);
        final Iterator<TreeConverter> treeConvertersIterator = treeConvertersLoader.iterator();
        while (treeConvertersIterator.hasNext()) {
            final TreeConverter converter = treeConvertersIterator.next();
            treeConverters.put(converter.forClass(), converter);
        }

        emptyConstructors = new HashMap<Class<?>, EmptyTreeConstructor<?, ?>>();
        final ServiceLoader<EmptyTreeConstructor> emptyConstructorLoader =
                ServiceLoader.load(EmptyTreeConstructor.class);
        final Iterator<EmptyTreeConstructor> emptyConstructorIterator = emptyConstructorLoader.iterator();
        while (emptyConstructorIterator.hasNext()) {
            final EmptyTreeConstructor constructor = emptyConstructorIterator.next();
            emptyConstructors.put(constructor.forClass(), constructor);
        }

        treeConstructors = new HashMap<Class<?>, TreeConstructor<?, ?>>();
        final ServiceLoader<TreeConstructor> treeConstructorLoader =
                ServiceLoader.load(TreeConstructor.class);
        final Iterator<TreeConstructor> treeConstructorIterator = treeConstructorLoader.iterator();
        while (treeConstructorIterator.hasNext()) {
            final TreeConstructor constructor = treeConstructorIterator.next();
            treeConstructors.put(constructor.forClass(), constructor);
        }

        converters = new HashMap<Class<?>, NodeToTreeConverter>();
        final ServiceLoader<NodeToTreeConverter> converterLoader = ServiceLoader.load(NodeToTreeConverter.class);
        final Iterator<NodeToTreeConverter> converterIterator = converterLoader.iterator();
        while (converterIterator.hasNext()) {
            final NodeToTreeConverter converter = converterIterator.next();
            converters.put(converter.forClass(), converter);
        }
    }

    public <E, T extends Tree<E, ? extends Node<E>>> T emptyTree(Class<T> klass) throws OperationNotSupportedForType {
        final EmptyTreeConstructor<E, T> constructor = (EmptyTreeConstructor<E, T>)emptyConstructors.get(klass);
        if (constructor == null) {
            throw new OperationNotSupportedForType(klass, EmptyTreeConstructor.class);
        }
        return constructor.build();
    }

    public <E, N extends Node<E>, T extends Tree<E, N>> T newTreeFrom(Class<T> klass, E e, T[] subtrees) throws OperationNotSupportedForType {
        final TreeConstructor<E, T> constructor = (TreeConstructor<E, T>)treeConstructors.get(klass);
        if (constructor == null) {
            throw new OperationNotSupportedForType(klass, TreeConstructor.class);
        }
        return constructor.build(e, subtrees);
    }

    public <E, N extends Node<E>, T extends Tree<E, N>> T convertTree(Class<T> klass, Tree<E, ? extends Node<E>> sourceTree) throws OperationNotSupportedForType {
        final TreeConverter<E, T> converter = (TreeConverter<E, T>)treeConverters.get(klass);
        if (converter == null) {
            throw new OperationNotSupportedForType(klass, TreeConverter.class);
        }
        return converter.build(sourceTree);
    }

    public <E, N extends Node<E>, T extends Tree<E, N>> T nodeToTree(N node) throws OperationNotSupportedForType {
        final Class<? extends Node> klass = node.getClass();
        final NodeToTreeConverter<E, N, T> converter = (NodeToTreeConverter<E, N, T>)converters.get(klass);
        if (converter == null) {
            throw new OperationNotSupportedForType(klass, NodeToTreeConverter.class);
        }
        return converter.treeFromRootNode(node);
    }
}
