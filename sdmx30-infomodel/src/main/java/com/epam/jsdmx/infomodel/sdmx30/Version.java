package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents artefact's version. May be a semver (version extended or not) or a legacy version.
 * <p>
 * Consists of version components (major, minor, patch) and an extension (typically '-draft' which is recommended by the standard).
 * </p>
 * <p>
 * Patch component is optional for legacy versions, and extension is optional for semver versions.
 * Legacy versions with extension are not allowed.
 * </p>
 */
public class Version {

    public static final Pattern PATTERN = Pattern.compile("\\d+\\.\\d+(\\.\\d+(-\\w+)?)?");

    private final String value;
    private final short[] components;
    private final String extension;

    public Version(Version other) {
        this.value = other.value;
        this.components = Arrays.copyOf(other.components, other.components.length);
        this.extension = other.extension;
    }

    private Version(String version) {
        this.value = version;
        this.extension = StringUtils.trimToNull(StringUtils.substringAfter(version, "-"));
        this.components = splitIntoComponents(version);
    }

    public static Version createFromString(String version) {
        return new Version(version);
    }

    private short[] splitIntoComponents(String version) {
        final String[] componentStrings = StringUtils.split(StringUtils.substringBefore(version, "-"), '.');
        final short[] result = new short[componentStrings.length];
        int i = 0;
        for (var component : componentStrings) {
            result[i++] = Short.parseShort(component);
        }
        return result;
    }

    public String getValue() {
        return this.value;
    }

    public short getMajor() {
        return components[0];
    }

    public short getMinor() {
        return components[1];
    }

    public Optional<Short> getPatch() {
        return components.length > 2 ? Optional.of(components[2]) : Optional.empty();
    }

    public Optional<String> getExtension() {
        return Optional.ofNullable(extension);
    }

    /**
     * @return true if version is a legacy version (i.e. does not have patch component, e.g. 1.0)
     */
    public boolean isLegacy() {
        return components.length == 2;
    }

    /**
     * @return true if version does not have an extension, hence it is considered stable
     */
    public boolean isStable() {
        return StringUtils.isEmpty(extension);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Version)) {
            return false;
        }

        Version version = (Version) o;

        return value.equals(version.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
