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

package com.mattunderscore.trees.pathcopy.holder;

import com.mattunderscore.trees.construction.TopDownTreeRootBuilder;
import com.mattunderscore.trees.construction.TreeBuilderFactory;
import com.mattunderscore.trees.spi.TreeBuilderFactoryAware;
import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.spi.impl.AbstractNodeToRelatedTreeConverter;

/**
 * Implementation of {@link com.mattunderscore.trees.spi.NodeToRelatedTreeConverter} for
 * {@link com.mattunderscore.trees.pathcopy.holder.PathCopyNode}.
 * @author Matt Champion on 28/01/15.
 */
public final class NodeConverter<E> extends AbstractNodeToRelatedTreeConverter<E, MutableNode<E>, PathCopyTree<E>> implements TreeBuilderFactoryAware {
    private volatile TreeBuilderFactory treeBuilderFactory;

    public NodeConverter() {
        super(PathCopyNode.class, PathCopyTree.class);
    }

    @Override
    public void setTreeBuilderFactory(TreeBuilderFactory treeBuilderFactory) {
        this.treeBuilderFactory = treeBuilderFactory;
    }

    @Override
    protected TopDownTreeRootBuilder<E, MutableNode<E>> getBuilder() {
        return treeBuilderFactory.topDownBuilder();
    }
}
