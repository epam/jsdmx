package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Set;

import org.junit.jupiter.api.Test;

class TextFormatImplTest {

    @Test
    void shouldNotFailOnMultipleFacets() {
        Facet f1 = new BaseFacetImpl(FacetValueType.STRING);
        var f2 = new BaseFacetImpl();
        f2.setType(FacetType.IS_MULTILINGUAL);
        f2.setValue("true");

        TextFormat value = assertDoesNotThrow(() -> new TextFormatImpl(Set.of(f1, f2)));

        assertThat(value.getValueType()).contains(FacetValueType.STRING);
        assertThat(value.getIsMultiLingual()).contains(true);
    }

}