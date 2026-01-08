package com.epam.jsdmx.serializer.sdmx30.common;

import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;

public interface MetadataAttributeValue {
    /**
     * @return id of the attribute
     */
    String getId();

    /**
     * @return string representation of the underlying data type
     */
    List<String> getStringValues();

    /**
     * @return localised values
     */
    List<InternationalString> getLocalisedValues();

    /**
     * @return the children of the attribute
     */
    List<MetadataAttributeValue> getChildren();

}
