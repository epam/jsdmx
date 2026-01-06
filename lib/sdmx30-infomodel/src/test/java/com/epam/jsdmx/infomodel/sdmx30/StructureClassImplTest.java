package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class StructureClassImplTest {

    @MethodSource
    @ParameterizedTest
    void testFindByName(String structureClass) {
        assertThat(StructureClassImpl.findByName(structureClass)).isNotNull();
    }

    private static Stream<Arguments> testFindByName() {
        return Stream.of(
            Arguments.of("AGENCY"),
            Arguments.of("AGENCY_SCHEME"),
            Arguments.of("DATA_CONSUMER"),
            Arguments.of("DATA_CONSUMER_SCHEME"),
            Arguments.of("DATA_PROVIDER"),
            Arguments.of("DATA_PROVIDER_SCHEME"),
            Arguments.of("METADATA_PROVIDER"),
            Arguments.of("METADATA_PROVIDER_SCHEME"),
            Arguments.of("ORGANISATION_UNIT"),
            Arguments.of("ORGANISATION_UNIT_SCHEME"),
            Arguments.of("ATTRIBUTE_DESCRIPTOR"),
            Arguments.of("DATA_ATTRIBUTE"),
            Arguments.of("DATAFLOW"),
            Arguments.of("DATA_STRUCTURE"),
            Arguments.of("DIMENSION"),
            Arguments.of("DIMENSION_DESCRIPTOR"),
            Arguments.of("GROUP_DIMENSION_DESCRIPTOR"),
            Arguments.of("MEASURE"),
            Arguments.of("MEASURE_DESCRIPTOR"),
            Arguments.of("TIME_DIMENSION"),
            Arguments.of("METADATA_SET"),
            Arguments.of("METADATA_ATTRIBUTE"),
            Arguments.of("METADATA_ATTRIBUTE_DESCRIPTOR"),
            Arguments.of("METADATA_STRUCTURE"),
            Arguments.of("METADATAFLOW"),
            Arguments.of("METADATA_SET"),
            Arguments.of("PROCESS"),
            Arguments.of("PROCESS_STEP"),
            Arguments.of("TRANSITION"),
            Arguments.of("DATA_CONSTRAINT"),
            Arguments.of("METADATA_CONSTRAINT"),
            Arguments.of("METADATA_PROVISION_AGREEMENT"),
            Arguments.of("PROVISION_AGREEMENT"),
            Arguments.of("SUBSCRIPTION"),
            Arguments.of("CATEGORY_SCHEME_MAP"),
            Arguments.of("CONCEPT_SCHEME_MAP"),
            Arguments.of("ORGANISATION_SCHEME_MAP"),
            Arguments.of("REPORTING_TAXONOMY_MAP"),
            Arguments.of("REPRESENTATION_MAP"),
            Arguments.of("STRUCTURE_MAP"),
            Arguments.of("DATE_PATTERN_MAP"),
            Arguments.of("FREQUENCY_FORMAT_MAPPING"),
            Arguments.of("EPOCH_MAP"),
            Arguments.of("CODELIST"),
            Arguments.of("CODE"),
            Arguments.of("GEO_GRID_CODELIST"),
            Arguments.of("GEOGRAPHIC_CODELIST"),
            Arguments.of("HIERARCHICAL_CODE"),
            Arguments.of("HIERARCHY"),
            Arguments.of("HIERARCHY_ASSOCIATION"),
            Arguments.of("LEVEL"),
            Arguments.of("VALUE_LIST"),
            Arguments.of("CATEGORISATION"),
            Arguments.of("CATEGORY"),
            Arguments.of("CATEGORY_SCHEME"),
            Arguments.of("REPORTING_CATEGORY"),
            Arguments.of("REPORTING_TAXONOMY"),
            Arguments.of("CONCEPT_SCHEME"),
            Arguments.of("CONCEPT"),
            Arguments.of("CUSTOM_TYPE"),
            Arguments.of("CUSTOM_TYPE_SCHEME"),
            Arguments.of("NAME_PERSONALISATION"),
            Arguments.of("NAME_PERSONALISATION_SCHEME"),
            Arguments.of("RULESET"),
            Arguments.of("RULESET_SCHEME"),
            Arguments.of("USER_DEFINED_OPERATOR"),
            Arguments.of("USER_DEFINED_OPERATOR_SCHEME"),
            Arguments.of("VLT_CODELIST_MAPPING"),
            Arguments.of("VLT_CONCEPT_MAPPING"),
            Arguments.of("VLT_DATAFLOW_MAPPING"),
            Arguments.of("VLT_MAPPING_SCHEME")
        );
    }

}