package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class VersionableArtefactImplTest {

    @Test
    void whenValidFromNull_validFromInstantIsNull() {
        var codelist = new CodelistImpl();
        codelist.setValidFrom(null);

        assertThat(codelist.getValidFrom()).isNull();
    }

    @Test
    void whenValidToNull_validToInstantIsNull() {
        var codelist = new CodelistImpl();
        codelist.setValidTo(null);

        assertThat(codelist.getValidTo()).isNull();
    }

    @Test
    void whenValidFromIsNotNull_instantIsNotNull() {
        var codelist = new CodelistImpl();
        codelist.setValidFrom("2020-01-01T00:00:00Z");

        assertThat(codelist.getValidFrom()).isEqualTo(Instant.parse("2020-01-01T00:00:00Z"));
    }

    @Test
    void whenValidToIsNotNull_instantIsNotNull() {
        var codelist = new CodelistImpl();
        codelist.setValidTo("2020-01-01T00:00:00Z");

        assertThat(codelist.getValidTo()).isEqualTo(Instant.parse("2020-01-01T00:00:00Z"));
    }

}