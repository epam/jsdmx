package com.epam.jsdmx.xml21.structure.writer;

import static com.epam.jsdmx.xml21.structure.TestUtils.AGENCY_SCHEME_XML;
import static com.epam.jsdmx.xml21.structure.TestUtils.ALL_XML;
import static com.epam.jsdmx.xml21.structure.TestUtils.CATEGORISATION_XML;
import static com.epam.jsdmx.xml21.structure.TestUtils.CATEGORY_SCHEME_XML;
import static com.epam.jsdmx.xml21.structure.TestUtils.CODELIST_XML;
import static com.epam.jsdmx.xml21.structure.TestUtils.CONCEPT_SCHEME_XML;
import static com.epam.jsdmx.xml21.structure.TestUtils.DATAFLOW_XML;
import static com.epam.jsdmx.xml21.structure.TestUtils.DATA_CONSUMER_SCHEME_XML;
import static com.epam.jsdmx.xml21.structure.TestUtils.DATA_PROVIDER_SCHEME_XML;
import static com.epam.jsdmx.xml21.structure.TestUtils.DSD_XML;
import static com.epam.jsdmx.xml21.structure.TestUtils.HIERARCHIES_XML;
import static com.epam.jsdmx.xml21.structure.TestUtils.validateXMLSchema;

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
        CATEGORY_SCHEME_XML,
        AGENCY_SCHEME_XML,
        DATA_CONSUMER_SCHEME_XML,
        DATA_PROVIDER_SCHEME_XML,
        CONCEPT_SCHEME_XML,
        ALL_XML,
    })
    void xmlToXsd(String path) throws FileNotFoundException {
        File fileXsd = ResourceUtils.getFile(Objects.requireNonNull(this.getClass().getResource(XML_XSD_SCHEMAS_SDMXMESSAGE_XSD)));
        File fileXml = ResourceUtils.getFile(Objects.requireNonNull(this.getClass().getResource(path)));
        Assertions.assertTrue(validateXMLSchema(fileXsd, fileXml));
    }
}
