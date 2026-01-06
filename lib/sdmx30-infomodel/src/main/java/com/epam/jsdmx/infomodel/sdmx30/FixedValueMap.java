package com.epam.jsdmx.infomodel.sdmx30;

/**
 * Links a Component (source or target) to a fixed value
 */
public interface FixedValueMap extends AnnotableArtefact {

    /**
     * @return {@link MappingRoleType}
     */
    MappingRoleType getRole();

    /**
     * @return The value that a Component will be fixed in a fixed component map
     */
    String getValue();

    /**
     * @return Component from the source or target DataStructureDefinition, that are map to a fixed value
     */
    String getComponent();
}
