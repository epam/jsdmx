package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;

@Getter
@Setter
@NoArgsConstructor
public abstract class ItemSchemeImpl<T extends Item>
    extends MaintainableArtefactImpl
    implements ItemScheme<T> {

    private boolean isPartial;
    private List<T> items = new ArrayList<>();

    protected ItemSchemeImpl(ItemScheme<T> from) {
        super(Objects.requireNonNull(from));
        this.isPartial = from.isPartial();
        this.items = StreamUtils.streamOfNullable(from.getItems())
            .map(item -> (T) item.clone())
            .collect(toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemScheme)) {
            return false;
        }
        return super.equals(o);
    }


    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ItemScheme)) {
            return false;
        }
        final ItemScheme<?> other = (ItemSchemeImpl<?>) o;
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        final List<? extends Item> thisItems = this.getItems();
        final List<? extends Item> otherItems = other.getItems();
        if (CollectionUtils.isEmpty(thisItems) && CollectionUtils.isEmpty(otherItems)) {
            return true;
        }
        return thisItems != null && equalsOrdered(thisItems, otherItems);
    }


    private boolean equalsOrdered(List<? extends Item> thisItems,
                                  List<? extends Item> otherItems) {
        List<? extends Item> thisSorted = sortById(thisItems);
        List<? extends Item> otherSorted = sortById(otherItems);
        return thisSorted.equals(otherSorted);
    }

    private List<? extends Item> sortById(List<? extends Item> unsortedItems) {
        return unsortedItems.stream()
            .sorted(comparing(Item::getId))
            .collect(toList());
    }

}
