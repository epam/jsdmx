package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.METADATA_PROVISION_AGREEMENT_XML;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.MetadataProvisionAgreement;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class MetadataProvisionAgreementReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(METADATA_PROVISION_AGREEMENT_XML, minifyXml);

        MetadataProvisionAgreementReader metadataProvisionAgreementReader = createMetadataProvisionAgreementReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(metadataProvisionAgreementReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        MetadataProvisionAgreement actual = artefacts.getMetadataProvisionAgreements().iterator().next();
        MetadataProvisionAgreement expected = MaintainableArtifactsTestUtils.buildMetadataProvisionAgreement();

        Assertions.assertEquals(expected, actual);
    }
}
