
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
