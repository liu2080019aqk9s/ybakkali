package info.h417.benchmark.rrmerge;

import info.h417.model.algo.RRMerge;
import info.h417.model.stream.Generator;
import info.h417.model.stream.buffered.BufferedGenerator;
import info.h417.model.stream.mmap.MmapGenerator;
import info.h417.model.stream.one.OneGenerator;
import info.h417.model.stream.oneBuffer.OneBufferGenerator;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.NANOSECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class RRmerge {

    final String path = "database/imdb/";
    final String[] files = {"comp_cast_type.csv", "kind_type.csv", "company_type.csv", "role_type.csv", "link_type.csv",
            "info_type.csv", "movie_link.csv", "complete_cast.csv", "keyword.csv", "company_name.csv", "movie_info_idx.csv",
            "aka_title.csv", "aka_name.csv", "movie_companies.csv", "movie_keyword.csv"};

    @Param({"2","3","4","5","6","7","8","9","10","11","12","13","14","15"})
    int filesNumber;

    List<String> currentFiles = new ArrayList<>();

    @Param({"One", "Buffered", "OneBuffer", "Mmap"})
    String xPair;

    @Param({"One", "Buffered", "OneBuffer", "Mmap"})
    String yPair;

    //@Param({"16", "256", "4096", "65536", "262144", "524288", "768000", "1048576", "4194304", "16777216"})
    int bufferSize = 524288;

    Generator readerGenerator;
    Generator writerGenerator;

    @Setup
    public void setup() {

        switch (xPair) {
            case "One":
                readerGenerator = new OneGenerator();
                break;
            case "Buffered":
                readerGenerator = new BufferedGenerator();
                break;
            case "OneBuffer":
                readerGenerator = new OneBufferGenerator(bufferSize);
                break;
            case "Mmap":
                readerGenerator = new MmapGenerator(bufferSize);
                break;
        }

        switch (yPair) {
            case "One":
                writerGenerator = new OneGenerator();
                break;
            case "Buffered":
                writerGenerator = new BufferedGenerator();
                break;
            case "OneBuffer":
                writerGenerator = new OneBufferGenerator(bufferSize);
                break;
            case "Mmap":
                writerGenerator = new MmapGenerator(bufferSize);
                break;
        }

        for (int i = 0; i < files.length; i++) {
            files[i] = path + files[i];
        }

        currentFiles.addAll(Arrays.asList(files).subList(0, filesNumber));

    }

    @Benchmark
    public void rrmerge() throws IOException {
        RRMerge rrMerge = new RRMerge(readerGenerator, writerGenerator);
        rrMerge.begin(currentFiles.toArray(new String[0]));
    }

}
