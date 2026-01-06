package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HierarchyImpl
    extends MaintainableArtefactImpl
    implements Hierarchy {

    private boolean hasFormalLevels;
    private LevelImpl level;
    private List<HierarchicalCode> codes;

    private HierarchyImpl(Hierarchy from) {
        super(Objects.requireNonNull(from));
        this.hasFormalLevels = from.isHasFormalLevels();
        this.level = new LevelImpl(from.getLevel());
        this.codes = StreamUtils.streamOfNullable(from.getCodes())
            .map(HierarchicalCodeImpl::new)
            .collect(toList());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.HIERARCHY;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return getReferencedCodelists(codes)
            .collect(toSet());
    }

    private Stream<CrossReference> getReferencedCodelists(List<? extends HierarchicalCode> codes) {
        return Optional.ofNullable(codes).stream()
            .flatMap(List::stream)
            .flatMap(this::getReferencedCodelists);
    }

    private Stream<CrossReference> getReferencedCodelists(HierarchicalCode code) {
        Stream<CrossReference> currentCodelistRef = Stream.of(toCodelistRef(code.getCode()));

        List<HierarchicalCode> childCodes = code.getHierarchicalCodes();
        Stream<CrossReference> childCodeListRefs = getReferencedCodelists(childCodes);

        return Stream.concat(currentCodelistRef, childCodeListRefs);
    }

    @Override
    public Hierarchy toStub() {
        return toStub(createInstance());
    }

    @Override
    public Hierarchy toCompleteStub() {
        return toCompleteStub(createInstance());
    }

    @Override
    public List<HierarchicalCode> getCodes() {
        if (codes == null) {
            return List.of();
        }
        return new ArrayList<>(codes);
    }

    private CrossReference toCodelistRef(ArtefactReference code) {
        return new CrossReferenceImpl(this, code.getMaintainableArtefactReference());
    }

    @Override
    protected HierarchyImpl createInstance() {
        return new HierarchyImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hierarchy)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Hierarchy)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        Hierarchy hierarchy = (HierarchyImpl) o;
        return isHasFormalLevels() == hierarchy.isHasFormalLevels()
            && Objects.equals(getLevel(), hierarchy.getLevel())
            && Objects.equals(getCodes(), hierarchy.getCodes());
    }
}
