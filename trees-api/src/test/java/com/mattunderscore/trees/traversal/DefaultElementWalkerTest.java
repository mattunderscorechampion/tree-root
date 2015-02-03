package com.mattunderscore.trees.traversal;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mattunderscore.trees.tree.Node;

public class DefaultElementWalkerTest {
    private final Walker<String> walker = new DefaultElementWalker<String>() {
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
