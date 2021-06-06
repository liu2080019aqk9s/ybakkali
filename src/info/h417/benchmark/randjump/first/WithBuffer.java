package info.h417.benchmark.randjump.first;

import info.h417.model.algo.Randjump;
import info.h417.model.stream.Generator;
import info.h417.model.stream.mmap.MmapGenerator;
import info.h417.model.stream.oneBuffer.OneBufferGenerator;
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
public class WithBuffer {

    Generator generator;

    @Param({"OneBuffer", "MMap"})
    String generatorType;

    @Param({"comp_cast_type.csv", "kind_type.csv", "company_type.csv"})
    String file;
    String path = "database/imdb/";

    @Param({"16", "1024", "1048576"})
    int bufferSize;

    int j = 42;

    @Setup
    public void setup() {
        this.generator = (this.generatorType.equals("OneBuffer"))? new OneBufferGenerator(bufferSize) : new MmapGenerator(bufferSize);
    }

    @Benchmark
    public void measureRandJumpDifferentFiles(Blackhole blackhole) throws IOException {
        Randjump randjump = new Randjump(this.generator);
        blackhole.consume(randjump.begin(path + file, j));
    }

}
