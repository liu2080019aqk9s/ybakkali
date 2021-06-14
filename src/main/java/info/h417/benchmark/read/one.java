package info.h417.benchmark.read;

import info.h417.model.algo.Length;
import info.h417.model.algo.Randjump;
import info.h417.model.stream.Generator;
import info.h417.model.stream.one.OneGenerator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class one {

    Generator generator;

    @Param({"comp_cast_type.csv", "info_type.csv", "movie_link.csv", "keyword.csv", "aka_title.csv"})
    String file;

    final String path = "database/imdb/";

    final int j = 42;


    @Setup
    public void setup() {
        this.generator = new OneGenerator();
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