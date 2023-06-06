package com.epam.jsdmx.infomodel.sdmx30;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrganisationUnitImpl extends OrganisationImpl<OrganisationUnit> implements OrganisationUnit {

    public OrganisationUnitImpl(OrganisationUnit from) {
        super(from);
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.ORGANISATION_UNIT;
    }

    @Override
    public Object clone() {
        return new OrganisationUnitImpl(this);
    }
}
