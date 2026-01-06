package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataflowImpl
    extends StructureUsageImpl
    implements Dataflow {

    public DataflowImpl(Dataflow from) {
        super(Objects.requireNonNull(from));
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.DATAFLOW;
    }

    @Override
    protected DataflowImpl createInstance() {
        return new DataflowImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dataflow)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dataflow)) {
            return false;
        }
        return super.deepEquals(o, exclusions);
    }

    @Override
    public Dataflow toStub() {
        return toStub(createInstance());
    }

    @Override
    public Dataflow toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
