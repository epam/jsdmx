package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

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
public class CodelistImpl
    extends ItemSchemeImpl<Code>
    implements Codelist {

    private List<CodelistExtension> extensions = new ArrayList<>();

    public CodelistImpl(Codelist from) {
        super(Objects.requireNonNull(from));
        this.extensions = StreamUtils.streamOfNullable(from.getExtensions())
            .map(extension -> (CodelistExtension) extension.clone())
            .collect(toList());
    }

    @Override
    public StructureClass getStructureClass() {
        return StructureClassImpl.CODELIST;
    }

    @Override
    public Set<CrossReference> getReferencedArtefacts() {
        return extensions.stream()
            .map(CodelistExtension::getCodelist)
            .map(clRef -> new CrossReferenceImpl(this, clRef))
            .collect(toSet());
    }

    @Override
    protected CodelistImpl createInstance() {
        return new CodelistImpl();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Codelist)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public boolean deepEquals(Object o, Set<DeepEqualsExclusion> exclusions) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Codelist)) {
            return false;
        }
        if (!super.deepEquals(o, exclusions)) {
            return false;
        }
        Codelist codelist = (CodelistImpl) o;
        return Objects.equals(getExtensions(), codelist.getExtensions());
    }

    @Override
    public Codelist toStub() {
        return toStub(createInstance());
    }

    @Override
    public Codelist toCompleteStub() {
        return toCompleteStub(createInstance());
    }
}
