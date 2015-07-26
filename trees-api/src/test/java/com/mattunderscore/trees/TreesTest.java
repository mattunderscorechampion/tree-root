package com.mattunderscore.trees;

import org.junit.Test;

/**
 * Unit tests for {@link Trees}.
 *
 * @author Matt Champion on 26/07/2015
 */
public final class TreesTest {

    @Test(expected = IllegalStateException.class)
    public void setUp() {
        Trees.get();
    }

}
