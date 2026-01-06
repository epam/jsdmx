package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * The identification and value of a Component of the key
 */
public interface ComponentValue extends Copyable {
    /**
     * The value of Component
     */
    String getValue();

    /**
     * Association to the Component in the Structure to which the Constrainable Artefact is linked
     */
    String getComponentId();

    /**
     * Indicates whether the ComponentValue is included in the constraint definition or excluded from the constraint definition.
     */
    boolean isIncluded();

    /**
     * Indicates whether the Codes should keep or not the prefix, as defined in the extension of Codelist.
     */
    boolean isRemovePrefix();

    /**
     * The values of the Time Dimension component.
     */
    List<TimeDimensionValue> getTimeDimensionValues();

    @Override
    ComponentValue clone();
}
