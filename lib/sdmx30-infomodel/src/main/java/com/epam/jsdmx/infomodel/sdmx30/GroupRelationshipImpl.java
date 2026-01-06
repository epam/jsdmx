package com.epam.jsdmx.infomodel.sdmx30;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GroupRelationshipImpl implements GroupRelationship {

    private String groupKey;

    public GroupRelationshipImpl(GroupRelationship from) {
        Objects.requireNonNull(from);
        this.groupKey = from.getGroupKey();
    }

    @Override
    public GroupRelationshipImpl clone() {
        return new GroupRelationshipImpl(this);
    }
}
