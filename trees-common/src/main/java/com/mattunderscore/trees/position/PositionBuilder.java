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

package com.mattunderscore.trees.position;

import com.mattunderscore.trees.ids.Id;

/**
 * A position builder.
 * @author Matt Champion on 25/10/14.
 */
public final class PositionBuilder {
    private final Position position;

    public PositionBuilder() {
        position = new RootPosition();
    }

    public PositionBuilder(Id id) {
        position = new IDValidatingPosition(new RootPosition(), id);
    }

    private PositionBuilder(Position position) {
        this.position = position;
    }

    /**
     * @param child The index of the child
     * @return A new position builder
     */
    public PositionBuilder child(int child) {
        if (child < 0 ) {
            throw new IllegalArgumentException("Child position cannot be less than zero");
        }

        return new PositionBuilder(
            new CompositePosition(
                position,
                new NChildRelativePosition(child)));
    }

    public PositionBuilder child(int child, Id id) {
        if (child < 0 ) {
            throw new IllegalArgumentException("Child position cannot be less than zero");
        }

        return new PositionBuilder(
                new CompositePosition(
                        position,
                        new IDValidatingRelativePosition(
                                new NChildRelativePosition(child), id)));
    }

    /**
     * @return The position
     */
    public Position build() {
        return position;
    }
}
