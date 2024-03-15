package com.epam.jsdmx.infomodel.sdmx30;

import static com.epam.jsdmx.infomodel.sdmx30.WildcardScope.MAJOR;
import static com.epam.jsdmx.infomodel.sdmx30.WildcardScope.MINOR;
import static com.epam.jsdmx.infomodel.sdmx30.WildcardScope.NONE;

import java.nio.channels.IllegalChannelGroupException;
import java.util.Comparator;
import java.util.Objects;

/**
 * Represents a wildcarded reference to a specific version of an artefact which is resolved according to the SDMX version management rules,
 * e.g. 1.2+.0
 */
public final class VersionReference extends AbstractVersionReference {

    private VersionReference(Version version, WildcardScope scope) {
        super(version, scope);
    }

    /**
     * Creates a {@link VersionReference} from a string representation.
     *
     * @param version valid string representation of a referenced version
     */
    public static VersionReference createFromString(String version) {
        try {
            return parse(version);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid version: " + version);
        }
    }

    private static VersionReference parse(String version) {
        WildcardScope scope = NONE;
        int majorUntil = -1;
        int minorFrom = -1;
        int minorUntil = -1;
        int patchFrom = -1;
        int patchUntil = -1;
        int extensionFrom = -1;
        Position p = Position.MAJOR;
        final int inputLength = version.length();
        for (int i = 0; i < inputLength; i++) {
            char c = version.charAt(i);
            if (c > 47 & c < 58) {
                switch (p) {
                    case MAJOR:
                        majorUntil = i;
                        break;
                    case MINOR:
                        minorUntil = i;
                        break;
                    case PATCH:
                        patchUntil = i;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            } else if (c == '+') {
                switch (p) {
                    case MAJOR:
                        scope = MAJOR;
                        break;
                    case MINOR:
                        scope = MINOR;
                        break;
                    case PATCH:
                        scope = WildcardScope.PATCH;
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            } else if (c == '.') {
                switch (p) {
                    case MAJOR:
                        p = Position.MINOR;
                        minorFrom = i + 1;
                        break;
                    case MINOR:
                        p = Position.PATCH;
                        patchFrom = i + 1;
                        break;
                    default:
                        throw new IllegalChannelGroupException();
                }
            } else if (c == '-') {
                extensionFrom = i + 1;
                break;
            } else {
                throw new IllegalArgumentException("Invalid version: " + version);
            }
        }
        short major = Short.parseShort(version.substring(0, majorUntil + 1));

        if (minorFrom == -1 || minorFrom >= inputLength || minorFrom > minorUntil || minorUntil < 0) {
            throw new IllegalArgumentException("Invalid version: " + version);
        }

        short minor = Short.parseShort(version.substring(minorFrom, minorUntil + 1));

        if (patchFrom > 0 && patchFrom >= inputLength || patchFrom > patchUntil) {
            throw new IllegalArgumentException("Invalid version: " + version);
        }

        short patch = patchFrom > 0 ? Short.parseShort(version.substring(patchFrom, patchUntil + 1)) : -1;

        if (extensionFrom > 0 && patchFrom == -1) {
            throw new IllegalArgumentException("Invalid version: " + version);
        }
        if (extensionFrom > 0 && extensionFrom >= inputLength) {
            throw new IllegalArgumentException("Invalid version: " + version);
        }

        String extension = extensionFrom == -1 ? null : version.substring(extensionFrom);

        return new VersionReference(
            patchFrom > 0
                ? Version.createFromComponents(major, minor, patch, extension)
                : Version.createFromComponents(major, minor),
            scope
        );
    }

    /**
     * Creates a {@link VersionReference} from a specific {@link Version}.
     *
     * @param version version to be referenced
     */
    public static VersionReference createFromVersion(Version version) {
        return new VersionReference(version, NONE);
    }

    /**
     * Creates a {@link VersionReference} from a specific {@link Version} and {@link WildcardScope}.
     *
     * @param version version to be referenced
     * @param scope   version component to wildcard
     */
    public static VersionReference createFromVersionAndWildcardScope(Version version, WildcardScope scope) {
        return new VersionReference(version, scope);
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

    private enum Position {
        MAJOR, MINOR, PATCH, EXTENSION
    }
}
