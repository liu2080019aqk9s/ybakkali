package info.h417.benchmark.extsort;

import info.h417.model.algo.ExtSort;
import info.h417.model.stream.Generator;
import info.h417.model.stream.buffered.BufferedGenerator;
import info.h417.model.stream.mmap.MMapGenerator;
import info.h417.model.stream.one.OneGenerator;
import info.h417.model.stream.oneBuffer.OneBufferGenerator;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.NANOSECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class Extsort {

    final String path = "database/imdb/";

    @Param({"comp_cast_type.csv", "kind_type.csv", "company_type.csv", "role_type.csv", "link_type.csv", "info_type.csv",
            "movie_link.csv", "complete_cast.csv", "keyword.csv", "company_name.csv", "movie_info_idx.csv",
            "aka_title.csv", "aka_name.csv", "movie_companies.csv", "movie_keyword.csv"})
    String file;

    @Param({"2","3","4"})
    int k;

    //@Param({"16", "256", "4096", "65536", "262144", "524288", "768000", "1048576", "4194304", "16777216"})
    int M = 524288;

    @Param({"10","20","30"})
    int d;

    @Param({"One", "Buffered", "OneBuffer", "Mmap"})
    String xPair;

    @Param({"One", "Buffered", "OneBuffer", "Mmap"})
    String yPair;



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
                readerGenerator = new OneBufferGenerator(M);
                break;
            case "Mmap":
                readerGenerator = new MMapGenerator(M);
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
                writerGenerator = new OneBufferGenerator(M);
                break;
            case "Mmap":
                writerGenerator = new MMapGenerator(M);
                break;
        }
    }

    @Benchmark
    public void extsort() throws IOException {
        ExtSort extSort = new ExtSort(readerGenerator, writerGenerator);
        extSort.begin(path + file, k, M, d);
    }

}
