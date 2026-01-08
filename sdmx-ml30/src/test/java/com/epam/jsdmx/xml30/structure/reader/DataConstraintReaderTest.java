package com.epam.jsdmx.xml30.structure.reader;

import static com.epam.jsdmx.xml30.structure.TestUtils.DATA_CONSTRAINTS2_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.DATA_CONSTRAINTS_EMPTY_XML;
import static com.epam.jsdmx.xml30.structure.TestUtils.DATA_CONSTRAINTS_XML;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CascadeValue;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionImpl;
import com.epam.jsdmx.infomodel.sdmx30.CubeRegionKeyImpl;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraint;
import com.epam.jsdmx.infomodel.sdmx30.DataConstraintImpl;
import com.epam.jsdmx.infomodel.sdmx30.MemberValueImpl;
import com.epam.jsdmx.infomodel.sdmx30.ReleaseCalendarImpl;
import com.epam.jsdmx.xml30.structure.MaintainableArtifactsTestUtils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DataConstraintReaderTest extends BaseXmlReaderTest {

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void read(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(DATA_CONSTRAINTS_XML, minifyXml);

        DataConstraintReader dataConstraintReader = createDataConstraintReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(dataConstraintReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        DataConstraint actual = artefacts.getDataConstraints().iterator().next();
        DataConstraint expected = MaintainableArtifactsTestUtils.buildDataConstraint();

        assertTrue(expected.deepEquals(actual));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void readWhenValidToIsNull(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(DATA_CONSTRAINTS2_XML, minifyXml);

        DataConstraintReader dataConstraintReader = createDataConstraintReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(dataConstraintReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        DataConstraint actual = artefacts.getDataConstraints().iterator().next();
        DataConstraint expected = createDataConstraint();

        assertTrue(expected.deepEquals(actual));
    }

    private static DataConstraintImpl createDataConstraint() {
        DataConstraintImpl dataConstraint = MaintainableArtifactsTestUtils.buildDataConstraint();
        dataConstraint.setDataContentKeys(List.of());
        CubeRegionKeyImpl cubeRegionKey1 = new CubeRegionKeyImpl();
        cubeRegionKey1.setComponentId("CubeReg");
        cubeRegionKey1.setIncluded(true);
        cubeRegionKey1.setRemovePrefix(true);
        cubeRegionKey1.setValidFrom(Instant.parse("2020-04-12T12:00:00.000000Z"));
        MemberValueImpl memberValue = new MemberValueImpl();
        memberValue.setValue("MEM");
        memberValue.setCascadeValue(CascadeValue.EXCLUDE_ROOT);
        cubeRegionKey1.setSelectionValues(List.of(memberValue));
        CubeRegionImpl cubeRegion = new CubeRegionImpl();
        cubeRegion.setIncluded(true);
        cubeRegion.setCubeRegionKeys(List.of(cubeRegionKey1));
        dataConstraint.setCubeRegions(List.of(cubeRegion));

        return dataConstraint;
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void readEmptyDataConstraint(boolean minifyXml) throws XMLStreamException, URISyntaxException, IOException {
        //given
        XMLStreamReader reader = createXmlStreamReaderInstance(DATA_CONSTRAINTS_EMPTY_XML, minifyXml);

        DataConstraintReader dataConstraintReader = createDataConstraintReader();

        XmlStructureReader xmlStructureReader = new XmlStructureReader(
            headerReader, List.of(dataConstraintReader));

        //when
        Artefacts artefacts = xmlStructureReader.read(reader);

        //then
        DataConstraint actual = artefacts.getDataConstraints().iterator().next();
        DataConstraintImpl expected = MaintainableArtifactsTestUtils.buildDataConstraint();
        expected.setDataContentKeys(List.of());
        expected.setCubeRegions(List.of());
        expected.setReleaseCalendar(new ReleaseCalendarImpl());

        assertTrue(expected.deepEquals(actual));
    }
}
