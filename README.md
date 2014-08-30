Trees
=====

A tree structure and utility library.

Building trees
==============

There are two ways to build trees. From the bottom-up, starting with leaves and attaching them to a parent. From the
top-down, starting the root and adding children.

Prior art
=========

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
the [Zkoss framework](http://www.zkoss.org/javadoc/latest/zk/org/zkoss/zul/Tree.html).

Design principles
=================

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

Tree implementations
====================

Tree implementations are flexible. Child collection mutability is based on the tree implementation. Likewise the
ordering of children is based on the tree implementation.

Balanced tree builders and balancing trees
==========================================

The API supports balanced tree builders and self-balancing trees. A
[balanced tree](http://xlinux.nist.gov/dads//HTML/balancedtree.html) requires specific placement of nodes. For a tree to
be balanced no leaf should be farther away from the root than any other. If the nodes can be placed anywhere this
constraint can be violated. This is where adding features as needed is useful. The BalancedTreeBuilder returns balanced
trees, it does this by not allowing the specification of the placement of nodes or elements but instead leaving the
placement up to the builder. It is possible to create a mutable tree using a BalancedTreeBuilder that starts balanced
and then modify it so that it is not. The BalancingTree is a mutable tree that ensures that trees remain balanced.
Again this is done by leaving the placement up to the tree. It provides immutable nodes and an API that allows placement
on the tree instead of the nodes.

SPI
===

There is also an SPI that can be used to support specific implementations. The SPI can be used to expose more powerful
functionality specific to tree implementations. The ServiceLoader is used to allow the implementations of the SPI to be
discovered at runtime. Each component of the SPI provides a method that returns the class of the tree or node that the
implementation applies to.

NOTE: handling lookup failure when invoking utilities for types that do not provide SPI implementations still needs to
be implemented.

When a TreesImpl object is created it looks up all the implementations using the ServiceLoader and registers the SPI
implementation against the class that is used for. When a utility operation is used it looks up the SPI implementation
for the tree or node class it has been invoked for and uses the SPI to perform the operation. The SPI implementation
may use the fact that it registered against the concrete type to cast a Tree or Node to the concrete type safely at
runtime. This allows for the use of methods not exposed by the API.

This is intended to provide a Python like approach to certain utilities. The Python len() function can be used for any
object that provides a \__len\__() method. This allows standard functions or utilities to be used with new and extended
types without exposing behaviour in the API.
