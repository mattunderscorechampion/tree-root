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

package com.mattunderscore.trees.strings;

import com.mattunderscore.trees.*;
import org.junit.Test;

/**
 * @author matt on 10/06/14.
 */
public class StringTreeBuilderTest {
    @Test
    public void build0() {
        TreeBuilder builder;
        final TreeMutator.NodeAppender depth1 = builder.setRoot("a");
        final TreeMutator.NodeAppender depth2 = depth1.addChild("b");
        depth1.addChild("c");
        depth2.addChild("d");
    }

    @Test
    public void build1() {
        TreeBuilder builder;
        builder.setRoot("a")
            .addChild("b")
                .addChild("c")
                .parent()
            .addChild("d");
    }

    @Test
    public void build2() {
        TreeFactory factory;
        factory.appendSubTrees(
            factory.createNode("a"),
            factory.appendSubTrees(
                factory.createNode("b"),
                factory.createNode("c")),
            factory.createNode("d"));
    }

    @Test
    public void build3() {
        Treer treer;
        treer.create(
            treer.create("a"),
            treer.create(
                treer.create(
                    treer.create("b"),
                    treer.create("c")),
                treer.create("d")));
    }

    @Test
    public void build4() {
        Trees trees;
        trees.create("a",
            trees.create("b",
                trees.create("c")),
            trees.create("d")
        );
    }
}
