package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

/**
 * Utility singletonclass to hold value fot he default {Locale} for the library to be consumed
 * by implementations of {@link InternationalObject}. The locale's language tag is read from the environment
 * variable <b>SDMX_DEFAULT_LOCALE_TAG</b> and if not present, defaults to {@link Locale#ENGLISH}.
 */
public class DefaultLocaleHolder {

    public static final DefaultLocaleHolder INSTANCE = new DefaultLocaleHolder();

    public static final String TAG_VARIABLE_NAME = "SDMX_DEFAULT_LOCALE_TAG";

    private final Locale defaultLocale;

    private DefaultLocaleHolder() {
        final String defaultTag = System.getenv(TAG_VARIABLE_NAME);
        defaultLocale = StringUtils.isEmpty(defaultTag)
                        ? Locale.ENGLISH
                        : Locale.forLanguageTag(defaultTag);
    }

    /**
     * Returns current value of the default locale.
     */
    public Locale get() {
        return defaultLocale;
    }

    /**
     * Shortcut to {@link Locale#toLanguageTag()} of {@link DefaultLocaleHolder#get()} locale value.
     */
    public String getLanguageTag() {
        return get().toLanguageTag();
    }
}
