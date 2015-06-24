package com.mattunderscore.trees.traversal;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mattunderscore.trees.tree.ClosedNode;

public class DefaultNodeWalkerTest {
    private final Walker<ClosedNode<String>> walker = new DefaultNodeWalker<String, ClosedNode<String>>() {
    };

    @Test
    public void testOnEmpty() throws Exception {
        walker.onEmpty();
    }

    @Test
    public void testOnNext() throws Exception {
        assertTrue(walker.onNext(null));
    }

    @Test
    public void testOnCompleted() throws Exception {
        walker.onCompleted();
    }
}
