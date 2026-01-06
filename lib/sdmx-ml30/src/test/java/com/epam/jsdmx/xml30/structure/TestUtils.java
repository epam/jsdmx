package com.epam.jsdmx.xml30.structure;

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
    public static final String DSD_XML = "/xml/dsd.xml";
    public static final String DSD_WITH_ENUM_FORMAT_XML = "/xml/dsd_with_enumFormat.xml";
    public static final String DATAFLOW_XML = "/xml/dataflow.xml";
    public static final String M_STRUCTURE_DEFINITION_XML = "/xml/mdsd.xml";
    public static final String METADATAFLOW_XML = "/xml/metadataflow.xml";
    public static final String REPRESENTATION_MAP_XML = "/xml/representation_map.xml";
    public static final String STRUCTURE_MAP_XML = "/xml/structure_map.xml";
    public static final String CATEGORY_SCHEME_XML = "/xml/category-scheme.xml";
    public static final String AGENCY_SCHEME_XML = "/xml/agency-scheme.xml";
    public static final String CATEGORY_SCHEME_MAP_XML = "/xml/category-scheme-map.xml";
    public static final String CONCEPT_SCHEME_MAP_XML = "/xml/concept-scheme-map.xml";
    public static final String DATA_CONSUMER_SCHEME_XML = "/xml/data-consumer-scheme.xml";
    public static final String DATA_PROVIDER_SCHEME_XML = "/xml/data-provider-scheme.xml";
    public static final String METADATA_PROVIDER_SCHEME_XML = "/xml/metadata-provider-scheme.xml";
    public static final String HIERARCHY_ASSOCIATION_XML = "/xml/hierarchy-association.xml";
    public static final String METADATA_CONSTRAINTS_XML = "/xml/metadata-constraints.xml";
    public static final String DATA_CONSTRAINTS_XML = "/xml/data-constraints.xml";
    public static final String DATA_CONSTRAINTS2_XML = "/xml/data-constraints-skip-validTo.xml";
    public static final String DATA_CONSTRAINTS_EMPTY_XML = "/xml/data-constraints-empty.xml";
    public static final String METADATA_PROVISION_AGREEMENT_XML = "/xml/metadata-provision-agreement.xml";
    public static final String ORGANISATION_SCHEME_MAP_XML = "/xml/organisation-scheme-map.xml";
    public static final String PROCESS_XML = "/xml/process.xml";
    public static final String PROVISION_AGREEMENT_XML = "/xml/provision-agreement.xml";
    public static final String REPORTING_TAXONOMY_XML = "/xml/reporting-taxonomy.xml";
    public static final String REPORTING_TAXONOMY_MAP_XML = "/xml/reporting-taxonomy-map.xml";
    public static final String VALUE_LIST_XML = "/xml/valuelist.xml";
    public static final String ORGANISATION_UNIT_SCHEME_XML = "/xml/organisation-unit-scheme.xml";
    public static final String GEOGRAPHICAL_CODELIST_XML = "/xml/geographical-codelist.xml";
    public static final String GEOGRID_CODELIST_XML = "/xml/geogrid-codelist.xml";

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
