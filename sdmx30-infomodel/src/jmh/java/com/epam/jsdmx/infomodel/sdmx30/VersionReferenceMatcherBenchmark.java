package com.epam.jsdmx.infomodel.sdmx30;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class VersionReferenceMatcherBenchmark {

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void testMatches(Versions versions, Blackhole bh) {
        bh.consume(versions.subject.matches(versions.input));
    }

    @State(Scope.Thread)
    public static class Versions {
        public WildcardReferenceMatcher subject = new WildcardReferenceMatcher(
            VersionReference.createFromVersionAndWildcardScope(
                Version.createFromComponents((short) 1, (short) 0, (short) 0, null),
                WildcardScope.MINOR
            )
        );
        public VersionReference input = VersionReference.createFromVersion(
            Version.createFromComponents((short) 1, (short) 1, (short) 0, null)
        );
    }
}
