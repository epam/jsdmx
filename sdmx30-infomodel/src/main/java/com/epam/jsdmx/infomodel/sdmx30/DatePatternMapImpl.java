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
public class DatePatternMapImpl
    extends DateMapImpl
    implements DatePatternMap {

    private String locale;
    private String sourcePattern;

    public DatePatternMapImpl(DatePatternMap from) {
        super(Objects.requireNonNull(from));
        this.locale = from.getLocale();
        this.sourcePattern = from.getSourcePattern();
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.DATE_PATTERN_MAP;
    }
}
