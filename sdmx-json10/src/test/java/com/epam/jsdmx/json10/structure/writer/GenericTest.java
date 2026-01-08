package com.epam.jsdmx.json10.structure.writer;

import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

import com.epam.jsdmx.infomodel.sdmx30.MaintainableArtefactImpl;
import com.epam.jsdmx.json10.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.json10.structure.TestUtils;
import com.epam.jsdmx.serializer.common.ReferenceAdapter;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class GenericTest extends JsonWriterTestBase {
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

    private static Stream<Arguments> testArtefactWriters() {

        ReferenceAdapter referenceAdapter = MaintainableArtifactsTestUtils.getReferenceAdapter();
        LinksWriter linksWriter = new LinksWriter(referenceAdapter);
        AnnotableWriter annotableWriter = new AnnotableWriter(linksWriter);
        IdentifiableWriter identifiableWriter = new IdentifiableWriter(annotableWriter);
        NameableWriter nameableWriter = new NameableWriter(identifiableWriter);
        VersionableWriter versionableWriter = new VersionableWriter(nameableWriter);
        ContactsWriter contactsWriter = new ContactsWriter();
        OrganisationWriter organisationWriter = new OrganisationWriter(contactsWriter, nameableWriter);
        AgencySchemeWriter agencySchemeWriter = new AgencySchemeWriter(versionableWriter, linksWriter, organisationWriter);

        return Stream.of(
            Arguments.of(TestUtils.AGENCY_SCHEME_JSON, agencySchemeWriter, MaintainableArtifactsTestUtils.buildAgencyScheme())
        );
    }
}
