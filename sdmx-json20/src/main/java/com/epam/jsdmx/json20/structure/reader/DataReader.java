package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

public class DataReader {

    private final List<? extends MaintainableReader<? extends MaintainableArtefact>> readers;

    public DataReader(List<? extends MaintainableReader<? extends MaintainableArtefact>> readers) {
        this.readers = readers;
    }

    public ArtefactsImpl read(JsonParser parser) throws IOException {

        ArtefactsImpl artefacts = new ArtefactsImpl();
        if (JsonToken.START_OBJECT.equals(parser.currentToken()) && JsonToken.END_OBJECT.equals(parser.nextToken())) {
            return null;
        }
        while (!JsonToken.END_OBJECT.equals(parser.currentToken())) {
            ReaderUtils.checkIsNotEmptyObjectAndSkipUntilFieldName(parser);
            String currentParserName = parser.getCurrentName();
            switch (currentParserName) {
                case StructureUtils.STRUCTURE_MAP:
                case StructureUtils.CATEGORISATIONS:
                case StructureUtils.CATEGORY_SCHEMES:
                case StructureUtils.CODE_LISTS:
                case StructureUtils.CONCEPT_SCHEMES:
                case StructureUtils.DATA_STRUCTURES:
                case StructureUtils.METADATA_STRUCTURES:
                case StructureUtils.HIERARCHIES:
                case StructureUtils.REPRESENTATION_MAPS:
                case StructureUtils.DATAFLOWS:
                case StructureUtils.METADATAFLOWS:
                case StructureUtils.AGENCY_SCHEMES:
                case StructureUtils.CATEGORY_SCHEME_MAPS:
                case StructureUtils.CONCEPT_SCHEME_MAPS:
                case StructureUtils.DATA_CONSUMER_SCHEMES:
                case StructureUtils.DATA_PROVIDER_SCHEMES:
                case StructureUtils.HIERARCHY_ASSOCIATIONS:
                case StructureUtils.METADATA_CONSTRAINTS:
                case StructureUtils.METADATA_PROVIDER_SCHEMES:
                case StructureUtils.METADATA_PROVISION_AGREEMENTS:
                case StructureUtils.ORGANISATION_UNIT_SCHEMES:
                case StructureUtils.ORGANISATION_SCHEME_MAPS:
                case StructureUtils.PROCESSES:
                case StructureUtils.PROVISION_AGREEMENTS:
                case StructureUtils.REPORTING_TAXONOMY_MAPS:
                case StructureUtils.REPORTING_TAXONOMIES:
                case StructureUtils.VALUE_LISTS:
                case StructureUtils.GEOGRAPHIC_CODELISTS:
                case StructureUtils.GEO_GRID_CODELISTS:
                case StructureUtils.DATA_CONSTRAINTS:
                    Optional<? extends MaintainableReader<? extends MaintainableArtefact>> jsonReader = readers.stream()
                        .filter(read -> currentParserName.equals(read.getName()))
                        .findAny();
                    if (jsonReader.isPresent()) {
                        addArtefacts(parser, jsonReader.get(), artefacts);
                    }
                    break;
                default:
                    throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "Artefacts: " + currentParserName);
            }
            parser.nextToken();
        }
        return artefacts;
    }

    private <T extends MaintainableArtefactImpl> void addArtefacts(JsonParser parser,
                                                                   MaintainableReader<T> maintainableReader,
                                                                   ArtefactsImpl artefacts) throws IOException {
        List<T> artefactList = ReaderUtils.getArray(parser, artefactParser -> {
            try {
                T art = maintainableReader.read(artefactParser);
                maintainableReader.clean();
                return art;
            } catch (IOException e) {
                throw new JsonRuntimeException(e);
            }
        });
        maintainableReader.setArtefacts(artefacts, Set.copyOf(artefactList));
    }
}
