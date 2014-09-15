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

import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.spi.*;

import java.util.*;

/**
 * SPI support class. Loads service implementations to provide extensibility and allows access to the correct
 * implementation.
 * @author Matt Champion on 26/06/14.
 */
final class SPISupport {
    private final Map<Class<?>, EmptyTreeConstructor> emptyConstructors;
    private final Map<Class<?>, TreeConstructor> treeConstructors;
    private final Map<Class<?>, TreeConverter> treeConverters;
    private final Map<Class<?>, NodeToTreeConverter> converters;
    private final Map<Class<?>, EmptySortedTreeConstructor> sortedEmptyConverters;
    private final Map<Class<?>, IteratorRemoveHandler> iteratorRemoveHandlers;

    public SPISupport() {
        treeConverters = new HashMap<>();
        populateLookupMap(treeConverters, TreeConverter.class);

        emptyConstructors = new HashMap<>();
        populateLookupMap(emptyConstructors, EmptyTreeConstructor.class);

        treeConstructors = new HashMap<>();
        populateLookupMap(treeConstructors, TreeConstructor.class);

        converters = new HashMap<>();
        populateLookupMap(converters, NodeToTreeConverter.class);

        sortedEmptyConverters = new HashMap<>();
        populateLookupMap(sortedEmptyConverters, EmptySortedTreeConstructor.class);

        iteratorRemoveHandlers = new HashMap<>();
        populateLookupMap(iteratorRemoveHandlers, IteratorRemoveHandler.class);
    }

    /**
     * Create an empty tree.
     * @param klass
     * @param <E>
     * @param <T>
     * @return
     * @throws OperationNotSupportedForType
     */
    public <E, T extends Tree<E, ? extends Node<E>>> T createEmptyTree(Class<T> klass) throws OperationNotSupportedForType {
        final EmptyTreeConstructor<E, T> constructor =
                (EmptyTreeConstructor<E, T>)performLookup(emptyConstructors, EmptyTreeConstructor.class, klass);
        return constructor.build();
    }

    /**
     * Create an empty tree.
     * @param klass
     * @param <E>
     * @param <T>
     * @return
     * @throws OperationNotSupportedForType
     */
    public <E, T extends Tree<E, ? extends Node<E>>> T createEmptyTree(Class<T> klass, Comparator<E> comparator) throws OperationNotSupportedForType {
        final EmptySortedTreeConstructor<E, T> constructor =
                (EmptySortedTreeConstructor<E, T>)performLookup(sortedEmptyConverters, EmptySortedTreeConstructor.class, klass);
        return constructor.build(comparator);
    }

    /**
     * Create a tree containing subtrees.
     * @param klass
     * @param e
     * @param subtrees
     * @param <E>
     * @param <T>
     * @return
     * @throws OperationNotSupportedForType
     */
    public <E, T extends Tree<E, ? extends Node<E>>> T newTreeFrom(Class<T> klass, E e, T[] subtrees) throws OperationNotSupportedForType {
        final TreeConstructor<E, T> constructor =
                (TreeConstructor<E, T>)performLookup(treeConstructors, TreeConstructor.class, klass);
        return constructor.build(e, subtrees);
    }

    /**
     * Convert from one tree to another.
     * @param klass
     * @param sourceTree
     * @param <E>
     * @param <N>
     * @param <T>
     * @return
     * @throws OperationNotSupportedForType
     */
    public <E, N extends Node<E>, T extends Tree<E, N>> T convertTree(Class<T> klass, Tree<E, ? extends Node<E>> sourceTree) throws OperationNotSupportedForType {
        final TreeConverter<E, T> converter =
                (TreeConverter<E, T>)performLookup(treeConverters, TreeConverter.class, klass);
        return converter.build(sourceTree);
    }

    /**
     * Convert node to tree.
     * @param node
     * @param <E>
     * @param <N>
     * @param <T>
     * @return
     * @throws OperationNotSupportedForType
     */
    public <E, N extends Node<E>, T extends Tree<E, N>> T nodeToTree(N node) throws OperationNotSupportedForType {
        final Class<? extends Node> klass = node.getClass();
        final NodeToTreeConverter<E, N, T> converter =
                (NodeToTreeConverter<E, N, T>)performLookup(converters, NodeToTreeConverter.class, klass);
        return converter.treeFromRootNode(node);
    }

    public <E, N extends Node<E>, T extends Tree<E, N>> IteratorRemoveHandler<E, N, T> lookupHandler(T tree) {
        final Class<? extends Tree> keyClass = tree.getClass();
        final IteratorRemoveHandler<E, N, T> handler = iteratorRemoveHandlers.get(keyClass);
        if (handler == null) {
            return new DefaultRemovalHandler<>();
        }
        else {
            return handler;
        }
    }

    /**
     * Populate a lookup map
     * @param componentMap lookup map
     * @param componentClass type of component to be returned by lookup
     * @param <C> type of component to be returned by lookup
     */
    private <C extends SPIComponent> void populateLookupMap(Map<Class<?>, C> componentMap, Class<C> componentClass) {
        final ServiceLoader<C> loader = ServiceLoader.load(componentClass);
        for (final C component : loader) {
            componentMap.put(component.forClass(), component);
        }
    }

    /**
     * @param componentMap lookup map
     * @param componentClass type of component returned by lookup
     * @param keyClass lookup key
     * @param <C> type of component returned by lookup
     * @return instance of SPI component
     */
    private <C> C performLookup(Map<Class<?>, C> componentMap, Class<C> componentClass, Class<?> keyClass) {
        final C result = componentMap.get(keyClass);
        if (result == null) {
            throw new OperationNotSupportedForType(keyClass, componentClass);
        }
        return result;
    }
}
