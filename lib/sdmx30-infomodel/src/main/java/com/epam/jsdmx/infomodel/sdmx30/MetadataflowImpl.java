package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MetadataflowImpl
    extends StructureUsageImpl
    implements Metadataflow {

    private List<IdentifiableObjectSelection> selections = new ArrayList<>();

    public MetadataflowImpl(Metadataflow from) {
        super(Objects.requireNonNull(from));
        this.selections = StreamUtils.streamOfNullable(from.getSelections())
            .map(ref -> (IdentifiableObjectSelection) ref.clone())
            .collect(toList());
    }

    @Override
    public Metadataflow toStub() {
        return toStub(createInstance());
    }

    @Override
    public Metadataflow toCompleteStub() {
        return toCompleteStub(createInstance());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.METADATAFLOW;
    }

    @Override
    protected MetadataflowImpl createInstance() {
        return new MetadataflowImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Metadataflow)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Metadataflow)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        MetadataflowImpl that = (MetadataflowImpl) o;

        return Objects.equals(getSelections(), that.getSelections());
    }
}
