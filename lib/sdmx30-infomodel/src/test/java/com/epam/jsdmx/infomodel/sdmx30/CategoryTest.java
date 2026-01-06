package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    void equals_firstNamesAreEmptyAndNewNamesAreNull_returnTrue() {
        // given
        CategoryImpl category1 = new CategoryImpl();
        category1.setName(new InternationalString());
        CategoryImpl category2 = new CategoryImpl();
        category2.setName(null);
        //when
        boolean equalsResult = category1.equals(category2);
        //then
        assertThat(equalsResult).isTrue();
    }

    @Test
    void equals_firstNamesAreNullAndNewNamesAreEmpty_returnTrue() {
        // given
        CategoryImpl category1 = new CategoryImpl();
        category1.setName(null);
        CategoryImpl category2 = new CategoryImpl();
        category2.setName(new InternationalString());
        //when
        boolean equalsResult = category1.equals(category2);
        //then
        assertThat(equalsResult).isTrue();
    }

    @Test
    void equals_firstNamesAreEmptyAndNewNamesHasOnlyEmptyValues_returnTrue() {
        // given
        CategoryImpl category1 = new CategoryImpl();
        category1.setName(new InternationalString());
        CategoryImpl category2 = new CategoryImpl();
        category2.setName(
            new InternationalString(
                Map.of(
                    "en", ""
                )
            )
        );
        //when
        boolean equalsResult = category1.equals(category2);
        //then
        assertThat(equalsResult).isTrue();
    }

    @Test
    void equals_firstAndSecondNamesHaveDifferentEmptyLocales_returnTrue() {
        // given
        CategoryImpl category1 = new CategoryImpl();
        category1.setName(
            new InternationalString(
                Map.of(
                    "en", ""
                )
            )
        );

        CategoryImpl category2 = new CategoryImpl();
        category2.setName(
            new InternationalString(
                Map.of(
                    "uk", ""
                )
            )
        );
        //when
        boolean equalsResult = category1.equals(category2);
        //then
        assertThat(equalsResult).isTrue();
    }

    @Test
    void equals_firstAndSecondNamesHaveDifferencesOnlyAtEmptyLocales_returnTrue() {
        // given
        CategoryImpl category1 = new CategoryImpl();
        category1.setName(
            new InternationalString(
                Map.of(
                    "en", "name",
                    "uk", ""
                )
            )
        );

        CategoryImpl category2 = new CategoryImpl();
        category2.setName(
            new InternationalString(
                Map.of(
                    "en", "name",
                    "kz", ""
                )
            )
        );
        //when
        boolean equalsResult = category1.equals(category2);
        //then
        assertThat(equalsResult).isTrue();
    }

    @Test
    void equals_firstAndSecondNamesHaveSameValues_returnTrue() {
        // given
        CategoryImpl category1 = new CategoryImpl();
        category1.setName(
            new InternationalString(
                Map.of(
                    "en", "enName",
                    "uk", "ukName"
                )
            )
        );

        CategoryImpl category2 = new CategoryImpl();
        category2.setName(
            new InternationalString(
                Map.of(
                    "en", "enName",
                    "uk", "ukName"
                )
            )
        );
        //when
        boolean equalsResult = category1.equals(category2);
        //then
        assertThat(equalsResult).isTrue();
    }

    @Test
    void equals_firstAndSecondNamesHaveDifferentValuesForOneOfLocales_returnTrue() {
        // given
        CategoryImpl category1 = new CategoryImpl();
        category1.setName(
            new InternationalString(
                Map.of(
                    "en", "enName",
                    "uk", "ukName1"
                )
            )
        );

        CategoryImpl category2 = new CategoryImpl();
        category2.setName(
            new InternationalString(
                Map.of(
                    "en", "enName",
                    "uk", "ukName2"
                )
            )
        );
        //when
        boolean equalsResult = category1.equals(category2);
        //then
        assertThat(equalsResult).isFalse();
    }
}
