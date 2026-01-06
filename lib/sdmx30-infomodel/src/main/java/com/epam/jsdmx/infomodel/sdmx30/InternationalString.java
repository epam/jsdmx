package com.epam.jsdmx.infomodel.sdmx30;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

/**
 * The InternationalString supports the representation of a description in multiple languages.
 * See {@link InternationalObject}.
 */
public class InternationalString extends InternationalObject<String> {

    public InternationalString() {
    }

    public InternationalString(InternationalString from) {
        super(from);
    }

    public InternationalString(String defaultLanguageValue) {
        super(defaultLanguageValue);
    }

    public InternationalString(Map<String, String> values) {
        super(values);
    }

    @Override
    protected boolean isEmpty(String value) {
        return StringUtils.isEmpty(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null) {
            return Objects.equals(
                collectNotEmptyLocales(getAll()),
                Map.of()
            );
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        InternationalString that = (InternationalString) o;
        return Objects.equals(
            collectNotEmptyLocales(getAll()),
            collectNotEmptyLocales(that.getAll())
        );
    }

    private Map<String, String> collectNotEmptyLocales(Map<String, String> locales) {
        if (locales == null) {
            return Map.of();
        }
        return locales.entrySet().stream()
            .filter(entry -> isNotEmpty(entry.getValue()))
            .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAll());
    }
}
