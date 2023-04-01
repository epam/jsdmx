package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * The Measures that a Data Attribute is reported for.
 */
public interface MeasureRelationship {

    /**
     * @return Association to the set of Measures to which a Data Attribute is related to.
     */
    List<String> getMeasures();
}
