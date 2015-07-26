
##Examples

####Immutable tree Example

```java
final Trees trees = Trees.get();

final BottomUpTreeBuilder<String, MutableNode<String>> builder = trees
    .treeBuilders()
    .bottomUpBuilder();
final Tree<String, MutableNode<String>> tree = builder.create("a",
    builder.create("b"),
    builder.create("c"))
    .build(new TypeKey<Tree<String, MutableNode<String>>>(){});

trees.treeWalkers().walkElementsInOrder(tree, new DefaultWalker<String>() {
    @Override
    public boolean onNext(String node) {
        System.out.println("Element: " + node);
        return true;
    }
});
```

This example builds an immutable tree and walks over the elements in order, left, root, right, generating the output:

```
Element: b
Element: a
Element: c
```

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
