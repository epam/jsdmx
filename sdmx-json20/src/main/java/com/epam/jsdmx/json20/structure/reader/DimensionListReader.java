package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.reader.ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.DimensionComponent;
import com.epam.jsdmx.infomodel.sdmx30.DimensionDescriptorImpl;
import com.epam.jsdmx.infomodel.sdmx30.DimensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.infomodel.sdmx30.TimeDimensionImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;
import com.epam.jsdmx.serializer.util.DimensionUtil;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class DimensionListReader {

    private final IdentifiableReader identifiableReader;

    public DimensionListReader(IdentifiableReader identifiableReader) {
        this.identifiableReader = identifiableReader;
    }

    public DimensionDescriptorImpl getDimensionDescriptor(JsonParser parser) throws IOException {
        parser.nextToken();
        if (ReaderUtils.isNullValue(parser)) {
            return null;
        }
        DimensionDescriptorImpl dimensionDescriptor = new DimensionDescriptorImpl();
        List<DimensionComponent> dimensionComponents = new ArrayList<>();
        while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
            checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.DIMENSIONS:
                    List<DimensionImpl> dimensions = ReaderUtils.getArray(parser, (this::getDimension));
                    if (CollectionUtils.isNotEmpty(dimensions)) {
                        dimensionComponents.addAll(dimensions);
                    }
                    break;
                case StructureUtils.TIME_DIMENSIONS:
                    List<TimeDimensionImpl> timeDimensions = ReaderUtils.getArray(parser, (this::getTimeDimension));
                    if (CollectionUtils.isNotEmpty(timeDimensions)) {
                        dimensionComponents.addAll(timeDimensions);
                    }
                    break;
                default:
                    identifiableReader.read(dimensionDescriptor, parser);
                    break;
            }
        }

        DimensionUtil.populateZeroBasedOrder(dimensionComponents);

        dimensionDescriptor.setComponents(dimensionComponents);
        dimensionDescriptor.setId(StructureUtils.DIMENSION_DESCRIPTOR_ID);
        return dimensionDescriptor;
    }

    private TimeDimensionImpl getTimeDimension(JsonParser parser) {
        try {
            TimeDimensionImpl timeDimension = new TimeDimensionImpl();
            while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.POSITION:
                        parser.nextToken();
                        timeDimension.setOrder(parser.getIntValue());
                        break;
                    case StructureUtils.TYPE:
                        parser.nextToken();
                        break;
                    case StructureUtils.CONCEPT_IDENTITY:
                        String conceptIdentity = ReaderUtils.getStringJsonField(parser);
                        if (conceptIdentity != null) {
                            timeDimension.setConceptIdentity(new IdentifiableArtefactReferenceImpl(conceptIdentity));
                        }
                        break;
                    case StructureUtils.LOCAL_REPRESENTATION:
                        timeDimension.setLocalRepresentation(getRepresentation(parser));
                        break;
                    default:
                        identifiableReader.read(timeDimension, parser);
                        break;
                }
            }
            return timeDimension;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private DimensionImpl getDimension(JsonParser parser) {
        try {
            DimensionImpl dimension = new DimensionImpl();
            while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
                checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.POSITION:
                        parser.nextToken();
                        dimension.setOrder(parser.getIntValue());
                        break;
                    case StructureUtils.TYPE:
                        parser.nextToken();
                        break;
                    case StructureUtils.CONCEPT_IDENTITY:
                        String conceptIdentity = ReaderUtils.getStringJsonField(parser);
                        if (conceptIdentity != null) {
                            dimension.setConceptIdentity(new IdentifiableArtefactReferenceImpl(conceptIdentity));
                        }
                        break;
                    case StructureUtils.CONCEPT_ROLES:
                        List<ArtefactReference> conceptRoles = ConceptRoleUtils.getConceptRoles(parser);
                        if (CollectionUtils.isNotEmpty(conceptRoles)) {
                            dimension.setConceptRoles(conceptRoles);
                        }
                        break;
                    case StructureUtils.LOCAL_REPRESENTATION:
                        dimension.setLocalRepresentation(getRepresentation(parser));
                        break;
                    default:
                        identifiableReader.read(dimension, parser);
                        break;
                }
            }
            return dimension;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private Representation getRepresentation(JsonParser parser) throws IOException {
        RepresentationReader representationReader = new RepresentationReader();
        return representationReader.getRepresentations(parser);
    }
}
