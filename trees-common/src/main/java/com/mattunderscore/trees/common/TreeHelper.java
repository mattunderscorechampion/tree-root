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

import com.mattunderscore.trees.INode;
import com.mattunderscore.trees.ITree;
import com.mattunderscore.trees.spi.*;
import net.jcip.annotations.Immutable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Helper class for trees. Loads services to provide extensibility.
 * @author matt on 26/06/14.
 */
final class TreeHelper {
    private final Map<Class<?>, IEmptyTreeConstructor<?, ?>> emptyConstructors;
    private final Map<Class<?>, ITreeConstructor<?, ?>> treeConstructors;
    private final Map<Class<?>, ITreeConverter<?, ?>> treeConverters;
    private final Map<Class<?>, INodeToTreeConverter> converters;

    public TreeHelper() {
        treeConverters = new HashMap<Class<?>, ITreeConverter<?, ?>>();
        final ServiceLoader<ITreeConverter> treeConvertersLoader =
                ServiceLoader.load(ITreeConverter.class);
        final Iterator<ITreeConverter> treeConvertersIterator = treeConvertersLoader.iterator();
        while (treeConvertersIterator.hasNext()) {
            final ITreeConverter converter = treeConvertersIterator.next();
            treeConverters.put(converter.forClass(), converter);
        }

        emptyConstructors = new HashMap<Class<?>, IEmptyTreeConstructor<?, ?>>();
        final ServiceLoader<IEmptyTreeConstructor> emptyConstructorLoader =
                ServiceLoader.load(IEmptyTreeConstructor.class);
        final Iterator<IEmptyTreeConstructor> emptyConstructorIterator = emptyConstructorLoader.iterator();
        while (emptyConstructorIterator.hasNext()) {
            final IEmptyTreeConstructor constructor = emptyConstructorIterator.next();
            emptyConstructors.put(constructor.forClass(), constructor);
        }

        treeConstructors = new HashMap<Class<?>, ITreeConstructor<?, ?>>();
        final ServiceLoader<ITreeConstructor> treeConstructorLoader =
                ServiceLoader.load(ITreeConstructor.class);
        final Iterator<ITreeConstructor> treeConstructorIterator = treeConstructorLoader.iterator();
        while (treeConstructorIterator.hasNext()) {
            final ITreeConstructor constructor = treeConstructorIterator.next();
            treeConstructors.put(constructor.forClass(), constructor);
        }

        converters = new HashMap<Class<?>, INodeToTreeConverter>();
        final ServiceLoader<INodeToTreeConverter> converterLoader = ServiceLoader.load(INodeToTreeConverter.class);
        final Iterator<INodeToTreeConverter> converterIterator = converterLoader.iterator();
        while (converterIterator.hasNext()) {
            final INodeToTreeConverter converter = converterIterator.next();
            converters.put(converter.forClass(), converter);
        }
    }

    public <E, T extends ITree<E, ? extends INode<E>>> T emptyTree(Class<T> klass) {
        final IEmptyTreeConstructor<E, T> constructor = (IEmptyTreeConstructor<E, T>)emptyConstructors.get(klass);
        return constructor.build();
    }

    public <E, N extends INode<E>, T extends ITree<E, N>> T newTreeFrom(Class<T> klass, E e, T[] subtrees) {
        final ITreeConstructor<E, T> constructor = (ITreeConstructor<E, T>)treeConstructors.get(klass);
        return constructor.build(e, subtrees);
    }

    public <E, N extends INode<E>, T extends ITree<E, N>> T convertTree(Class<T> klass, ITree<E, ? extends INode<E>> sourceTree) {
        final ITreeConverter<E, T> converter = (ITreeConverter<E, T>)treeConverters.get(klass);
        return converter.build(sourceTree);
    }

    public <E, N extends INode<E>, T extends ITree<E, N>> T nodeToTree(N node) {
        final INodeToTreeConverter<E, N, T> converter = (INodeToTreeConverter<E, N, T>)converters.get(node.getClass());
        return converter.treeFromRootNode(node);
    }
}
