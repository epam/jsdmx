package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

/**
 * An organisation is a unique framework of authority within which a person or persons
 * act, or are designated to act, towards some purpose.
 */
public interface Organisation<T> extends Item {

    /**
     * @return Association to child Organisations.
     */
    @Override
    List<? extends Organisation<T>> getHierarchy();

    /**
     * An instance of a role of an individual or an organization (or organization part or organization person)
     * to whom an information item(s), a material object(s) and/or person(s) can be sent to or from
     * in a specified context.
     *
     * @return list of contacts
     */
    List<Contact> getContacts();
}