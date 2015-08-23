#Changelog


###0.0.0 - Failed release
Wednesday August 27 21:00:51 2014

###0.0.1 - Failed release
Wednesday August 27 21:15:37 2014

###0.0.2 - Initial release of API
Wednesday August 27 22:37:17 2014

###0.0.3 - Failed release
Thursday January 22 20:59:57 2015

###0.0.4 - Second release of API
Thursday January 22 21:27:56 2015

* Added leaf method to nodes
* Renamed all interfaces to conform to convention
* Allow mutable trees change the root node 
* Added balanced tree API
* Made the handling of unknown SPI implementations explicit
* Added iterators
* Changed the generic parameters of tree builders, NodeMatcher, NodeSelector, TreeSelector, tree iterators
* Added a binary tree API
* Added sorted  tree API
* Changed the getChildren method of nodes to return a SimpleCollection
* Moved the methods to get the builders out of Trees into a factory
* Changed the class names of the factories
* Changed the method names of the Trees interface to get the factories
* Added element walkers and iterators
* Added path copy based tree
* Moved collections into separate package
* Moved tree implementations into separate packages
* Provided some support for mutating trees through iterators
* Added new selectors
* Added copy based node to tree converter to SPI
* Added abstract node implementations
* Added copy path tree SPI components
* Added bounded key SPI components
* Added type key to tree builders to help match generics
* Added separate keys for default specific implementations

###0.0.5 - Failed release
Wednesday June 10 22:09:16 2015

Deploy goal skipped

###0.0.6 - Failed release
Wednesday June 10 22:43:55 2015

Deploy goal skipped

###0.0.7 - Bad release
Wednesday June 10 23:22:02 2015

Root POM not deployed, prevents dependency resolution

###0.0.8 - Third release of API
Wednesday June 10 23:22:02 2015

* Renamed project to Tree Root
* Separated tree implementations into different modules
* Separated tests that rely on multiple modules into separate module
* Separated tree implementations into different packages
* Separated common elements into different modules
* Made examples executable
* Renamed examples module
* Allow the creation of sorting and sorted tree builders without specifying a comparator if the element type is
comparable
* Added structural nodes that allow specific access and placement of nodes
* Removed the simple collection returned from Node
* Added size and child iterator methods to Node
* Added enhanced tree internal iterator for pre-order traversal
* Targeted Java 8
* Changed group ID

###0.0.9 - Fourth release of API
Sunday July 12 21:12:34 2015

* Introduced array iterator
* Rely on default method for Iterator.remove when possible
* Have EmptyIterator throw UnsupportedOperationException instead of IllegalStateException on remove
* Address type safety of collections
* Removed AbstractNode and moved implementation into default methods on the Node interface
* Substantial changes to the generic types of nodes, introduced distinction between open and closed nodes
* Fixed unchecked cast warnings
* Added initial support for Java 8 Streams
* Replaced NodeMatcher with Java 8 Predicate
* Expose the comparator of a sorting tree
* Added findbugs to build and fixed some of the reported issues
* Altered signature of NodeToTreeConverter and renamed to NodeToRelatedTreeConverter to better restrict the range of
source nodes
* Added abstract implementation of NodeToTreeConverter that copies the tree using a top down builder
* Removed empty iterator implementation

###0.0.10 - TBD

* Removed unused TreeAware API class
* Renamed DefaultElementWalker to DefaultWalker and changed DefaultNodeWalker to extend it
* Bug fixes
* Added convenience method for obtaining an implementation of the API
* Refactored walker drivers to avoid recursion
* Added JoinIterator that combines multiple iterators
* Renamed traversal driver classes to make clear that they drive the iteration
* Refactored TreeConverter for MutableTreeImpl to reduce collection copying
* Allow presizing of ArrayListSimpleCollection
* Rely on key mapping for immutable nodes instead of multiple SPI components
* Moved binary search trees into separate module
* Separated LinkedTree SPI components into separate files
* Added package-info files to describe packages
* Moved utility iterators to separate module
* Moved simple collections to separate module
* Renamed simple collections package
* Moved SimpleCollection out of the API module 
* Renamed iterators package
* Changed the return type of the TreeNodeImpl child iterator as it is final
* Added SPI component to allow tree implementations to be discovered 
* Moved mutable binary trees into separate module
* Added rotator for the MutableBinaryTree implementation
* Added type keys for the tree interfaces
* Added tree transformation API and provided support for rotation
