package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IsoConceptReferenceImpl implements IsoConceptReference {

    private String agency;
    private String schemeId;
    private String conceptId;

    public IsoConceptReferenceImpl(IsoConceptReferenceImpl from) {
        Objects.requireNonNull(from);
        this.agency = from.getAgency();
        this.schemeId = from.getSchemeId();
        this.conceptId = from.getConceptId();
    }
}
