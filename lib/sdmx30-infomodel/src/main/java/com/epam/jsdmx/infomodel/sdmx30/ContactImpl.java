package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URI;
import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

@Data
@EqualsAndHashCode
@NoArgsConstructor
public class ContactImpl implements Contact {

    private String email;
    private String fax;
    private String name;
    private String organizationUnit;
    private InternationalString responsibility;
    private String telephone;
    private URI uri;
    private String X400;

    @SneakyThrows
    public ContactImpl(Contact from) {
        Objects.requireNonNull(from);
        this.email = from.getEmail();
        this.fax = from.getFax();
        this.name = from.getName();
        this.organizationUnit = from.getOrganizationUnit();
        if (from.getResponsibility() != null) {
            this.responsibility = new InternationalString(from.getResponsibility());
        }
        this.telephone = from.getTelephone();
        if (from.getUri() != null) {
            this.uri = new URI(from.getUri().toString());
        }
        this.X400 = from.getX400();
    }

    @Override
    public Object clone() {
        return new ContactImpl(this);
    }
}
