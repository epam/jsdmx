package com.epam.jsdmx.infomodel.sdmx30;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class VersionReferenceHashCodeBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Fork(value = 1, warmups = 1)
    public void testHashcode(Subject subject, Blackhole bh) {
        bh.consume(subject.hashCode());
    }

    @State(Scope.Thread)
    public static class Subject {
        VersionReference v = VersionReference.createFromVersionAndWildcardScope(
            Version.createFromComponents((short) 1, (short) 0, (short) 0, null),
            WildcardScope.MINOR
        );
    }
}
