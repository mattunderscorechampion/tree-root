package com.mattunderscore.trees.transformation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Unit tests for {@link RotationDirection}.
 *
 * @author Matt Champion on 23/08/2015
 */
public final class RotationDirectionTest {

    @Test
    public void left() {
        assertEquals("LEFT", RotationDirection.LEFT.toString());
    }

    @Test
    public void right() {
        assertEquals("RIGHT", RotationDirection.RIGHT.toString());
    }
}
