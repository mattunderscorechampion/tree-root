
##Custom tree implementations

It is possible for you to implement your own tree implementations the conform to this API. The easiest way to get
information is to refer to the existing implementations. You should feel free to reach out to me for help with anything
that is not clear to you.

###SPI Components

The SPI components are how the inner workings of a tree implementation are exposed to the API. These will need to be
implemented for any operations you wish to support. The API discovers them using Java ServiceLoaders. The JAR you package
your tree implementation in will need to have a ```META-INF/services``` directory containing a provider-configuration file.
The provider configuration file should be named after the fully qualified SPI component name. This allows the service
loader to find the configuration file. The provider-configuration file should contain the fully qualified name of the
implementation of you SPI component. The SPI component should have a public, zero argument constructor so that an
instance of it can be created. For more information on services you should refer to the Java documentation.
