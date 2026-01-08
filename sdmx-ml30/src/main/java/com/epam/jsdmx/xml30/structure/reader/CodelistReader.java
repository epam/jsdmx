package com.epam.jsdmx.xml30.structure.reader;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Code;
import com.epam.jsdmx.infomodel.sdmx30.CodeImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtensionImpl;
import com.epam.jsdmx.infomodel.sdmx30.CodelistImpl;
import com.epam.jsdmx.xml30.structure.writer.XmlConstants;

import org.apache.commons.lang3.StringUtils;

public class CodelistReader extends XmlReader<CodelistImpl> {

    private final CodelistExtensionReader codelistExtensionReader;
    private final CodeImplReader codeImplReader;
    private final List<Code> codes = new ArrayList<>();
    private final List<CodelistExtension> codelistExtensions = new ArrayList<>();
    Map<CodeImpl, String> codeWithParentId = new HashMap<>();

    public CodelistReader(AnnotableReader annotableReader,
                          NameableReader nameableReader,
                          CodelistExtensionReader codelistExtensionReader, CodeImplReader codeImplReader) {
        super(annotableReader, nameableReader);
        this.codelistExtensionReader = codelistExtensionReader;
        this.codeImplReader = codeImplReader;
    }

    @Override
    protected CodelistImpl createMaintainableArtefact() {
        return new CodelistImpl();
    }

    @Override
    protected CodelistImpl read(XMLStreamReader reader) throws XMLStreamException, URISyntaxException {
        CodelistImpl codelist = super.read(reader);
        codelist.setItems(new ArrayList<>(codes));
        codelist.setExtensions(new ArrayList<>(codelistExtensions));
        return codelist;
    }

    @Override
    protected void read(XMLStreamReader reader, CodelistImpl codelist) throws URISyntaxException, XMLStreamException {
        String localName = reader.getLocalName();
        switch (localName) {
            case XmlConstants.STR_CODE:
                var code = new CodeImpl();
                codeImplReader.setCode(
                    reader,
                    code,
                    nameableReader,
                    annotableReader,
                    codeWithParentId
                );
                codes.add(code);
                break;
            case XmlConstants.CODELIST_EXTENSION:
                String prefix = reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.PREFIX);
                CodelistExtensionImpl codelistExtension = codelistExtensionReader.getCodelistExtension(reader);
                codelistExtension.setPrefix(prefix);
                codelistExtensions.add(codelistExtension);
                break;
            default:
                throw new IllegalArgumentException("Codelist " + XmlConstants.DOES_NOT_SUPPORT_ELEMENT + localName);
        }
    }

    @Override
    protected void setAttributes(XMLStreamReader reader, CodelistImpl codelist) throws XMLStreamException {
        XmlReaderUtils.setCommonAttributes(reader, codelist);
        Optional.ofNullable(reader.getAttributeValue(StringUtils.EMPTY, XmlConstants.IS_PARTIAL))
            .map(Boolean::parseBoolean)
            .ifPresent(codelist::setPartial);
    }

    @Override
    protected String getName() {
        return XmlConstants.CODELIST;
    }

    @Override
    protected String getNames() {
        return XmlConstants.CODELISTS;
    }

    @Override
    protected void setArtefacts(Artefacts artefact, Set<CodelistImpl> artefacts) {
        artefact.getCodelists().addAll(artefacts);
    }

    @Override
    protected void clean() {
        codes.clear();
        codelistExtensions.clear();
    }
}
