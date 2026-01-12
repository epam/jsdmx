package com.epam.jsdmx.xml30.structure.writer;

import com.epam.jsdmx.infomodel.sdmx30.Code;

public class CodeWriterImpl extends CodeWriter<Code> {
    public CodeWriterImpl(UrnWriter urnWriter, NameableWriter nameableWriter, AnnotableWriter annotableWriter) {
        super(urnWriter, nameableWriter, annotableWriter);
    }
}
