package com.epam.jsdmx.infomodel.sdmx30;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class MaintainableArtefactReferenceTest {

    @ParameterizedTest
    @MethodSource
    void shouldTrimUrnOnInit(String urn) {
        var res = new MaintainableArtefactReference("\nurn:sdmx:org.sdmx.infomodel.codelist.Codelist=EPM:CL(1.0)");
        assertThat(res).isNotNull();
    }

    private static Stream<Arguments> shouldTrimUrnOnInit() {
        return Stream.of(
            Arguments.of("\nurn:sdmx:org.sdmx.infomodel.codelist.Codelist=EPM:CL(1.0)"),
            Arguments.of("\n urn:sdmx:org.sdmx.infomodel.codelist.Codelist=EPM:CL(1.0)"),
            Arguments.of("\nurn:sdmx:org.sdmx.infomodel.codelist.Codelist=EPM:CL(1.0) "),
            Arguments.of("\n urn:sdmx:org.sdmx.infomodel.codelist.Codelist=EPM:CL(1.0) "),
            Arguments.of("\n urn:sdmx:org.sdmx.infomodel.codelist.Codelist=EPM:CL(1.0)\n ")
        );
    }

}