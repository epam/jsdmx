package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Concept;
import com.epam.jsdmx.infomodel.sdmx30.ConceptImpl;
import com.epam.jsdmx.infomodel.sdmx30.ConceptSchemeImpl;
import com.epam.jsdmx.infomodel.sdmx30.IsoConceptReferenceImpl;
import com.epam.jsdmx.infomodel.sdmx30.Representation;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import org.apache.commons.collections.CollectionUtils;

public class ConceptSchemeReader extends MaintainableReader<ConceptSchemeImpl> {
    private final NameableReader nameableReader;

    public ConceptSchemeReader(VersionableReader versionableArtefact, NameableReader nameableReader) {
        super(versionableArtefact);
        this.nameableReader = nameableReader;
    }

    @Override
    protected ConceptSchemeImpl createMaintainableArtefact() {
        return new ConceptSchemeImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, ConceptSchemeImpl conceptScheme) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.IS_PARTIAL:
                conceptScheme.setPartial(ReaderUtils.getBooleanJsonField(parser));
                break;
            case StructureUtils.CONCEPTS:
                List<Concept> concepts = ReaderUtils.getArray(parser, this::getConcept);
                if (CollectionUtils.isNotEmpty(concepts)) {
                    conceptScheme.setItems(concepts);
                }
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "ConceptScheme: " + fieldName);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.CONCEPT_SCHEMES;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<ConceptSchemeImpl> artefacts) {
        artefact.getConceptSchemes().addAll(artefacts);
    }

    private Concept getConcept(JsonParser parser) {
        try {
            ConceptImpl concept = new ConceptImpl();
            while (!JsonToken.END_OBJECT.equals(parser.nextToken())) {
                ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
                String fieldName = parser.getCurrentName();
                switch (fieldName) {
                    case StructureUtils.ISOCONCEPTREFERENCE:
                        concept.setIsoConceptReference(getIsoConceptReference(parser));
                        break;
                    case StructureUtils.CORE_REPRESENTATION:
                        RepresentationReader reader = new RepresentationReader();
                        Representation representations = reader.getRepresentations(parser);
                        if (representations != null) {
                            concept.setCoreRepresentation(representations);
                        }
                        break;
                    default:
                        nameableReader.read(concept, parser);
                        break;
                }
            }
            return concept;
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    private IsoConceptReferenceImpl getIsoConceptReference(JsonParser parser) throws IOException {
        parser.nextToken();
        IsoConceptReferenceImpl isoConceptReference = new IsoConceptReferenceImpl();
        if (parser.currentToken()
            .equals(JsonToken.START_OBJECT) && parser.nextToken()
            .equals(JsonToken.END_OBJECT)) {
            return null;
        }
        while (!JsonToken.END_OBJECT.equals(parser.currentToken())) {
            ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String fieldName = parser.getCurrentName();
            switch (fieldName) {
                case StructureUtils.CONCEPT_SCHEME_ID:
                    String conceptSchemeId = ReaderUtils.getStringJsonField(parser);
                    if (conceptSchemeId != null) {
                        isoConceptReference.setSchemeId(conceptSchemeId);
                    }
                    parser.nextToken();
                    break;
                case StructureUtils.CONCEPT_ID:
                    String conceptId = ReaderUtils.getStringJsonField(parser);
                    if (conceptId != null) {
                        isoConceptReference.setConceptId(conceptId);
                    }
                    parser.nextToken();
                    break;
                case StructureUtils.CONCEPT_AGENCY:
                    String conceptAgency = ReaderUtils.getStringJsonField(parser);
                    if (conceptAgency != null) {
                        isoConceptReference.setAgency(conceptAgency);
                    }
                    parser.nextToken();
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "IsoConceptReference: " + fieldName);
            }
        }
        return isoConceptReference;
    }
}
