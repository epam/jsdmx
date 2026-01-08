package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.TestUtils.METADATA_PROVIDER_SCHEME_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProviderScheme;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class MetadataProviderSchemeWriterTest extends BaseXmlWriterTest {

    @Test
    void writeMetadataProviderScheme() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        MetadataProviderSchemeWriter metadataProviderSchemeWriter = createMetadataProviderSchemeWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(metadataProviderSchemeWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        MetadataProviderScheme metadataProviderScheme = MaintainableArtifactsTestUtils.buildMetadataProviderScheme();
        artefacts.getMetadataProviderSchemes().add(metadataProviderScheme);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(METADATA_PROVIDER_SCHEME_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }

}
