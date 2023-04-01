package com.epam.jsdmx.infomodel.sdmx30;

/**
 * This facet indicates that an Attribute or a Measure has sentinel values with special
 * meaning within their data type. This is realised by providing such values within the
 * TextFormat, in addition to any textType or other Facet.
 */
public interface SentinelValue extends Copyable {
    /**
     * Value that has a special meaning in context of the component representation.
     */
    String getValue();

    /**
     * An association of a Sentinel Value to a multilingual name.
     */
    InternationalString getName();

    /**
     * An association of a Sentinel Value to a multilingual description.
     */
    InternationalString getDescription();

    @Override
    SentinelValue clone();
}
