package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class GeoCodelistImpl
    extends MaintainableArtefactImpl
    implements GeoCodelist {

    protected boolean isPartial;
    protected List<CodelistExtension> extensions = new ArrayList<>();

    public GeoCodelistImpl(GeoCodelist from) {
        super(requireNonNull(from));
        this.isPartial = from.isPartial();
        this.extensions = StreamUtils.streamOfNullable(from.getExtensions())
            .map(extension -> (CodelistExtension) extension.clone())
            .collect(toList());
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return extensions.stream()
            .map(CodelistExtension::getCodelist)
            .map(clRef -> new CrossReferenceImpl(this, clRef))
            .collect(toSet());
    }
}
