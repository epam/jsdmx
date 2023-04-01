package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Locale;

import org.junit.jupiter.api.Test;

class DefaultLocaleHolderTest {

    @Test
    void defaultLocaleIsInitialised() {
        var subject = DefaultLocaleHolder.INSTANCE;

        assertThat(subject.get()).isEqualTo(Locale.forLanguageTag("en"));
    }

    @Test
    void languageTagForDefaultLocaleIsCorrect() {
        var subject = DefaultLocaleHolder.INSTANCE;

        assertThat(subject.getLanguageTag()).isEqualTo("en");
    }

}


