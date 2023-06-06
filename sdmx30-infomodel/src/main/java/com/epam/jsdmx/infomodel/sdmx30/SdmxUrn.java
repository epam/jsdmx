package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class SdmxUrn {

    private static final String CLASS = "class";
    private static final String AGENCY = "agency";
    private static final String ID = "id";
    private static final String VERSION = "version";
    private static final String ITEM_ID = "itemId";

    private static final String URN_SDMX = "urn:sdmx:";

    private static final Pattern FULL_URN_PATTERN = Pattern.compile(
        "^"
            + URN_SDMX
            + "(?<class>\\w+(\\.\\w+)*)="
            + "(?<agency>[\\w.-]+):(?<id>[\\w@$-]+)"
            + "\\((?<version>(\\d+\\.\\d+)|(\\d+\\.\\d+\\.\\d+(-[\\w-]+)?)|(\\d+\\+\\.\\d+\\.\\d+)|(\\d+\\.\\d+\\+\\.\\d+)|(\\d+\\.\\d+\\.\\d+\\+))\\)"
            + "(\\.(?<itemId>([\\w@$-]+)+(\\.[\\w@$-]+)*))?$"
    );

    private static final Pattern SHORT_URN_PATTERN = Pattern.compile(
        "^(?<agency>[\\w.-]+):"
            + "(?<id>[\\w@$-]+)"
            + "\\((?<version>(\\d+\\.\\d+)|(\\d+\\.\\d+\\.\\d+(-[\\w-]+)?)|(\\d+\\+\\.\\d+\\.\\d+)|(\\d+\\.\\d+\\+\\.\\d+)|(\\d+\\.\\d+\\.\\d+\\+))\\)"
            + "(\\.(?<itemId>([\\w@$-]+)+(\\.[\\w@$-]+)*))?$"
    );

    private static final String FULL_SDMX_URN_FORMAT = URN_SDMX + "%s=%s:%s(%s)%s";
    private static final String SHORT_SDMX_URN_FORMAT = "%s:%s(%s)%s";

    public static boolean isUrn(String candidate) {
        if (candidate == null) {
            return false;
        }
        return FULL_URN_PATTERN.matcher(candidate).matches() || SHORT_URN_PATTERN.matcher(candidate).matches();
    }

    public static UrnComponents getUrnComponents(String urn) {
        final Matcher fullUrnMatcher = FULL_URN_PATTERN.matcher(urn);
        if (fullUrnMatcher.matches()) {
            return buildFromFullUrn(fullUrnMatcher);
        }

        final Matcher shortUrnMatcher = SHORT_URN_PATTERN.matcher(urn);
        if (shortUrnMatcher.matches()) {
            return buildFromShortUrn(shortUrnMatcher);
        }

        throw new UrnFormatException(urn);
    }

    public static Optional<? extends StructureClass> getType(String urn) {
        return getType(urn, StructureClassImpl::getByFullyQualifiedName);
    }

    public static Optional<? extends StructureClass> getType(String urn, Function<String, Optional<? extends StructureClass>> typeResolver) {
        final String name = StringUtils.replace(getStructureClass(urn), URN_SDMX, "");
        return typeResolver.apply(name);
    }

    public static String toFullUrnString(StructureClass type, String agency, String code, Version version) {
        return toFullUrnString(type, agency, code, version != null ? version.toString() : null);
    }

    public static String toFullUrnString(StructureClass klass, String agency, String code, String version) {
        return toFullItemUrnString(klass, agency, code, version, null);
    }

    public static String toFullItemUrnString(StructureClass klass, String agency, String code, String version, String itemId) {
        final String dotItemId = getDotItemId(itemId);
        return String.format(FULL_SDMX_URN_FORMAT, klass.getFullyQualifiedName(), agency, code, version, dotItemId);
    }

    public static String toShortUrnString(String agency, String id, String version) {
        return toShortItemUrnString(agency, id, version, null);
    }

    public static String toShortItemUrnString(String agency, String id, String version, String itemId) {
        final String dotItemId = getDotItemId(itemId);
        return String.format(SHORT_SDMX_URN_FORMAT, agency, id, version, dotItemId);
    }

    public static String parseToShortUrnString(String fullUrn) {
        var components = getUrnComponents(fullUrn);
        return toShortUrnString(components.getAgency(), components.getId(), components.getVersion());
    }

    public static String parseToShortItemUrnString(String fullUrn) {
        var components = getUrnComponents(fullUrn);
        return toShortItemUrnString(components.getAgency(), components.getId(), components.getVersion(), components.getItemId());
    }

    public static String getItemUrnString(String containerUrn, String containedId) {
        return containerUrn + "." + containedId;
    }

    private static String getStructureClass(String urn) {
        final Matcher fullUrnMatcher = FULL_URN_PATTERN.matcher(urn);
        if (fullUrnMatcher.matches()) {
            return fullUrnMatcher.group(CLASS);
        }
        throw new UrnFormatException(urn);
    }

    private static UrnComponents buildFromFullUrn(Matcher fullUrnMatcher) {
        return UrnComponents.builder()
            .structureClass(fullUrnMatcher.group(CLASS))
            .agency(fullUrnMatcher.group(AGENCY))
            .id(fullUrnMatcher.group(ID))
            .version(fullUrnMatcher.group(VERSION))
            .itemId(fullUrnMatcher.group(ITEM_ID))
            .build();
    }

    private static UrnComponents buildFromShortUrn(Matcher shortUrnMatcher) {
        return UrnComponents.builder()
            .agency(shortUrnMatcher.group(AGENCY))
            .id(shortUrnMatcher.group(ID))
            .version(shortUrnMatcher.group(VERSION))
            .itemId(shortUrnMatcher.group(ITEM_ID))
            .build();
    }

    private static String getDotItemId(String itemId) {
        return itemId != null ? "." + itemId : "";
    }

    public static class UrnFormatException extends IllegalArgumentException {
        public UrnFormatException(String urn) {
            super("Invalid urn: " + urn);
        }
    }
}
