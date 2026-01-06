package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class ItemImpl<T extends Item>
    extends NameableArtefactImpl
    implements Item, Copyable {

    private List<T> hierarchy = new ArrayList<>();

    public ItemImpl(Item from) {
        super(Objects.requireNonNull(from));
        this.hierarchy = StreamUtils.streamOfNullable(from.getHierarchy())
            .map(hierarchyItem -> (T) hierarchyItem.clone())
            .collect(toList());
    }

    public void addChild(T child) {
        getHierarchy().add(child);
    }

    public abstract Object clone();
}
