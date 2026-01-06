package com.epam.jsdmx.json10.structure.writer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.epam.jsdmx.json10.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json10.structure.SdmxSourceCompatibilityTester;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;
import com.epam.jsdmx.serializer.sdmx21.ReferenceResolver;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class JsonWriterTestBase {

    SdmxSourceCompatibilityTester sdmxSourceCompatibilityTester = new SdmxSourceCompatibilityTester();

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
    private IdentifiableWriter identifiableWriter = MaintainableArtifactsTestUtils.getIdentifiableWriter();
    @Spy
    private RepresentationWriter representationWriter = MaintainableArtifactsTestUtils.getRepresentationWriter();
    @Spy
    private ReferenceResolver referenceResolver = MaintainableArtifactsTestUtils.getReferenceResolver();
    @Spy
    private ReferenceAdapter referenceAdapter = MaintainableArtifactsTestUtils.getReferenceAdapter();
    @Spy
    private DataKeySetsWriter dataKeySetsWriter = new DataKeySetsWriter();
    @Spy
    private CubeRegionWriter cubeRegionWriter = new CubeRegionWriter(new SelectionValueWriter(), annotableWriter);
    @Spy
    private MetadataConstraintWriter metadataConstraintWriter = new MetadataConstraintWriter(new SelectionValueWriter());

    AutoCloseable closeable;

    @BeforeEach
    void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void teardown() throws Exception {
        closeable.close();
    }

    public JsonGenerator createJsonGenerator(ByteArrayOutputStream stream) throws IOException {
        JsonFactory jfactory = new JsonFactory();
        JsonGenerator jGenerator = jfactory.createGenerator(stream, JsonEncoding.UTF8);
        return jGenerator;
    }

}
