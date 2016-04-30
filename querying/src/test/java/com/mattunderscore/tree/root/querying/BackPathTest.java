package com.mattunderscore.tree.root.querying;

import com.mattunderscore.trees.binary.BinaryTreeNode;
import com.mattunderscore.trees.tree.Tree;
import org.junit.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link BackPath}.
 * @author Matt Champion on 30/04/16
 */
public final class BackPathTest {
    @Mock
    private BinaryTreeNode<String> parent;

    @Mock
    private BinaryTreeNode<String> node;

    @Test
    public void testGetNullParent() throws Exception {
        final BackPath<String, BinaryTreeNode<String>> backPath = new BackPath<>(null, node);

        assertNull(backPath.getParent());
    }

    @Test
    public void testGetParent() throws Exception {
        BackPath<String, BinaryTreeNode<String>> parentBackPath = new BackPath<>(null, parent);
        final BackPath<String, BinaryTreeNode<String>> backPath = new BackPath<>(parentBackPath, node);

        assertEquals(parentBackPath, backPath.getParent());
    }

    @Test
    public void testGetNode() throws Exception {
        final BackPath<String, BinaryTreeNode<String>> backPath = new BackPath<>(null, node);

        assertEquals(node, backPath.getNode());
    }

    @Test
    public void testToTrivialPath() throws Exception {
        final BackPath<String, BinaryTreeNode<String>> backPath = new BackPath<>(null, node);

        final List<BinaryTreeNode<String>> path = backPath.toPath();
        assertEquals(1, path.size());
        assertEquals(node, path.get(0));
    }

    @Test
    public void testToSimplePath() throws Exception {
        final BackPath<String, BinaryTreeNode<String>> backPath = new BackPath<>(new BackPath<>(null, parent), node);

        final List<BinaryTreeNode<String>> path = backPath.toPath();
        assertEquals(2, path.size());
        assertEquals(parent, path.get(0));
        assertEquals(node, path.get(1));
    }
}
