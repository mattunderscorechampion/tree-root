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

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.binary.OpenMutableBinaryTreeNode;
import com.mattunderscore.trees.spi.Rotator;
import com.mattunderscore.trees.transformation.RotationDirection;

/**
 * Supplier for {@link Rotator} components.
 * @author Matt Champion on 22/08/2015
 */
public final class RotatorSupplier {
    private final Map<Class<?>, Rotator> leftRotators = new HashMap<>();
    private final Map<Class<?>, Rotator> rightRotators = new HashMap<>();
    private final KeyMappingSupplier keyMappingSupplier;

    public RotatorSupplier(KeyMappingSupplier keyMappingSupplier) {
        this.keyMappingSupplier = keyMappingSupplier;
        final ServiceLoader<Rotator> loader = ServiceLoader.load(Rotator.class);
        for (final Rotator component : loader) {
            if (RotationDirection.LEFT.equals(component.forDirection())) {
                leftRotators.put(component.forClass(), component);
            }
            else if (RotationDirection.RIGHT.equals(component.forDirection())) {
                rightRotators.put(component.forClass(), component);
            }
        }
    }

    public <E, N extends OpenMutableBinaryTreeNode<E, N>> Rotator<E, N> get(N node, RotationDirection direction) {
        final Class<?> rawClass = node.getClass();
        final Class<?> mappedClass = keyMappingSupplier.get(rawClass);
        final Rotator<E, N> component;

        if (RotationDirection.LEFT.equals(direction)) {
            component = leftRotators.get(mappedClass);
        }
        else if (RotationDirection.RIGHT.equals(direction)) {
            component = rightRotators.get(mappedClass);
        }
        else {
            throw new IllegalArgumentException("Rotation direction not recognised");
        }

        if (component == null) {
            throw new OperationNotSupportedForType(rawClass, Rotator.class);
        }
        else {
            return component;
        }
    }
}
