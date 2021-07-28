package info.h417.model.algo;


import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.Generator;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;

public class Randjump extends BaseAlgo {


    /**
     * A generic Constructor that takes a generator as parameter
     *
     * @param generator The generator
     */
    public Randjump(Generator generator) {
        super(generator);
    }

    /**
     * Run the Randjump algorithm.
     *
     * @param filename The filename
     * @param j The number of iterations to do
     * @return The computed length
     * @throws IOException If some I/O error occurs
     */
    public int begin(String filename, int j) throws IOException {
        int sum = 0;
        BaseInputStream inputStream = generator.getInputStream(filename);
        inputStream.open();
        long size = new FileInputStream(filename).getChannel().size();
        long p;
        Random generator = new Random(15000);
        for(int i = 0; i < j; i++){

            p = (long)(size * generator.nextDouble());
            inputStream.seek(p);
            String line = inputStream.readln();
            if (line != null) {
                sum += line.length();
            }
        }
        inputStream.close();

        return sum;
    }
}

