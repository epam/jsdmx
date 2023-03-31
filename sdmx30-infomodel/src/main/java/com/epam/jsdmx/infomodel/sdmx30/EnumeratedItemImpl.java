package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Data
public abstract class EnumeratedItemImpl
    extends AnnotableArtefactImpl
    implements EnumeratedItem {

    private String id;
    private InternationalString name;
    private InternationalString description;

    public EnumeratedItemImpl(EnumeratedItem from) {
        super(Objects.requireNonNull(from));
        this.id = from.getId();
        this.name = new InternationalString(from.getName());
        this.description = new InternationalString(from.getDescription());
    }
}
