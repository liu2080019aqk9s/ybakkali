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

    @Param({"1","2","3","4","5","6","7","8","9","10","11","12"})
    int k;

    @Param({"524288", "1048576", "4194304"})
    int M;

    @Param({"5","10", "15", "20", "25", "30","35","40"})
    int d;

    @Param({"One", "Buffered", "OneBuffer", "Mmap"})
    String xPair;

    @Param({"One", "Buffered", "OneBuffer", "Mmap"})
    String yPair;

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
                readerGenerator = new MMapGenerator(bufferSize);
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
                writerGenerator = new MMapGenerator(bufferSize);
                break;
        }
    }

    @Benchmark
    public void extsort() throws IOException {
        ExtSort extSort = new ExtSort(readerGenerator, writerGenerator);
        extSort.begin(path + file, k, M, d);
    }

}
