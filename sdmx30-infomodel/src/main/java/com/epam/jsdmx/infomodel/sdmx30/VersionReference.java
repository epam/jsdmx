package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Represents a wildcarded reference to a specific version of an artefact which is resolved according to the SDMX version management rules,
 * e.g. 1.2+.0
 */
public final class VersionReference extends AbstractVersionReference {

    private static final Pattern MAJOR_WILDCARD_PATTERN = Pattern.compile("(0|[1-9]\\d*)\\+\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)(-\\w+)?");
    private static final Pattern MINOR_WILDCARD_PATTERN = Pattern.compile("(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\+\\.(0|[1-9]\\d*)(-\\w+)?");
    private static final Pattern PATCH_WILDCARD_PATTERN = Pattern.compile("(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\.(0|[1-9]\\d*)\\+(-\\w+)?");

    private static final List<Pair<Pattern, Function<String, VersionReference>>> CREATORS_BY_PATTERN = List.of(
        Pair.of(Version.PATTERN, (String s) -> new VersionReference(Version.createFromString(s), WildcardScope.NONE)),
        Pair.of(MINOR_WILDCARD_PATTERN, (String s) -> new VersionReference(Version.createFromString(removeWildcard(s)), WildcardScope.MINOR)),
        Pair.of(MAJOR_WILDCARD_PATTERN, (String s) -> new VersionReference(Version.createFromString(removeWildcard(s)), WildcardScope.MAJOR)),
        Pair.of(PATCH_WILDCARD_PATTERN, (String s) -> new VersionReference(Version.createFromString(removeWildcard(s)), WildcardScope.PATCH))
    );

    private VersionReference(Version version, WildcardScope scope) {
        super(version, scope);
    }

    private static String removeWildcard(String s) {
        return s.replaceAll("\\+", "");
    }

    /**
     * Creates a {@link VersionReference} from a string representation.
     * @param version valid string representation of a referenced version
     */
    public static VersionReference createFromString(String version) {
        return createFromString(CREATORS_BY_PATTERN, version);
    }

    /**
     * Creates a {@link VersionReference} from a specific {@link Version}.
     * @param version version to be referenced
     */
    public static VersionReference createFromVersion(Version version) {
        return new VersionReference(version, WildcardScope.NONE);
    }

    /**
     * @return comparator for {@link VersionReference} instances
     */
    public static Comparator<VersionReference> getComparator() {
        return (v1, v2) -> {
            if (v1.isWildcarded() || v2.isWildcarded()) {
                throw new IllegalArgumentException(
                    "Cannot compare wildcarded versions: "
                        + v1 + " & " + v2
                );
            }

            var v1Base = v1.getBase();
            var v2Base = v2.getBase();

            var result = Short.compare(v1Base.getMajor(), v2Base.getMajor());
            if (result != 0) {
                return result;
            }

            result = Short.compare(v1Base.getMinor(), v2Base.getMinor());
            if (result != 0) {
                return result;
            }

            if (v1Base.isLegacy() && v2Base.isLegacy()) {
                return 0;
            }

            if (!v1Base.isLegacy() && !v2Base.isLegacy()) {
                int patchComparingResult = Short.compare(v1Base.getPatch().get(), v2Base.getPatch().get());
                if (patchComparingResult != 0) {
                    return patchComparingResult;
                }
                if (v1Base.isStable() && v2Base.isStable() || !v1Base.isStable() && !v2Base.isStable()) {
                    return 0;
                }
                if (v1Base.isStable()) {
                    return 1;
                }
                return -1;
            } else {
                if (v2Base.isLegacy()) {
                    return 1;
                } else {
                    return -1;
                }
            }

        };
    }

    @Override
    public String toString() {
        if (this.isSpecific()) {
            return base.toString();
        } else {
            return Formatter.format(base, scope);
        }
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VersionReference)) {
            return false;
        }
        return Objects.equals(this.toString(), obj.toString());
    }

    private static final class Formatter {

        private static final String MAJOR_WILDCARD_FORMAT = "%d+.%d.%d";
        private static final String MINOR_WILDCARD_FORMAT = "%d.%d+.%d";
        private static final String PATCH_WILDCARD_FORMAT = "%d.%d.%d+";

        private static String format(Version version, WildcardScope scope) {
            switch (scope) {
                case MAJOR:
                    return String.format(MAJOR_WILDCARD_FORMAT, version.getMajor(), version.getMinor(), version.getPatch().orElseThrow());
                case MINOR:
                    return String.format(MINOR_WILDCARD_FORMAT, version.getMajor(), version.getMinor(), version.getPatch().orElseThrow());
                case PATCH:
                    return String.format(PATCH_WILDCARD_FORMAT, version.getMajor(), version.getMinor(), version.getPatch().orElseThrow());
                default:
                    throw new IllegalArgumentException("Invalid scope: " + scope);
            }
        }
    }
}
