package com.epam.jsdmx.json20.structure.reader;

import static com.epam.jsdmx.json20.structure.TestUtils.CODE_LIST_JSON;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;

import com.epam.jsdmx.infomodel.sdmx30.CodelistImpl;
import com.epam.jsdmx.json20.structure.MaintainableArtifactsTestUtils;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

class CodelistReaderTest {

    @InjectMocks
    public CodelistReader codeListReader;
    @Spy
    private VersionableReader versionableWriter = MaintainableArtifactsTestUtils.getVersionableReader();
    @Spy
    private NameableReader nameableWriter = MaintainableArtifactsTestUtils.getNameableReader();
    @Spy
    private CodelistExtensionReader codelistExtensionReader = MaintainableArtifactsTestUtils.getCodelistExtensionReader();
    @Spy
    private CodeImplReader codeImplReader = MaintainableArtifactsTestUtils.getCodeImplReader();

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void readCodeListFromJsonTest() throws IOException {
        //given
        InputStream resourceAsStream = CodelistReaderTest.class.getResourceAsStream(CODE_LIST_JSON);
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(resourceAsStream);
        CodelistImpl expected = MaintainableArtifactsTestUtils.buildCodeList();
        //when
        CodelistImpl actual = (CodelistImpl) codeListReader.readAndClose(parser);
        //then
        assertTrue(expected.deepEquals(actual));
    }
}

