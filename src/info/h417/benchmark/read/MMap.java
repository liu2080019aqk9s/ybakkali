package info.h417.benchmark.read;

import info.h417.model.algo.Length;
import info.h417.model.algo.Randjump;
import info.h417.model.stream.Generator;
import info.h417.model.stream.mmap.MmapGenerator;
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
public class MMap {
    Generator generator;

    // @Param({"movie_link.csv", "complete_cast.csv", "keyword.csv"})
    @Param({"comp_cast_type.csv", "kind_type.csv", "company_type.csv"})
    String file;

    final String path = "database/imdb/";

    @Param({"16", "1024", "1048576"})
    int bufferSize;

    final int j = 42;

    @Setup
    public void setup() {
        this.generator = new MmapGenerator(bufferSize);
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