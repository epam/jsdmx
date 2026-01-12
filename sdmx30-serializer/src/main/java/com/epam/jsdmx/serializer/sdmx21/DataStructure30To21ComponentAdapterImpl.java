package com.epam.jsdmx.serializer.sdmx21;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.epam.jsdmx.infomodel.sdmx30.AttributeDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataAttribute;
import com.epam.jsdmx.infomodel.sdmx30.DataAttributeImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;
import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinitionImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.Measure;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptor;
import com.epam.jsdmx.infomodel.sdmx30.MeasureDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.MeasureImpl;
import com.epam.jsdmx.infomodel.sdmx30.ObservationRelationshipImpl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@RequiredArgsConstructor
public class DataStructure30To21ComponentAdapterImpl implements DataStructure30To21ComponentAdapter {

    private static final String OBS_VALUE = "OBS_VALUE";

    private final MetadataConverter metadataConverter;

    @Override
    public DataStructureDefinition recompose(DataStructureDefinition dsd) {
        var dsdCopy = new DataStructureDefinitionImpl(dsd);
        dsdCopy.setMeasureDescriptor(withSingleMeasure(dsd.getMeasureDescriptor()));
        dsdCopy.setDimensionDescriptor(copy(dsd.getDimensionDescriptor()));
        dsdCopy.setAttributeDescriptor(combinedAttributesMetaAndMeasures(dsd));
        return dsdCopy;
    }

    private DimensionDescriptorImpl copy(DimensionDescriptor dimensionDescriptor) {
        if (dimensionDescriptor == null) {
            return null;
        }
        var descriptor = new DimensionDescriptorImpl();
        descriptor.setId(dimensionDescriptor.getId());
        descriptor.setAnnotations(dimensionDescriptor.getAnnotations());
        descriptor.setUri(dimensionDescriptor.getUri());
        descriptor.setContainer(dimensionDescriptor.getContainer());
        descriptor.setComponents(new ArrayList<>(dimensionDescriptor.getComponents()));
        return descriptor;
    }

    private MeasureDescriptorImpl withSingleMeasure(MeasureDescriptor measureDescriptor) {
        if (measureDescriptor == null) {
            return null;
        }
        var descriptor = new MeasureDescriptorImpl();
        descriptor.setId(measureDescriptor.getId());
        descriptor.setAnnotations(measureDescriptor.getAnnotations());
        descriptor.setUri(measureDescriptor.getUri());
        descriptor.setContainer(measureDescriptor.getContainer());
        descriptor.setComponents(extractFirstMeasure(measureDescriptor.getComponents()));
        return descriptor;
    }

    private AttributeDescriptorImpl combinedAttributesMetaAndMeasures(DataStructureDefinition dsd) {
        if (dsd.getAttributeDescriptor() == null) {
            return null;
        }
        var attributeDescriptor = dsd.getAttributeDescriptor();
        var descriptor = new AttributeDescriptorImpl();
        descriptor.setId(attributeDescriptor.getId());
        descriptor.setAnnotations(attributeDescriptor.getAnnotations());
        descriptor.setUri(attributeDescriptor.getUri());
        descriptor.setContainer(attributeDescriptor.getContainer());
        descriptor.setComponents(getCombinedAttributes(dsd));
        return descriptor;
    }

    private List<DataAttribute> getCombinedAttributes(DataStructureDefinition dsd) {
        List<DataAttribute> dataAttributes = dsd.getDataAttributes();
        List<DataAttribute> metaAttributes = metadataConverter.convertMetaAttributesToData(dsd);
        List<DataAttribute> measuresAsAttributes = convertMeasuresToAttributes(dsd.getMeasureDescriptor());

        var result = new ArrayList<>(dataAttributes);
        result.addAll(metaAttributes);
        result.addAll(measuresAsAttributes);

        return result;
    }

    private List<DataAttribute> convertMeasuresToAttributes(MeasureDescriptor measureDescriptor) {
        List<Measure> measures = measureDescriptor.getComponents();

        if (CollectionUtils.size(measures) < 2) {
            return List.of();
        }

        Measure measureToSkip = measures.stream()
            .filter(measure -> OBS_VALUE.equals(measure.getId()))
            .findFirst()
            .orElse(measures.get(0));

        return measures.stream()
            .filter(measure -> !measure.getId().equals(measureToSkip.getId()))
            .map(this::convertMeasureToAttribute)
            .collect(Collectors.toList());
    }

    private DataAttribute convertMeasureToAttribute(Measure measure) {
        var a = new DataAttributeImpl();
        a.setId(measure.getId());
        a.setLocalRepresentation(measure.getLocalRepresentation());
        a.setConceptIdentity(measure.getConceptIdentity());
        a.setAttributeRelationship(new ObservationRelationshipImpl());
        a.setAnnotations(measure.getAnnotations());
        a.setContainer(measure.getContainer());
        return a;
    }

    private List<Measure> extractFirstMeasure(List<Measure> measures) {
        if (CollectionUtils.isEmpty(measures)) {
            return new ArrayList<>(0);
        }

        for (Measure measure : measures) {
            if (measure.getId().equals(OBS_VALUE)) {
                return new ArrayList<>(List.of(measure));
            }
        }

        var firstMeasure = new MeasureImpl(measures.get(0));
        firstMeasure.setId(OBS_VALUE);
        return new ArrayList<>(List.of(firstMeasure));

    }
}
