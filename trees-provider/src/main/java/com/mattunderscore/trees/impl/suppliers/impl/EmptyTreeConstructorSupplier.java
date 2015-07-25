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

package com.mattunderscore.trees.impl.suppliers.impl;

import static com.mattunderscore.trees.impl.suppliers.SPIUtilities.populateLookupMap;

import java.util.HashMap;
import java.util.Map;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.spi.EmptyTreeConstructor;
import com.mattunderscore.trees.tree.OpenNode;
import com.mattunderscore.trees.tree.Tree;

/**
 * Supplier for {@link EmptyTreeConstructor}s.
 * @author Matt Champion on 24/07/2015
 */
public final class EmptyTreeConstructorSupplier {
    private final Map<Class<?>, EmptyTreeConstructor> converters;
    private final KeyMappingSupplier keyMappingSupplier;

    public EmptyTreeConstructorSupplier(KeyMappingSupplier keyMappingSupplier) {
        this.keyMappingSupplier = keyMappingSupplier;
        converters = new HashMap<>();
        populateLookupMap(converters, EmptyTreeConstructor.class);
    }

    /**
     * @param klass The key to lookup
     * @param <E> The type of element
     * @param <N> The type of node
     * @param <T> The type of tree
     * @return The empty tree constructor
     * @throws OperationNotSupportedForType If the key is not supported
     */
    @SuppressWarnings("unchecked")
    public <E, N extends OpenNode<E, N>, T extends Tree<E, N>> EmptyTreeConstructor<E, N, T> get(Class<T> klass) {
        final Class<? extends T> concreteClass = keyMappingSupplier.get(klass);
        final EmptyTreeConstructor<E, N, T> constructor = converters.get(concreteClass);
        if (constructor == null) {
            throw new OperationNotSupportedForType(klass, EmptyTreeConstructor.class);
        }
        return constructor;
    }
}
