package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public abstract class BaseCodeSelectionImpl implements CodeSelection {

    private List<MemberValue> members = new ArrayList<>();

}
