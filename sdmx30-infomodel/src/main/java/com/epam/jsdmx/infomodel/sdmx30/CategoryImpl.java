package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class CategoryImpl
    extends ItemImpl<Category>
    implements Category {

    public CategoryImpl(CategoryImpl from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.CATEGORY;
    }

    @Override
    public CategoryImpl clone() {
        return new CategoryImpl(this);
    }
}
