package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URI;
import java.util.Objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public abstract class IdentifiableArtefactImpl
    extends AnnotableArtefactImpl
    implements IdentifiableArtefact {

    private URI uri;

    @EqualsAndHashCode.Include
    private String id;

    private ArtefactReference container;

    @SneakyThrows
    protected IdentifiableArtefactImpl(IdentifiableArtefact from) {
        super(Objects.requireNonNull(from));
        if (from.getUri() != null) {
            this.uri = new URI(from.getUri().toString());
        }
        this.id = from.getId();
        ArtefactReference containerRef = from.getContainer();
        if (containerRef != null) {
            this.container = (ArtefactReference) containerRef.clone();
        }
    }

    public String getUrn() {
        if (container == null) {
            throw new IllegalStateException("Get urn of non-maintainable object with null parent reference");
        }
        return SdmxUrn.getItemUrnString(container, this);
    }

    @Override
    public String toString() {
        if (container != null) {
            return toStringWithUrn();
        }
        return toStringWithId();
    }

    protected String toStringWithUrn() {
        return String.format("%s=%s", getStructureClass().getSimpleName(), SdmxUrn.parseToShortItemUrnString(getUrn()));
    }

    private String toStringWithId() {
        return String.format("%s=%s", getStructureClass().getSimpleName(), getId());
    }
}
