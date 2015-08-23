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

package com.mattunderscore.trees;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.mattunderscore.simple.collections.SimpleCollection;
import com.mattunderscore.trees.construction.TreeBuilderFactory;
import com.mattunderscore.trees.selection.NodeSelectorFactory;
import com.mattunderscore.trees.selection.TreeSelectorFactory;
import com.mattunderscore.trees.transformation.TreeTransformer;
import com.mattunderscore.trees.traversal.NodeStreamFactory;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;
import com.mattunderscore.trees.traversal.TreeWalkerFactory;

/**
 * API interface.
 * <p>
 * Access to the API is provided through this class.
 * @author Matt Champion on 12/07/14.
 */
public interface Trees {

    /**
     * Obtain a {@link com.mattunderscore.trees.selection.TreeSelectorFactory}.
     * @return A {@link com.mattunderscore.trees.selection.TreeSelectorFactory}
     */
    TreeSelectorFactory treeSelectors();

    /**
     * Obtain a {@link com.mattunderscore.trees.selection.NodeSelectorFactory}.
     * @return A {@link com.mattunderscore.trees.selection.NodeSelectorFactory}
     */
    NodeSelectorFactory nodeSelectors();

    /**
     * Obtain a {@link com.mattunderscore.trees.traversal.TreeWalkerFactory}.
     * @return A {@link com.mattunderscore.trees.traversal.TreeWalkerFactory}
     */
    TreeWalkerFactory treeWalkers();

    /**
     * Obtain a {@link com.mattunderscore.trees.traversal.TreeIteratorFactory}.
     * @return A {@link com.mattunderscore.trees.traversal.TreeIteratorFactory}
     */
    TreeIteratorFactory treeIterators();

    /**
     * Obtain a {@link com.mattunderscore.trees.traversal.NodeStreamFactory}.
     * @return A {@link com.mattunderscore.trees.traversal.NodeStreamFactory}
     */
    NodeStreamFactory nodeStreams();

    /**
     * Obtain a {@link com.mattunderscore.trees.construction.TreeBuilderFactory}.
     * @return A {@link com.mattunderscore.trees.construction.TreeBuilderFactory}
     */
    TreeBuilderFactory treeBuilders();

    /**
     * Obtain a {@link com.mattunderscore.trees.transformation.TreeTransformer}.
     * @return A {@link com.mattunderscore.trees.transformation.TreeTransformer}
     */
    TreeTransformer transformations();

    /**
     * @return A simple collection containing the available tree implementations.
     */
    SimpleCollection<Class<?>> availableTreeImplementations();

    /**
     * Obtain an instance of the API.
     * <p>
     * This searches through the classpath using {@link ServiceLoader} to find implementations. It returns the first one
     * found.
     * @return An instance of the API
     * @throws IllegalStateException If no implementation is found on the class path
     */
    static Trees get() {
        final ServiceLoader<Trees> loader = ServiceLoader.load(Trees.class);
        final Iterator<Trees> iterator = loader.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        throw new IllegalStateException("No Trees implementation found on classpath");
    }
}
