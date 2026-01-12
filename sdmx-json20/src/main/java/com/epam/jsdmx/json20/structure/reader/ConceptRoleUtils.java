package com.epam.jsdmx.json20.structure.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.epam.jsdmx.infomodel.sdmx30.ArtefactReference;
import com.epam.jsdmx.infomodel.sdmx30.IdentifiableArtefactReferenceImpl;

import com.fasterxml.jackson.core.JsonParser;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConceptRoleUtils {

    public static List<ArtefactReference> getConceptRoles(JsonParser parser) throws IOException {
        List<ArtefactReference> identifiableArtefactReferences = new ArrayList<>();
        List<String> conceptRolesStrings = ReaderUtils.getListStrings(parser);
        if (conceptRolesStrings.isEmpty()) {
            return Collections.emptyList();
        }
        for (String conceptRole : conceptRolesStrings) {
            if (conceptRole != null) {
                IdentifiableArtefactReferenceImpl identifiableArtefactReference =
                    new IdentifiableArtefactReferenceImpl(conceptRole);
                identifiableArtefactReferences.add(identifiableArtefactReference);
            }
        }
        return identifiableArtefactReferences;
    }
}
