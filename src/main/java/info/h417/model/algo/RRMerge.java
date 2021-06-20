package info.h417.model.algo;


import info.h417.model.stream.BaseInputStream;
import info.h417.model.stream.BaseOutputStream;
import info.h417.model.stream.Generator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    /**
     * @param fileNames
     * @throws IOException
     */
    public void begin(String... fileNames) throws IOException {
        ArrayList<BaseInputStream> inputStreams = new ArrayList<>();
        for(String fileName: fileNames){
            BaseInputStream inputStream = generator.getInputStream(fileName);
            inputStreams.add(inputStream);
            inputStream.open();
        }

        BaseOutputStream outputStream =  writeGenerator.getOutputStream(outputString);
        outputStream.create();

        List<BaseInputStream> activeInputStreams = new ArrayList<>(inputStreams);

        while (!activeInputStreams.isEmpty()) {
            for (int i = 0; i < activeInputStreams.size(); i++) {
                BaseInputStream inputStream = activeInputStreams.get(i);
                if (inputStream.end_of_stream()) {
                    activeInputStreams.remove(i);
                    i--;
                } else {
                    outputStream.writeln(inputStream.readln());
                }
            }
        }

        outputStream.close();
        for (BaseInputStream inputStream : inputStreams){
            inputStream.close();
        }
    }
}
