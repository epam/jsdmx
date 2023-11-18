package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Test;

class InternationalObjectTest {

    InternationalObject<String> subject = new InternationalObject<>() { };

    @Test
    void testAddingDefaultValue() {
        subject.addForDefaultLocale("value");

        assertThat(subject.getForDefaultLocale()).isEqualTo("value");
    }

    @Test
    void testAddingNonDefaultValue() {
        subject.add("uk", "значення");

        assertThat(subject.get("uk")).contains("значення");
    }

    @Test
    void testAddingDefaultAndNonDefaultValues() {
        subject.add("uk", "значення");
        subject.add("en", "value");

        assertThat(subject.get("uk")).contains("значення");
        assertThat(subject.get("en")).contains("value");
    }

    @Test
    void testAddingMultipleNonDefaultValues() {
        subject.add("fr", "valeur");
        subject.add("uk", "значення");
        subject.add("en", "value");

        assertThat(subject.get("uk")).contains("значення");
        assertThat(subject.get("en")).contains("value");
        assertThat(subject.get("fr")).contains("valeur");
    }

    @Test
    void testOverridingDefaultValueByCorrectLangTag() {
        subject.addForDefaultLocale("value_v1");

        assertThat(subject.getForDefaultLocale()).isEqualTo("value_v1");

        subject.add("en", "value_v2");

        assertThat(subject.getForDefaultLocale()).isEqualTo("value_v2");
    }

    @Test
    void testCopyConstructor() {
        InternationalObject<String> oldOne = new InternationalObject<>() { };
        oldOne.addForDefaultLocale("value");
        oldOne.add("uk", "значення");

        var newOne = new InternationalObject<>(oldOne) { };

        assertThat(newOne.getForDefaultLocale()).isEqualTo("value");
        assertThat(newOne.get("uk")).contains("значення");
    }

    @Test
    void testCopyConstructorWithEmptyValue() {
        InternationalObject<String> oldOne = new InternationalObject<>() { };

        var newOne = new InternationalObject<>(oldOne) { };

        assertThat(newOne.getForDefaultLocale()).isEqualTo(null);
        assertThat(newOne.get("uk")).isEmpty();
    }

    @Test
    void testAddingValuesFromMapAndGettingThemAsMap() {
        subject.addAll(Map.of("uk", "значення", "en", "value"));

        assertThat(subject.getAll()).isEqualTo(Map.of("uk", "значення", "en", "value"));
    }

    @Test
    void testAddingValuesFromMapAndGettingThemSeparately() {
        subject.addAll(Map.of("uk", "значення", "en", "value"));

        assertThat(subject.get("uk")).contains("значення");
        assertThat(subject.get("en")).contains("value");
    }

    @Test
    void testCombinedAddition() {
        subject.addAll(Map.of("uk", "значення", "en", "value"));
        subject.add("fr", "valeur");

        assertThat(subject.get("uk")).contains("значення");
        assertThat(subject.get("en")).contains("value");
        assertThat(subject.get("fr")).contains("valeur");
    }

    @Test
    void testGettingValuesAsStream() {
        subject.addAll(Map.of("uk", "значення", "en", "value"));

        assertThat(subject.getAllAsStream()).containsExactlyInAnyOrder(Map.entry("uk", "значення"), Map.entry("en", "value"));
    }

    @Test
    void testGettingByRange() {
        subject.addAll(Map.of("uk", "значення", "en", "value", "fr", "valeur"));

        assertThat(subject.getForRanges(List.of(new Locale.LanguageRange("en"), new Locale.LanguageRange("fr"), new Locale.LanguageRange("uk"))))
            .contains("value");
    }
}