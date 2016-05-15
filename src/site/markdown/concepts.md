
##Tree implementations

Tree implementations are flexible. Child collection mutability is based on the tree implementation. Likewise the
ordering of children is based on the tree implementation.

##Building trees

There are multiple ways to build trees. From the bottom-up, starting with leaves and attaching them to a parent. From
the top-down, starting the root and adding children. Organised and sorted tree builders where the structure of the tree
is left up to the builder. The builders are generalised for any tree implementation. The tree structure is specified
first then the implementation of the tree.

The implementation of the tree is specified either by the implementation class or a TypeKey. The TypeKey is an abstract
class that should be subclassed to fix the type of the tree implementation. This allows for the generic parameters to
be matched without warnings about unchecked casts.

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

##Open and closed nodes

For node selection it is desirable to be able to select nodes of the same type (or supertype) to the one passed in. To
preserve this type, open nodes expose a type parameter that the child accessor methods, such as childIterator will use
in their return type. Closed nodes extend open nodes and make this type parameter concrete. This ensures that methods
can prove that nodes will return certain subtypes as children. This does restrict returning similar nodes, similar nodes
must return an open subtype.

##Tree traversal

Tree traversal can be implemented manually by iterating over the children of nodes. Support is provided by the API for
both external and internal iterators. Internal iteration is supported through walkers. External iteration is supported
through the Java standard library iterators. Pre-order, in-order, post-order and breadth-first orders are all supported.
Iterating over nodes and elements are both supported.

##Selection

The API can be used to select nodes and subtrees. The construction of selectors needs to be made more concise. Selectors
return iterators over either nodes or trees.
