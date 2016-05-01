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

package com.mattunderscore.tree.root.querying;

import com.mattunderscore.trees.query.ReductionResult;
import com.mattunderscore.trees.query.ReductionResults;
import com.mattunderscore.trees.tree.Node;
import org.hamcrest.core.IsCollectionContaining;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.BiFunction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit tests for {@link PostOrderPartialReducerDriver}.
 * @author Matt Champion on 01/05/16
 */
public final class PostOrderPartialReducerDriverTest {
    @Mock
    private Node<String> parent;

    @Mock
    private Node<String> child0;

    @Mock
    private Node<String> child1;

    @Mock
    private BiFunction<Node<String>, Collection<String>, ReductionResult<String>> function;

    @Captor
    private ArgumentCaptor<Collection<String>> resultsCaptor;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        initMocks(this);

        when(parent.isLeaf()).thenReturn(false);
        when(child0.isLeaf()).thenReturn(true);
        when(child1.isLeaf()).thenReturn(true);
        final Iterator parentIterator = Arrays.asList(child0, child1).iterator();
        when(parent.childIterator()).thenReturn(parentIterator);
        when(child0.childIterator()).thenReturn(Collections.emptyIterator());
        when(child1.childIterator()).thenReturn(Collections.emptyIterator());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void reduceNoResult() {
        when(function.apply(child0, Collections.emptyList())).thenReturn(ReductionResults.noResult());
        when(function.apply(child1, Collections.emptyList())).thenReturn(ReductionResults.noResult());
        when(function.apply(eq(parent), isA(Collection.class))).thenReturn(ReductionResults.noResult());

        boolean exception = false;
        final PostOrderPartialReducerDriver driver = new PostOrderPartialReducerDriver();
        try {
            driver.reduce(parent, function);
        }
        catch (IllegalStateException e) {
            exception = true;
        }

        verify(function).apply(eq(parent), resultsCaptor.capture());
        final Collection<String> childResults = resultsCaptor.getValue();
        assertEquals(0, childResults.size());

        assertTrue(exception);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void reduce() {
        when(function.apply(child0, Collections.emptyList())).thenReturn(ReductionResults.result("a"));
        when(function.apply(child1, Collections.emptyList())).thenReturn(ReductionResults.result("b"));
        when(function.apply(eq(parent), isA(Collection.class))).thenReturn(ReductionResults.result("c"));

        final PostOrderPartialReducerDriver driver = new PostOrderPartialReducerDriver();
        final String result = driver.reduce(parent, function);

        verify(function).apply(eq(parent), resultsCaptor.capture());
        final Collection<String> childResults = resultsCaptor.getValue();
        assertEquals(2, childResults.size());
        assertThat(childResults, IsCollectionContaining.hasItems("a", "b"));

        assertEquals(result, "c");
    }

    @SuppressWarnings("unchecked")
    @Test
    public void reduceShortcut() {
        when(function.apply(child0, Collections.emptyList())).thenReturn(ReductionResults.haltAndResult("a"));

        final PostOrderPartialReducerDriver driver = new PostOrderPartialReducerDriver();
        final String result = driver.reduce(parent, function);

        verify(function, never()).apply(eq(parent), isA(Collection.class));
        verify(function, never()).apply(eq(child1), isA(Collection.class));

        assertEquals(result, "a");
    }
}
