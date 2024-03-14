package com.epam.jsdmx.infomodel.sdmx30;

import static com.epam.jsdmx.infomodel.sdmx30.WildcardScope.MAJOR;
import static com.epam.jsdmx.infomodel.sdmx30.WildcardScope.MINOR;

import java.util.Comparator;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

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
        final String[] split = StringUtils.split(version, '.');
        if (split.length == 2) {
            final short major = Short.parseShort(split[0]);
            final short minor = Short.parseShort(split[1]);
            return new VersionReference(Version.createFromComponents(major, minor), WildcardScope.NONE);
        }
        if (split.length != 3) {
            throw new IllegalArgumentException("Invalid version format: " + version);
        }
        final WildcardScope scope = findWildcardScope(split);
        final short major = Short.parseShort(scope == MAJOR ? StringUtils.substringBefore(split[0], '+') : split[0]);
        final short minor = Short.parseShort(scope == MINOR ? StringUtils.substringBefore(split[1], '+') : split[1]);

        final String[] patchSplit = StringUtils.split(split[2], '-');
        short patch;
        if (patchSplit.length > 1) {
            if (scope != WildcardScope.NONE) {
                // if the version is wildcarded, there is no extension expected
                throw new IllegalArgumentException("Invalid version format: " + version);
            }
            patch = Short.parseShort(patchSplit[0]);
        } else {
            patch = Short.parseShort(scope == WildcardScope.PATCH ? StringUtils.substringBefore(split[2], '+') : split[2]);
        }
        return new VersionReference(
            Version.createFromComponents(
                major,
                minor,
                patch,
                patchSplit.length > 1 ? StringUtils.substringAfter(split[2], "-") : ""
            ),
            scope
        );
    }

    private static WildcardScope findWildcardScope(String[] components) {
        if (StringUtils.endsWith(components[0], "+")) {
            return MAJOR;
        } else if (StringUtils.endsWith(components[1], "+")) {
            return MINOR;
        } else if (StringUtils.endsWith(components[2], "+")) {
            return WildcardScope.PATCH;
        } else {
            return WildcardScope.NONE;
        }
    }

    /**
     * Creates a {@link VersionReference} from a specific {@link Version}.
     *
     * @param version version to be referenced
     */
    public static VersionReference createFromVersion(Version version) {
        return new VersionReference(version, WildcardScope.NONE);
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
}
