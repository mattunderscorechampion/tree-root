package com.mattunderscore.trees.binary.search;

import java.util.Comparator;

import net.jcip.annotations.Immutable;

import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.sorted.SortingAlgorithm;

/**
 * Sorting algorithm for binary search trees.
 * @author Matt Champion on 29/01/15
 */
@Immutable
public final class BinarySearchTreeSortingAlgorithm implements SortingAlgorithm {
    @Override
    public <E> void addNewElement(Comparator<E> comparator, MutableTree<E, ? extends MutableNode<E>> tree, E element) {
        if (tree.isEmpty()) {
            tree.setRoot(element);
        }
        else {
            addTo(comparator, tree.getRoot(), element);
        }
    }

    private <E> void addTo(Comparator<E> comparator, MutableNode<E> node, E element) {
        final int comparison = comparator.compare(node.getElement(), element);
        // Requires a way to add an element to a specific position
        throw new UnsupportedOperationException("Not currently implemented");
    }
}
