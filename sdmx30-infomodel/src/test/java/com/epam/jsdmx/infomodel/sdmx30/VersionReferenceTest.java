package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class VersionReferenceTest {

    @Test
    void createFixedVersionReference() {
        VersionReference subject = VersionReference.createFromString("1.0.0");

        assertThat(subject.toString()).isEqualTo("1.0.0");
        assertThat(subject.getScope()).isEqualTo(WildcardScope.NONE);
        assertThat(subject.isWildcarded()).isFalse();
    }

    @Test
    void createFixedLegacyVersionReference() {
        VersionReference subject = VersionReference.createFromString("1.0");

        assertThat(subject.toString()).isEqualTo("1.0");
        assertThat(subject.getScope()).isEqualTo(WildcardScope.NONE);
        assertThat(subject.isWildcarded()).isFalse();
    }

    @Test
    void createFixedVersionWithExtensionReference() {
        VersionReference subject = VersionReference.createFromString("1.0.0-draft");

        assertThat(subject.toString()).isEqualTo("1.0.0-draft");
        assertThat(subject.getScope()).isEqualTo(WildcardScope.NONE);
        assertThat(subject.isWildcarded()).isFalse();
    }

    @Test
    void createLatestVersionReferenceMajor() {
        VersionReference subject = VersionReference.createFromString("1+.0.0");

        assertThat(subject.toString()).isEqualTo("1+.0.0");
        assertThat(subject.getScope()).isEqualTo(WildcardScope.MAJOR);
        assertThat(subject.isWildcarded()).isTrue();
    }

    @Test
    void createLatestVersionReferenceMinor() {
        VersionReference subject = VersionReference.createFromString("1.2+.0");

        assertThat(subject.toString()).isEqualTo("1.2+.0");
        assertThat(subject.getScope()).isEqualTo(WildcardScope.MINOR);
        assertThat(subject.isWildcarded()).isTrue();
    }

    @Test
    void createLatestVersionReferencePatch() {
        VersionReference subject = VersionReference.createFromString("1.2.1+");

        assertThat(subject.toString()).isEqualTo("1.2.1+");
        assertThat(subject.getScope()).isEqualTo(WildcardScope.PATCH);
        assertThat(subject.isWildcarded()).isTrue();
    }

    @ParameterizedTest
    @MethodSource
    void comparator_whenBothSemvers(String v1, String v2, int expectedResult) {
        final Comparator<VersionReference> subject = VersionReference.getComparator();

        assertThat(subject.compare(VersionReference.createFromString(v1), VersionReference.createFromString(v2))).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> comparator_whenBothSemvers() {
        return Stream.of(
            Arguments.of("1.0.0", "1.1.0", -1),
            Arguments.of("1.0.0", "1.1.1", -1),
            Arguments.of("1.0.1", "1.1.1", -1),
            Arguments.of("1.1.1", "1.1.1", 0),
            Arguments.of("1.1.2", "1.1.1", 1),
            Arguments.of("1.2.1", "1.1.1", 1),
            Arguments.of("2.1.1", "1.1.1", 1),
            Arguments.of("2.0.0", "1.42.69", 1),
            Arguments.of("2.0.0", "1.1.1", 1),

            Arguments.of("1.0.0-draft", "1.1.0-draft", -1),
            Arguments.of("1.0.0-draft", "1.1.1-draft", -1),
            Arguments.of("1.0.1-draft", "1.1.1-draft", -1),
            Arguments.of("1.1.1-draft", "1.1.1-draft", 0),
            Arguments.of("1.1.2-draft", "1.1.1-draft", 1),
            Arguments.of("1.2.1-draft", "1.1.1-draft", 1),
            Arguments.of("2.1.1-draft", "1.1.1-draft", 1),
            Arguments.of("2.0.0-draft", "1.42.69-draft", 1),
            Arguments.of("2.0.0-draft", "1.1.1-draft", 1),

            Arguments.of("1.0.0-draft", "1.1.0", -1),
            Arguments.of("1.0.0", "1.1.0-draft", -1),
            Arguments.of("1.0.0-draft", "1.1.1", -1),
            Arguments.of("1.0.0", "1.1.1-draft", -1),
            Arguments.of("1.0.1-draft", "1.1.1", -1),
            Arguments.of("1.0.1", "1.1.1-draft", -1),
            Arguments.of("1.1.2-draft", "1.1.1", 1),
            Arguments.of("1.1.2", "1.1.1-draft", 1),
            Arguments.of("1.2.1-draft", "1.1.1", 1),
            Arguments.of("1.2.1", "1.1.1-draft", 1),
            Arguments.of("2.1.1-draft", "1.1.1", 1),
            Arguments.of("2.1.1", "1.1.1-draft", 1),
            Arguments.of("2.0.0-draft", "1.42.69", 1),
            Arguments.of("2.0.0", "1.42.69-draft", 1),
            Arguments.of("2.0.0-draft", "1.1.1", 1),
            Arguments.of("2.0.0", "1.1.1-draft", 1),

            Arguments.of("2.0.0-draft", "2.0.0", -1),
            Arguments.of("2.0.0", "2.0.0-draft", 1),
            Arguments.of("2.0.0-draft", "2.0.0-draft", 0),
            Arguments.of("2.1.0-draft", "2.1.0", -1),
            Arguments.of("2.1.0", "2.1.0-draft", 1),
            Arguments.of("2.1.1-draft", "2.1.1", -1),
            Arguments.of("2.1.1", "2.1.1-draft", 1)
        );
    }

    @ParameterizedTest
    @MethodSource
    void comparator_whenBothLegacy(String v1, String v2, int expectedResult) {
        final Comparator<VersionReference> subject = VersionReference.getComparator();

        assertThat(subject.compare(VersionReference.createFromString(v1), VersionReference.createFromString(v2))).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> comparator_whenBothLegacy() {
        return Stream.of(
            Arguments.of("1.0", "1.1", -1),
            Arguments.of("1.1", "1.1", 0),
            Arguments.of("1.2", "1.1", 1),
            Arguments.of("2.1", "1.1", 1),
            Arguments.of("2.0", "1.4", 1),
            Arguments.of("2.0", "1.1", 1)
        );
    }

    @ParameterizedTest
    @MethodSource
    void comparator_whenOneSemverOneLegacy(String v1, String v2, int expectedResult) {
        final Comparator<VersionReference> subject = VersionReference.getComparator();

        assertThat(subject.compare(VersionReference.createFromString(v1), VersionReference.createFromString(v2))).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> comparator_whenOneSemverOneLegacy() {
        return Stream.of(
            Arguments.of("1.0.0", "2.0", -1),
            Arguments.of("1.0.0", "1.1", -1),
            Arguments.of("1.1.0", "1.1", 1),
            Arguments.of("1.2.0", "1.1", 1),
            Arguments.of("2.0.0", "1.1", 1),
            Arguments.of("2.1.0", "2.1", 1),
            Arguments.of("2.1.0", "2.2", -1),

            Arguments.of("1.0.0-draft", "2.0", -1),
            Arguments.of("1.0.0-draft", "1.1", -1),
            Arguments.of("1.1.0-draft", "1.1", 1),
            Arguments.of("1.2.0-draft", "1.1", 1),
            Arguments.of("2.0.0-draft", "1.1", 1),
            Arguments.of("2.1.0-draft", "2.1", 1),
            Arguments.of("2.1.0-draft", "2.2", -1)
        );
    }
}