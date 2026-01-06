package com.epam.jsdmx.infomodel.sdmx30;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
public class DataProviderImpl extends OrganisationImpl<DataProvider> implements DataProvider {

    private String dataSource;

    public DataProviderImpl(DataProvider from) {
        super(from);
        this.dataSource = from.getDataSource();
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.DATA_PROVIDER;
    }

    @Override
    public DataProvider clone() {
        return new DataProviderImpl(this);
    }
}
