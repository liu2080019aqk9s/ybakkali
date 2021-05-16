package info.h417.Model.Algo;


import info.h417.Model.Stream.BaseInputStream;
import info.h417.Model.Stream.BaseOutputStream;
import info.h417.Model.Stream.Generator;

import java.io.IOException;
import java.util.ArrayList;

public class RRMerge extends BaseAlgo{

    /**
     * A generic Constructor that takes a generator as parameter
     *
     * @param generator
     * @param writeGenerator
     */
    public RRMerge(Generator generator,Generator writeGenerator) {
        super(generator,writeGenerator);
        this.outputString = "RRMergeOutput.csv";
    }


    public void begin(String... fileNames) throws IOException {
        ArrayList<BaseInputStream> inputStreams = new ArrayList<>();
        for(String fileName: fileNames){
            BaseInputStream inputStream = generator.getInputStream(fileName);
            inputStreams.add(inputStream);
            inputStream.open();
        }

        BaseOutputStream outputStream =  writeGenerator.getOutputStream(outputString);
        outputStream.create();

        // Merge les fichiers avec ROUND ROBIN



        outputStream.close();
        for (BaseInputStream inputStream : inputStreams){
            inputStream.close();
        }


    }

}
