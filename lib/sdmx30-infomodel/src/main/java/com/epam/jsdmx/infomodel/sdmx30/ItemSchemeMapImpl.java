package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class ItemSchemeMapImpl
    extends MaintainableArtefactImpl
    implements ItemSchemeMap {

    private ArtefactReference source;
    private ArtefactReference target;
    private List<ItemMap> itemMaps = new ArrayList<>();

    public ItemSchemeMapImpl(ItemSchemeMap from) {
        super(from);
        this.source = from.getSource();
        this.target = from.getTarget();
        this.itemMaps = StreamUtils.streamOfNullable(from.getItemMaps())
            .map(item -> (ItemMap) item.clone())
            .collect(toList());
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return Stream.of(source, target)
            .filter(Objects::nonNull)
            .map(ref -> new CrossReferenceImpl(this, ref))
            .collect(toSet());
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemSchemeMap)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        ItemSchemeMap that = (ItemSchemeMap) o;
        return Objects.equals(source, that.getSource())
            && Objects.equals(target, that.getTarget())
            && Objects.equals(itemMaps, that.getItemMaps());
    }
}