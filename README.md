Trees
=====

A tree collecion and utility library.

Building trees
==============

There are two ways to build trees. From the bottom-up, starting with leaves and attaching them to a parent. From the
top-down, starting the root and adding children.

Prior art
=========

There is a [TreeSet](http://docs.oracle.com/javase/7/docs/api/java/util/TreeSet.html) and a
[TreeMap](http://docs.oracle.com/javase/7/docs/api/java/util/TreeMap.html) implementation in the standard
Java library. This does not expose a tree implementation just a navigable, sorted set. The tree basis iss just an
implementation detail. This feels like the correct approach for a Collection API. For the Collection API it does not
matter for the data structure is implemented.

There is a [tree API](https://github.com/markbaird/JavaTree) that extends the Collection API by implementing the
Iterable and Serializable interfaces. The Serializable interface depends on whether the element type is Serializable.
The Iterable interface is not suitable as the order of tree traversal matters. The implementation assumes that the trees
will be mutable, This is like the Collection implementation that to expose an unmodifiable implementation does so using
a mutable API that throws an
[UnsupportedOperationException](http://docs.oracle.com/javase/7/docs/api/java/lang/UnsupportedOperationException.html)
when invoking mutation operations. This is evidence that the Collections API is broken, its dependence on doing things 
st run time that should be enforced by the API.

There is an implementation of the [DOM Document](http://docs.oracle.com/javase/7/docs/api/org/w3c/dom/Document.html)
API that provides a use case specific tree implementation. Tree elements and node metadata both implement the same Node
interface.
 
Tree implementations
====================

Tree implementations are flexible. Child collection mutability is based on the tree implementation. Likewise the
ordering of children is based on the tree implementation.