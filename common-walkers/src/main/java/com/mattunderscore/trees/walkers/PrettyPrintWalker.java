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

package com.mattunderscore.trees.walkers;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;

import com.mattunderscore.trees.traversal.TreeWalker;

/**
 * Pretty printer for trees. Prints tree in a Lisp-like representation.
 * @author Matt Champion on 31/08/2015
 */

public final class PrettyPrintWalker<T> implements TreeWalker<T> {
    private final OutputStream stream;

    public PrettyPrintWalker(OutputStream stream) {
        this.stream = stream;
    }

    @Override
    public void onStarted() {
        try {
            stream.write("(".getBytes());
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void onNode(Object node) {
        try {
            stream.write(node.toString().getBytes());
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void onNodeChildrenStarted(Object node) {
        try {
            stream.write(" (".getBytes());
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void onNodeChildrenRemaining(Object node) {
        try {
            stream.write(" ".getBytes());
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void onNodeChildrenCompleted(Object node) {
        try {
            stream.write(")".getBytes());
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public void onNodeNoChildren(Object node) {
    }

    @Override
    public void onCompleted() {
        try {
            stream.write(")".getBytes());
        }
        catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
