package com.epam.jsdmx.xml21.structure;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public final class TestUtils {

    public static final String CODELIST_XML = "/xml/codelist.xml";
    public static final String HIERARCHIES_XML = "/xml/hierarchy.xml";
    public static final String HIERARCHIES_NAMES_XML = "/xml/hierarchy_names.xml";
    public static final String CONCEPT_SCHEME_XML = "/xml/concept-scheme.xml";
    public static final String CATEGORISATION_XML = "/xml/categorisation.xml";
    public static final String CATEGORISATION_HIERARCHY_XML = "/xml/categorisation_with_hierarchy_ref.xml";
    public static final String DSD_XML = "/xml/dsd.xml";
    public static final String DATAFLOW_XML = "/xml/dataflow.xml";
    public static final String CATEGORY_SCHEME_XML = "/xml/category-scheme.xml";
    public static final String AGENCY_SCHEME_XML = "/xml/agency-scheme.xml";
    public static final String DATA_CONSUMER_SCHEME_XML = "/xml/data-consumer-scheme.xml";
    public static final String DATA_PROVIDER_SCHEME_XML = "/xml/data-provider-scheme.xml";
    public static final String DATA_CONSTRAINTS_XML = "/xml/data-constraints.xml";
    public static final String META_DATA_CONSTRAINTS_XML = "/xml/metadata-constraints.xml";

    public static final String ALL_XML = "/xml/all.xml";

    public static boolean validateXMLSchema(File xsdPath, File xmlPath) {

        try {
            SchemaFactory factory =
                SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(xsdPath);
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(xmlPath));
        } catch (IOException | SAXException e) {
            System.out.println("Exception: " + e.getMessage());
            return false;
        }
        return true;
    }

}
