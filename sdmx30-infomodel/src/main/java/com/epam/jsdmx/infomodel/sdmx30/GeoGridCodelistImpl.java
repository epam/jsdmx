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
public class GeoGridCodelistImpl
    extends GeoCodelistImpl
    implements GeoGridCodelist {

    private String gridDefinition;
    private List<GridCode> items = new ArrayList<>();

    public GeoGridCodelistImpl(GeoGridCodelist from) {
        super(from);
        this.gridDefinition = from.getGridDefinition();
        this.items = StreamUtils.streamOfNullable(from.getItems())
            .map(item -> (GridCode) item.clone())
            .collect(toList());
    }

    @Override
    public GeoGridCodelist toStub() {
        return toStub(createInstance());
    }

    public GeoGridCodelist toCompleteStub() {
        return toCompleteStub(createInstance());
    }

    public GeoGridCodelistImpl createInstance() {
        return new GeoGridCodelistImpl();
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.GEO_GRID_CODELIST;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GeoGridCodelist)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof GeoGridCodelist)) {
            return false;
        }
        final var other = (GeoGridCodelist) o;
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        if (!Objects.equals(gridDefinition, other.getGridDefinition())) {
            return false;
        }
        return Objects.equals(items, other.getItems());
    }
}
