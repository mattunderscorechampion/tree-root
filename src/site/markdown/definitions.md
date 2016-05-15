
##Definitions

###Basic definitions

* Element - a value associated with the node
* Node - node of the tree, may contain a value, may have child nodes, may not be the child of more than one node
* Leaf - a node that has no children
* Root - a node that has no parent node
* Tree - a data structure that may contain nodes and has at most one root node

###Types of tree

* Structural tree - a tree where the child nodes are ordered and may have empty spaces
* Sorted tree - tree that has elements in certain positions based on comparisons between the elements, a mutable sorted
tree can be modified so that it is no longer sorted
* Sorting tree - a mutable sorted tree that adds new elements to the correct positions based on comparisons between the
elements
* Balanced tree - tree that has all its leaves at the same distance from the root
* Balancing tree - a mutable tree that has adds new elements to the correct positions to keep it balanced

###Mutablity

* Immutable - cannot be modified
* Mutable - can be modified
* Content mutable - the element of a node can be replaced with a different element
* Structurally mutable - the structure of the tree can be changed through the addition or removal of nodes
* Element mutable - the element can be modified, when talking about the mutability of trees the mutability of the
element is not a consideration
