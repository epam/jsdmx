package com.epam.jsdmx.infomodel.sdmx30;

import java.time.temporal.ChronoField;

import lombok.Getter;

@Getter
public class YearStart {

    private final int dayOfMonth;
    private final int monthOfYear;

    public YearStart(int dayOfMonth, int monthOfYear) {
        this.dayOfMonth = ChronoField.DAY_OF_MONTH.checkValidIntValue(dayOfMonth);
        this.monthOfYear = ChronoField.MONTH_OF_YEAR.checkValidIntValue(monthOfYear);
    }
}
