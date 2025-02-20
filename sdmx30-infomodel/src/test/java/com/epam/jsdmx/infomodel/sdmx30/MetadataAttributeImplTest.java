package com.epam.jsdmx.infomodel.sdmx30;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class MetadataAttributeImplTest {

    @Test
    void testInit_shouldCorrectlyCopyNestedHierarchy() {
        var rootAttribute1 = new MetadataAttributeImpl();
        rootAttribute1.setId("root1");


        var root1_child1 = new MetadataAttributeImpl();
        root1_child1.setId("root1_child1");
        var root1_child2 = new MetadataAttributeImpl();
        root1_child2.setId("root1_child2");

        var root1_child1_child1 = new MetadataAttributeImpl();
        root1_child1_child1.setId("root1_child1_child1");

        var root1_child2_child1 = new MetadataAttributeImpl();
        root1_child2_child1.setId("root1_child2_child1");

        rootAttribute1.setHierarchy(List.of(root1_child1, root1_child2));

        root1_child1.setHierarchy(List.of(root1_child1_child1));

        root1_child2.setHierarchy(List.of(root1_child2_child1));


        var copy = new MetadataAttributeImpl(rootAttribute1);

        assertThat(copy.getHierarchy()).hasSize(2);
        assertThat(copy.getHierarchy().get(0).getHierarchy()).hasSize(1);
        assertThat(copy.getHierarchy().get(1).getHierarchy()).hasSize(1);
    }

}