package com.mattunderscore.trees.examples;

/**
 * Entry point for examples module.
 * @author Matt Champion on 29/01/15
 */
public final class ExamplesEntryPoint {
    private ExamplesEntryPoint() {
    }

    /**
     * Entry point.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Readme Examples:");
        final ReadmeExamples readmeExamples = new ReadmeExamples();
        System.out.println("Immutable tree example:");
        readmeExamples.immutableTree();

        System.out.println("Binary search tree example:");
        readmeExamples.binarySearchTree();
    }
}
