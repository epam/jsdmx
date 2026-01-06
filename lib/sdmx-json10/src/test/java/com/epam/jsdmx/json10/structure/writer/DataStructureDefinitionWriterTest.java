package com.epam.jsdmx.json10.structure.writer;

import static com.epam.jsdmx.json10.structure.TestUtils.DATA_STRUCTURE_DEFINITION_JSON;
import static net.javacrumbs.jsonunit.JsonAssert.assertJsonEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import com.epam.jsdmx.infomodel.sdmx30.DataStructureDefinition;
import com.epam.jsdmx.json10.structure.MaintainableArtifactsTestUtils;
import com.epam.jsdmx.serializer.common.TimeDimensionLocalRepresentationAdapter;
import com.epam.jsdmx.serializer.sdmx21.DataStructure30To21ComponentAdapter;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;

class DataStructureDefinitionWriterTest extends JsonWriterTestBase {

    @InjectMocks
    public DataStructureDefinitionWriter dataStructureDefinitionWriter;
    @Spy
    private AttributeListWriter attributeListWriterSpy = MaintainableArtifactsTestUtils.getAttributeListWriter();
    @Spy
    private DimensionListWriter dimensionListWriterSpy = MaintainableArtifactsTestUtils.getDimensionListWriter();
    @Spy
    private MeasureListWriter measureListWriterSpy = MaintainableArtifactsTestUtils.getMeasureListWriter();
    @Spy
    private GroupDimensionListWriter groupDimensionListWriter = MaintainableArtifactsTestUtils.getGroupDimensionListWriter();
    @Spy
    private DataStructure30To21ComponentAdapter dsdRecomposerSpy = new DataStructure30To21ComponentAdapter() {
        @Override
        public DataStructureDefinition recompose(DataStructureDefinition dsd) {
            return dsd;
        }
    };
    @Spy
    private TimeDimensionLocalRepresentationAdapter dsdAdapterSpy = new TimeDimensionLocalRepresentationAdapter() {
        @Override
        public DataStructureDefinition adapt(DataStructureDefinition dsd) {
            return dsd;
        }
    };

    @Test
    void writeDataStructureDefinitionToJson() throws IOException {
        //given
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataStructureDefinition dataStructureDefinition = MaintainableArtifactsTestUtils.buildDataStructureDefinition();

        //when
        dataStructureDefinitionWriter.writeAndClose(createJsonGenerator(stream), dataStructureDefinition);
        String actual = stream.toString(StandardCharsets.UTF_8);

        System.out.println(actual);
        //then
        String expected = IOUtils.resourceToString(DATA_STRUCTURE_DEFINITION_JSON, StandardCharsets.UTF_8);

        assertJsonEquals(expected, actual);

        // commented out due to bug in sdmxsource with npe when initializing dsd with attachment group
        sdmxSourceCompatibilityTester.test(
            "{ \"meta\": {}, \"data\": { \"dataStructures\": [" + actual + "] } }",
            SdmxBeans::getDataStructures
        );
    }

}
