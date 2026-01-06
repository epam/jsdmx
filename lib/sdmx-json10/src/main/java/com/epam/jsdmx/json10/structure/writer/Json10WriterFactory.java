package com.epam.jsdmx.json10.structure.writer;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefact;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;
import com.epam.jsdmx.serializer.common.TimeDimensionLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.sdmx21.DataStructure30To21ComponentAdapter;
import com.epam.jsdmx.serializer.sdmx21.ReferenceResolver;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultReferenceAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Json10WriterFactory {

    private final ObjectMapper mapper;
    private final List<Locale.LanguageRange> languagePriorities;
    private final DefaultHeaderProvider defaultHeaderProvider;
    private final DataStructure30To21ComponentAdapter dsdComponentAdapter;
    private final ReferenceResolver referenceResolver;
    private final ReferenceAdapter referenceAdapter;
    private final TimeDimensionLocalRepresentationAdapter timeDimensionLocalRepresentationAdapter;

    public Json10WriterFactory(ObjectMapper mapper,
                               DataStructure30To21ComponentAdapter dsdComponentAdapter,
                               TimeDimensionLocalRepresentationAdapter timeDimensionLocalRepresentationAdapter,
                               ReferenceResolver referenceResolver,
                               List<Locale.LanguageRange> languagePriorities,
                               DefaultHeaderProvider defaultHeaderProvider,
                               ReferenceAdapter referenceAdapter) {
        this.mapper = mapper;
        this.languagePriorities = languagePriorities;
        this.defaultHeaderProvider = defaultHeaderProvider;
        this.dsdComponentAdapter = dsdComponentAdapter;
        this.referenceResolver = referenceResolver;
        this.referenceAdapter = referenceAdapter;
        this.timeDimensionLocalRepresentationAdapter = timeDimensionLocalRepresentationAdapter;
    }

    public Json10WriterFactory(ObjectMapper mapper,
                               DataStructure30To21ComponentAdapter dsdComponentAdapter,
                               TimeDimensionLocalRepresentationAdapter timeDimensionLocalRepresentationAdapter,
                               ReferenceResolver referenceResolver,
                               List<Locale.LanguageRange> languagePriorities,
                               DefaultHeaderProvider defaultHeaderProvider) {
        this(
            mapper,
            dsdComponentAdapter,
            timeDimensionLocalRepresentationAdapter,
            referenceResolver,
            languagePriorities,
            defaultHeaderProvider,
            new DefaultReferenceAdapter()
        );
    }


    public Json10StructureWriter newInstance(OutputStream writeTo) {
        return new Json10StructureWriter(
            mapper,
            writeTo,
            new MetaWriter(languagePriorities, defaultHeaderProvider),
            writers()
        );
    }

    public Json10StructureWriter newInstance(OutputStream writeTo,
                                             Map<StructureClass, ? extends MaintainableWriter<? extends MaintainableArtefact>> customStructureWriters) {
        var defaultStructureWriters = writers();
        List<MaintainableWriter<? extends MaintainableArtefact>> structureWriters = new ArrayList<>();
        for (var writer : defaultStructureWriters) {
            writer.getWritableArtefactStructureClass()
                .filter(customStructureWriters::containsKey)
                .map(customStructureWriters::get)
                .ifPresentOrElse(structureWriters::add, () -> structureWriters.add(writer));
        }
        return new Json10StructureWriter(
            mapper,
            writeTo,
            new MetaWriter(languagePriorities, defaultHeaderProvider),
            structureWriters
        );
    }

    private List<? extends MaintainableWriter<? extends MaintainableArtefact>> writers() {
        final LinksWriter linksWriter = new LinksWriter(referenceAdapter);
        final AnnotableWriter annotableWriter = new AnnotableWriter(linksWriter);
        final IdentifiableWriter identifiableWriter = new IdentifiableWriter(annotableWriter);
        final NameableWriter nameableWriter = new NameableWriter(identifiableWriter);
        final VersionableWriter versionableWriter = new VersionableWriter(nameableWriter);

        final ContactsWriter contactsWriter = new ContactsWriter();
        final OrganisationWriter organisationWriter = new OrganisationWriter(contactsWriter, nameableWriter);

        final RepresentationWriter representationWriter = new RepresentationWriter(referenceResolver, referenceAdapter);

        final CodeWriterImpl codeImplWriter = new CodeWriterImpl();

        final ConceptRoleWriter conceptRoleWriter = new ConceptRoleWriter(referenceResolver);
        final ComponentWriter componentWriter = new ComponentWriter(identifiableWriter, representationWriter, referenceResolver);
        final AttributeListWriter attributeListWriter = new AttributeListWriter(identifiableWriter, conceptRoleWriter, componentWriter);
        final DimensionListWriter dimensionListWriter = new DimensionListWriter(identifiableWriter, conceptRoleWriter, componentWriter);
        final MeasureListWriter measureListWriter = new MeasureListWriter(identifiableWriter, conceptRoleWriter, componentWriter);
        final GroupDimensionListWriter groupDimensionListWriter = new GroupDimensionListWriter(identifiableWriter);
        final DataKeySetsWriter dataKeySetsWriter = new DataKeySetsWriter();
        final CubeRegionWriter cubeRegionWriter = new CubeRegionWriter(new SelectionValueWriter(), annotableWriter);
        final MetadataConstraintWriter metadataConstraintWriter = new MetadataConstraintWriter(new SelectionValueWriter());

        return List.of(
            new AgencySchemeWriter(versionableWriter, linksWriter, organisationWriter),
            new CategorisationWriter(versionableWriter, linksWriter, referenceResolver, referenceAdapter),
            new CategorySchemeWriter(versionableWriter, linksWriter, nameableWriter),
            new CodelistWriter(versionableWriter, linksWriter, codeImplWriter, nameableWriter),
            new ConceptSchemeWriter(versionableWriter, linksWriter, nameableWriter, representationWriter),
            new DataStructureDefinitionWriter(
                versionableWriter,
                linksWriter,
                attributeListWriter,
                dimensionListWriter,
                measureListWriter,
                groupDimensionListWriter,
                dsdComponentAdapter,
                timeDimensionLocalRepresentationAdapter
            ),
            new DataflowWriter(versionableWriter, linksWriter, referenceResolver),
            new HierarchicalCodelistWriter(versionableWriter, linksWriter, identifiableWriter, nameableWriter, referenceResolver, referenceAdapter),
            new ContentConstraintWriter(versionableWriter, linksWriter, referenceAdapter, dataKeySetsWriter, cubeRegionWriter, metadataConstraintWriter)
        );
    }

}
