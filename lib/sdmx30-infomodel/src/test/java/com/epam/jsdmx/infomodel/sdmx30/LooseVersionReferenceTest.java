package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;

class LooseVersionReferenceTest {

    @Test
    void testNonWildcard() {
        LooseVersionReference subject = LooseVersionReference.createFromString("1.0.0");

        assertThat(subject.toString()).isEqualTo("1.0.0");
        assertThat(subject.getScope()).isEqualTo(WildcardScope.NONE);
        assertThat(subject.isWildcarded()).isFalse();
    }

    @Test
    void testMajorWildcard() {
        LooseVersionReference subject = LooseVersionReference.createFromString("*");

        assertThat(subject.toString()).isEqualTo("*");
        assertThat(subject.getBase().toString()).isEqualTo("0.0.0");
        assertThat(subject.getScope()).isEqualTo(WildcardScope.MAJOR);
        assertThat(subject.isWildcarded()).isTrue();
    }

    @Test
    void testMinorWildcard() {
        LooseVersionReference subject = LooseVersionReference.createFromString("1.*");

        assertThat(subject.toString()).isEqualTo("1.*");
        assertThat(subject.getBase().toString()).isEqualTo("1.0.0");
        assertThat(subject.getScope()).isEqualTo(WildcardScope.MINOR);
        assertThat(subject.isWildcarded()).isTrue();
    }

    @Test
    void testPatchWildcard() {
        LooseVersionReference subject = LooseVersionReference.createFromString("1.2.*");

        assertThat(subject.toString()).isEqualTo("1.2.*");
        assertThat(subject.getBase().toString()).isEqualTo("1.2.0");
        assertThat(subject.getScope()).isEqualTo(WildcardScope.PATCH);
        assertThat(subject.isWildcarded()).isTrue();
    }

}