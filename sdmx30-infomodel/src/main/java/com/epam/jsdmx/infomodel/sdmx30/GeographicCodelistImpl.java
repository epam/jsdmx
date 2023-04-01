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
public class GeographicCodelistImpl
    extends GeoCodelistImpl
    implements GeographicCodelist {

    private List<GeoFeatureSetCode> items = new ArrayList<>();

    public GeographicCodelistImpl(GeographicCodelist from) {
        super(from);
        this.items = StreamUtils.streamOfNullable(from.getItems())
            .map(item -> (GeoFeatureSetCode) item.clone())
            .collect(toList());
    }

    @Override
    public GeographicCodelist toStub() {
        return toStub(createInstance());
    }

    @Override
    public GeographicCodelist toCompleteStub() {
        return toCompleteStub(createInstance());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.GEOGRAPHIC_CODELIST;
    }

    @Override
    protected GeographicCodelistImpl createInstance() {
        return new GeographicCodelistImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GeographicCodelist)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (!(o instanceof GeographicCodelist)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        return Objects.equals(this.items, ((GeographicCodelist) o).getItems());
    }
}
