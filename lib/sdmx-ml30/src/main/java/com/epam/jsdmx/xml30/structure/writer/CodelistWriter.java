package com.epam.jsdmx.xml30.structure.writer;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.Code;
import com.epam.jsdmx.infomodel.sdmx30.Codelist;
import com.epam.jsdmx.infomodel.sdmx30.CodelistExtension;
import com.epam.jsdmx.infomodel.sdmx30.StructureClass;
import com.epam.jsdmx.infomodel.sdmx30.StructureClassImpl;

public class CodelistWriter extends XmlWriter<Codelist> {

    private final CodeWriterImpl codeWriterImpl;
    private final CodeListExtensionWriter codeListExtensionWriter;

    public CodelistWriter(NameableWriter nameableWriter,
                          AnnotableWriter annotableWriter,
                          CommonAttributesWriter commonAttributesWriter,
                          LinksWriter linksWriter,
                          CodeWriterImpl codeWriterImpl, CodeListExtensionWriter codeListExtensionWriter) {
        super(nameableWriter, annotableWriter, commonAttributesWriter, linksWriter);
        this.codeWriterImpl = codeWriterImpl;
        this.codeListExtensionWriter = codeListExtensionWriter;
    }

    @Override
    protected void writeAttributes(Codelist codelist, XMLStreamWriter writer) throws XMLStreamException {
        this.commonAttributesWriter.writeAttributes(codelist, writer);
        writer.writeAttribute(XmlConstants.IS_PARTIAL, String.valueOf(codelist.isPartial()));
    }

    public Set<Codelist> extractArtefacts(Artefacts artefacts) {
        return artefacts.getCodelists();
    }

    @Override
    protected String getName() {
        return XmlConstants.CODELIST;
    }

    @Override
    protected String getNamePlural() {
        return XmlConstants.CODELISTS;
    }

    @Override
    public Optional<StructureClass> getWritableArtefactStructureClass() {
        return Optional.of(StructureClassImpl.CODELIST);
    }

    @Override
    protected void writeCustomAttributeElements(Codelist codelist, XMLStreamWriter writer) throws XMLStreamException {
        List<? extends Code> codes = codelist.getItems();
        for (Code code : codes) {
            if (code != null) {
                codeWriterImpl.write(code, writer, XmlConstants.STR_CODE);
            }
        }

        List<? extends CodelistExtension> codelistExtensions = codelist.getExtensions();
        codeListExtensionWriter.writeCodeListExtensions(writer, codelistExtensions);
    }
}