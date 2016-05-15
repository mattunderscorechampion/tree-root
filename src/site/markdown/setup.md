
##Setup

You will usually need to add at least two dependencies to use the API. Each tree implementation is in a separate module,
you will need to add the module for each tree implementation you wish to use. You will also need to add
```trees-provider``` as a dependency, this dependency provides an implementation for the Trees class, builders and other
parts of the API that are not specific to a single tree.

Here is an example of how to add the API and immutable tree implementation to your project:

```xml
<dependency>
    <groupId>com.mattunderscore.tree.root</groupId>
    <artifactId>trees-provider</artifactId>
    <version>0.10.0-SNAPSHOT</version>
</dependency>

<dependency>
    <groupId>com.mattunderscore.tree.root</groupId>
    <artifactId>trees-immutable</artifactId>
    <version>0.10.0-SNAPSHOT</version>
</dependency>
```
