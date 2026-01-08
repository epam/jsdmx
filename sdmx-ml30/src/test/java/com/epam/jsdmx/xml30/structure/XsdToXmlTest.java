package com.epam.jsdmx.xml30.structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.util.ResourceUtils;

class XsdToXmlTest {

    public static final String XML_XSD_SCHEMAS_SDMXMESSAGE_XSD = "/xml/xsd/schemas/SDMXMessage.xsd";

    @ParameterizedTest
    @ValueSource(strings = {
        TestUtils.CODELIST_XML,
        TestUtils.HIERARCHIES_XML,
        TestUtils.CONCEPT_SCHEME_XML,
        TestUtils.CATEGORISATION_XML,
        TestUtils.DSD_XML,
        TestUtils.DATAFLOW_XML,
        TestUtils.M_STRUCTURE_DEFINITION_XML,
        TestUtils.METADATAFLOW_XML,
        TestUtils.REPRESENTATION_MAP_XML,
        TestUtils.STRUCTURE_MAP_XML,
        TestUtils.CATEGORY_SCHEME_XML,
        TestUtils.AGENCY_SCHEME_XML,
        TestUtils.CATEGORY_SCHEME_MAP_XML,
        TestUtils.CONCEPT_SCHEME_XML,
        TestUtils.ALL_XML,
        TestUtils.DATA_CONSUMER_SCHEME_XML,
        TestUtils.DATA_PROVIDER_SCHEME_XML,
        TestUtils.METADATA_PROVIDER_SCHEME_XML,
        TestUtils.HIERARCHY_ASSOCIATION_XML,
        TestUtils.METADATA_CONSTRAINTS_XML,
        TestUtils.DATA_CONSTRAINTS_XML,
        TestUtils.METADATA_PROVISION_AGREEMENT_XML,
        TestUtils.ORGANISATION_SCHEME_MAP_XML,
        TestUtils.PROCESS_XML,
        TestUtils.PROVISION_AGREEMENT_XML,
        TestUtils.REPORTING_TAXONOMY_XML,
        TestUtils.REPORTING_TAXONOMY_MAP_XML,
        TestUtils.VALUE_LIST_XML,
        TestUtils.ORGANISATION_UNIT_SCHEME_XML,
        TestUtils.GEOGRAPHICAL_CODELIST_XML,
        TestUtils.GEOGRID_CODELIST_XML
    })
    void xmlToXsd(String path) throws FileNotFoundException {
        File fileXsd = ResourceUtils.getFile(Objects.requireNonNull(this.getClass().getResource(XML_XSD_SCHEMAS_SDMXMESSAGE_XSD)));
        File fileXml = ResourceUtils.getFile(Objects.requireNonNull(this.getClass().getResource(path)));
        Assertions.assertTrue(TestUtils.validateXMLSchema(fileXsd, fileXml));
    }
}
