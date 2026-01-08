package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.CodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.CodelistImpl;
import com.epam.jsdmx.json20.structure.writer.StructureUtils;

import com.fasterxml.jackson.core.JsonParser;
import org.apache.commons.collections.CollectionUtils;

public class CodelistReader extends MaintainableReader<CodelistImpl> {

    private final NameableReader nameableReader;
    private final CodelistExtensionReader codelistExtensionReader;
    private final CodeImplReader codeImplReader;
    Map<CodeImpl, String> codeWithParentId = new HashMap<>();

    public CodelistReader(VersionableReader versionableArtefact,
                          NameableReader nameableArtefact,
                          CodelistExtensionReader codelistExtensionReader,
                          CodeImplReader codeImplReader) {
        super(versionableArtefact);
        this.nameableReader = nameableArtefact;
        this.codelistExtensionReader = codelistExtensionReader;
        this.codeImplReader = codeImplReader;
    }

    @Override
    protected CodelistImpl createMaintainableArtefact() {
        return new CodelistImpl();
    }

    @Override
    public void readArtefact(JsonParser parser, CodelistImpl codeList) throws IOException {
        String fieldName = parser.getCurrentName();
        switch (fieldName) {
            case StructureUtils.CODES:
                List<CodeImpl> codeImpls = ReaderUtils.getArray(parser, this::getCodeImpl);
                if (CollectionUtils.isNotEmpty(codeImpls)) {
                    codeList.setItems(List.copyOf(codeImpls));
                }
                break;
            case StructureUtils.CODE_LIST_EXTENSIONS:
                parser.nextToken();
                List<CodelistExtension> codeListExtensions = codelistExtensionReader.getCodeListExtensions(parser);
                if (CollectionUtils.isNotEmpty(codeListExtensions)) {
                    codeList.setExtensions(codeListExtensions);
                }
                break;
            case StructureUtils.IS_PARTIAL:
                boolean isPartial = ReaderUtils.getBooleanJsonField(parser);
                codeList.setPartial(isPartial);
                break;
            default:
                throw new IllegalArgumentException(StructureUtils.NO_SUCH_PROPERTY_IN + "CodeList: " + fieldName);
        }
    }

    private CodeImpl getCodeImpl(JsonParser parser) {
        try {
            return codeImplReader.getCode(parser, new CodeImpl(), nameableReader, codeWithParentId);
        } catch (IOException e) {
            throw new JsonRuntimeException(e);
        }
    }

    @Override
    protected String getName() {
        return StructureUtils.CODE_LISTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<CodelistImpl> artefacts) {
        artefact.getCodelists().addAll(artefacts);
    }

    @Override
    protected void clean() {
        codeWithParentId.clear();
    }
}
