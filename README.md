#Tree Root

A tree structure and utility library called Tree Root.

##Status
This library is sill in an early stage of development. The first major release has not yet been made and the API has
not been fixed.

Travis CI: [![Build Status](https://travis-ci.org/mattunderscorechampion/tree-root.svg?branch=master)](https://travis-ci.org/mattunderscorechampion/tree-root)
Coveralls: [![Coverage Status](https://coveralls.io/repos/mattunderscorechampion/tree-root/badge.png)](https://coveralls.io/r/mattunderscorechampion/tree-root)

####Binary search tree example

```java
final ServiceLoader<Trees> serviceLoader = ServiceLoader.load(Trees.class);
final Trees trees = serviceLoader.iterator().next();

final SortingTreeBuilder<Integer, BinaryTreeNode<Integer>> builder = trees
    .treeBuilders()
    .sortingTreeBuilder();
final BinarySearchTree<Integer> tree = builder
    .addElement(2)
    .addElement(1)
    .addElement(3)
    .build(BinarySearchTree.<Integer>typeKey());

tree
    .addElement(4)
    .addElement(6)
    .addElement(5);

final Iterator<Integer> iterator = trees
    .treeIterators()
    .inOrderElementsIterator(tree);

while (iterator.hasNext()) {
    System.out.println("Element: " + iterator.next());
}
```

This example builds a mutable, binary search tree and iterates over the elements in order, left, root, right,
generating the output:

```
Element: 1
Element: 2
Element: 3
Element: 5
Element: 6
```

##Documentation

* [Prior art](docs/prior-art.md)
* [Design principles](docs/prior-art.md)
* API
   * [Definitions](docs/definitions.md)
   * [Concepts](docs/concepts.md)
   * [Examples](docs/examples.md)
   * [Tree implementations](docs/tree-implementations.md)
* [SPI](docs/spi.md)
* [Simple collections](docs/simple-collections.md)

##Targeted Language

The current target is Java 8. Some Java 8 features are being used in the API.

##License

Copyright (c) 2014, 2015 Matthew Champion

Licensed under BSD 3-clause license
