package com.mattunderscore.trees.traversal;

import static org.junit.Assert.*;

import org.junit.Test;

public class DefaultTreeWalkerTest {
    private static final DefaultTreeWalker<String> WALKER = new DefaultTreeWalker<String>() {};

    @Test
    public void testOnStarted() throws Exception {
        WALKER.onStarted();
    }

    @Test
    public void testOnNode() throws Exception {
        WALKER.onNode(null);
    }

    @Test
    public void testOnNodeChildrenStarted() throws Exception {
        WALKER.onNodeChildrenStarted(null);
    }

    @Test
    public void testOnNodeChildrenRemaining() throws Exception {
        WALKER.onNodeChildrenStarted(null);
    }

    @Test
    public void testOnNodeChildrenCompleted() throws Exception {
        WALKER.onNodeChildrenCompleted(null);
    }

    @Test
    public void testOnNodeNoChildren() throws Exception {
        WALKER.onNodeNoChildren(null);
    }

    @Test
    public void testOnCompleted() throws Exception {
        WALKER.onCompleted();
    }
}
