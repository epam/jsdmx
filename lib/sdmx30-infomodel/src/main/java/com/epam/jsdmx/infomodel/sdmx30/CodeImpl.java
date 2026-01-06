package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CodeImpl
    extends ItemImpl<Code>
    implements Code {

    private String parentId;

    public CodeImpl(Code from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.CODE;
    }

    @Override
    public CodeImpl clone() {
        return new CodeImpl(this);
    }
}
