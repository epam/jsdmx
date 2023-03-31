package com.epam.jsdmx.infomodel.sdmx30;

import java.net.URI;

/**
 * An instance of a role of an individual or an organization (or organization part or
 * organization person) to whom an information item(s), a material object(s) and/or
 * person(s) can be sent to or from in a specified context.
 */
public interface Contact extends Copyable {
    /**
     * The Internet e-mail address of the Contact.
     */
    String getEmail();

    /**
     * The fax number of the Contact.
     */
    String getFax();

    /**
     * The designation of the Contact person by a linguistic expression.
     */
    String getName();

    /**
     * The designation of the organisational structure by a linguistic expression, within which Contact person works.
     */
    String getOrganizationUnit();

    /**
     * The function of the contact person with respect to the organisation role for which this person is the Contact.
     */
    InternationalString getResponsibility();

    /**
     * The telephone number of the Contact.
     */
    String getTelephone();

    /**
     * The URL of the Contact.
     */
    URI getUri();

    /**
     * The X.400 address of the Contact.
     */
    String getX400();
}
