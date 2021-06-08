package info.h417.benchmark.rrmerge.first;

import info.h417.model.stream.Generator;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class WithoutBuffer {

    final String path = "database/imdb/";
    final String[] files = {path + "comp_cast_type.csv", path + "kind_type.csv", path + "company_type.csv"};

    @Param({"1","2","3"})
    int filesNumber;

    List<String> currentFiles;

    Generator readerGenerator;
    Generator writeGenerator;

    @Setup
    public void setup() {
        currentFiles.addAll(Arrays.asList(files).subList(0, filesNumber));

    }

    @Benchmark
    public void measureRRMergeDifferentFilesNumber() {
        // RRMerge rrMerge = new RRMerge();
    }

}
