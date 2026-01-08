package com.epam.jsdmx.xml21.structure.writer;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;
import com.epam.jsdmx.serializer.common.TimeDimensionLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.sdmx21.DataStructure30To21ComponentAdapter;
import com.epam.jsdmx.serializer.sdmx21.ReferenceResolver;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultHeaderProvider;

public class Xml21WriterFactory {

    private final DataStructure30To21ComponentAdapter dsdAdapter;
    private final TimeDimensionLocalRepresentationAdapter dsdLocalRepresentationAdapter;
    private final ReferenceResolver referenceResolver;
    private final ReferenceAdapter referenceAdapter;

    public Xml21WriterFactory(DataStructure30To21ComponentAdapter dsdAdapter,
                              TimeDimensionLocalRepresentationAdapter dsdLocalRepresentationAdapter,
                              ReferenceResolver resolver,
                              ReferenceAdapter referenceAdapter) {
        this.dsdAdapter = dsdAdapter;
        this.dsdLocalRepresentationAdapter = dsdLocalRepresentationAdapter;
        this.referenceResolver = resolver;
        this.referenceAdapter = referenceAdapter;
    }

    public Xml21StructureWriter newInstance(OutputStream outputStream, boolean isPretty) {
        HeaderWriter headerWriter = new HeaderWriter(new DefaultHeaderProvider());
        return new Xml21StructureWriter(
            outputStream,
            writers(),
            headerWriter,
            isPretty
        );
    }

    public Xml21StructureWriter newInstance(OutputStream outputStream,
                                            boolean isPretty,
                                            Map<StructureClass, ? extends XmlWriter<? extends MaintainableArtefact>> customStructureWriters) {
        HeaderWriter headerWriter = new HeaderWriter(new DefaultHeaderProvider());
        var defaultWriters = writers();
        List<XmlWriter<? extends MaintainableArtefact>> writers = new ArrayList<>();

        for (var writer : defaultWriters) {
            writer.getWritableArtefactStructureClass()
                .filter(customStructureWriters::containsKey)
                .map(customStructureWriters::get)
                .ifPresentOrElse(writers::add, () -> writers.add(writer));
        }

        return new Xml21StructureWriter(
            outputStream,
            writers,
            headerWriter,
            isPretty
        );
    }
    private List<? extends XmlWriter<? extends MaintainableArtefact>> writers() {

        final var annotableWriter = new AnnotableWriter();
        final var nameableWriter = new NameableWriter();
        final var contactWriter = new ContactWriter();

        final var referenceWriter = new ReferenceWriter(referenceResolver, referenceAdapter);
        final var representationWriter = new RepresentationWriter(referenceWriter);

        final var attributeListWriter = new AttributeListWriter(annotableWriter, representationWriter, referenceWriter);
        final var dimensionListWriter = new DimensionListWriter(annotableWriter, representationWriter, referenceWriter);
        final var measureListWriter = new MeasureListWriter(annotableWriter, representationWriter, referenceWriter);
        final var groupDimensionListWriter = new GroupDimensionListWriter(annotableWriter, referenceWriter);

        final var commonAttributesWriter = new CommonAttributesWriter(referenceWriter);

        final var organisationWriter = new OrganisationWriter(contactWriter, annotableWriter, nameableWriter);

        final var codeWriter = new CodeWriterImpl(referenceWriter, nameableWriter, annotableWriter);

        final var memberSelectionWriter = new MemberSelectionWriter();
        final var releaseCalenderWriter = new ReleaseCalenderWriter();
        final var dataKeySetsWriter = new DataKeySetsWriter(annotableWriter, memberSelectionWriter);
        final var cubeRegionWriter = new CubeRegionWriter(annotableWriter, memberSelectionWriter);
        final var metadataConstraintWriter = new MetadataConstraintWriter(memberSelectionWriter);

        return List.of(
            new OrganisationSchemeWriter(
                nameableWriter,
                annotableWriter,
                commonAttributesWriter,
                organisationWriter
            ),
            new DataflowWriter(
                nameableWriter,
                annotableWriter,
                commonAttributesWriter,
                referenceWriter
            ),
            new CategorySchemeWriter(
                nameableWriter,
                annotableWriter,
                commonAttributesWriter
            ),
            new CategorisationWriter(
                nameableWriter,
                annotableWriter,
                commonAttributesWriter,
                referenceWriter
            ),
            new CodelistWriter(
                nameableWriter,
                annotableWriter,
                commonAttributesWriter,
                codeWriter
            ),
            new HierarchicalCodelistWriter(
                nameableWriter,
                annotableWriter,
                commonAttributesWriter,
                referenceWriter
            ),
            new ConceptSchemeWriter(
                nameableWriter,
                annotableWriter,
                commonAttributesWriter,
                representationWriter
            ),
            new DataStructureDefinitionWriter(
                nameableWriter,
                annotableWriter,
                commonAttributesWriter,
                attributeListWriter,
                dimensionListWriter,
                measureListWriter,
                groupDimensionListWriter,
                dsdAdapter,
                dsdLocalRepresentationAdapter
            ),
            new ContentConstraintWriter(
                nameableWriter,
                annotableWriter,
                commonAttributesWriter,
                referenceWriter,
                releaseCalenderWriter,
                dataKeySetsWriter,
                cubeRegionWriter,
                metadataConstraintWriter
            )
        );
    }
}
