package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SdmxUrnTest {

    @Test
    void testToFullUrnString() {
        final var urn = SdmxUrn.toFullUrnString(StructureClassImpl.DATA_STRUCTURE, "agency", "id", "1.0.0");
        assertThat(urn).isEqualTo("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=agency:id(1.0.0)");
    }

    @Test
    void testToFullItemUrnString() {
        final var urn = SdmxUrn.toFullItemUrnString(StructureClassImpl.DATAFLOW, "agency", "id", "1.0.0", "itemId");
        assertThat(urn).isEqualTo("urn:sdmx:org.sdmx.infomodel.datastructure.Dataflow=agency:id(1.0.0).itemId");
    }

    @Test
    void testToShortUrnString() {
        final var urn = SdmxUrn.toShortUrnString("agency", "id", "1.0.0");
        assertThat(urn).isEqualTo("agency:id(1.0.0)");
    }

    @Test
    void testToShortItemUrnString() {
        final var urn = SdmxUrn.toShortItemUrnString("agency", "id", "1.0.0", "itemId");
        assertThat(urn).isEqualTo("agency:id(1.0.0).itemId");
    }

    @Test
    void testIsUrnWhenNullArg() {
        assertThat(SdmxUrn.isUrn(null)).isFalse();
    }

    @SuppressWarnings("unchecked")
    @ParameterizedTest
    @MethodSource
    void testGetType(String urn, StructureClass expected) {
        final var actual = (Optional<StructureClass>) SdmxUrn.getType(urn);
        assertThat(actual)
            .isNotEmpty()
            .contains(expected);
    }

    private static Stream<Arguments> testGetType() {
        return Stream.of(
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.0)", StructureClassImpl.DATA_STRUCTURE),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.Dataflow=ECB:ECB_EXR12(1.0)", StructureClassImpl.DATAFLOW),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.conceptscheme.Concept=ECB:ECB_EXR12(1.0).full", StructureClassImpl.CONCEPT),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.codelist.Codelist=ECB:ECB_EXR12(1.0)", StructureClassImpl.CODELIST),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.codelist.Code=ECB:ECB_EXR12(1.0).ABC", StructureClassImpl.CODE),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.categoryscheme.CategoryScheme=ECB:ECB_EXR12(1.0)", StructureClassImpl.CATEGORY_SCHEME),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.categoryscheme.Category=ECB:ECB_EXR12(1.0).root", StructureClassImpl.CATEGORY)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testValidFullUrns(String urn) {
        assertThat(SdmxUrn.isUrn(urn)).isTrue();
    }

    private static Stream<Arguments> testValidFullUrns() {
        return Stream.of(
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.0)"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.0).ful$l"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.0).sdm-x_full"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.0).sdmx_full.compact"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.0).sdmx_compact.full.compact"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.0).sdmx_full.compact.full.compact.full"),

            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69)"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1+.42.69)"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42+.69)"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69+)"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69).full"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69).sdmx_compact"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69).sdmx_full.compact"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69).sdmx_compact.full.compact"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69).sdmx_full.compact.full.compact.full"),

            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69-draft)"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69-draft).full"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69-draft).sdmx_full"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69-draft).sdmx_full.compact"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69-draft).sdmx_compact.full.compact"),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69-draft).sdmx_full.compact.full.compact.full"),

            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=EC-B:ECB_EXR12(1.42.69-draft).sdmx_full.compact.full.compact.full")
        );
    }

    @ParameterizedTest
    @MethodSource
    void testValidShortUrns(String urn) {
        assertThat(SdmxUrn.isUrn(urn)).isTrue();
    }

    private static Stream<Arguments> testValidShortUrns() {
        return Stream.of(
            Arguments.of("ECB:ECB_EXR12(1.0)"),
            Arguments.of("ECB:ECB_EXR12(1.0).full"),
            Arguments.of("ECB:ECB_EXR12(1.0).sdmx_full"),
            Arguments.of("ECB:ECB_EXR12(1.0).sdmx_full.compact"),
            Arguments.of("ECB:ECB_EXR12(1.0).sdmx_compact.full.compact"),
            Arguments.of("ECB:ECB_EXR12(1.0).sdmx_full.compact.full.compact.full"),

            Arguments.of("ECB:ECB_EXR12(1.42.69)"),
            Arguments.of("ECB:ECB_EXR12(1.42.69).full"),
            Arguments.of("ECB:ECB_EXR12(1.42.69).sdmx_compact"),
            Arguments.of("ECB:ECB_EXR12(1.42.69).sdmx_full.compact"),
            Arguments.of("ECB:ECB_EXR12(1.42.69).sdmx_compact.full.compact"),
            Arguments.of("ECB:ECB_EXR12(1.42.69).sdmx_full.compact.full.compact.full"),

            Arguments.of("ECB:ECB_EXR12(1.42.69-draft)"),
            Arguments.of("ECB:ECB_EXR12(1.42.69-draft).full"),
            Arguments.of("ECB:ECB_EXR12(1.42.69-draft).sdmx_full"),
            Arguments.of("ECB:ECB_EXR12(1.42.69-draft).sdmx_full.compact"),
            Arguments.of("ECB:ECB_EXR12(1.42.69-draft).sdmx_compact.full.compact"),
            Arguments.of("ECB:ECB_EXR12(1.42.69-draft).sdmx_full.compact.full.compact.full")
        );
    }

    @ParameterizedTest
    @MethodSource
    void testGetUrnComponents(String urn, UrnComponents expected) {
        assertThat(SdmxUrn.getUrnComponents(urn)).isEqualTo(expected);
    }

    private static Stream<Arguments> testGetUrnComponents() {
        return Stream.of(
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.0)", UrnComponents.builder()
                .structureClass("org.sdmx.infomodel.datastructure.DataStructure")
                .agency("ECB")
                .id("ECB_EXR12")
                .version("1.0")
                .build()),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.0).ful$l", UrnComponents.builder()
                .structureClass("org.sdmx.infomodel.datastructure.DataStructure")
                .agency("ECB")
                .id("ECB_EXR12")
                .version("1.0")
                .itemId("ful$l")
                .build()),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69)", UrnComponents.builder()
                .structureClass("org.sdmx.infomodel.datastructure.DataStructure")
                .agency("ECB")
                .id("ECB_EXR12")
                .version("1.42.69")
                .build()),
            Arguments.of("urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69-draft)", UrnComponents.builder()
                .structureClass("org.sdmx.infomodel.datastructure.DataStructure")
                .agency("ECB")
                .id("ECB_EXR12")
                .version("1.42.69-draft")
                .build()),
            Arguments.of(
                "urn:sdmx:org.sdmx.infomodel.datastructure.DataStructure=ECB:ECB_EXR12(1.42.69-draft).sdmx_full.compact.full.compact.full",
                UrnComponents.builder()
                    .structureClass("org.sdmx.infomodel.datastructure.DataStructure")
                    .agency("ECB")
                    .id("ECB_EXR12")
                    .version("1.42.69-draft")
                    .itemId("sdmx_full.compact.full.compact.full")
                    .build()
            ),
            Arguments.of("ECB:ECB_EXR12(1.42.69-draft).sdmx_full.compact.full.compact.full", UrnComponents.builder()
                .agency("ECB")
                .id("ECB_EXR12")
                .version("1.42.69-draft")
                .itemId("sdmx_full.compact.full.compact.full")
                .build()),
            Arguments.of("ECB:ECB_EXR12(1.42.69-draft)", UrnComponents.builder()
                .agency("ECB")
                .id("ECB_EXR12")
                .version("1.42.69-draft")
                .build())
        );
    }

}