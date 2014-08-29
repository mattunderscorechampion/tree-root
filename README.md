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
 
Tree implementations
====================

Tree implementations are flexible. Child collection mutability is based on the tree implementation. Likewise the
ordering of children is based on the tree implementation.