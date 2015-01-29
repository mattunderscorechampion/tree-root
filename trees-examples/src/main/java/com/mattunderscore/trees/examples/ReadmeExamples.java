package com.mattunderscore.trees.examples;

import java.util.Iterator;
import java.util.ServiceLoader;

import com.mattunderscore.trees.Trees;
import com.mattunderscore.trees.binary.search.BinarySearchTree;
import com.mattunderscore.trees.construction.BottomUpTreeBuilder;
import com.mattunderscore.trees.construction.TypeKey;
import com.mattunderscore.trees.sorted.SortingTreeBuilder;
import com.mattunderscore.trees.traversal.Walker;
import com.mattunderscore.trees.tree.Node;
import com.mattunderscore.trees.tree.Tree;
import com.mattunderscore.trees.utilities.ComparableComparator;

/**
 * Examples used in the README.
 * @author Matt Champion on 29/01/15
 */
public final class ReadmeExamples {
    public void immutableTree() {
        final ServiceLoader<Trees> serviceLoader = ServiceLoader.load(Trees.class);
        final Trees trees = serviceLoader.iterator().next();

        final BottomUpTreeBuilder<String> builder = trees.treeBuilders().bottomUpBuilder();
        final Tree<String, Node<String>> tree = builder.create("a",
            builder.create("b"),
            builder.create("c"))
            .build(new TypeKey<Tree<String, Node<String>>>(){});

        trees.treeWalkers().walkElementsInOrder(tree, new Walker<String>() {
            @Override
            public void onEmpty() {
                System.out.println("Empty");
            }

            @Override
            public boolean onNext(String node) {
                System.out.println("Element: " + node);
                return true;
            }

            @Override
            public void onCompleted() {
                System.out.println("Complete");
            }
        });
    }

    public void binarySearchTree() {
        final ServiceLoader<Trees> serviceLoader = ServiceLoader.load(Trees.class);
        final Trees trees = serviceLoader.iterator().next();

        final SortingTreeBuilder<Integer> builder = trees.treeBuilders().sortingTreeBuilder();
        final BinarySearchTree<Integer> tree = builder
            .addElement(2)
            .addElement(1)
            .addElement(3)
            .build(BinarySearchTree.<Integer>typeKey());

        tree
            .addElement(4)
            .addElement(6)
            .addElement(5);

        final Iterator<Integer> iterator = trees.treeIterators().inOrderElementsIterator(tree);
        while (iterator.hasNext()) {
            System.out.println("Element: " + iterator.next());
        }
    }
}
