
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
