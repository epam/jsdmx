package com.epam.jsdmx.infomodel.sdmx30;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@Builder
@EqualsAndHashCode
public final class UrnComponents {

    private final String id;
    private final String agency;
    private final String version;
    private final String itemId;
    private final String structureClass;

}
