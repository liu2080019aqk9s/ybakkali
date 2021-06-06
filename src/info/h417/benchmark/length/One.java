package info.h417.benchmark.length;

import info.h417.model.algo.Length;
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
public class One {

    Generator generator;

    // @Param({"movie_link.csv", "complete_cast.csv", "keyword.csv"})
    @Param({"comp_cast_type.csv", "kind_type.csv", "company_type.csv"})
    String file;

    String path = "database/imdb/";

    @Setup
    public void setup() throws IOException {
        this.generator = new OneGenerator();
    }

    @Benchmark
    public void measureLengthAlgorithm(Blackhole blackhole) throws IOException {
        Length length = new Length(this.generator);
        blackhole.consume(length.begin(path + file));
    }
}