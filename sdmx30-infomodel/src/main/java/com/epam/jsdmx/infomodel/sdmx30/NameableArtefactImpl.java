package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Optional;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public abstract class NameableArtefactImpl
    extends IdentifiableArtefactImpl
    implements NameableArtefact {

    private InternationalString name;
    private InternationalString description;

    protected NameableArtefactImpl(NameableArtefact from) {
        super(Objects.requireNonNull(from));
        if (from.getName() != null) {
            this.name = new InternationalString(from.getName());
        }
        if (from.getDescription() != null) {
            this.description = new InternationalString(from.getDescription());
        }
    }

    public String getNameInDefaultLocale() {
        return Optional.ofNullable(name).map(InternationalObject::getForDefaultLocale).orElse(null);
    }

    public String getDescriptionInDefaultLocale() {
        return Optional.ofNullable(description).map(InternationalObject::getForDefaultLocale).orElse(null);
    }

    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof NameableArtefactImpl)) {
            return false;
        }
        final NameableArtefactImpl other = (NameableArtefactImpl) o;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(
            Optional.ofNullable(getName()).orElseGet(InternationalString::new),
            Optional.ofNullable(other.getName()).orElseGet(InternationalString::new)
        );
    }

    protected boolean canEqual(final Object other) {
        return other instanceof NameableArtefactImpl;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        return result * PRIME + (isEmpty(this.getName()) ? 43 : this.getName().hashCode());
    }

    private static boolean isEmpty(InternationalString str) {
        return str == null || str.isEmpty();
    }
}
