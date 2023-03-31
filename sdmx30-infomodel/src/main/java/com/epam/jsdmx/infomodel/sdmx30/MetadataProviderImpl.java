package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MetadataProviderImpl
    extends OrganisationImpl<MetadataProvider>
    implements MetadataProvider {

    private String dataSource;

    public MetadataProviderImpl(MetadataProvider from) {
        super(Objects.requireNonNull(from));
        this.dataSource = from.getDataSource();
    }

    @Override
    public String getDataSource() {
        return dataSource;
    }

    @Override
    public void setHierarchy(List<MetadataProvider> hierarchy) {
        throw new IllegalArgumentException("MetadataProvider doesn't allow to set hierarchy");
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.METADATA_PROVIDER;
    }

    @Override
    public MetadataProvider clone() {
        return new MetadataProviderImpl(this);
    }
}
