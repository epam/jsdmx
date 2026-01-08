package com.epam.jsdmx.xml21.structure.writer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.InternationalString;
import com.epam.jsdmx.infomodel.sdmx30.Party;
import com.epam.jsdmx.serializer.common.TimeDimensionLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.sdmx21.DataStructure30To21ComponentAdapter;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultHeaderProvider;
import com.epam.jsdmx.serializer.sdmx30.common.DefaultReferenceAdapter;
import com.epam.jsdmx.serializer.sdmx30.common.Header;
import com.epam.jsdmx.xml21.structure.SdmxSourceCompatibilityTester;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseXmlWriterTest {
    SdmxSourceCompatibilityTester sdmxSourceCompatibilityTester = new SdmxSourceCompatibilityTester();

    NameableWriter nameableWriter = new NameableWriter();

    AnnotableWriter annotableWriter = new AnnotableWriter();

    ReferenceWriter referenceWriter = new ReferenceWriter(ref -> ref, new DefaultReferenceAdapter());

    CommonAttributesWriter commonAttributesWriter = new CommonAttributesWriter(referenceWriter);

    ContactWriter contactWriter = new ContactWriter();

    ReleaseCalenderWriter releaseCalenderWriter = new ReleaseCalenderWriter();

    MemberSelectionWriter memberSelectionWriter = new MemberSelectionWriter();

    OrganisationWriter organisationWriter = new OrganisationWriter(contactWriter, annotableWriter, nameableWriter);

    RepresentationWriter representationWriter = new RepresentationWriter(referenceWriter);

    DefaultHeaderProvider defaultHeaderProvider = mock(DefaultHeaderProvider.class);

    HeaderWriter headerWriter = new HeaderWriter(defaultHeaderProvider);

    DataStructure30To21ComponentAdapter dataStructure30To21ComponentAdapter = dsd -> dsd;

    TimeDimensionLocalRepresentationAdapter timeDimensionLocalRepresentationAdapter = dsd -> dsd;

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
            referenceWriter
        );
    }

    public CodelistWriter createCodelistWriter() {
        return new CodelistWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            new CodeWriterImpl(referenceWriter, nameableWriter, annotableWriter)
        );
    }

    public ConceptSchemeWriter createConceptSchemeWriter() {
        return new ConceptSchemeWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            representationWriter
        );
    }

    public CategorySchemeWriter createCategorySchemeWriter() {
        return new CategorySchemeWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter
        );
    }

    public DataflowWriter createDataflowWriter() {
        return new DataflowWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            referenceWriter
        );
    }

    public DataStructureDefinitionWriter createDataStructureDefinitionWriter() {
        var attributeListWriter = new AttributeListWriter(annotableWriter, representationWriter, referenceWriter);
        var dimensionListWriter = new DimensionListWriter(annotableWriter, representationWriter, referenceWriter);
        var measureListWriter = new MeasureListWriter(annotableWriter, representationWriter, referenceWriter);
        var groupDimensionListWriter = new GroupDimensionListWriter(annotableWriter, referenceWriter);
        return new DataStructureDefinitionWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            attributeListWriter,
            dimensionListWriter,
            measureListWriter,
            groupDimensionListWriter,
            dataStructure30To21ComponentAdapter,
            timeDimensionLocalRepresentationAdapter
        );
    }

    public HierarchicalCodelistWriter createHierarchyWriter() {
        return new HierarchicalCodelistWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            referenceWriter
        );
    }

    public OrganisationSchemeWriter createOrganisationSchemeWriter() {
        return new OrganisationSchemeWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            organisationWriter
        );
    }

    public ContentConstraintWriter createContentConstraintWriter() {
        DataKeySetsWriter dataKeySetsWriter = new DataKeySetsWriter(annotableWriter, memberSelectionWriter);
        CubeRegionWriter cubeRegionWriter = new CubeRegionWriter(annotableWriter, memberSelectionWriter);
        MetadataConstraintWriter metadataConstraintWriter = new MetadataConstraintWriter(memberSelectionWriter);

        return new ContentConstraintWriter(
            nameableWriter,
            annotableWriter,
            commonAttributesWriter,
            referenceWriter,
            releaseCalenderWriter,
            dataKeySetsWriter,
            cubeRegionWriter,
            metadataConstraintWriter
        );
    }
}
