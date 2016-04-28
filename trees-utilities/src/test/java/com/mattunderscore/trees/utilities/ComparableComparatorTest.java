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

package com.mattunderscore.trees.utilities;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Comparator test.
 * @author Matt Champion
 */
public final class ComparableComparatorTest {
    @Test
    public void lessThan() {
        final Comparator<Integer> comparator = ComparableComparator.get();
        assertEquals(-1, comparator.compare(3, 7));
    }

    @Test
    public void greaterThan() {
        final Comparator<Integer> comparator = ComparableComparator.get();
        assertEquals(1, comparator.compare(7, 3));
    }

    @Test
    public void equals() {
        final Comparator<Integer> comparator = ComparableComparator.get();
        assertEquals(0, comparator.compare(4, 4));
    }

    @Test
    public void max() {
        final Comparator<Integer> comparator = ComparableComparator.get();
        final List<Integer> collection = asList(1, 5, 3, 7, 3, 1, 3, 12, 5, 2);
        assertEquals(12, (int) Collections.max(collection, comparator));
    }

    @Test
    public void min() {
        final Comparator<Integer> comparator = ComparableComparator.get();
        final List<Integer> collection = asList(1, 5, 3, 7, 3, 1, 3, 12, 5, 2);
        assertEquals(1, (int) Collections.min(collection, comparator));
    }
}
