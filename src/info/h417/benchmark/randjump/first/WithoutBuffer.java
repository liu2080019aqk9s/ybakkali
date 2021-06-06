package info.h417.benchmark.randjump.first;

import info.h417.model.algo.Randjump;
import info.h417.model.stream.Generator;
import info.h417.model.stream.buffered.BufferedGenerator;
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
public class WithoutBuffer {

    Generator generator;

    @Param({"One", "Buffered"})
    String generatorType;

    @Param({"comp_cast_type.csv", "kind_type.csv", "company_type.csv"})
    String file;
    String path = "database/imdb/";

    int j = 42;

    @Setup
    public void setup() {
        this.generator = (this.generatorType.equals("One"))? new OneGenerator() : new BufferedGenerator();
    }

    @Benchmark
    public void measureRandJumpDifferentFiles(Blackhole blackhole) throws IOException {
        Randjump randjump = new Randjump(this.generator);
        blackhole.consume(randjump.begin(path + file, j));
    }

}
