package com.epam.jsdmx.json20.structure.reader;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json20.structure.TestUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class BaseJsonReaderTest {

    @Spy
    VersionableReader versionableReader = MaintainableArtifactsTestUtils.getVersionableReader();

    @Spy
    OrganisationReader organisationReader = MaintainableArtifactsTestUtils.getOrganisationReader();

    @Spy
    NameableReader nameableReader = MaintainableArtifactsTestUtils.getNameableReader();

    @Spy
    AnnotableReader annotableReader = MaintainableArtifactsTestUtils.getAnnotableReader();

    @Spy
    private IdentifiableReader identifiableReader = MaintainableArtifactsTestUtils.getIdentifiableReader();

    @Spy
    private MemberSelectionReader memberSelectionReader = new MemberSelectionReader();

    private static Stream<Arguments> testArtefactReaders() {

        VersionableReader versionableReader = Mockito.spy(MaintainableArtifactsTestUtils.getVersionableReader());
        IdentifiableReader identifiableReader = Mockito.spy(MaintainableArtifactsTestUtils.getIdentifiableReader());
        NameableReader nameableReader = Mockito.spy(MaintainableArtifactsTestUtils.getNameableReader());
        AnnotableReader annotableReader = Mockito.spy(MaintainableArtifactsTestUtils.getAnnotableReader());
        OrganisationReader organisationReader = Mockito.spy(MaintainableArtifactsTestUtils.getOrganisationReader());
        MemberSelectionReader memberSelectionReader = Mockito.spy(MaintainableArtifactsTestUtils.getMemberSelectionReader());
        ReleaseCalendarReader releaseCalendarReader = Mockito.spy(MaintainableArtifactsTestUtils.getReleaseCalendarReader());
        AgencySchemeReader agencySchemeReader = new AgencySchemeReader(versionableReader, organisationReader);
        CategorySchemeMapReader categorySchemeMapReader = new CategorySchemeMapReader(versionableReader);
        ConceptSchemeMapReader conceptSchemeMapReader = new ConceptSchemeMapReader(versionableReader);
        DataConsumerSchemeReader dataConsumerSchemeReader = new DataConsumerSchemeReader(versionableReader, organisationReader);
        DataProviderSchemeReader dataProviderSchemeReader = new DataProviderSchemeReader(versionableReader, organisationReader);
        HierarchyAssociationReader hierarchyAssociationReader = new HierarchyAssociationReader(versionableReader);
        MetadataConstraintReader metadataConstraintReader = new MetadataConstraintReader(versionableReader, memberSelectionReader, releaseCalendarReader);
        MetadataProviderSchemeReader metadataProviderSchemeReader = new MetadataProviderSchemeReader(versionableReader, organisationReader);
        MetadataProvisionAgreementReader metadataProvisionAgreementReader = new MetadataProvisionAgreementReader(versionableReader);
        OrganisationSchemeMapReader organisationSchemeMapReader = new OrganisationSchemeMapReader(versionableReader);
        OrganisationUnitSchemeReader organisationUnitSchemeReader = new OrganisationUnitSchemeReader(versionableReader, organisationReader);
        ProcessReader processReader = new ProcessReader(versionableReader, nameableReader, annotableReader, identifiableReader);
        ProvisionAgreementReader provisionAgreementReader = new ProvisionAgreementReader(versionableReader);
        ReportingTaxonomyMapReader reportingTaxonomyMapReader = new ReportingTaxonomyMapReader(versionableReader);
        ReportingTaxonomyReader reportingTaxonomyReader = new ReportingTaxonomyReader(versionableReader, nameableReader);
        ValueListReader valueListReader = new ValueListReader(versionableReader, annotableReader);
        DataKeySetReader dataKeySetReader = new DataKeySetReader(annotableReader, memberSelectionReader);
        CubeRegionReader cubeRegionReader = new CubeRegionReader(memberSelectionReader, annotableReader);
        DataConstraintReader dataConstraintReader = new DataConstraintReader(versionableReader, releaseCalendarReader, dataKeySetReader, cubeRegionReader);
        CodelistExtensionReader codelistExtensionReader = new CodelistExtensionReader();
        GeoFeatureSetCodeReader geoFeatureSetCodeReader = new GeoFeatureSetCodeReader();
        GeographicCodelistReader geographicCodelistReader = new GeographicCodelistReader(
            versionableReader,
            nameableReader,
            codelistExtensionReader,
            geoFeatureSetCodeReader
        );
        GridCodeReader gridCodeReader = new GridCodeReader();
        GeoGridCodelistReader geoGridCodelistReader = new GeoGridCodelistReader(versionableReader, codelistExtensionReader, gridCodeReader, nameableReader);
        return Stream.of(
            Arguments.of(TestUtils.ORGANISATION_UNIT_SCHEME_JSON, organisationUnitSchemeReader, MaintainableArtifactsTestUtils.buildOrganisationUnitScheme()),
            Arguments.of(TestUtils.AGENCY_SCHEME_JSON, agencySchemeReader, MaintainableArtifactsTestUtils.buildAgencyScheme()),
            Arguments.of(TestUtils.CATEGORY_SCHEME_MAP_JSON, categorySchemeMapReader, MaintainableArtifactsTestUtils.buildCategorySchemeMap()),
            Arguments.of(TestUtils.CONCEPT_SCHEME_MAP_JSON, conceptSchemeMapReader, MaintainableArtifactsTestUtils.buildConceptSchemeMap()),
            Arguments.of(TestUtils.DATA_CONSUMER_SCHEME_JSON, dataConsumerSchemeReader, MaintainableArtifactsTestUtils.buildDataConsumerScheme()),
            Arguments.of(TestUtils.DATA_PROVIDER_SCHEME_JSON, dataProviderSchemeReader, MaintainableArtifactsTestUtils.buildDataProviderScheme()),
            Arguments.of(TestUtils.HIERARCHY_ASSOCIATION_JSON, hierarchyAssociationReader, MaintainableArtifactsTestUtils.buildHierarchyAssociation()),
            Arguments.of(TestUtils.METADATA_CONSTRAINT_JSON, metadataConstraintReader, MaintainableArtifactsTestUtils.buildMetadataConstraint(true)),
            Arguments.of(TestUtils.METADATA_PROVIDER_SCHEME_JSON, metadataProviderSchemeReader, MaintainableArtifactsTestUtils.buildMetadataProviderScheme()),
            Arguments.of(
                TestUtils.METADATA_PROVISION_AGREEMENT_JSON,
                metadataProvisionAgreementReader,
                MaintainableArtifactsTestUtils.buildMetadataProvisionAgreement()
            ),
            Arguments.of(TestUtils.ORGANISATION_SCHEME_MAP_JSON, organisationSchemeMapReader, MaintainableArtifactsTestUtils.buildOrganisationSchemeMap()),
            Arguments.of(TestUtils.PROCESS_JSON, processReader, MaintainableArtifactsTestUtils.buildProcess()),
            Arguments.of(TestUtils.PROVISION_AGREEMENT_JSON, provisionAgreementReader, MaintainableArtifactsTestUtils.buildProvisionAgreement()),
            Arguments.of(TestUtils.REPORTING_TAXONOMY_MAP_JSON, reportingTaxonomyMapReader, MaintainableArtifactsTestUtils.buildReportingTaxonomyMap()),
            Arguments.of(TestUtils.REPORTING_TAXONOMY_JSON, reportingTaxonomyReader, MaintainableArtifactsTestUtils.buildReportingTaxonomy()),
            Arguments.of(TestUtils.VALUE_LIST_JSON, valueListReader, MaintainableArtifactsTestUtils.buildValueList()),
            Arguments.of(TestUtils.DATA_CONSTRAINT_JSON, dataConstraintReader, MaintainableArtifactsTestUtils.buildDataConstraint()),
            Arguments.of(TestUtils.GEOCODELIST_JSON, geographicCodelistReader, MaintainableArtifactsTestUtils.buildGeographicCodelist()),
            Arguments.of(TestUtils.GEOGRIDCODELIST_JSON, geoGridCodelistReader, MaintainableArtifactsTestUtils.buildGeoGridCodelist())
        );
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    public JsonParser createParser(String resource) throws IOException {
        InputStream resourceAsStream = BaseJsonReaderTest.class.getResourceAsStream(resource);
        JsonFactory factory = new JsonFactory();
        return factory.createParser(resourceAsStream);
    }

    @ParameterizedTest
    @MethodSource("testArtefactReaders")
    <T extends MaintainableArtefactImpl> void readJson(String path,
                                                       MaintainableReader<T> reader,
                                                       T expected) throws IOException {
        JsonParser parser = createParser(path);
        T actual = (T) reader.readAndClose(parser);
        assertTrue(expected.deepEquals(actual));
    }
}
