package com.epam.jsdmx.infomodel.sdmx30;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class DataConsumerImpl extends OrganisationImpl<DataConsumer> implements DataConsumer {

    public DataConsumerImpl(DataConsumerImpl from) {
        super(from);
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.DATA_CONSUMER;
    }

    @Override
    public Object clone() {
        return new DataConsumerImpl(this);
    }
}
