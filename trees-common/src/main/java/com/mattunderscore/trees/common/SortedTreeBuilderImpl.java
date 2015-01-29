package com.mattunderscore.trees.common;

import java.util.Comparator;

import com.mattunderscore.trees.OperationNotSupportedForType;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.organised.OrganisedTreeBuilder;
import com.mattunderscore.trees.sorted.SortedTreeBuilder;
import com.mattunderscore.trees.sorted.SortingAlgorithm;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;

/**
 * A sorted tree builder that uses a temporary tree to sort the tree.
 * @author Matt Champion on 29/01/15
 */
public final class SortedTreeBuilderImpl<E> implements SortedTreeBuilder<E> {
    private final SPISupport helper;
    private final Comparator<E> comparator;
    private final SortingAlgorithm algorithm;
    private final LinkedTree<E> tree;

    public SortedTreeBuilderImpl(SPISupport helper, Comparator<E> comparator, SortingAlgorithm algorithm) {
        this.helper = helper;
        this.comparator = comparator;
        this.algorithm = algorithm;
        tree = new LinkedTree<>(null);
    }

    @Override
    public OrganisedTreeBuilder<E, Tree<E, Node<E>>> addElement(E element) {
        return null;
    }

    @Override
    public <T extends Tree<E, Node<E>>> T build(Class<T> klass) throws OperationNotSupportedForType {
        return helper.convertTree(klass, tree);
    }

    @Override
    public <T extends Tree<E, Node<E>>> T build(TypeKey<T> type) throws OperationNotSupportedForType {
        return build(type.getType());
    }
}
