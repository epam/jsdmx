package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodelistExtensionImpl implements CodelistExtension {

    private String prefix;
    private int sequence;
    private ArtefactReference codelist;
    private CodeSelection codeSelection;

    public CodelistExtensionImpl(CodelistExtension from) {
        Objects.requireNonNull(from);
        this.prefix = from.getPrefix();
        this.sequence = from.getSequence();
        this.codelist = Optional.ofNullable(from.getCodelist())
            .map(clRef -> (ArtefactReference) clRef.clone())
            .orElse(null);
    }

    @Override
    public CodelistExtensionImpl clone() {
        return new CodelistExtensionImpl(this);
    }
}
