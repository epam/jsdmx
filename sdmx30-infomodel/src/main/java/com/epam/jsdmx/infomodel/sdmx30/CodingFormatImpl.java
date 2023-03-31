package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;
import java.util.Optional;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CodingFormatImpl implements CodingFormat {

    private Facet codingFormat;

    public CodingFormatImpl(CodingFormat from) {
        Objects.requireNonNull(from);
        this.codingFormat = Optional.ofNullable(from.getCodingFormat())
            .map(Facet::clone)
            .orElse(null);
    }
}
