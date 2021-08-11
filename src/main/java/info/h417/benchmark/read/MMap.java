package info.h417.benchmark.read;

import info.h417.model.algo.Length;
import info.h417.model.algo.Randjump;
import info.h417.model.stream.Generator;
import info.h417.model.stream.mmap.MMapGenerator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 1, time = 1, timeUnit = TimeUnit.NANOSECONDS)
@Measurement(iterations = 3, time = 1, timeUnit = TimeUnit.NANOSECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class MMap {

    Generator generator;

    @Param({"comp_cast_type.csv", "kind_type.csv", "company_type.csv", "role_type.csv", "link_type.csv", "info_type.csv",
        "movie_link.csv", "complete_cast.csv", "keyword.csv", "company_name.csv", "movie_info_idx.csv",
        "aka_title.csv", "aka_name.csv", "movie_companies.csv", "movie_keyword.csv", "title.csv", "char_name.csv",
        "name.csv", "person_info.csv", "movie_info.csv", "cast_info.csv"})
    String file;

    final String path = "database/imdb/";

    @Param({"16", "256", "4096", "65536", "262144", "524288", "768000", "1048576", "4194304", "16777216"})
    int bufferSize;

    //@Param({"10", "1000", "10000", "100000"})
    int j = 5;

    @Setup
    public void setup() {
        this.generator = new MMapGenerator(bufferSize);
    }

    @Benchmark
    public void length(Blackhole blackhole) throws IOException {
        Length length = new Length(this.generator);
        blackhole.consume(length.begin(path + file));
    }

    @Benchmark
    public void randJump(Blackhole blackhole) throws IOException {
        Randjump randjump = new Randjump(this.generator);
        blackhole.consume(randjump.begin(path + file, j));
    }
}