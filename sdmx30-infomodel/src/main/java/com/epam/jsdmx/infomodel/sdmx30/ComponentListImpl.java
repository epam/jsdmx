package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public abstract class ComponentListImpl<T extends Component>
    extends IdentifiableArtefactImpl
    implements ComponentList<T> {

    private List<T> components = new ArrayList<>();

    public ComponentListImpl(ComponentList<T> from) {
        super(Objects.requireNonNull(from));
        this.components = StreamUtils.streamOfNullable(from.getComponents())
            .map(a -> (T) a.clone())
            .collect(toList());
    }

    public void addComponent(T component) {
        getComponents().add(component);
    }
}
