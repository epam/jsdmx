package com.epam.jsdmx.infomodel.sdmx30;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Party {

    private String id;
    private InternationalString name;

}
