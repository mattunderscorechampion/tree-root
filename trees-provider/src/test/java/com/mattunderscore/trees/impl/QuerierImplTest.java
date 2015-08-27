package com.mattunderscore.trees.impl;

import static org.junit.Assert.*;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import com.mattunderscore.trees.linked.tree.LinkedTree;
import com.mattunderscore.trees.query.Querier;

/**
 * Unit tests for {@link QuerierImpl}.
 *
 * @author Matt Champion on 27/08/2015
 */
public final class QuerierImplTest {

    @Ignore("Paths not implemented")
    @Test
    public void heightLeaf() {
        final LinkedTree<String> node = new LinkedTree<>("a");

        final Querier querier = new QuerierImpl();

        assertEquals(0, querier.height(node));
    }

    @Ignore("Paths not implemented")
    @Test
    public void heightSimple() {
        final LinkedTree<String> node = new LinkedTree<>("a");
        node.addChild("b");

        final Querier querier = new QuerierImpl();

        assertEquals(1, querier.height(node));
    }

    @Ignore("Paths not implemented")
    @Test
    public void heightManyPaths() {
        final LinkedTree<String> node = new LinkedTree<>("a");
        node.addChild("b");
        node.addChild("c");

        final Querier querier = new QuerierImpl();

        assertEquals(1, querier.height(node));
    }

    @Ignore("Paths not implemented")
    @Test
    public void heightSinglePath() {
        final LinkedTree<String> node = new LinkedTree<>("a");
        node.addChild("b");
        node
            .addChild("c")
            .addChild("d");

        final Querier querier = new QuerierImpl();

        assertEquals(2, querier.height(node));
    }
}
