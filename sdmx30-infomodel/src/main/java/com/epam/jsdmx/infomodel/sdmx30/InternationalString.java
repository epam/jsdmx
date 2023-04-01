package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Map;
import java.util.Objects;

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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternationalString that = (InternationalString) o;
        return Objects.equals(getAll(), that.getAll());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAll());
    }
}
