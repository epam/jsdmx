package com.epam.jsdmx.json20.structure.writer;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Locale.LanguageRange;
import java.util.Map;

import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;
import com.epam.jsdmx.serializer.common.StubDataStructureLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.common.TimeDimensionLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultHeaderProvider;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultReferenceAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonWriterFactory {

    private final ObjectMapper mapper;
    private final List<Locale.LanguageRange> languagePriorities;
    private final ReferenceAdapter referenceAdapter;
    private final TimeDimensionLocalRepresentationAdapter dsdAdapter;

    private JsonWriterFactory() {
        this(new ObjectMapper(), List.of(), new DefaultReferenceAdapter(), new StubDataStructureLocalRepresentationAdapter());
    }

    public JsonWriterFactory(ObjectMapper mapper,
                             List<LanguageRange> languagePriorities,
                             TimeDimensionLocalRepresentationAdapter timeDimensionLocalRepresentationAdapter) {
        this(mapper, languagePriorities, new DefaultReferenceAdapter(), timeDimensionLocalRepresentationAdapter);
    }

    public JsonWriterFactory(ObjectMapper mapper,
                             List<Locale.LanguageRange> languagePriorities,
                             ReferenceAdapter referenceAdapter,
                             TimeDimensionLocalRepresentationAdapter timeDimensionLocalRepresentationAdapter) {
        this.mapper = mapper;
        this.languagePriorities = languagePriorities;
        this.referenceAdapter = referenceAdapter;
        this.dsdAdapter = timeDimensionLocalRepresentationAdapter;
    }

    public JsonStructureWriter newInstance(OutputStream outputStream) {
        final var metaWriter = new MetaWriter(languagePriorities, new DefaultHeaderProvider());
        final var structureWriters = writers();

        return new JsonStructureWriter(
            mapper,
            outputStream,
            metaWriter,
            structureWriters
        );
    }

    public JsonStructureWriter newInstance(OutputStream outputStream,
                                           Map<StructureClass, ? extends MaintainableWriter<? extends MaintainableArtefact>> customStructureWriters) {
        final var metaWriter = new MetaWriter(languagePriorities, new DefaultHeaderProvider());
        final var defaultStructureWriters = writers();
        final List<MaintainableWriter<? extends MaintainableArtefact>> structureWriters = new ArrayList<>();

        for (var writer : defaultStructureWriters) {
            writer.getWritableArtefactStructureClass()
                .filter(customStructureWriters::containsKey)
                .map(customStructureWriters::get)
                .ifPresentOrElse(structureWriters::add, () -> structureWriters.add(writer));
        }

        return new JsonStructureWriter(
            mapper,
            outputStream,
            metaWriter,
            structureWriters
        );
    }

    private ReferenceAdapter getUrnAdapter() {
        return referenceAdapter;
    }

    private TimeDimensionLocalRepresentationAdapter getDsdAdapter() {
        return dsdAdapter;
    }

    private RepresentationWriter getRepresentationWriter() {
        return new RepresentationWriter(getUrnAdapter());
    }

    private LinksWriter getLinksWriter() {
        return new LinksWriter(getUrnAdapter());
    }

    private AnnotableWriter getAnnotableWriter() {
        return new AnnotableWriter(getLinksWriter());
    }

    private IdentifiableWriter getIdentifiableWriter() {
        return new IdentifiableWriter(getAnnotableWriter());
    }

    private NameableWriter getNameableWriter() {
        return new NameableWriter(getIdentifiableWriter());
    }

    private VersionableWriter getVersionableWriter() {
        return new VersionableWriter(getNameableWriter());
    }

    private ComponentWriter getComponentWriter() {
        return new ComponentWriter(getIdentifiableWriter(), getRepresentationWriter(), getUrnAdapter());
    }

    private ConceptRoleWriter getConceptRoleWriter() {
        return new ConceptRoleWriter(getUrnAdapter());
    }

    private DateTimeWriter getDateTimeWriter() {
        return new DateTimeWriter();
    }

    private DataKeySetsWriter getDataKeySetsWriter() {
        return new DataKeySetsWriter(getAnnotableWriter(), getMemberSelectionWriter(), getDateTimeWriter());
    }

    private AttributeListWriter getAttributeListWriter() {
        return new AttributeListWriter(getAnnotableWriter(), getConceptRoleWriter(), getComponentWriter());
    }

    private DimensionListWriter getDimensionListWriter() {
        return new DimensionListWriter(getAnnotableWriter(), getConceptRoleWriter(), getComponentWriter());
    }

    private GroupDimensionListWriter getGroupDimensionListWriter() {
        return new GroupDimensionListWriter(getIdentifiableWriter());
    }

    private MeasureListWriter getMeasureListWriter() {
        return new MeasureListWriter(getAnnotableWriter(), getConceptRoleWriter(), getComponentWriter());
    }

    private CodeWriterImpl getCodeImplWriter() {
        return new CodeWriterImpl();
    }

    private GeoFeatureSetCodeWriter getGeoFeatureSetCodeWriter() {
        return new GeoFeatureSetCodeWriter();
    }

    private GridCodeWriter getGridCodeWriter() {
        return new GridCodeWriter();
    }

    private CodelistExtensionWriter getCodelistExtensionWriter() {
        return new CodelistExtensionWriter(getUrnAdapter());
    }

    private CubeRegionWriter getCubeRegionWriter() {
        return new CubeRegionWriter(getMemberSelectionWriter(), getDateTimeWriter(), getAnnotableWriter());
    }

    private ContactsWriter getContactsWriter() {
        return new ContactsWriter();
    }

    private OrganisationWriter getOrganisationWriter() {
        return new OrganisationWriter(getContactsWriter(), getNameableWriter());
    }

    private MemberSelectionWriter getMemberSelectionWriter() {
        return new MemberSelectionWriter(getDateTimeWriter());
    }

    private AgencySchemeWriter getAgencySchemeWriter() {
        return new AgencySchemeWriter(getVersionableWriter(), getLinksWriter(), getOrganisationWriter());
    }

    private CategorisationWriter getCategorisationWriter() {
        return new CategorisationWriter(getVersionableWriter(), getLinksWriter(), getUrnAdapter());
    }

    private ItemMapWriter getItemMapWriter() {
        return new ItemMapWriter(getUrnAdapter());
    }

    private CategorySchemeMapWriter getCategorySchemeMapWriter() {
        return new CategorySchemeMapWriter(getVersionableWriter(), getLinksWriter(), getAnnotableWriter(), getItemMapWriter());
    }

    private CategorySchemeWriter getCategorySchemeWriter() {
        return new CategorySchemeWriter(getVersionableWriter(), getLinksWriter(), getNameableWriter());
    }

    private CodelistWriter getCodelistWriter() {
        return new CodelistWriter(getVersionableWriter(), getLinksWriter(), getCodeImplWriter(), getCodelistExtensionWriter(), getNameableWriter());
    }

    private ConceptSchemeMapWriter getConceptSchemeMapWriter() {
        return new ConceptSchemeMapWriter(getVersionableWriter(), getLinksWriter(), getAnnotableWriter(), getItemMapWriter());
    }

    private ConceptSchemeWriter getConceptSchemeWriter() {
        return new ConceptSchemeWriter(getVersionableWriter(), getLinksWriter(), getNameableWriter(), getRepresentationWriter());
    }

    private DataConstraintWriter getDataConstraintWriter() {
        return new DataConstraintWriter(getVersionableWriter(), getLinksWriter(), getCubeRegionWriter(), getDataKeySetsWriter(), getUrnAdapter());
    }

    private DataConsumerSchemeWriter getDataConsumerSchemeWriter() {
        return new DataConsumerSchemeWriter(getVersionableWriter(), getLinksWriter(), getOrganisationWriter());
    }

    private DataflowWriter getDataflowWriter() {
        return new DataflowWriter(getVersionableWriter(), getLinksWriter(), getUrnAdapter());
    }

    private DataProviderSchemeWriter getDataProviderSchemeWriter() {
        return new DataProviderSchemeWriter(getVersionableWriter(), getLinksWriter(), getOrganisationWriter());
    }

    private DataStructureDefinitionWriter getDataStructureDefinitionWriter() {
        return new DataStructureDefinitionWriter(
            getVersionableWriter(),
            getLinksWriter(),
            getAttributeListWriter(),
            getDimensionListWriter(),
            getGroupDimensionListWriter(),
            getMeasureListWriter(),
            getUrnAdapter(),
            getDsdAdapter()
        );
    }

    private HierarchyAssociationWriter getHierarchyAssociationWriter() {
        return new HierarchyAssociationWriter(getVersionableWriter(), getLinksWriter());
    }

    private HierarchyWriter getHierarchyWriter() {
        return new HierarchyWriter(getVersionableWriter(), getLinksWriter(), getIdentifiableWriter(), getNameableWriter(), getUrnAdapter());
    }

    private MetadataConstraintWriter getMetadataConstraintWriter() {
        return new MetadataConstraintWriter(getVersionableWriter(), getLinksWriter(), getMemberSelectionWriter(), getUrnAdapter());
    }

    private MetadataflowWriter getMetadataflowWriter() {
        return new MetadataflowWriter(getVersionableWriter(), getLinksWriter(), getUrnAdapter());
    }

    private MetadataProviderSchemeWriter getMetadataProviderSchemeWriter() {
        return new MetadataProviderSchemeWriter(getVersionableWriter(), getLinksWriter(), getOrganisationWriter());
    }

    private MetadataProvisionAgreementWriter getMetadataProvisionAgreementWriter() {
        return new MetadataProvisionAgreementWriter(getVersionableWriter(), getLinksWriter(), getUrnAdapter());
    }

    private MetadataStructureDefinitionWriter getMetadataStructureDefinitionWriter() {
        return new MetadataStructureDefinitionWriter(
            getVersionableWriter(),
            getLinksWriter(),
            getComponentWriter(),
            getIdentifiableWriter()
        );
    }

    private OrganisationSchemeMapWriter getOrganisationSchemeMapWriter() {
        return new OrganisationSchemeMapWriter(getVersionableWriter(), getLinksWriter(), getAnnotableWriter(), getItemMapWriter());
    }

    private OrganisationUnitSchemeWriter getOrganisationUnitSchemeWriter() {
        return new OrganisationUnitSchemeWriter(getVersionableWriter(), getLinksWriter(), getOrganisationWriter());
    }

    private ProcessWriter getProcessWriter() {
        return new ProcessWriter(
            getVersionableWriter(),
            getLinksWriter(),
            getNameableWriter(),
            getAnnotableWriter(),
            getIdentifiableWriter()
        );
    }

    private ProvisionAgreementWriter getProvisionAgreementWriter() {
        return new ProvisionAgreementWriter(getVersionableWriter(), getLinksWriter(), getUrnAdapter());
    }

    private ReportingTaxonomyWriter getReportingTaxonomyWriter() {
        return new ReportingTaxonomyWriter(getVersionableWriter(), getLinksWriter(), getNameableWriter(), getUrnAdapter());
    }

    private ReportingTaxonomyMapWriter getReportingTaxonomyMapWriter() {
        return new ReportingTaxonomyMapWriter(getVersionableWriter(), getLinksWriter(), getAnnotableWriter(), getItemMapWriter());
    }

    private RepresentationMapWriter getRepresentationMapWriter() {
        return new RepresentationMapWriter(getVersionableWriter(), getLinksWriter(), getAnnotableWriter(), getUrnAdapter());
    }

    private StructureMapWriter getStructureMapWriter() {
        return new StructureMapWriter(
            getVersionableWriter(),
            getLinksWriter(),
            getAnnotableWriter(),
            getIdentifiableWriter(),
            getUrnAdapter()
        );
    }

    private ValueListWriter getValueListWriter() {
        return new ValueListWriter(
            getVersionableWriter(),
            getLinksWriter(),
            getAnnotableWriter()
        );
    }

    private GeographicCodelistWriter getGeographicCodelistWriter() {
        return new GeographicCodelistWriter(
            getVersionableWriter(),
            getLinksWriter(),
            getGeoFeatureSetCodeWriter(),
            getCodelistExtensionWriter(),
            getNameableWriter()
        );
    }

    private GeoGridCodelistWriter getGeoGridCodelistWriter() {
        return new GeoGridCodelistWriter(
            getVersionableWriter(),
            getLinksWriter(),
            getCodelistExtensionWriter(),
            getGridCodeWriter(),
            getNameableWriter()
        );
    }

    private List<? extends MaintainableWriter<? extends MaintainableArtefact>> writers() {
        return List.of(
            getAgencySchemeWriter(),
            getCategorisationWriter(),
            getCategorySchemeWriter(),
            getCategorySchemeMapWriter(),
            getCodelistWriter(),
            getConceptSchemeWriter(),
            getConceptSchemeMapWriter(),
            getDataConstraintWriter(),
            getDataConsumerSchemeWriter(),
            getDataProviderSchemeWriter(),
            getDataStructureDefinitionWriter(),
            getDataflowWriter(),
            getHierarchyWriter(),
            getHierarchyAssociationWriter(),
            getMetadataConstraintWriter(),
            getMetadataProviderSchemeWriter(),
            getMetadataProvisionAgreementWriter(),
            getMetadataStructureDefinitionWriter(),
            getMetadataflowWriter(),
            getOrganisationSchemeMapWriter(),
            getOrganisationUnitSchemeWriter(),
            getProcessWriter(),
            getProvisionAgreementWriter(),
            getReportingTaxonomyWriter(),
            getReportingTaxonomyMapWriter(),
            getRepresentationMapWriter(),
            getStructureMapWriter(),
            getValueListWriter(),
            getGeographicCodelistWriter(),
            getGeoGridCodelistWriter()
        );
    }


}
