/* Copyright © 2016 Matthew Champion
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

package com.mattunderscore.trees.query;

/**
 * Results for partial reductions.
 * @author Matt Champion on 29/04/16
 */
public final class ReductionResults {
    private static final ReductionResult NO_RESULT = new NoResult(true);
    private static final ReductionResult HALT_AND_NO_RESULT = new NoResult(true);

    private ReductionResults() {
    }

    /**
     * @param result The result
     * @param <R> The type of result
     * @return The result of reducing a node
     */
    public static <R> ReductionResult<R> result(R result) {
        return new Result<>(result, true);
    }

    /**
     * @param result The result
     * @param <R> The type of result
     * @return The final result of reducing the tree
     */
    public static <R> ReductionResult<R> haltAndResult(R result) {
        return new Result<>(result, false);
    }

    /**
     * Report that no value resulted from the reduction of the node.
     * @param <R> The type of result
     * @return The result of reducing a node
     */
    public static <R> ReductionResult<R> noResult() {
        return NO_RESULT;
    }

    /**
     * Report that no value resulted from the reduction of the tree
     * @param <R> The type of result
     * @return The final result of reducing the tree
     */
    public static <R> ReductionResult<R> haltAndNoResult() {
        return HALT_AND_NO_RESULT;
    }

    private static final class NoResult<R> implements ReductionResult<R> {
        private final boolean shouldContinue;

        private NoResult(boolean shouldContinue) {
            this.shouldContinue = shouldContinue;
        }

        @Override
        public boolean shouldContinue() {
            return shouldContinue;
        }

        @Override
        public boolean hasResult() {
            return false;
        }

        @Override
        public R result() {
            throw new IllegalStateException("No result");
        }
    }

    private static final class Result<R> implements ReductionResult<R> {
        private final R result;
        private final boolean shouldContinue;

        private Result(R result, boolean shouldContinue) {
            this.result = result;
            this.shouldContinue = shouldContinue;
        }

        @Override
        public boolean shouldContinue() {
            return shouldContinue;
        }

        @Override
        public boolean hasResult() {
            return true;
        }

        @Override
        public R result() {
            return result;
        }
    }
}
