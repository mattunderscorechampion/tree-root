
##Simple Collections

I have created a SimpleCollection extending Iterable. I had intended to rely on the Collections API for this and for a
time considered returning an Iterable to represent the children. All the interfaces of the Collections API expose
mutator methods, which is undesirable. I generally want to iterate over the children without nulls however nulls are
occasionally needed to represent the empty branches.

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
