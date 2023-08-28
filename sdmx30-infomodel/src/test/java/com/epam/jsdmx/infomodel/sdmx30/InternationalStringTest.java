package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class InternationalStringTest {

    @Test
    void equals_firstTextsAreEmptyAndNewTextsAreNull_returnTrue() {
        // given
        InternationalString texts1 = new InternationalString();
        //when
        boolean equalsResult = texts1.equals(null);
        //then
        assertThat(equalsResult).isTrue();
    }

    @Test
    void equals_firstTextsAreEmptyAndNewTextsHasOnlyEmptyValues_returnTrue() {
        // given
        InternationalString texts1 = new InternationalString();
        InternationalString texts2 = new InternationalString(
            Map.of(
                "en", ""
            )
        );
        //when
        boolean equalsResult = texts1.equals(texts2);
        //then
        assertThat(equalsResult).isTrue();
    }

    @Test
    void equals_firstAndSecondTextsHaveDifferentEmptyLocales_returnTrue() {
        // given
        InternationalString texts1 = new InternationalString(
            Map.of(
                "en", ""
            )
        );

        InternationalString texts2 = new InternationalString(
            Map.of(
                "uk", ""
            )
        );
        //when
        boolean equalsResult = texts1.equals(texts2);
        //then
        assertThat(equalsResult).isTrue();
    }

    @Test
    void equals_firstAndSecondTextsHaveDifferencesOnlyAtEmptyLocales_returnTrue() {
        // given
        InternationalString texts1 = new InternationalString(
            Map.of(
                "en", "name",
                "uk", ""
            )
        );

        InternationalString texts2 = new InternationalString(
            Map.of(
                "en", "name",
                "kz", ""
            )
        );
        //when
        boolean equalsResult = texts1.equals(texts2);
        //then
        assertThat(equalsResult).isTrue();
    }

    @Test
    void equals_firstAndSecondTextsHaveSameValues_returnTrue() {
        // given
        InternationalString texts1 = new InternationalString(
            Map.of(
                "en", "enName",
                "uk", "ukName"
            )
        );

        InternationalString texts2 = new InternationalString(
            Map.of(
                "en", "enName",
                "uk", "ukName"
            )
        );
        //when
        boolean equalsResult = texts1.equals(texts2);
        //then
        assertThat(equalsResult).isTrue();
    }

    @Test
    void equals_firstAndSecondTextsHaveDifferentValuesForOneOfLocales_returnTrue() {
        // given
        InternationalString texts1 = new InternationalString(
            Map.of(
                "en", "enName",
                "uk", "ukName1"
            )
        );

        InternationalString texts2 = new InternationalString(
            Map.of(
                "en", "enName",
                "uk", "ukName2"
            )
        );
        //when
        boolean equalsResult = texts1.equals(texts2);
        //then
        assertThat(equalsResult).isFalse();
    }
}
