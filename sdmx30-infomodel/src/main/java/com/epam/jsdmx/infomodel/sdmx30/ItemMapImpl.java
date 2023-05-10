package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.Objects.requireNonNull;

import java.time.Instant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Describes how the source value maps to the target value
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ItemMapImpl extends AnnotableArtefactImpl implements ItemMap {

    private String source;
    private String target;
    private Instant validFrom;
    private Instant validTo;
    private boolean regEx;
    private int startIndex;
    private int endIndex;

    public ItemMapImpl(ItemMap from) {
        super(requireNonNull(from));
        this.source = from.getSource();
        this.target = from.getTarget();
        this.validFrom = from.getValidFrom();
        this.validTo = from.getValidTo();
        this.regEx = from.isRegEx();
        this.startIndex = from.getStartIndex();
        this.endIndex = from.getEndIndex();
    }

    @Override
    public ItemMapImpl clone() {
        return new ItemMapImpl(this);
    }
}
