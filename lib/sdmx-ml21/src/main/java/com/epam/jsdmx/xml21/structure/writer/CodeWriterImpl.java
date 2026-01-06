package com.epam.jsdmx.xml21.structure.writer;

import com.epam.jsdmx.infomodel.sdmx30.Code;

public class CodeWriterImpl extends CodeWriter<Code> {
    public CodeWriterImpl(ReferenceWriter referenceWriter,
                          NameableWriter nameableWriter,
                          AnnotableWriter annotableWriter) {
        super(referenceWriter, nameableWriter, annotableWriter);
    }
}
