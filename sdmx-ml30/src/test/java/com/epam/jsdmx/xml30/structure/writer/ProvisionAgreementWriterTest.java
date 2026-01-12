package com.epam.jsdmx.xml30.structure.writer;

import static com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils.buildProvisionAgreement;
import static com.epam.jsdmx.xml30.structure.TestUtils.PROVISION_AGREEMENT_XML;
import static org.custommonkey.xmlunit.XMLAssert.assertXMLEqual;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.ArtefactsImpl;
import com.epam.jsdmx.infomodel.sdmx30.ProvisionAgreement;

import org.apache.commons.io.IOUtils;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

class ProvisionAgreementWriterTest extends BaseXmlWriterTest {

    @Test
    void writeProvisionAgreement() throws IOException, SAXException {
        //given
        XMLUnit.setIgnoreWhitespace(true);

        ProvisionAgreementWriter provisionAgreementWriter = createProvisionAgreementWriter();

        ByteArrayOutputStream actual = new ByteArrayOutputStream();
        XmlStructureWriter xmlStructureWriter = new XmlStructureWriter(
            actual,
            List.of(provisionAgreementWriter),
            headerWriter,
            false
        );

        //when
        Artefacts artefacts = new ArtefactsImpl();
        ProvisionAgreement provisionAgreement = buildProvisionAgreement();
        artefacts.getProvisionAgreements().add(provisionAgreement);
        xmlStructureWriter.write(artefacts);

        InputStream resourceAsStream = this.getClass().getResourceAsStream(PROVISION_AGREEMENT_XML);
        assert resourceAsStream != null;

        //then
        String expected = IOUtils.toString(resourceAsStream, StandardCharsets.UTF_8);
        assertXMLEqual(expected, actual.toString());
    }

}
