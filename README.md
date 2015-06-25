#Tree Root

A tree structure and utility library called Tree Root.

##Status
This library is sill in an early stage of development. The first major release has not yet been made and the API has
not been fixed.

Travis CI: [![Build Status](https://travis-ci.org/mattunderscorechampion/tree-root.svg?branch=master)](https://travis-ci.org/mattunderscorechampion/tree-root)
Coveralls: [![Coverage Status](https://coveralls.io/repos/mattunderscorechampion/tree-root/badge.png)](https://coveralls.io/r/mattunderscorechampion/tree-root)

##Building trees

There are multiple ways to build trees. From the bottom-up, starting with leaves and attaching them to a parent. From
the top-down, starting the root and adding children. Organised and sorted tree builders where the structure of the tree
is left up to the builder. The builders are generalised for any tree implementation. The tree structure is specified
first then the implementation of the tree.

The implementation of the tree is specified either by the implementation class or a TypeKey. The TypeKey is an abstract
class that should be subclassed to fix the type of the tree implementation. This allows for the generic parameters to
be matched without warnings about unchecked casts.

##Examples

####Immutable tree Example

    final ServiceLoader<Trees> serviceLoader = ServiceLoader.load(Trees.class);
    final Trees trees = serviceLoader.iterator().next();

    final BottomUpTreeBuilder<String, ClosedMutableNode<String>> builder = trees.treeBuilders().bottomUpBuilder();
    final Tree<String, ClosedMutableNode<String>> tree = builder.create("a",
        builder.create("b"),
        builder.create("c"))
        .build(new TypeKey<Tree<String, ClosedMutableNode<String>>>(){});

    trees.treeWalkers().walkElementsInOrder(tree, new DefaultElementWalker<String>() {
        @Override
        public boolean onNext(String node) {
            System.out.println("Element: " + node);
            return true;
        }
    });

This example builds an immutable tree and walks over the elements in order, left, root, right, generating the output:

    Element: b
    Element: a
    Element: c

####Binary search tree example

    final ServiceLoader<Trees> serviceLoader = ServiceLoader.load(Trees.class);
    final Trees trees = serviceLoader.iterator().next();

    final SortingTreeBuilder<Integer, ClosedBinaryTreeNode<Integer>> builder = trees.treeBuilders().sortingTreeBuilder();
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

This example builds a mutable, binary search tree and iterates over the elements in order, left, root, right,
generating the output:

    Element: 1
    Element: 2
    Element: 3
    Element: 5
    Element: 6

##Prior art

There is a [TreeSet](http://docs.oracle.com/javase/7/docs/api/java/util/TreeSet.html) and a
[TreeMap](http://docs.oracle.com/javase/7/docs/api/java/util/TreeMap.html) class in the standard
Java library. This does not expose a tree API just a set and map. The tree is just an
implementation detail. This feels like the correct approach for a Collection API.

There is a [tree API](https://github.com/markbaird/JavaTree) that extends the Collection API by implementing the
Iterable and Serializable interfaces. The Iterable interface is not suitable as it does not specify the order of the
iteration. It has additional methods for returning ordered collections, from the implementations these are not evaluated
lazily. All tree implementations need to provide these separately duplicating work. The API assumes that the trees will
be mutable. This is like the Collection implementation that to expose an unmodifiable implementation does so using
a mutable API that throws an
[UnsupportedOperationException](http://docs.oracle.com/javase/7/docs/api/java/lang/UnsupportedOperationException.html)
when invoking mutation operations. Characteristics should be enforced by the API not by the runtime behavior. This API
is intended for extension will a clean separation between the API and the provided implementations.

There is an implementation of the [DOM Document](http://docs.oracle.com/javase/7/docs/api/org/w3c/dom/Document.html)
API that provides a use case specific tree implementation. The Document tree has multiple types of nodes (elements,
attributes and character data etc.). During navigation these are returned in a custom
[NodeList](http://docs.oracle.com/javase/7/docs/api/org/w3c/dom/NodeList.html) that provides an immutable list that is
not based on the Collections API. This list exposes nodes, not the different types of nodes so it is necessary to check
the type of each node returned.

The [Stanford JavaNLP API](http://nlp.stanford.edu/nlp/javadoc/javanlp/index.html?overview-summary.html) provides a
[tree API](http://nlp.stanford.edu/nlp/javadoc/javanlp/index.html?edu/stanford/nlp/trees/Tree.html). These trees extend
the Collection interface, providing implementations only for non-mutating methods. The tree is mutable using the methods
defined in the Tree class. The Tree implementation contains convenience methods for many things like deep coping and
deciding if one tree dominates another. Placing these methods within the tree implementation makes it hard to extend.

There are other tree implementations provided as a part of other libraries. The
[Prefuse visualisation toolkit](http://prefuse.org/doc/api/index.html?prefuse/data/Tree.html), the
[Apache POI for Open and Microsoft office documents](https://poi.apache.org/apidocs/org/apache/poi/util/BinaryTree.html),
the [Zkoss framework](http://www.zkoss.org/javadoc/latest/zk/org/zkoss/zul/Tree.html). There are many tree
implementations that have been created for specific use cases.

##Design principles

The intention is to provide an easily extensible, generic tree API. The API should build upon itself, adding features
as needed. For example the basic tree API is immutable and it must be extended to provide a mutable interface.

The API should not allow the violation of the constraints of the implementation. An immutable tree should not provide
any way of attempting to mutate the tree.

Utilities should be generic implementations provided outside of a tree that can be applied to any implementation. They
should not be implemented within specific tree implementations.

Where utilities depend on the specific implementations of the tree this should use the SPI and the ServiceLoader
interface. The ServiceLoader can be used to look up implementation specific support for the task provided by the SPI.

For example to rebalance a tree in place, using the methods provided by the API may not allow efficient operations as
they are based on elements. An SPI implementation can be provided for each concrete mutable tree implementation that
provides operations on nodes to support more efficient rebalancing.

##Definitions

* Element - a value associated with the node
* Node - node of the tree, may contain a value, may have child nodes, may not be the child of more than one node
* Leaf - a node that has no children
* Root - a node that has no parent node
* Tree - a data structure that may contain nodes and has at most one root node
* Structural tree - a tree where the child nodes are ordered and may have empty spaces
* Sorted tree - tree that has elements in certain positions based on comparisons between the elements, a mutable sorted
tree can be modified so that it is no longer sorted
* Sorting tree - a mutable sorted tree that adds new elements to the correct positions based on comparisons between the
elements
* Balanced tree - tree that has all its leaves at the same distance from the root
* Balancing tree - a mutable tree that has adds new elements to the correct positions to keep it balanced

##Tree implementations

Tree implementations are flexible. Child collection mutability is based on the tree implementation. Likewise the
ordering of children is based on the tree implementation.

##Balanced tree builders and balancing trees

The API supports balanced tree builders and self-balancing trees. A
[balanced tree](http://xlinux.nist.gov/dads//HTML/balancedtree.html) requires specific placement of nodes. For a tree to
be balanced no leaf should be farther away from the root than any other. If the nodes can be placed anywhere this
constraint can be violated. This is where adding features as needed is useful. The BalancedTreeBuilder returns balanced
trees, it does this by not allowing the specification of the placement of nodes or elements but instead leaving the
placement up to the builder. It is possible to create a mutable tree using a BalancedTreeBuilder that starts balanced
and then modify it so that it is not. The BalancingTree is a mutable tree that ensures that trees remain balanced.
Again this is done by leaving the placement up to the tree. It provides immutable nodes and an API that allows placement
on the tree instead of the nodes.

##Sorted and sorting tree builders

The API supports creating sorted trees and trees that maintain a sorted structure. The builders for these nodes share
the OrganisedTreeBuilder with the balanced tree builders. The sorting tree builders leave the placement of the elements
up to the sorting trees. The sorting tree builders are constrained to only build SortingTrees. The sorted tree builders
require a separate sorting algorithm to place the elements. Sorted and sorting tree builders both need a comparator for
the elements.

##SPI

There is also an SPI that can be used to support specific implementations. The SPI can be used to expose more powerful
functionality specific to tree implementations. The ServiceLoader is used to allow the implementations of the SPI to be
discovered at runtime. Each component of the SPI provides a method that returns the class of the tree or node that the
implementation applies to.

When the SPI component lookup fails ab OperationNotSupportedForType unchecked exception is thrown. This implies that the
service is not complete or the implementation does not support the action.

When a TreesImpl object is created it looks up all the implementations using the ServiceLoader and registers the SPI
implementation against the class that is used for. When a utility operation is used it looks up the SPI implementation
for the tree or node class it has been invoked for and uses the SPI to perform the operation.

The SPI implementation may use the fact that it registered against the concrete type to cast a Tree or Node to the
concrete type safely at runtime. This allows for the use of methods not exposed by the API.

This is intended to provide a Python like approach to certain utilities. The Python len() function can be used for any
object that provides a \__len\__() method. This allows standard functions or utilities to be used with new and extended
types without exposing behaviour in the API.

A more Java centric way of describing this is that the API provides a library that can be used to work with trees.
The SPI provides a framework for implementing tree based data structures that can be worked on by the API.

###Components

_TreeConstructor_ - Used to create trees from the bottom up.

_EmptyTreeConstructor_ - Used to create empty trees. That do not have a root node.

_EmptySortedTreeConstructor_ - Used to create empty trees that have a sorting algorithm. Only of value to mutable,
empty trees.

_TreeConverter_ - Converts a tree of one type to another preserving the tree structure and using the same element
references.

_NodeToTreeConverter_ - Takes a node and creates a subtree with the node as the root. The subtree created must not
mirror modifications to the source tree.

##Tree traversal

Tree traversal can be implemented manually by iterating over the children of nodes. Support is provided by the API for
both external and internal iterators. Internal iteration is supported through walkers. External iteration is supported
through the Java standard library iterators. Pre-order, in-order, post-order and breadth-first orders are all supported.
Iterating over nodes and elements are both supported.

##Selection

The API can be used to select nodes and subtrees. The construction of selectors needs to be made more concise. Selectors
return iterators over either nodes or trees.

##Simple Collection

I have created a SimpleCollection extending Iterable. I had intended to reply on the Collections API for this and for a
time considered returning an Iterable to represent the children. However I found that I wanted to iterate over the 
children without nulls and occasionally with nulls to represent the empty branches.

The SimpleCollection provides a pair of methods for getting iterators and a pair of methods for getting some idea of
the size of the collection. It does not provide indexed access to its elements or methods for modifying the collection,
excepting the remove method in the iterator.

The SimpleCollection might be ordered. If it is ordered it should also have some structure, an upper and lower bound on
the number elements of the collection, for example a binary tree node may have 0 to 2 child nodes, one node for each
branch. One iterator would return only the nodes present, the structural iterator must return two values, in order left
to right returning null if the branch is not present.

##Simple collection implementations

_FixedUncheckedSimpleCollection_ - a simple immutable collection, it is not checked and the array it is created from is
not copied. The creator of the collection must not modify or keep a reference to the array passed to it.

_ArrayListSimpleCollection_ - a simple mutable collection that is not thread safe.

_DuplicateOnWriteSimpleCollection_ - simple collection that returns a modified duplicate on mutation.

_EmptySimpleCollection_ - an empty, immutable simple collection.

##Tree Implementations

_LinkedTree_ - a mutable, not thread safe tree

_TreeNodeImpl_ - an immutable tree

_MutableTreeNodeImpl_ - a mutable tree that does not preserve modification order

_BinaryTreeWrapper_ - an immutable binary tree

_MutableBinaryTreeImpl_ - a mutable binary tree

_BinarySearchTree_ - a mutable binary search tree

_PathCopyTree_ - a path copy tree that uses a separate data structure for the back references between nodes

##Targeted Language

The current target is Java 7. I know Java 8 is the latest version and the Streams API may be helpful for tree
traversals. I maybe able to add some Java 8 specific extensions and I will move the target over to Java 8 towards EOL of
Java 7.

##License

Copyright (c) 2014, 2015 Matthew Champion

Licensed under BSD 3-clause license
