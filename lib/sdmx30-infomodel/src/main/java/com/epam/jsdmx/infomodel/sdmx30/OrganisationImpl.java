package com.epam.jsdmx.infomodel.sdmx30;

import static java.util.stream.Collectors.toList;

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
public abstract class OrganisationImpl<T extends Organisation<T>>
    extends ItemImpl<T>
    implements Organisation<T> {

    private List<Contact> contacts;

    public OrganisationImpl(Organisation<T> from) {
        super(Objects.requireNonNull(from));
        if (from.getContacts() != null) {
            this.contacts = from.getContacts().stream()
                .map(contact -> (Contact) contact.clone())
                .collect(toList());
        }
    }
}
