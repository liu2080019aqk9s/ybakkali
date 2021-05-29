package info.h417.model.algo;

import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.BaseOutputStream;
import info.h417.model.stream.Generator;

import java.io.IOException;

public class ExtSort extends BaseAlgo{

    /**
     * A generic Constructor that takes a generator as parameter
     *
     * @param generator
     */
    public ExtSort(Generator generator,Generator writeGenerator) {
        super(generator,writeGenerator);
        this.outputString = "ExtSortOutput.csv";
    }

    public void begin(String fileName, int k, int M,int d) throws IOException {
        BaseInputStream inputStream = generator.getInputStream(fileName);
        BaseOutputStream outputStream = writeGenerator.getOutputStream(outputString);
        inputStream.open();
        outputStream.create();


        //



        inputStream.close();
        outputStream.close();
    }
}
