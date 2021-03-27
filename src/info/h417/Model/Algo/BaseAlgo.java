package info.h417.Model.Algo;


import info.h417.Model.Stream.Generator;

public class BaseAlgo {
    protected Generator generator;

    /**
     * A generic Constructor that takes a generator as parameter
     *
     * @param generator
     */
    public BaseAlgo(Generator generator) {
        this.generator = generator;
    }
}
