package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URI;
import java.util.Map;
import java.util.Objects;

/**
 * Internationalised URI. See {@link InternationalObject}.
 */
public class InternationalUri extends InternationalObject<URI> {

    public InternationalUri() { }

    public InternationalUri(InternationalUri from) {
        super(from);
    }

    public InternationalUri(URI defaultLanguageValue) {
        super(defaultLanguageValue);
    }

    public InternationalUri(Map<String, URI> values) {
        super(values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAll());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InternationalUri that = (InternationalUri) o;
        return Objects.equals(getAll(), that.getAll());
    }
}
