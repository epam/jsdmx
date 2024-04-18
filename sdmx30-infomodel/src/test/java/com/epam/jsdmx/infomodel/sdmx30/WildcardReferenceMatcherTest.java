package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class WildcardReferenceMatcherTest {

    @ParameterizedTest
    @MethodSource
    void testMajorWildcard(VersionReference candidate, boolean expected) {
        final VersionReference ref = VersionReference.createFromVersionAndWildcardScope(
            Version.createFromComponents(1, 0, 0, null),
            WildcardScope.MAJOR
        );

        final var subject = new WildcardReferenceMatcher(ref);

        assertThat(subject.matches(candidate)).isEqualTo(expected);

    }

    static Stream<Arguments> testMajorWildcard() {
        return Stream.of(
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(1, 0, 0, null)), true),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(1, 0, 1, null)), true),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(1, 1, 1, null)), true),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(1, 1, 0, null)), true),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(2, 0, 0, null)), true),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(2, 0, 1, null)), true),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(2, 1, 1, null)), true)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testMinorWildcard(VersionReference candidate, boolean expected) {
        final VersionReference ref = VersionReference.createFromVersionAndWildcardScope(
            Version.createFromComponents(1, 0, 0, null),
            WildcardScope.MINOR
        );

        final var subject = new WildcardReferenceMatcher(ref);

        assertThat(subject.matches(candidate)).isEqualTo(expected);

    }

    static Stream<Arguments> testMinorWildcard() {
        return Stream.of(
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(1, 0, 0, null)), true),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(1, 0, 1, null)), true),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(1, 1, 1, null)), true),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(1, 1, 0, null)), true),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(2, 0, 0, null)), false),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(2, 0, 1, null)), false),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(2, 1, 1, null)), false)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testPatchWildcard(VersionReference candidate, boolean expected) {
        final VersionReference ref = VersionReference.createFromVersionAndWildcardScope(
            Version.createFromComponents(1, 0, 0, null),
            WildcardScope.PATCH
        );

        final var subject = new WildcardReferenceMatcher(ref);

        assertThat(subject.matches(candidate)).isEqualTo(expected);

    }

    static Stream<Arguments> testPatchWildcard() {
        return Stream.of(
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(1, 0, 0, null)), true),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(1, 0, 1, null)), true),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(1, 1, 1, null)), false),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(1, 1, 0, null)), false),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(2, 0, 0, null)), false),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(2, 0, 1, null)), false),
            Arguments.of(VersionReference.createFromVersion(Version.createFromComponents(2, 1, 1, null)), false)
        );
    }

}