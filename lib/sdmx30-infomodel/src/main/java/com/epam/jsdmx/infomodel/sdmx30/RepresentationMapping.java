package com.epam.jsdmx.infomodel.sdmx30;

import java.time.Instant;
import java.util.List;

/**
 * Describes how the source value(s) map to the target value(s)
 */
public interface RepresentationMapping extends AnnotableArtefact {

    /**
     * @return Optional period describing when the mapping is applicable
     */
    Instant getValidFrom();

    /**
     * @return Optional period describing which the mapping is no longer applicable.
     */
    Instant getValidTo();

    /**
     * @return Collection of input values for source in the RepresentationMap
     */
    List<MappedValue> getSourceValues();

    /**
     * @return Collection of output value for each mapped target in the RepresentationMap
     */
    List<TargetValue> getTargetValues();
}
