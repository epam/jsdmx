package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AgencyImpl
    extends OrganisationImpl<Agency>
    implements Agency {

    public AgencyImpl(Agency from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public List<Agency> getHierarchy() {
        return List.of();
    }

    @Override
    public void setHierarchy(List<Agency> hierarchy) {
        throw new IllegalArgumentException("Agency doesn't allow to set hierarchy");
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.AGENCY;
    }

    @Override
    public AgencyImpl clone() {
        return new AgencyImpl(this);
    }
}
