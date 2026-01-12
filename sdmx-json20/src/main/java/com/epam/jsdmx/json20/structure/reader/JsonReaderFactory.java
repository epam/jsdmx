package com.epam.jsdmx.json20.structure.reader;

import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;

public final class JsonReaderFactory {

    private JsonReaderFactory() {
    }

    public static DataReader newInstance() {
        return new DataReader(readers());
    }

    private static AnnotableReader getAnnotableReader() {
        return new AnnotableReader();
    }

    private static IdentifiableReader getIdentifiableReader() {
        return new IdentifiableReader(getAnnotableReader());
    }

    private static NameableReader getNameableReader() {
        return new NameableReader(getIdentifiableReader());
    }

    private static VersionableReader getVersionableReader() {
        return new VersionableReader(getNameableReader());
    }

    private static ContactReader getContactReader() {
        return new ContactReader();
    }

    private static OrganisationReader getOrganisationReader() {
        return new OrganisationReader(getContactReader(), getNameableReader());
    }

    private static MemberSelectionReader getMemberSelectionReader() {
        return new MemberSelectionReader();
    }

    private static AgencySchemeReader getAgencySchemeReader() {
        return new AgencySchemeReader(getVersionableReader(), getOrganisationReader());
    }

    private static CategorisationReader getCategorisationReader() {
        return new CategorisationReader(getVersionableReader());
    }

    private static CategorySchemeMapReader getCategorySchemeMapReader() {
        return new CategorySchemeMapReader(getVersionableReader());
    }

    private static CategorySchemeReader getCategorySchemeReader() {
        return new CategorySchemeReader(getVersionableReader(), getNameableReader());
    }

    private static CodelistReader getCodelistReader() {
        return new CodelistReader(getVersionableReader(), getNameableReader(), getCodelistExtensionReader(), getCodeImplReader());
    }

    private static CodeImplReader getCodeImplReader() {
        return new CodeImplReader();
    }

    private static CodelistExtensionReader getCodelistExtensionReader() {
        return new CodelistExtensionReader();
    }

    private static GeoFeatureSetCodeReader getGeoFeatureSetCodeReader() {
        return new GeoFeatureSetCodeReader();
    }

    private static GridCodeReader getGridCodeReader() {
        return new GridCodeReader();
    }

    private static ConceptSchemeMapReader getConceptSchemeMapReader() {
        return new ConceptSchemeMapReader(getVersionableReader());
    }

    private static ConceptSchemeReader getConceptSchemeReader() {
        return new ConceptSchemeReader(getVersionableReader(), getNameableReader());
    }

    private static DataConsumerSchemeReader getDataConsumerSchemeReader() {
        return new DataConsumerSchemeReader(getVersionableReader(), getOrganisationReader());
    }

    private static DataFlowReader getDataflowReader() {
        return new DataFlowReader(getVersionableReader());
    }

    private static DataProviderSchemeReader getDataProviderSchemeReader() {
        return new DataProviderSchemeReader(getVersionableReader(), getOrganisationReader());
    }

    private static DataStructureDefinitionReader getDataStructureDefinitionReader() {
        return new DataStructureDefinitionReader(
            getVersionableReader(),
            getIdentifiableReader()
        );
    }

    private static HierarchyAssociationReader getHierarchyAssociationReader() {
        return new HierarchyAssociationReader(getVersionableReader());
    }

    private static HierarchyReader getHierarchyReader() {
        return new HierarchyReader(getVersionableReader(), getIdentifiableReader(), getNameableReader());
    }

    private static MetadataConstraintReader getMetadataConstraintReader() {
        return new MetadataConstraintReader(getVersionableReader(), getMemberSelectionReader(), getReleaseCalendarReader());
    }

    private static ReleaseCalendarReader getReleaseCalendarReader() {
        return new ReleaseCalendarReader();
    }

    private static MetadataFlowReader getMetadataflowReader() {
        return new MetadataFlowReader(getVersionableReader());
    }

    private static MetadataProviderSchemeReader getMetadataProviderSchemeReader() {
        return new MetadataProviderSchemeReader(getVersionableReader(), getOrganisationReader());
    }

    private static MetadataProvisionAgreementReader getMetadataProvisionAgreementReader() {
        return new MetadataProvisionAgreementReader(getVersionableReader());
    }

    private static MetaDataStructureDefinitionReader getMetadataStructureDefinitionReader() {
        return new MetaDataStructureDefinitionReader(
            getVersionableReader(),
            getIdentifiableReader()
        );
    }

    private static OrganisationSchemeMapReader getOrganisationSchemeMapReader() {
        return new OrganisationSchemeMapReader(getVersionableReader());
    }

    private static OrganisationUnitSchemeReader getOrganisationUnitSchemeReader() {
        return new OrganisationUnitSchemeReader(getVersionableReader(), getOrganisationReader());
    }

    private static ProcessReader getProcessReader() {
        return new ProcessReader(
            getVersionableReader(),
            getNameableReader(),
            getAnnotableReader(),
            getIdentifiableReader()
        );
    }

    private static ProvisionAgreementReader getProvisionAgreementReader() {
        return new ProvisionAgreementReader(getVersionableReader());
    }

    private static ReportingTaxonomyReader getReportingTaxonomyReader() {
        return new ReportingTaxonomyReader(getVersionableReader(), getNameableReader());
    }

    private static ReportingTaxonomyMapReader getReportingTaxonomyMapReader() {
        return new ReportingTaxonomyMapReader(getVersionableReader());
    }

    private static RepresentationMapReader getRepresentationMapReader() {
        return new RepresentationMapReader(getVersionableReader());
    }

    private static StructureMapReader getStructureMapReader() {
        return new StructureMapReader(
            getVersionableReader(),
            getIdentifiableReader(),
            getAnnotableReader()
        );
    }

    private static ValueListReader getValueListReader() {
        return new ValueListReader(
            getVersionableReader(),
            getAnnotableReader()
        );
    }

    private static DataKeySetReader getDataKeySetReader() {
        return new DataKeySetReader(getAnnotableReader(), getMemberSelectionReader());
    }

    private static CubeRegionReader getCubeRegionReader() {
        return new CubeRegionReader(getMemberSelectionReader(), getAnnotableReader());
    }

    private static DataConstraintReader getDataConstraintsReader() {
        return new DataConstraintReader(getVersionableReader(), getReleaseCalendarReader(), getDataKeySetReader(), getCubeRegionReader());
    }

    private static GeographicCodelistReader getGeographicCodelistReader() {
        return new GeographicCodelistReader(
            getVersionableReader(),
            getNameableReader(),
            getCodelistExtensionReader(),
            getGeoFeatureSetCodeReader()
        );
    }

    private static GeoGridCodelistReader getGeoGridCodelistReader() {
        return new GeoGridCodelistReader(
            getVersionableReader(),
            getCodelistExtensionReader(),
            getGridCodeReader(),
            getNameableReader()
        );
    }

    private static List<? extends MaintainableReader<? extends MaintainableArtefactImpl>> readers() {
        return List.of(
            getAgencySchemeReader(),
            getCategorisationReader(),
            getCategorySchemeReader(),
            getCategorySchemeMapReader(),
            getCodelistReader(),
            getConceptSchemeReader(),
            getConceptSchemeMapReader(),
            getDataConstraintsReader(),
            getDataConsumerSchemeReader(),
            getDataProviderSchemeReader(),
            getDataStructureDefinitionReader(),
            getDataflowReader(),
            getHierarchyReader(),
            getHierarchyAssociationReader(),
            getMetadataConstraintReader(),
            getMetadataProviderSchemeReader(),
            getMetadataProvisionAgreementReader(),
            getMetadataStructureDefinitionReader(),
            getMetadataflowReader(),
            getOrganisationSchemeMapReader(),
            getOrganisationUnitSchemeReader(),
            getProcessReader(),
            getProvisionAgreementReader(),
            getReportingTaxonomyReader(),
            getReportingTaxonomyMapReader(),
            getRepresentationMapReader(),
            getStructureMapReader(),
            getValueListReader(),
            getGeographicCodelistReader(),
            getGeoGridCodelistReader()
        );
    }
}
