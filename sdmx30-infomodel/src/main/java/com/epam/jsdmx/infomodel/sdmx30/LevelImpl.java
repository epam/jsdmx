package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class LevelImpl
    extends NameableArtefactImpl
    implements Level {

    private List<CodingFormat> codeFormat = new ArrayList<>();
    private Level child;

    public LevelImpl(Level from) {
        super(Objects.requireNonNull(from));
        this.codeFormat = StreamUtils.streamOfNullable(from.getCodeFormat())
            .map(CodingFormatImpl::new)
            .collect(toList());
        if (from.getChild() != null) {
            this.child = new LevelImpl(from.getChild());
        }
    }

    @Override
    public StructureClassImpl getStructureClass() {
        return StructureClassImpl.LEVEL;
    }

}
