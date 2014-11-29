package com.mattunderscore.trees.spi;

import com.mattunderscore.trees.tree.Tree;

/**
 * SPI component that can be looked up from the class of the node. Uses raw type to work with class literals.
 * @author Matt Champion
 */
public interface TreeKeyedSPIComponent extends SPIComponent {
    /**
     * @return The key to use for the component lookup
     */
    Class<? extends Tree> forClass();
}
