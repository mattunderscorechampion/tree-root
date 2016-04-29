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

package com.mattunderscore.tree.root.querying;

import com.mattunderscore.simple.collections.SimpleCollection;
import com.mattunderscore.simple.collections.WrappingSimpleCollection;
import com.mattunderscore.tree.root.querying.IsBalancedTreeReducer.IsBalancedResult;
import com.mattunderscore.trees.binary.OpenBinaryTreeNode;
import com.mattunderscore.trees.query.Querier;
import com.mattunderscore.trees.query.ReductionResult;
import com.mattunderscore.trees.tree.OpenNode;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Implementation of {@link Querier}.
 * @author Matt Champion on 27/08/2015
 */
public final class QuerierImpl implements Querier {
    private final PostOrderPartialReducerDriver reducerDriver = new PostOrderPartialReducerDriver();

    public QuerierImpl() {
    }

    @Override
    public <E, N extends OpenNode<E, N>> int height(N node) {
        if (node == null) {
            throw new NullPointerException("A null node cannot have a height");
        }

        return this.<E, N, Integer>reduce(node, (N n, Collection<Integer> childResults) -> childResults.size() == 0 ? 0 : Collections.max(childResults) + 1);
    }

    @Override
    public <E, N extends OpenNode<E, N>> SimpleCollection<List<N>> pathsToLeaves(N node) {
        return new WrappingSimpleCollection<>(
            BackPathsFromLeaves
                .backPathsFromLeavesOf(node)
                .stream()
                .map(BackPath::toPath)
                .collect(Collectors.toSet()));
    }

    @Override
    public <E, N extends OpenBinaryTreeNode<E, N>> boolean isBalanced(N node) {
        if (node == null) {
            throw new NullPointerException("A null node cannot be balanced");
        }

        return partialReduce(node, IsBalancedTreeReducer.get()).isBalanced();
    }

    @Override
    public <E, N extends OpenNode<E, N>, R> R partialReduce(
            N node,
            BiFunction<N, Collection<R>, ReductionResult<R>> reducer) {

        return reducerDriver.reduce(node, reducer);
    }

}
