package info.h417.Model.Algo;

import info.h417.Model.Stream.BaseInputStream;
import info.h417.Model.Stream.BaseOutputStream;
import info.h417.Model.Stream.Generator;

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
