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
import com.mattunderscore.trees.Node;
import com.mattunderscore.trees.Tree;
import com.mattunderscore.trees.spi.IEmptyTreeConstructor;
import com.mattunderscore.trees.spi.ITreeConstructor;
import com.mattunderscore.trees.spi.TreeConstructor;
import com.mattunderscore.trees.spi.TreeToNodeConverter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * Helper class for trees. Loads services to provide extensibility.
 * @author matt on 26/06/14.
 */
public final class TreeHelper {
    private final Map<Class<?>, IEmptyTreeConstructor<?, ?>> emptyConstructors;
    private final Map<Class<?>, ITreeConstructor<?, ?>> treeConstructors;
    private final Map<Class<?>, TreeConstructor<?, ?>> constructors;
    private final Map<Class<?>, TreeToNodeConverter> converters;

    public TreeHelper() {
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

        constructors = new HashMap<Class<?>, TreeConstructor<?, ?>>();
        final ServiceLoader<TreeConstructor> constructorLoader = ServiceLoader.load(TreeConstructor.class);
        final Iterator<TreeConstructor> constructorIterator = constructorLoader.iterator();
        while (constructorIterator.hasNext()) {
            final TreeConstructor constructor = constructorIterator.next();
            constructors.put(constructor.forClass(), constructor);
        }

        converters = new HashMap<Class<?>, TreeToNodeConverter>();
        final ServiceLoader<TreeToNodeConverter> converterLoader = ServiceLoader.load(TreeToNodeConverter.class);
        final Iterator<TreeToNodeConverter> converterIterator = converterLoader.iterator();
        while (converterIterator.hasNext()) {
            final TreeToNodeConverter converter = converterIterator.next();
            converters.put(converter.forClass(), converter);
        }
    }

    public Tree treeFromRootNode(Node node) {
        return converters.get(node.getClass()).treeFromRootNode(node);
    }

    public <E, T extends Tree<E>> T treeFrom(Class<T> klass, Tree<E> tree) {
        final TreeConstructor<E, T> constructor = (TreeConstructor<E, T>)constructors.get(klass);
        return constructor.build(tree);
    }

    public <E, T extends ITree<E, ? extends INode<E>>> T emptyTree(Class<T> klass) {
        final IEmptyTreeConstructor<E, T> constructor = (IEmptyTreeConstructor<E, T>)emptyConstructors.get(klass);
        return constructor.build();
    }

    public <E, N extends INode<E>, T extends ITree<E, N>> T newTreeFrom(Class<T> klass, E e, T[] subtrees) {
        final ITreeConstructor<E, T> constructor = (ITreeConstructor<E, T>)treeConstructors.get(klass);
        return constructor.build(e, subtrees);
    }
}
