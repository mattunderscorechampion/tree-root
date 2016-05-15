#Tree Root

A tree structure and utility library called Tree Root.

####Status
This library is sill in an early stage of development. The first major release has not yet been made and the API has
not been fixed. No formalised version scheme is currently being followed.

Travis CI: [![Build Status](https://travis-ci.org/mattunderscorechampion/tree-root.svg?branch=master)](https://travis-ci.org/mattunderscorechampion/tree-root)

Coveralls: [![Coverage Status](https://coveralls.io/repos/mattunderscorechampion/tree-root/badge.png)](https://coveralls.io/r/mattunderscorechampion/tree-root)

[Maven site](http://www.mattunderscore.com/tree-root/index.html)

[API Javadoc](http://www.mattunderscore.com/tree-root/trees-api/apidocs/index.html)

####Binary search tree example

```java
final Trees trees = Trees.get();

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

####Documentation

* [Prior art](src/site/markdown/prior-art.md)
* [Design principles](src/site/markdown/design-principles.md)
* [Changelog](src/site/markdown/changelog.md)
* API
   * [Definitions](src/site/markdown/definitions.md)
   * [Concepts](src/site/markdown/concepts.md)
   * [Examples](src/site/markdown/examples.md)
   * [Tree implementations](src/site/markdown/tree-implementations.md)
   * [Setup](src/site/markdown/setup.md)
* [SPI](src/site/markdown/spi.md)
* [Custom tree implementations](src/site/markdown/custom-tree-implementations.md)
* [Simple collections](src/site/markdown/simple-collections.md)
* [Iterators](src/site/markdown/iterators.md)
* [Tree reducers](src/site/markdown/tree-reducers.md)

####Targeted Language

The current target is Java 8. Some Java 8 features are being used in the API.

####License

Copyright (c) 2014, 2015 Matthew Champion

Licensed under BSD 3-clause license
