package com.epam.jsdmx.xml30.structure.writer;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;
import com.epam.jsdmx.serializer.common.StubDataStructureLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.common.TimeDimensionLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultHeaderProvider;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultReferenceAdapter;

public class XmlWriterFactory {

    private final boolean isPretty;
    private final ReferenceAdapter referenceAdapter;
    private final TimeDimensionLocalRepresentationAdapter timeDimensionLocalRepresentationAdapter;

    public XmlWriterFactory(boolean isPretty) {
        this.isPretty = isPretty;
        referenceAdapter = new DefaultReferenceAdapter();
        timeDimensionLocalRepresentationAdapter = new StubDataStructureLocalRepresentationAdapter();
    }

    public XmlWriterFactory(boolean isPretty,
                            ReferenceAdapter referenceAdapter,
                            TimeDimensionLocalRepresentationAdapter timeDimensionLocalRepresentationAdapter) {
        this.isPretty = isPretty;
        this.referenceAdapter = referenceAdapter;
        this.timeDimensionLocalRepresentationAdapter = timeDimensionLocalRepresentationAdapter;
    }

    public XmlStructureWriter newInstance(OutputStream outputStream,
                                          Map<StructureClass, ? extends XmlWriter<? extends MaintainableArtefact>> customStructureWriters) {
        var defaultWriters = writers();
        List<XmlWriter<? extends MaintainableArtefact>> writers = new ArrayList<>();

        for (var writer : defaultWriters) {
            writer.getWritableArtefactStructureClass()
                .filter(customStructureWriters::containsKey)
                .map(customStructureWriters::get)
                .ifPresentOrElse(writers::add, () -> writers.add(writer));
        }
        return new XmlStructureWriter(
            outputStream,
            writers,
            new HeaderWriter(new DefaultHeaderProvider()),
            isPretty
        );
    }


    public XmlStructureWriter newInstance(OutputStream outputStream) {
        return new XmlStructureWriter(
            outputStream,
            this.writers(),
            new HeaderWriter(new DefaultHeaderProvider()),
            isPretty
        );
    }

    protected List<? extends XmlWriter<? extends MaintainableArtefact>> writers() {
        var nameableWriter = new NameableWriter();
        var annotableWriter = new AnnotableWriter();
        var urnWriter = new UrnWriter(referenceAdapter);
        var commonAttributesWriter = new CommonAttributesWriter(urnWriter);
        var linksWriter = new LinksWriter(urnWriter);

        var itemSchemeMapWriter = new ItemSchemeMapWriter(urnWriter);

        var contactWriter = new ContactWriter();
        var orgWriter = new OrganisationWriter(contactWriter, annotableWriter, nameableWriter);

        final var representationWriter = new RepresentationWriter(urnWriter);

        final var codeWriter = new CodeWriterImpl(urnWriter, nameableWriter, annotableWriter);
        final var codeListExtensionWriter = new CodeListExtensionWriter(urnWriter);

        final var attributeListWriter = new AttributeListWriter(annotableWriter, representationWriter);
        final var dimensionListWriter = new DimensionListWriter(annotableWriter, representationWriter);
        final var measureListWriter = new MeasureListWriter(annotableWriter, representationWriter);
        final var groupDimensionListWriter = new GroupDimensionListWriter(annotableWriter);

        final var releaseCalenderWriter = new ReleaseCalenderWriter();
        final var memberSelectionWriter = new MemberSelectionWriter();

        return List.of(
            new AgencySchemeWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, orgWriter),
            new CategorisationWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, urnWriter),
            new CategorySchemeWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter),
            new CategorySchemeMapWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, itemSchemeMapWriter),
            new CodelistWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, codeWriter, codeListExtensionWriter),
            new ConceptSchemeWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, representationWriter),
            new ConceptSchemeMapWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, itemSchemeMapWriter),
            new DataConstraintWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, releaseCalenderWriter, memberSelectionWriter),
            new DataConsumerSchemeWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, orgWriter),
            new DataProviderSchemeWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, orgWriter),
            new DataStructureDefinitionWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, attributeListWriter, dimensionListWriter,
                measureListWriter, groupDimensionListWriter, timeDimensionLocalRepresentationAdapter
            ),
            new DataflowWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter),
            new HierarchyWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, urnWriter),
            new HierarchyAssociationWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter),
            new MetadataConstraintWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, releaseCalenderWriter, memberSelectionWriter),
            new MetadataProviderSchemeWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, orgWriter),
            new MetadataProvisionAgreementWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter),
            new MetadataStructureDefinitionWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, representationWriter),
            new MetadataflowWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter),
            new OrganisationSchemeMapWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, itemSchemeMapWriter, urnWriter),
            new OrganisationUnitSchemeWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, orgWriter),
            new ProcessWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter),
            new ProvisionAgreementWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter),
            new ReportingTaxonomyWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter),
            new ReportingTaxonomyMapWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, itemSchemeMapWriter),
            new RepresentationMapWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, urnWriter),
            new StructureMapWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter),
            new ValueListWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter),
            new GeographicCodelistWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, new GeoFeatureSetCodeWriter(urnWriter,
                nameableWriter, annotableWriter
            ),
                codeListExtensionWriter
            ),
            new GeoGridCodelistWriter(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter, codeListExtensionWriter,
                new GridCodeWriter(urnWriter, nameableWriter, annotableWriter)
            )
        );
    }
}
