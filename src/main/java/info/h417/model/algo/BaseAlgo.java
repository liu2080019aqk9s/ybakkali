package info.h417.model.algo;


import info.h417.model.stream.Generator;

public class BaseAlgo {
    protected final Generator generator;
    protected Generator writeGenerator;
    protected String outputFilename;

    /**
     * A generic Constructor that takes a generator as parameter
     *
     * @param generator The generator
     */
    public BaseAlgo(Generator generator) {
        this.generator = generator;
    }


    /**
     * A generic Constructor that takes a generator as parameter
     *
     * @param generator The read generator
     * @param writeGenerator The write generator
     */
    public BaseAlgo(Generator generator,Generator writeGenerator) {
        this.generator = generator;
        this.writeGenerator = writeGenerator;
    }
}
