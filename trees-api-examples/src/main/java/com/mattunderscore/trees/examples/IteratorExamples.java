package com.mattunderscore.trees.examples;

import com.mattunderscore.trees.mutable.MutableNode;
import com.mattunderscore.trees.mutable.MutableTree;
import com.mattunderscore.trees.traversal.TreeIteratorFactory;
import com.mattunderscore.trees.tree.Node;

import java.util.Iterator;

/**
 * @author Matt Champion
 */
public final class IteratorExamples {
    public void nodeIterator(TreeIteratorFactory iterators, MutableTree<String, MutableNode<String>> tree) {
        final Iterator<MutableNode<String>> iterator = iterators.breadthFirstIterator(tree);
        if (iterator.hasNext()) {
            final MutableNode<String> node = iterator.next();
        }
    }

    public void elementIterator(TreeIteratorFactory iterators, MutableTree<String, MutableNode<String>> tree) {
        final Iterator<String> iterator = iterators.breadthFirstElementsIterator(tree);
        if (iterator.hasNext()) {
            final String element = iterator.next();
        }
    }

    public void iterateAsSubtypeOfNode(TreeIteratorFactory iterators, MutableTree<String, MutableNode<String>> tree) {
        final Iterator<Node<String>> iterator = iterators
            .<String, Node<String>, MutableTree<String, MutableNode<String>>>breadthFirstIterator(tree);
        if (iterator.hasNext()) {
            final Node<String> node = iterator.next();
        }
    }
}
