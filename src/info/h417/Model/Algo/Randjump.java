package info.h417.Model.Algo;


import info.h417.Model.Stream.BaseInputStream;
import info.h417.Model.Stream.Generator;

import java.io.IOException;

public class Randjump extends BaseAlgo{


    /**
     * A generic Constructor that takes a generator as parameter
     *
     * @param generator
     */
    public Randjump(Generator generator) {
        super(generator);
    }

    public void begin(String filename,int j) throws IOException {
        int sum = 0;
        BaseInputStream inputStream = generator.getInputStream(filename);
        inputStream.open();
        long size = inputStream.sizeFile();
        long p;
        for(int i = 0; i < j; i++){
            p = (long)(size * Math.random());
            inputStream.seek(p);
            sum += inputStream.readln().length();
        }
        inputStream.close();

        System.out.println("Somme : " + sum);
    }
}

