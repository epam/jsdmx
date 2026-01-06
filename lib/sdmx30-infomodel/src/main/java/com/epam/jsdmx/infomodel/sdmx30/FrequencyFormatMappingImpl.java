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
public class FrequencyFormatMappingImpl
    extends IdentifiableArtefactImpl
    implements FrequencyFormatMapping {

    private String datePattern;
    private String frequencyCode;

    public FrequencyFormatMappingImpl(FrequencyFormatMapping from) {
        super(Objects.requireNonNull(from));
        this.datePattern = from.getDatePattern();
        this.frequencyCode = from.getFrequencyCode();
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.FREQUENCY_FORMAT_MAPPING;
    }
}
