package com.mattunderscore.trees.impl;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.mattunderscore.trees.Trees;

/**
 * Unit tests for {@link TreesImpl}.
 *
 * @author Matt Champion on 26/07/2015
 */
public final class TreesImplTest {

    @Test
    public void setUp() {
        assertTrue(Trees.get() instanceof TreesImpl);
    }

}
