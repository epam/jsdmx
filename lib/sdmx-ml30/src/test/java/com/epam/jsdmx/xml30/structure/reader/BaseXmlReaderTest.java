package com.epam.jsdmx.xml30.structure.reader;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class BaseXmlReaderTest {

    AnnotableReader annotableReader = new AnnotableReader();
    NameableReader nameableReader = new NameableReader();
    HeaderReader headerReader = new HeaderReader();
    RepresentationReader representationReader = new RepresentationReader(nameableReader);

    AttributeListReader attributeListReader = new AttributeListReader(representationReader, annotableReader);
    DimensionListReader dimensionListReader = new DimensionListReader(representationReader, annotableReader);
    MeasureListReader measureListReader = new MeasureListReader(representationReader, annotableReader);
    MemberSelectionReader memberSelectionReader = new MemberSelectionReader();
    ReleaseCalendarReader releaseCalendarReader = new ReleaseCalendarReader();

    OrganisationReader organisationReader = new OrganisationReader(annotableReader, nameableReader);

    public XMLStreamReader createXmlStreamReaderInstance(String resourcePath, boolean minifyXml) throws XMLStreamException, IOException {
        XMLInputFactory xmlInFact = XMLInputFactory.newInstance();
        return xmlInFact.createXMLStreamReader(createXmlInputSteam(resourcePath, minifyXml));
    }

    private InputStream createXmlInputSteam(String resourcePath, boolean minifyXml) throws IOException {
        InputStream is = this.getClass().getResourceAsStream(resourcePath);
        if (minifyXml) {
            try (is) {
                String xml = minifyXml(new String(is.readAllBytes(), StandardCharsets.UTF_8));
                return new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
            }
        }

        return is;
    }

    private String minifyXml(String xml) {
        return xml.replaceAll("[\\n\\r]", "")
            .replaceAll(">\\s+<", "><");
    }

    public CodelistReader createCodelistReader() {
        return new CodelistReader(annotableReader, nameableReader, new CodelistExtensionReader(), new CodeImplReader());
    }

    public AgencySchemeReader createAgencySchemeReader() {
        return new AgencySchemeReader(
            annotableReader,
            nameableReader,
            organisationReader
        );
    }

    public CategorisationReader createCategorisationReader() {
        return new CategorisationReader(annotableReader, nameableReader);
    }

    public CategorySchemeMapReader createCategorySchemeMapReader() {
        return new CategorySchemeMapReader(annotableReader, nameableReader);
    }

    public CategorySchemeReader createCategorySchemeReader() {
        return new CategorySchemeReader(annotableReader, nameableReader);
    }

    public ConceptSchemeMapReader createConceptSchemeMapReader() {
        return new ConceptSchemeMapReader(annotableReader, nameableReader);
    }

    public ConceptSchemeReader createConceptSchemeReader() {
        return new ConceptSchemeReader(
            annotableReader,
            nameableReader,
            representationReader
        );
    }

    public DataConsumerSchemeReader createDataConsumerSchemeReader() {
        return new DataConsumerSchemeReader(
            annotableReader,
            nameableReader,
            organisationReader
        );
    }

    public DataflowReader createDataFlowReader() {
        return new DataflowReader(annotableReader, nameableReader);
    }

    public DataConstraintReader createDataConstraintReader() {
        return new DataConstraintReader(
            annotableReader,
            nameableReader,
            releaseCalendarReader,
            memberSelectionReader
        );
    }

    public DataProviderSchemeReader createDataProviderSchemeReader() {
        return new DataProviderSchemeReader(
            annotableReader,
            nameableReader,
            organisationReader
        );
    }

    public DataStructureDefinitionReader createDataStructureDefinitionReader() {
        return new DataStructureDefinitionReader(
            annotableReader,
            nameableReader,
            attributeListReader,
            dimensionListReader,
            measureListReader
        );
    }

    public MetadataStructureDefinitionReader createMetadataStructureDefinitionReader() {
        return new MetadataStructureDefinitionReader(
            annotableReader,
            nameableReader,
            representationReader
        );
    }

    public HierarchyAssociationReader createHierarchyAssociationReader() {
        return new HierarchyAssociationReader(annotableReader, nameableReader);
    }

    public HierarchyReader createHierarchyReader() {
        return new HierarchyReader(annotableReader, nameableReader);
    }

    public MetadataConstraintReader createMetadataConstraintReader() {
        return new MetadataConstraintReader(
            annotableReader,
            nameableReader,
            memberSelectionReader,
            releaseCalendarReader
        );
    }

    public MetadataflowReader createMetadataflowReader() {
        return new MetadataflowReader(annotableReader, nameableReader);
    }

    public MetadataProviderSchemeReader createMetadataProviderSchemeReader() {
        return new MetadataProviderSchemeReader(
            annotableReader,
            nameableReader,
            organisationReader
        );
    }

    public MetadataProvisionAgreementReader createMetadataProvisionAgreementReader() {
        return new MetadataProvisionAgreementReader(annotableReader, nameableReader);
    }

    public OrganisationSchemeMapReader createOrganisationSchemeMapReader() {
        return new OrganisationSchemeMapReader(
            annotableReader,
            nameableReader
        );
    }

    public OrganisationUnitSchemeReader createOrganisationUnitSchemeReader() {
        return new OrganisationUnitSchemeReader(
            annotableReader,
            nameableReader,
            organisationReader
        );
    }

    public ProcessReader createProcessReader() {
        return new ProcessReader(
            annotableReader,
            nameableReader
        );
    }

    public ProvisionAgreementReader createProvisionAgreementReader() {
        return new ProvisionAgreementReader(
            annotableReader,
            nameableReader
        );
    }

    public ReportingTaxonomyReader createReportingTaxonomyReader() {
        return new ReportingTaxonomyReader(
            annotableReader,
            nameableReader
        );
    }

    public ReportingTaxonomyMapReader createReportingTaxonomyMapReader() {
        return new ReportingTaxonomyMapReader(
            annotableReader,
            nameableReader
        );
    }

    public RepresentationMapReader createRepresentationMapReader() {
        return new RepresentationMapReader(
            annotableReader,
            nameableReader
        );
    }

    public StructureMapReader createStructureMapReader() {
        return new StructureMapReader(
            annotableReader,
            nameableReader
        );
    }

    public ValueListReader createValueListReader() {
        return new ValueListReader(
            annotableReader,
            nameableReader
        );
    }

    public GeographicCodelistReader createGeographicCodelistReader() {
        return new GeographicCodelistReader(
            annotableReader,
            nameableReader,
            new GeoFeatureSetCodeReader(),
            new CodelistExtensionReader()
        );
    }

    public GeoGridCodelistReader createGeoGridCodelistReader() {
        return new GeoGridCodelistReader(
            annotableReader,
            nameableReader,
            new CodelistExtensionReader(),
            new GridCodeReader()
        );
    }
}