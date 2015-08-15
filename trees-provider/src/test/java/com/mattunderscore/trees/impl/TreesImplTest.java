package com.mattunderscore.trees.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.mattunderscore.simple.collections.SimpleCollection;
import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.linked.tree.LinkedTree;

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

    @Test
    public void availableTreeImplementations() {
        final Trees trees = Trees.get();
        final SimpleCollection<Class<?>> implementations = trees.availableTreeImplementations();
        assertTrue(implementations.size() == 1);
        final List<Class<?>> classes = new ArrayList<>();
        implementations.forEach(classes::add);
        assertTrue(classes.contains(LinkedTree.class));
    }
}
