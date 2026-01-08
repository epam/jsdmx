package com.epam.jsdmx.xml30.structure.writer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Party;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;
import com.epam.jsdmx.serializer.common.StubDataStructureLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultHeaderProvider;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultReferenceAdapter;
import com.epam.jsdmx.serializer.sdmx30.common.Header;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseXmlWriterTest {
    NameableWriter nameableWriter = new NameableWriter();
    AnnotableWriter annotableWriter = new AnnotableWriter();
    ReferenceAdapter referenceAdapter = new DefaultReferenceAdapter();
    UrnWriter urnWriter = new UrnWriter(referenceAdapter);
    CommonAttributesWriter commonAttributesWriter = new CommonAttributesWriter(urnWriter);
    LinksWriter linksWriter = new LinksWriter(urnWriter);

    ItemSchemeMapWriter itemSchemeMapWriter = new ItemSchemeMapWriter(urnWriter);

    ContactWriter contactWriter = new ContactWriter();
    ReleaseCalenderWriter releaseCalenderWriter = new ReleaseCalenderWriter();

    MemberSelectionWriter memberSelectionWriter = new MemberSelectionWriter();

    OrganisationWriter organisationWriter = new OrganisationWriter(contactWriter, annotableWriter, nameableWriter);

    RepresentationWriter representationWriter = new RepresentationWriter(urnWriter);

    DefaultHeaderProvider defaultHeaderProvider = mock(DefaultHeaderProvider.class);

    HeaderWriter headerWriter = new HeaderWriter(defaultHeaderProvider);

    @BeforeEach
    @SneakyThrows
    void setup() {
        Header header = new Header();
        header.setId("HED1");
        header.setName(new InternationalString("HeaderTest"));
        header.setTest(true);
        header.setReceivers(List.of(new Party("id", new InternationalString("str"))));
        header.setSender(new Party("id", new InternationalString()));
        header.setPrepared(Instant.parse("2023-04-17T10:11:16Z"));
        when(defaultHeaderProvider.provide()).thenReturn(header);
    }

    public CategorisationWriter createCategorisationWriter() {
        return new CategorisationWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            urnWriter
        );
    }

    public CodelistWriter createCodelistWriter() {
        return new CodelistWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            new CodeWriterImpl(urnWriter, nameableWriter, annotableWriter),
            new CodeListExtensionWriter(urnWriter)
        );
    }

    public GeographicCodelistWriter createGeographicCodelistWriter() {
        return new GeographicCodelistWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            new GeoFeatureSetCodeWriter(urnWriter, nameableWriter, annotableWriter),
            new CodeListExtensionWriter(urnWriter)
        );
    }

    public GeoGridCodelistWriter createGeoGridCodelistWriter() {
        return new GeoGridCodelistWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            new CodeListExtensionWriter(urnWriter),
            new GridCodeWriter(urnWriter, nameableWriter, annotableWriter)
        );
    }

    public ConceptSchemeWriter createConceptSchemeWriter() {
        return new ConceptSchemeWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            representationWriter
        );
    }

    public CategorySchemeWriter createCategorySchemeWriter() {
        return new CategorySchemeWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter
        );
    }

    public DataflowWriter createDataflowWriter() {
        return new DataflowWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter
        );
    }

    public DataStructureDefinitionWriter createDataStructureDefinitionWriter() {
        var attributeListWriter = new AttributeListWriter(annotableWriter, representationWriter);
        var dimensionListWriter = new DimensionListWriter(annotableWriter, representationWriter);
        var measureListWriter = new MeasureListWriter(annotableWriter, representationWriter);
        var groupDimensionListWriter = new GroupDimensionListWriter(annotableWriter);
        return new DataStructureDefinitionWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            attributeListWriter,
            dimensionListWriter,
            measureListWriter,
            groupDimensionListWriter,
            new StubDataStructureLocalRepresentationAdapter()
        );
    }

    public HierarchyWriter createHierarchyWriter() {
        return new HierarchyWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            urnWriter
        );
    }

    public MetadataflowWriter createMetadataflowWriter() {
        return new MetadataflowWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter
        );
    }

    public MetadataStructureDefinitionWriter createMetadataStructureDefinitionWriter() {
        return new MetadataStructureDefinitionWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            representationWriter
        );
    }

    public RepresentationMapWriter createRepresentationMapWriter() {
        return new RepresentationMapWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            urnWriter
        );
    }

    public StructureMapWriter createStructureMapWriter() {
        return new StructureMapWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter
        );
    }

    public AgencySchemeWriter createAgencySchemeWriter() {
        return new AgencySchemeWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            organisationWriter
        );
    }

    public HierarchyAssociationWriter createHierarchyAssociationWriter() {
        return new HierarchyAssociationWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter
        );
    }

    public CategorySchemeMapWriter createCategorySchemeMapWriter() {
        return new CategorySchemeMapWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            itemSchemeMapWriter
        );
    }

    public ConceptSchemeMapWriter createConceptSchemeMapWriter() {
        return new ConceptSchemeMapWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            itemSchemeMapWriter
        );
    }

    public DataConsumerSchemeWriter createDataConsumerSchemeWriter() {
        return new DataConsumerSchemeWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            organisationWriter
        );
    }

    public DataProviderSchemeWriter createDataProviderSchemeWriter() {
        return new DataProviderSchemeWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            organisationWriter
        );
    }

    public MetadataProviderSchemeWriter createMetadataProviderSchemeWriter() {
        return new MetadataProviderSchemeWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            organisationWriter
        );
    }

    public MetadataConstraintWriter createMetadataConstraintWriter() {
        return new MetadataConstraintWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            releaseCalenderWriter,
            memberSelectionWriter
        );
    }

    public MetadataProvisionAgreementWriter createMetadataProvisionAgreementWriter() {
        return new MetadataProvisionAgreementWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter
        );
    }

    public OrganisationSchemeMapWriter createOrganisationSchemeMapWriter() {
        return new OrganisationSchemeMapWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            itemSchemeMapWriter,
            urnWriter
        );
    }

    public OrganisationUnitSchemeWriter createOrganisationUnitSchemeWriter() {
        return new OrganisationUnitSchemeWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            organisationWriter
        );
    }

    public ProcessWriter createProcessWriter() {
        return new ProcessWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter
        );
    }

    public ProvisionAgreementWriter createProvisionAgreementWriter() {
        return new ProvisionAgreementWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter
        );
    }

    public ReportingTaxonomyWriter createReportingTaxonomyWriter() {
        return new ReportingTaxonomyWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter
        );
    }

    public ReportingTaxonomyMapWriter createReportingTaxonomyMapWriter() {
        return new ReportingTaxonomyMapWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            itemSchemeMapWriter
        );
    }

    public ValueListWriter createValueListWriter() {
        return new ValueListWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter
        );
    }

    public DataConstraintWriter createDataConstraintWriter() {
        return new DataConstraintWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            linksWriter,
            releaseCalenderWriter,
            memberSelectionWriter
        );
    }
}
