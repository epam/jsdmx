package com.epam.jsdmx.json10.structure;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.function.Function;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.sdmxsource.sdmx.api.factory.StructureParserFactory;
import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.sdmxbeans.model.SdmxStructureJsonFormat;
import org.sdmxsource.sdmx.structureparser.builder.sdmxbeans.impl.SdmxObjectsJsonV1Builder;
import org.sdmxsource.sdmx.structureparser.factory.SdmxJsonStructureParserFactory;
import org.sdmxsource.util.factory.SdmxSourceReadableDataLocationFactory;

public class SdmxSourceCompatibilityTester {

    public void test(String serialized, Function<SdmxBeans, Set<? extends MaintainableBean>> extractor) {
        final ReadableDataLocation readableDataLocation = new SdmxSourceReadableDataLocationFactory()
            .getReadableDataLocation(new ByteArrayInputStream(serialized.getBytes(StandardCharsets.UTF_8)));
        final StructureParserFactory structureParserFactory = new SdmxJsonStructureParserFactory(new SdmxStructureJsonFormat());

        registerJavaTimeModuleWithinFactor(structureParserFactory);

        SdmxBeans beans = structureParserFactory.getSdmxBeans(readableDataLocation);
        assertThat(extractor.apply(beans)).isNotEmpty();
    }

    private static void registerJavaTimeModuleWithinFactor(StructureParserFactory structureParserFactory) {
        Field sdmxBeansJsonBuilderField = null;
        Field objectMapperField = null;
        try {
            sdmxBeansJsonBuilderField = structureParserFactory.getClass().getDeclaredField("sdmxBeansJsonBuilder");
            sdmxBeansJsonBuilderField.setAccessible(true);
            final var sdmxBeansJsonBuilder = (SdmxObjectsJsonV1Builder) sdmxBeansJsonBuilderField.get(structureParserFactory);
            objectMapperField = sdmxBeansJsonBuilder.getClass().getDeclaredField("objectMapper");
            objectMapperField.setAccessible(true);
            final var objectMapper = (ObjectMapper) objectMapperField.get(sdmxBeansJsonBuilder);
            objectMapper.registerModule(new JavaTimeModule());
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        } finally {
            if (objectMapperField != null) {
                objectMapperField.setAccessible(false);
            }
            if (sdmxBeansJsonBuilderField != null) {
                sdmxBeansJsonBuilderField.setAccessible(false);
            }
        }
    }
}
