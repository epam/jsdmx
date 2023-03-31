package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * The ComponentList is a list of one or more Component(s). The ComponentList has
 * several concrete descriptor classes based on it: DimensionDescriptor, GroupDimensionDescriptor, MeasureDescriptor,
 * and AttributeDescriptor of the DataStructureDefinition
 * and MetadataAttributeDescriptor of the MetadataStructureDefinition
 */
public interface ComponentList<T extends Component> extends IdentifiableArtefact {
    /**
     * An aggregate association to one or more components which make up the list.
     *
     * @return List of components
     */
    List<T> getComponents();
}
