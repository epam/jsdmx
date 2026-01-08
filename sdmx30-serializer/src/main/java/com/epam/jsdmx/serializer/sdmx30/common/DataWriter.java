package com.epam.jsdmx.serializer.sdmx30.common;

import java.util.List;
import java.util.Map;

import com.epam.jsdmx.infomodel.sdmx30.Annotation;
import com.epam.jsdmx.infomodel.sdmx30.Artefacts;
import com.epam.jsdmx.infomodel.sdmx30.InternationalString;

public interface DataWriter extends AutoCloseable {
    void startDataset(ProvisionAgreement agreement, Artefacts artefacts, DatasetHeader header);

    void startSeries(Annotation... annotations);

    void writeSeriesComponent(String concept, String code);

    void startGroup(Annotation... annotations);

    void writeGroupComponent(String concept, String code);

    void writeObservation(String obsConceptValue, Map<String, String> measureValues, Annotation[] annotations30);

    void writeObservation(String dimAtObsForWrite, String obsConceptValue, Map<String, String> measureValues, Annotation[] annotations30);

    void writeAttribute(String concept, String code);

    void writeAttribute(String concept, List<String> code);

    void writeAttribute(String concept, InternationalString value);

    void writeInternationalAttributes(String concept, List<InternationalString> value);

    void writeMetaData(MetadataAttributeValue metadataAttributeValue);

    void close();
}
