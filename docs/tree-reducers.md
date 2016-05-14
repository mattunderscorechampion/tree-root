
##Tree Reducers

Many operations on trees can be expressed as recursive algorithms on trees. It is however desirable to separate the
traversal of the tree from the function applied to a node. This can be done by defining two functions.

A function `r`, that is applied to a node, `n`, and the results of applying `r` to the children of `n`.
The function `r` can be used to calculate a property of `n` based on the values of the property for the children of `n`.

A function `a` that is applied to a node the function `r`.
The function `a` provides the post-order traversal of the tree and applies `r`.

Consider a simple way to calculate the height of a tree. It uses the recursive application of the function to traverse
the tree.

```
height(node) = max(height(node.left), height(node.right)) + 1
```

From this a function can be extracted that calculates the height of a node from the height of its children.

```
heightReducer(node, leftHeight, rightHeight) = max(leftHeight, rightHeight) + 1
```

Another function can be used to apply the `heightReducer` to every descendant of a node.

```
reducerApplier(node, reducer) = reducer(node, reducerApplier(node.left, reducer), reducerApplier(node.right, reducer))
```

The height of a tree can then be calculated by

```
height = reducerApplier(node, heightReducer)
```

Reducing functions can be used as a generic way to evaluate a property of a node based on the properties of its children.
