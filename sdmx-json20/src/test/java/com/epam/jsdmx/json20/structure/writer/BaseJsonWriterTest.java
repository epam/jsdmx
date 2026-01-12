package com.epam.jsdmx.json20.structure.writer;

import static com.epam.jsdmx.json20.structure.TestUtils.AGENCY_SCHEME_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.CATEGORY_SCHEME_MAP_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.CONCEPT_SCHEME_MAP_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.DATA_CONSUMER_SCHEME_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.DATA_PROVIDER_SCHEME_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.GEOCODELIST_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.GEOGRIDCODELIST_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.HIERARCHY_ASSOCIATION_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.METADATA_CONSTRAINT_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.METADATA_PROVIDER_SCHEME_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.METADATA_PROVISION_AGREEMENT_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.ORGANISATION_SCHEME_MAP_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.ORGANISATION_UNIT_SCHEME_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.PROCESS_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.PROVISION_AGREEMENT_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.REPORTING_TAXONOMY_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.REPORTING_TAXONOMY_MAP_JSON;
import static com.epam.jsdmx.json20.structure.TestUtils.VALUE_LIST_JSON;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;
import com.epam.jsdmx.serializer.common.TimeDimensionLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultReferenceAdapter;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class BaseJsonWriterTest {

    @Spy
    private ReferenceAdapter referenceAdapter = MaintainableArtifactsTestUtils.getUrnAdapter();
    @Spy
    private TimeDimensionLocalRepresentationAdapter dsdAdapter = MaintainableArtifactsTestUtils.getDsdAdapter();
    @Spy
    private RepresentationWriter representationWriter = MaintainableArtifactsTestUtils.getRepresentationWriter();
    @Spy
    private MemberSelectionWriter memberSelectionWriter = MaintainableArtifactsTestUtils.getMemberSelectionWriter();
    @Spy
    private VersionableWriter versionableWriterSpy = MaintainableArtifactsTestUtils.getVersionableWriter();
    @Spy
    private LinksWriter linksWriterSpy = MaintainableArtifactsTestUtils.getLinksWriter();
    @Spy
    private OrganisationWriter organisationWriter = MaintainableArtifactsTestUtils.getOrganisationWriter();
    @Spy
    private AnnotableWriter annotableWriter = MaintainableArtifactsTestUtils.getAnnotableWriter();
    @Spy
    private NameableWriter nameableWriter = MaintainableArtifactsTestUtils.getNameableWriter();
    @Spy
    private CodeWriterImpl codeWriter = MaintainableArtifactsTestUtils.getCodeImplWriter();
    @Spy
    private CodelistExtensionWriter extensionWriter = MaintainableArtifactsTestUtils.getCodelistExtensionWriter();
    @Spy
    private IdentifiableWriter identifiableWriter = MaintainableArtifactsTestUtils.getIdentifiableWriter();

    private static Stream<Arguments> testArtefactWriters() {

        ReferenceAdapter referenceAdapter = new DefaultReferenceAdapter();
        ItemMapWriter itemMapWriter = new ItemMapWriter(referenceAdapter);
        LinksWriter linksWriter = new LinksWriter(referenceAdapter);
        AnnotableWriter annotableWriter = new AnnotableWriter(linksWriter);
        IdentifiableWriter identifiableWriter = new IdentifiableWriter(annotableWriter);
        NameableWriter nameableWriter = new NameableWriter(identifiableWriter);
        VersionableWriter versionableWriter = new VersionableWriter(nameableWriter);
        ContactsWriter contactsWriter = new ContactsWriter();
        OrganisationWriter organisationWriter = new OrganisationWriter(contactsWriter, nameableWriter);
        DateTimeWriter dateTimeWriter = new DateTimeWriter();
        MemberSelectionWriter memberSelectionWriter = new MemberSelectionWriter(dateTimeWriter);
        AgencySchemeWriter agencySchemeWriter = new AgencySchemeWriter(versionableWriter, linksWriter, organisationWriter);
        CategorySchemeMapWriter categorySchemeMapWriter = new CategorySchemeMapWriter(versionableWriter, linksWriter, annotableWriter, itemMapWriter);
        ConceptSchemeMapWriter conceptSchemeMapWriter = new ConceptSchemeMapWriter(versionableWriter, linksWriter, annotableWriter, itemMapWriter);
        DataConsumerSchemeWriter dataConsumerSchemeWriter = new DataConsumerSchemeWriter(versionableWriter, linksWriter, organisationWriter);
        DataProviderSchemeWriter dataProviderSchemeWriter = new DataProviderSchemeWriter(versionableWriter, linksWriter, organisationWriter);
        HierarchyAssociationWriter hierarchyAssociationWriter = new HierarchyAssociationWriter(versionableWriter, linksWriter);
        MetadataConstraintWriter metadataConstraintWriter = new MetadataConstraintWriter(
            versionableWriter,
            linksWriter,
            memberSelectionWriter,
            referenceAdapter
        );
        MetadataProviderSchemeWriter metadataProviderSchemeWriter = new MetadataProviderSchemeWriter(versionableWriter, linksWriter, organisationWriter);
        MetadataProvisionAgreementWriter metadataProvisionAgreementWriter = new MetadataProvisionAgreementWriter(
            versionableWriter,
            linksWriter,
            referenceAdapter
        );
        OrganisationSchemeMapWriter organisationSchemeMapWriter = new OrganisationSchemeMapWriter(
            versionableWriter,
            linksWriter,
            annotableWriter,
            itemMapWriter
        );
        ProcessWriter processWriter = new ProcessWriter(versionableWriter, linksWriter, nameableWriter, annotableWriter, identifiableWriter);
        ProvisionAgreementWriter provisionAgreementWriter = new ProvisionAgreementWriter(versionableWriter, linksWriter, referenceAdapter);
        ReportingTaxonomyMapWriter reportingTaxonomyMapWriter = new ReportingTaxonomyMapWriter(versionableWriter, linksWriter, annotableWriter, itemMapWriter);
        ReportingTaxonomyWriter reportingTaxonomyWriter = new ReportingTaxonomyWriter(versionableWriter, linksWriter, nameableWriter, referenceAdapter);
        ValueListWriter valueListWriter = new ValueListWriter(versionableWriter, linksWriter, annotableWriter);
        OrganisationUnitSchemeWriter organisationUnitSchemeWriter = new OrganisationUnitSchemeWriter(versionableWriter, linksWriter, organisationWriter);
        GeoFeatureSetCodeWriter codeWriter = new GeoFeatureSetCodeWriter();
        CodelistExtensionWriter codelistExtensionWriter = new CodelistExtensionWriter(referenceAdapter);
        GeographicCodelistWriter geographicCodelistWriter = new GeographicCodelistWriter(
            versionableWriter,
            linksWriter,
            codeWriter,
            codelistExtensionWriter,
            nameableWriter
        );
        GridCodeWriter gridCodeWriter = new GridCodeWriter();
        GeoGridCodelistWriter geoGridCodelistWriter = new GeoGridCodelistWriter(
            versionableWriter,
            linksWriter,
            codelistExtensionWriter,
            gridCodeWriter,
            nameableWriter
        );

        return Stream.of(
            Arguments.of(
                ORGANISATION_UNIT_SCHEME_JSON,
                organisationUnitSchemeWriter,
                MaintainableArtifactsTestUtils.buildOrganisationUnitScheme()
            ),
            Arguments.of(AGENCY_SCHEME_JSON, agencySchemeWriter, MaintainableArtifactsTestUtils.buildAgencyScheme()),
            Arguments.of(CATEGORY_SCHEME_MAP_JSON, categorySchemeMapWriter, MaintainableArtifactsTestUtils.buildCategorySchemeMap()),
            Arguments.of(CONCEPT_SCHEME_MAP_JSON, conceptSchemeMapWriter, MaintainableArtifactsTestUtils.buildConceptSchemeMap()),
            Arguments.of(DATA_CONSUMER_SCHEME_JSON, dataConsumerSchemeWriter, MaintainableArtifactsTestUtils.buildDataConsumerScheme()),
            Arguments.of(DATA_PROVIDER_SCHEME_JSON, dataProviderSchemeWriter, MaintainableArtifactsTestUtils.buildDataProviderScheme()),
            Arguments.of(HIERARCHY_ASSOCIATION_JSON, hierarchyAssociationWriter, MaintainableArtifactsTestUtils.buildHierarchyAssociation()),
            Arguments.of(METADATA_CONSTRAINT_JSON, metadataConstraintWriter, MaintainableArtifactsTestUtils.buildMetadataConstraint(true)),
            Arguments.of(METADATA_PROVIDER_SCHEME_JSON, metadataProviderSchemeWriter, MaintainableArtifactsTestUtils.buildMetadataProviderScheme()),
            Arguments.of(METADATA_PROVISION_AGREEMENT_JSON, metadataProvisionAgreementWriter, MaintainableArtifactsTestUtils.buildMetadataProvisionAgreement()),
            Arguments.of(ORGANISATION_SCHEME_MAP_JSON, organisationSchemeMapWriter, MaintainableArtifactsTestUtils.buildOrganisationSchemeMap()),
            Arguments.of(PROCESS_JSON, processWriter, MaintainableArtifactsTestUtils.buildProcess()),
            Arguments.of(PROVISION_AGREEMENT_JSON, provisionAgreementWriter, MaintainableArtifactsTestUtils.buildProvisionAgreement()),
            Arguments.of(REPORTING_TAXONOMY_MAP_JSON, reportingTaxonomyMapWriter, MaintainableArtifactsTestUtils.buildReportingTaxonomyMap()),
            Arguments.of(REPORTING_TAXONOMY_JSON, reportingTaxonomyWriter, MaintainableArtifactsTestUtils.buildReportingTaxonomy()),
            Arguments.of(VALUE_LIST_JSON, valueListWriter, MaintainableArtifactsTestUtils.buildValueList()),
            Arguments.of(GEOCODELIST_JSON, geographicCodelistWriter, MaintainableArtifactsTestUtils.buildGeographicCodelist()),
            Arguments.of(GEOGRIDCODELIST_JSON, geoGridCodelistWriter, MaintainableArtifactsTestUtils.buildGeoGridCodelist())
        );
    }

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    public JsonGenerator createJsonGenerator(ByteArrayOutputStream stream) throws IOException {
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
        return jGenerator;
    }

    @ParameterizedTest
    @MethodSource("testArtefactWriters")
    <T extends MaintainableArtefactImpl> void writeToJson(String path, MaintainableWriter<T> writer, T artefact) throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        //when
        writer.writeAndClose(createJsonGenerator(stream), artefact);
        String actual = stream.toString(StandardCharsets.UTF_8);

        //then
        String expected = IOUtils.resourceToString(path, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);
    }
}
