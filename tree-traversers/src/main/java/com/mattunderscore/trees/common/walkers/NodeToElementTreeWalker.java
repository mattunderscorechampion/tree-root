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

package com.mattunderscore.trees.common.walkers;

import com.mattunderscore.trees.traversal.TreeWalker;
import com.mattunderscore.trees.tree.Node;

/**
 * A Node tree walker that unwraps the elements and passes them to a delegated element tree walker.
 * @author Matt Champion on 31/01/15
 */
public final class NodeToElementTreeWalker<E, N extends Node<E>> implements TreeWalker<N> {
      private final TreeWalker<E> delegateTreeWalker;

      public NodeToElementTreeWalker(TreeWalker<E> delegateTreeWalker) {
            this.delegateTreeWalker = delegateTreeWalker;
      }

      @Override
      public void onStarted() {
            delegateTreeWalker.onStarted();
      }

      @Override
      public void onNode(N node) {
            delegateTreeWalker.onNode(node.getElement());
      }

      @Override
      public void onNodeChildrenStarted(N node) {
            delegateTreeWalker.onNodeChildrenStarted(node.getElement());
      }

      @Override
      public void onNodeChildrenRemaining(N node) {
            delegateTreeWalker.onNodeChildrenRemaining(node.getElement());
      }

      @Override
      public void onNodeChildrenCompleted(N node) {
            delegateTreeWalker.onNodeChildrenCompleted(node.getElement());
      }

      @Override
      public void onNodeNoChildren(N node) {
            delegateTreeWalker.onNodeNoChildren(node.getElement());
      }

      @Override
      public void onCompleted() {
            delegateTreeWalker.onCompleted();
      }
}
