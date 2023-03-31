package com.epam.jsdmx.infomodel.sdmx30;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public abstract class AnnotableArtefactImpl implements AnnotableArtefact {

    private List<Annotation> annotations = new ArrayList<>();

    protected AnnotableArtefactImpl(AnnotableArtefact from) {
        Objects.requireNonNull(from);
        this.annotations.addAll(CollectionUtils.emptyIfNull(from.getAnnotations()));
    }

    public void addAnnotation(Annotation a) {
        getAnnotations().add(a);
    }

}
