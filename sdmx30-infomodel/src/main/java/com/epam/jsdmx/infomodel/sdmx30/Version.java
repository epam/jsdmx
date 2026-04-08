package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Arrays;
import java.util.Objects;
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

    private final short[] componentsArray;
    private final String extension;

    private String value;

    public Version(Version other) {
        this.value = other.value;
        this.componentsArray = Arrays.copyOf(other.componentsArray, other.componentsArray.length);
        this.extension = other.extension;
    }

    private Version(String version) {
        this.value = version;
        this.extension = StringUtils.trimToNull(StringUtils.substringAfter(version, "-"));
        this.componentsArray = splitIntoComponents(version);
    }

    private Version(short major, short minor, short patch, String extension) {
        validateComponents(major, minor, patch);
        this.componentsArray = new short[]{major, minor, patch};
        this.extension = extension;
    }

    private Version(short major, short minor) {
        validateComponents(major, minor);
        this.value = major + "." + minor;
        this.componentsArray = new short[]{major, minor};
        this.extension = null;
    }

    private void validateComponents(short... components) {
        for (var component : components) {
            if (component < 0) {
                throw new IllegalArgumentException("Version components must be non-negative");
            }
        }
    }

    public static Version createFromString(String version) {
        return new Version(version);
    }

    public static Version createFromComponents(int major, int minor, int patch, String extension) {
        return createFromComponents((short) major, (short) minor, (short) patch, extension);
    }

    public static Version createFromComponents(short major, short minor, short patch, String extension) {
        return new Version(major, minor, patch, extension);
    }

    public static Version createFromComponents(short major, short minor) {
        return new Version(major, minor);
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
        if (this.value == null) {
            // may be null only for 3-component version
            value = componentsArray[0] + "." + componentsArray[1] + "." + componentsArray[2] + (StringUtils.isNotEmpty(extension) ? "-" + extension : "");
        }
        return value;
    }

    public short getMajor() {
        return componentsArray[0];
    }

    public short getMinor() {
        return componentsArray[1];
    }

    public Optional<Short> getPatch() {
        return componentsArray.length > 2 ? Optional.of(componentsArray[2]) : Optional.empty();
    }

    public Optional<String> getExtension() {
        return Optional.ofNullable(extension);
    }

    /**
     * @return true if version is a legacy version (i.e. does not have patch component, e.g. 1.0)
     */
    public boolean isLegacy() {
        return componentsArray.length == 2;
    }

    /**
     * @return true if version does not have an extension, hence it is considered stable
     */
    public boolean isStable() {
        return StringUtils.isEmpty(extension);
    }

    @Override
    public String toString() {
        return getValue();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Version version = (Version) o;
        return Arrays.equals(componentsArray, version.componentsArray) && Objects.equals(extension, version.extension);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(componentsArray);
        result = 31 * result + Objects.hashCode(extension);
        return result;
    }
}
