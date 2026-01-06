package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Represents a wildcarded reference in artefact queries for all available semantic versions within the wildcard scope (*, X.* or X.Y.*),
 * where only the first form is required for resolving wildcarded loose references
 * I.e infomodel describes a possibility to have references like 2.1.* which may resolve to 2.1.0, 2.1.1, 2.1.2 etc.
 */
public final class LooseVersionReference extends AbstractVersionReference {

    private static final Pattern MAJOR_WILDCARD_PATTERN = Pattern.compile("\\*");
    private static final Pattern MINOR_WILDCARD_PATTERN = Pattern.compile("(0|[1-9]\\d*)\\.\\*");
    private static final Pattern PATCH_WILDCARD_PATTERN = Pattern.compile("(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.\\*");

    private static final List<Pair<Pattern, Function<String, LooseVersionReference>>> CREATORS_BY_PATTERN = List.of(
        Pair.of(
            Version.PATTERN,
            (String s) -> new LooseVersionReference(Version.createFromString(s), WildcardScope.NONE)
        ),
        Pair.of(
            MINOR_WILDCARD_PATTERN,
            (String s) -> new LooseVersionReference(Version.createFromString(StringUtils.substringBeforeLast(s, ".") + ".0.0"), WildcardScope.MINOR)
        ),
        Pair.of(
            MAJOR_WILDCARD_PATTERN,
            (String s) -> new LooseVersionReference(Version.createFromString("0.0.0"), WildcardScope.MAJOR)
        ),
        Pair.of(
            PATCH_WILDCARD_PATTERN,
            (String s) -> new LooseVersionReference(Version.createFromString(StringUtils.substringBeforeLast(s, ".") + ".0"), WildcardScope.PATCH)
        )
    );

    private LooseVersionReference(Version version, WildcardScope scope) {
        super(version, scope);
    }

    public static LooseVersionReference createFromString(String version) {
        return createFromString(CREATORS_BY_PATTERN, version);
    }

    @Override
    public String toString() {
        if (isSpecific()) {
            return base.toString();
        }
        return Formatter.format(base, scope);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LooseVersionReference)) {
            return false;
        }
        return Objects.equals(this.toString(), obj.toString());
    }

    private static final class Formatter {

        private static final String MAJOR_WILDCARD_FORMAT = "*";
        private static final String MINOR_WILDCARD_FORMAT = "%d.*";
        private static final String PATCH_WILDCARD_FORMAT = "%d.%d.*";

        private static String format(Version version, WildcardScope scope) {
            switch (scope) {
                case MAJOR:
                    return MAJOR_WILDCARD_FORMAT;
                case MINOR:
                    return String.format(MINOR_WILDCARD_FORMAT, version.getMajor());
                case PATCH:
                    return String.format(PATCH_WILDCARD_FORMAT, version.getMajor(), version.getMinor());
                default:
                    throw new IllegalArgumentException("Invalid scope: " + scope);
            }
        }
    }
}
