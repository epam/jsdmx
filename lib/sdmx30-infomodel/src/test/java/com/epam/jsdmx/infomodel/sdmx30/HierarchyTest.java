package com.epam.jsdmx.infomodel.sdmx30;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

class HierarchyTest {

    @Test
    void getCrossReferences() {
        var subject = new HierarchyImpl();
        subject.setVersion(Version.createFromString("1.0"));
        subject.setCodes(getCodes());

        Set<CrossReference> crossReferences = subject.getReferencedArtefacts();

        assertThat(crossReferences)
            .extracting(CrossReference::getUrn)
            .containsExactlyInAnyOrder(
                "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=EPM:CODELIST1(1.0)",
                "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=EPM:CODELIST2(1.0)",
                "urn:sdmx:org.sdmx.infomodel.codelist.Codelist=EPM:CODELIST3(1.0)"
            );
    }

    private List<HierarchicalCode> getCodes() {

        // level 0
        var hc1 = new HierarchicalCodeImpl();
        hc1.setCode(new IdentifiableArtefactReferenceImpl("CODELIST1", "EPM", "1.0", StructureClassImpl.CODE, "code1"));
        var hc2 = new HierarchicalCodeImpl();
        hc2.setCode(new IdentifiableArtefactReferenceImpl("CODELIST2", "EPM", "1.0", StructureClassImpl.CODE, "code2"));

        // level 1
        var hc3 = new HierarchicalCodeImpl();
        hc3.setCode(new IdentifiableArtefactReferenceImpl("CODELIST3", "EPM", "1.0", StructureClassImpl.CODE, "code3"));
        hc1.setHierarchicalCodes(List.of(hc3));

        // level 2
        var hc4 = new HierarchicalCodeImpl();
        hc4.setCode(new IdentifiableArtefactReferenceImpl("CODELIST2", "EPM", "1.0", StructureClassImpl.CODE, "code4")); // note: same codelist as hc2
        hc3.setHierarchicalCodes(List.of(hc4));

        return List.of(hc1, hc2);
    }

}