package com.epam.jsdmx.infomodel.sdmx30;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class VersionReferenceBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testCreateFromString(Versions versions, Blackhole bh) {
        bh.consume(VersionReference.createFromString(versions.fixedStable));
        bh.consume(VersionReference.createFromString(versions.fixedDraft));
        bh.consume(VersionReference.createFromString(versions.wildcardPatch));
        bh.consume(VersionReference.createFromString(versions.wildcardMinor));
        bh.consume(VersionReference.createFromString(versions.wildcardMajor));
    }

    @State(Scope.Thread)
    public static class Versions {
        public String fixedStable = "1.0.0";
        public String fixedDraft = "1.0.0-draft";
        public String wildcardPatch = "1.0.0+";
        public String wildcardMinor = "1.0+.0";
        public String wildcardMajor = "1+.0.0";
    }
}
