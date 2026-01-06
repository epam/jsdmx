package com.epam.jsdmx.xml30.structure.reader;

import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;

public final class XmlReaderFactory {

    private XmlReaderFactory() {
    }

    public static XmlStructureReader newInstance() {
        HeaderReader headerReader = new HeaderReader();
        return new XmlStructureReader(
            headerReader,
            XmlReaderFactory.readers()
        );
    }

    private static CodelistReader createCodelistReader() {
        return new CodelistReader(
            getAnnotableReader(),
            getNameableReader(),
            getCodelistExtensionReader(),
            getCodeImplReader()
        );
    }

    private static CodeImplReader getCodeImplReader() {
        return new CodeImplReader();
    }

    private static AgencySchemeReader createAgencySchemeReader() {
        return new AgencySchemeReader(
            getAnnotableReader(),
            getNameableReader(),
            getOrganisationReader()
        );
    }

    private static CategorisationReader createCategorisationReader() {
        return new CategorisationReader(getAnnotableReader(), getNameableReader());
    }

    private static CategorySchemeMapReader createCategorySchemeMapReader() {
        return new CategorySchemeMapReader(getAnnotableReader(), getNameableReader());
    }

    private static CategorySchemeReader createCategorySchemeReader() {
        return new CategorySchemeReader(getAnnotableReader(), getNameableReader());
    }

    private static ConceptSchemeMapReader createConceptSchemeMapReader() {
        return new ConceptSchemeMapReader(getAnnotableReader(), getNameableReader());
    }

    private static ConceptSchemeReader createConceptSchemeReader() {
        return new ConceptSchemeReader(
            getAnnotableReader(),
            getNameableReader(),
            getRepresentationReader()
        );
    }

    private static DataConsumerSchemeReader createDataConsumerSchemeReader() {
        return new DataConsumerSchemeReader(
            getAnnotableReader(),
            getNameableReader(),
            getOrganisationReader()
        );
    }

    private static DataflowReader createDataFlowReader() {
        return new DataflowReader(getAnnotableReader(), getNameableReader());
    }

    private static DataConstraintReader createDataConstraintReader() {
        return new DataConstraintReader(
            getAnnotableReader(),
            getNameableReader(),
            getReleaseCalendarReader(),
            getMemberSelectionReader()
        );
    }

    private static DataProviderSchemeReader createDataProviderSchemeReader() {
        return new DataProviderSchemeReader(
            getAnnotableReader(),
            getNameableReader(),
            getOrganisationReader()
        );
    }

    private static DataStructureDefinitionReader createDataStructureDefinitionReader() {

        AttributeListReader attributeListReader = new AttributeListReader(getRepresentationReader(), getAnnotableReader());
        DimensionListReader dimensionListReader = new DimensionListReader(getRepresentationReader(), getAnnotableReader());
        MeasureListReader measureListReader = new MeasureListReader(getRepresentationReader(), getAnnotableReader());

        return new DataStructureDefinitionReader(
            getAnnotableReader(),
            getNameableReader(),
            attributeListReader,
            dimensionListReader,
            measureListReader
        );
    }

    private static MetadataStructureDefinitionReader createMetadataStructureDefinitionReader() {
        return new MetadataStructureDefinitionReader(
            getAnnotableReader(),
            getNameableReader(),
            getRepresentationReader()
        );
    }

    private static HierarchyAssociationReader createHierarchyAssociationReader() {
        return new HierarchyAssociationReader(
            getAnnotableReader(),
            getNameableReader()
        );
    }

    private static HierarchyReader createHierarchyReader() {
        return new HierarchyReader(getAnnotableReader(), getNameableReader());
    }

    private static MetadataConstraintReader createMetadataConstraintReader() {
        return new MetadataConstraintReader(
            getAnnotableReader(),
            getNameableReader(),
            getMemberSelectionReader(),
            getReleaseCalendarReader()
        );
    }

    private static MetadataflowReader createMetadataflowReader() {
        return new MetadataflowReader(getAnnotableReader(), getNameableReader());
    }

    private static MetadataProviderSchemeReader createMetadataProviderSchemeReader() {
        return new MetadataProviderSchemeReader(
            getAnnotableReader(),
            getNameableReader(),
            getOrganisationReader()
        );
    }

    private static MetadataProvisionAgreementReader createMetadataProvisionAgreementReader() {
        return new MetadataProvisionAgreementReader(
            getAnnotableReader(),
            getNameableReader()
        );
    }

    private static OrganisationSchemeMapReader createOrganisationSchemeMapReader() {
        return new OrganisationSchemeMapReader(
            getAnnotableReader(),
            getNameableReader()
        );
    }

    private static OrganisationUnitSchemeReader createOrganisationUnitSchemeReader() {
        return new OrganisationUnitSchemeReader(
            getAnnotableReader(),
            getNameableReader(),
            getOrganisationReader()
        );
    }

    private static ProcessReader createProcessReader() {
        return new ProcessReader(
            getAnnotableReader(),
            getNameableReader()
        );
    }

    private static ProvisionAgreementReader createProvisionAgreementReader() {
        return new ProvisionAgreementReader(
            getAnnotableReader(),
            getNameableReader()
        );
    }

    private static ReportingTaxonomyReader createReportingTaxonomyReader() {
        return new ReportingTaxonomyReader(
            getAnnotableReader(),
            getNameableReader()
        );
    }

    private static ReportingTaxonomyMapReader createReportingTaxonomyMapReader() {
        return new ReportingTaxonomyMapReader(
            getAnnotableReader(),
            getNameableReader()
        );
    }

    private static RepresentationMapReader createRepresentationMapReader() {
        return new RepresentationMapReader(
            getAnnotableReader(),
            getNameableReader()
        );
    }

    private static StructureMapReader createStructureMapReader() {
        return new StructureMapReader(
            getAnnotableReader(),
            getNameableReader()
        );
    }

    private static ValueListReader createValueListReader() {
        return new ValueListReader(
            getAnnotableReader(),
            getNameableReader()
        );
    }

    private static GeographicCodelistReader createGeographicCodelistReader() {
        return new GeographicCodelistReader(
            getAnnotableReader(),
            getNameableReader(),
            getGeoFeatureSetCodeReader(),
            getCodelistExtensionReader()
        );
    }

    private static GeoGridCodelistReader createGeoGridCodelistReader() {
        return new GeoGridCodelistReader(
            getAnnotableReader(),
            getNameableReader(),
            getCodelistExtensionReader(),
            getGridCodeReader()
        );
    }

    private static RepresentationReader getRepresentationReader() {
        return new RepresentationReader(getNameableReader());
    }

    private static OrganisationReader getOrganisationReader() {
        return new OrganisationReader(
            getAnnotableReader(),
            getNameableReader()
        );
    }

    private static AnnotableReader getAnnotableReader() {
        return new AnnotableReader();
    }

    private static NameableReader getNameableReader() {
        return new NameableReader();
    }

    private static ReleaseCalendarReader getReleaseCalendarReader() {
        return new ReleaseCalendarReader();
    }

    private static MemberSelectionReader getMemberSelectionReader() {
        return new MemberSelectionReader();
    }

    private static GeoFeatureSetCodeReader getGeoFeatureSetCodeReader() {
        return new GeoFeatureSetCodeReader();
    }

    private static GridCodeReader getGridCodeReader() {
        return new GridCodeReader();
    }

    private static CodelistExtensionReader getCodelistExtensionReader() {
        return new CodelistExtensionReader();
    }

    private static List<? extends XmlReader<? extends MaintainableArtefactImpl>> readers() {
        return List.of(
            createAgencySchemeReader(),
            createCategorisationReader(),
            createCategorySchemeReader(),
            createCategorySchemeMapReader(),
            createCodelistReader(),
            createConceptSchemeReader(),
            createConceptSchemeMapReader(),
            createDataConstraintReader(),
            createDataConsumerSchemeReader(),
            createDataProviderSchemeReader(),
            createDataStructureDefinitionReader(),
            createDataFlowReader(),
            createHierarchyReader(),
            createHierarchyAssociationReader(),
            createMetadataConstraintReader(),
            createMetadataProviderSchemeReader(),
            createMetadataProvisionAgreementReader(),
            createMetadataStructureDefinitionReader(),
            createMetadataflowReader(),
            createOrganisationSchemeMapReader(),
            createOrganisationUnitSchemeReader(),
            createProcessReader(),
            createProvisionAgreementReader(),
            createReportingTaxonomyReader(),
            createReportingTaxonomyMapReader(),
            createRepresentationMapReader(),
            createStructureMapReader(),
            createValueListReader(),
            createGeographicCodelistReader(),
            createGeoGridCodelistReader()
        );
    }
}
