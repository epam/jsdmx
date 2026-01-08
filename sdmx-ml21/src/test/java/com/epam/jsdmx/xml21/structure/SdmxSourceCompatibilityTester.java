package com.epam.jsdmx.xml21.structure;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.function.Function;

import org.sdmxsource.sdmx.api.model.beans.SdmxBeans;
import org.sdmxsource.sdmx.api.model.beans.base.MaintainableBean;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.structureparser.factory.SdmxStructureParserFactory;
import org.sdmxsource.util.factory.SdmxSourceReadableDataLocationFactory;

public class SdmxSourceCompatibilityTester {

    public void test(String serialized, Function<SdmxBeans, Set<? extends MaintainableBean>> extractor) {
        final ReadableDataLocation readableDataLocation = new SdmxSourceReadableDataLocationFactory()
            .getReadableDataLocation(new ByteArrayInputStream(serialized.getBytes(StandardCharsets.UTF_8)));
        final SdmxStructureParserFactory structureParserFactory = new SdmxStructureParserFactory();
        SdmxBeans beans = structureParserFactory.getSdmxBeans(readableDataLocation);
        assertThat(extractor.apply(beans)).isNotEmpty();
    }

}
