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

###0.0.5 - TBD

* Renamed project to Tree Root
* Separated tree implementations into different modules
* Separated tests that rely on multiple modules into separate module
* Separated tree implementations into different packages
* Made examples executable
* Renamed examples module
* Allow the creation of sorting and sorted tree builders without specifying a comparator if the element type is
comparable
* Added structural nodes that allow specific access and placement of nodes
* Removed the simple collection returned from Node
* Added size and child iterator methods to Node
* Added enhanced tree internal iterator for pre-order traversal
