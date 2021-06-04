package info.h417.benchmark;

import info.h417.model.stream.Generator;
import info.h417.model.stream.mmap.MmapGenerator;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;


import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class LengthBenchmark {

    @State(Scope.Benchmark)
    public static class MyState {
        Generator generator;
        @Setup(Level.Invocation)
        public void setup() {
            this.generator = new MmapGenerator(1);
        }
    }

    @Param({"first", "second"})
    String ahaha;

    @Fork(value = 1, warmups = 1)
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void measureLengthAlgorithm(Blackhole blackhole) {
        blackhole.consume("AHA HA");
    }

}
