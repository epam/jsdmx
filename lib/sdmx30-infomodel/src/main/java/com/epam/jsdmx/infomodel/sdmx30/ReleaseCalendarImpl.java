package com.epam.jsdmx.infomodel.sdmx30;

import lombok.Data;

@Data
public class ReleaseCalendarImpl implements ReleaseCalendar {

    private String offset;
    private String periodicity;
    private String tolerance;

}
