package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A collection of metadata concepts, their structure and usage when used to collect or disseminate data
 * A DataStructureDefinition defines the Dimensions, TimeDimension, DataAttributes, and Measures,
 * and associated Representations, that comprise the valid structure of data and related attributes
 * that are contained in a DataSet, which is defined by a Dataflow.
 */
public interface DataStructureDefinition extends Structure, ConstrainableArtefact {

    @Override
    DataStructureDefinition toStub();

    @Override
    DataStructureDefinition toCompleteStub();

    /**
     * @return reference to an {@link AttributeDescriptor}
     */
    AttributeDescriptor getAttributeDescriptor();

    /**
     * @return reference to a {@link DimensionDescriptor}
     */
    DimensionDescriptor getDimensionDescriptor();

    /**
     * @return Collection of a {@link GroupDimensionDescriptor}
     */
    List<GroupDimensionDescriptor> getGroupDimensionDescriptors();

    /**
     * @return The reference to a {@link MeasureDescriptor}
     */
    MeasureDescriptor getMeasureDescriptor();

    /**
     * @return The reference to an {@link ArtefactReference}
     */
    ArtefactReference getMetadataStructure();

    /**
     * @return Map of components and their IDs
     */
    Map<String, Component> getComponentsGroupedById();

    /**
     * @return Collection of series attributes
     */
    List<DataAttribute> getSeriesAttributes();

    /**
     * @return Collection of observation attributes
     */
    List<DataAttribute> getObservationAttributes();

    /**
     * @return Collection of dimension attributes
     */
    List<DataAttribute> getDimensionGroupAttributes();

    /**
     * @return Collection of dataset attributes
     */
    List<DataAttribute> getDataSetAttributes();

    /**
     * @return Collection of {@link Component Components}
     */
    default List<Component> getComponents() {
        final List<Measure> measures = getMeasures();
        final List<DimensionComponent> dimensions = getDimensions();
        final List<DataAttribute> attributes = getDataAttributes();
        final var result = new ArrayList<Component>(measures.size() + dimensions.size() + attributes.size());
        result.addAll(measures);
        result.addAll(dimensions);
        result.addAll(attributes);
        return result;
    }

    /**
     * @return {@link Component} by its ID
     */
    default Component getComponent(String id) {
        return getComponentsGroupedById().get(id);
    }

    /**
     * @return Collection of {@link Measure Measures}. Note that this collection is detached from
     * the measure descriptor, so changes to the returned collection will not affect the measure descriptor.
     */
    default List<Measure> getMeasures() {
        final var md = getMeasureDescriptor();
        if (md == null) {
            return List.of();
        }
        return md.getComponents();
    }

    /**
     * @return Collection of {@link DimensionComponent DimensionComponents}. Note that this collection is detached from
     * the dimension descriptor, so changes to the returned collection will not affect the dimension descriptor.
     */
    default List<DimensionComponent> getDimensions() {
        final var dd = getDimensionDescriptor();
        if (dd == null) {
            return List.of();
        }
        return dd.getComponents();
    }

    /**
     * @return Collection of {@link DataAttribute DataAttributes}. Note that this collection is detached from
     * the attribute descriptor, so changes to the returned collection will not affect the attribute descriptor.
     */
    default List<DataAttribute> getDataAttributes() {
        final var ad = getAttributeDescriptor();
        if (ad == null) {
            return List.of();
        }
        return ad.getComponents();
    }

    /**
     * @return Collection of {@link MetadataAttributeRef MetadataAttributeRefs}. Note that this collection is detached from
     * the attribute descriptor, so changes to the returned collection will not affect meta attribute references in the
     * origin attribute descriptor.
     */
    default List<MetadataAttributeRef> getMetaAttributeReferences() {
        final var ad = getAttributeDescriptor();
        if (ad == null) {
            return List.of();
        }
        return ad.getMetadataAttributes();
    }

    /**
     * @return {@link DimensionComponent DimensionComponent} by ID if present in the dimension descriptor, null otherwise.
     */
    default DimensionComponent getDimension(String dimensionId) {
        final var component = getComponent(dimensionId);
        if (component instanceof DimensionComponent) {
            return (DimensionComponent) component;
        }
        return null;
    }

    /**
     * @return {@link DimensionComponent DimensionComponent} by order ID if present in the dimension descriptor, null otherwise.
     */
    default DimensionComponent getDimension(int order) {
        return getDimensions().stream()
            .filter(d -> d.getOrder() == order)
            .findFirst()
            .orElse(null);
    }

    /**
     * @return {@link DataAttribute DataAttribute} by ID if present in the attribute descriptor, null otherwise.
     */
    default DataAttribute getAttribute(String attrId) {
        final var component = getComponent(attrId);
        if (component instanceof DataAttribute) {
            return (DataAttribute) component;
        }
        return null;
    }

    /**
     * @return {@link Measure Measure} by ID if present in the measure descriptor, null otherwise.
     */
    default Measure getMeasure(String measureId) {
        final var component = getComponent(measureId);
        if (component instanceof Measure) {
            return (Measure) component;
        }
        return null;
    }

    /**
     * @return {@link TimeDimension TimeDimension} if present in the dimension descriptor, null otherwise.
     */
    default TimeDimension getTimeDimension() {
        return (TimeDimension) getDimensions()
            .stream()
            .filter(DimensionComponent::isTimeDimension)
            .findFirst()
            .orElse(null);
    }

}
