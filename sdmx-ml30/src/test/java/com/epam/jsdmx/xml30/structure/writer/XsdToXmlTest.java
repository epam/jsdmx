package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.TestUtils.AGENCY_SCHEME_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.ALL_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.CATEGORISATION_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.CATEGORY_SCHEME_MAP_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.CATEGORY_SCHEME_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.CODELIST_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.CONCEPT_SCHEME_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.DATAFLOW_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.DATA_CONSTRAINTS_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.DATA_CONSUMER_SCHEME_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.DATA_PROVIDER_SCHEME_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.DSD_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.HIERARCHIES_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.HIERARCHY_ASSOCIATION_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.METADATAFLOW_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.METADATA_CONSTRAINTS_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.METADATA_PROVIDER_SCHEME_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.METADATA_PROVISION_AGREEMENT_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.M_STRUCTURE_DEFINITION_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.ORGANISATION_SCHEME_MAP_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.ORGANISATION_UNIT_SCHEME_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.PROCESS_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.PROVISION_AGREEMENT_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.REPORTING_TAXONOMY_MAP_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.REPORTING_TAXONOMY_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.REPRESENTATION_MAP_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.STRUCTURE_MAP_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.VALUE_LIST_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.validateXMLSchema;

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
        CODELIST_XML,
        HIERARCHIES_XML,
        CONCEPT_SCHEME_XML,
        CATEGORISATION_XML,
        DSD_XML,
        DATAFLOW_XML,
        M_STRUCTURE_DEFINITION_XML,
        METADATAFLOW_XML,
        REPRESENTATION_MAP_XML,
        STRUCTURE_MAP_XML,
        CATEGORY_SCHEME_XML,
        AGENCY_SCHEME_XML,
        CATEGORY_SCHEME_MAP_XML,
        CONCEPT_SCHEME_XML,
        ALL_XML,
        DATA_CONSUMER_SCHEME_XML,
        DATA_PROVIDER_SCHEME_XML,
        METADATA_PROVIDER_SCHEME_XML,
        HIERARCHY_ASSOCIATION_XML,
        METADATA_CONSTRAINTS_XML,
        DATA_CONSTRAINTS_XML,
        METADATA_PROVISION_AGREEMENT_XML,
        ORGANISATION_SCHEME_MAP_XML,
        PROCESS_XML,
        PROVISION_AGREEMENT_XML,
        REPORTING_TAXONOMY_XML,
        REPORTING_TAXONOMY_MAP_XML,
        VALUE_LIST_XML,
        ORGANISATION_UNIT_SCHEME_XML
    })
    void xmlToXsd(String path) throws FileNotFoundException {
        File fileXsd = ResourceUtils.getFile(Objects.requireNonNull(this.getClass().getResource(XML_XSD_SCHEMAS_SDMXMESSAGE_XSD)));
        File fileXml = ResourceUtils.getFile(Objects.requireNonNull(this.getClass().getResource(path)));
        Assertions.assertTrue(validateXMLSchema(fileXsd, fileXml));
    }
}
