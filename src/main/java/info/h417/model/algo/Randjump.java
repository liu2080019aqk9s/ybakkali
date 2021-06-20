package info.h417.model.algo;


import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.Generator;

import java.io.IOException;
import java.util.Random;

public class Randjump extends BaseAlgo{


    /**
     * A generic Constructor that takes a generator as parameter
     *
     * @param generator
     */
    public Randjump(Generator generator) {
        super(generator);
    }

    public int begin(String filename, int j) throws IOException {
        int sum = 0;
        BaseInputStream inputStream = generator.getInputStream(filename);
        inputStream.open();
        long size = inputStream.sizeFile();
        long p;
        Random generator = new Random(15000);
        for(int i = 0; i < j; i++){

            p = (long)(size * generator.nextDouble());
            inputStream.seek(p);
            sum += inputStream.readln().length();
        }
        inputStream.close();

        return sum;
    }
}

