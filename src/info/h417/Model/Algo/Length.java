package info.h417.Model.Algo;

import info.h417.Model.Stream.BaseInputStream;
import info.h417.Model.Stream.Generator;

import java.io.IOException;

public class Length extends BaseAlgo{


    /**
     * A generic Constructor that takes a generator as parameter
     *
     * @param generator
     */
    public Length(Generator generator) {
        super(generator);
    }


    public void begin(String filename) throws IOException {
        int sum = 0;

        BaseInputStream inputStream = generator.getInputStream(filename);
        inputStream.open();
        while (! inputStream.end_of_stream()){
            sum += inputStream.readln().length();
        }
        inputStream.close();

        System.out.println("Somme : " + sum);
    }
}
