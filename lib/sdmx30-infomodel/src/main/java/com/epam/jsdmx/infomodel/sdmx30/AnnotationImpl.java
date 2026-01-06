package com.epam.jsdmx.infomodel.sdmx30;

import java.util.List;

import lombok.Data;

@Data
public class AnnotationImpl implements Annotation {

    private String id;
    private String title;
    private String type;
    private String value;
    private InternationalUri url;
    private InternationalString text;
    private List<Link> links;

    public void addText(String language, String localisedText) {
        if (this.text == null) {
            this.text = new InternationalString();
        }
        this.text.add(language, localisedText);
    }

    @Override
    public List<Link> getLinks() {
        return links;
    }
}
