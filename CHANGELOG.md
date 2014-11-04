#Changelog


##0.0.0 - Failed release
Wednesday August 27 21:00:51 2014

##0.0.1 - Failed release
Wednesday August 27 21:15:37 2014

##0.0.2 - Initial release of API
Wednesday August 27 22:37:17 2014

##0.1.0 - Unreleased (TBD)
* Added leaf method to nodes
* Renamed all interfaces to conform to convention
* Allow mutable trees change the root node 
* Added balanced tree API
* Made the handling of unknown SPI implementations explicit
* Added iterators
* Changed the generic parameters of tree builders, NodeMatcher, NodeSelector, TreeSelector
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
* Added alternative mutable tree API
* Added copy path tree SPI components
* Added IDs
* Added operations
* Added second copy path based tree implementation that uses a separate data structure for back references