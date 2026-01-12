package com.epam.jsdmx.serializer.util;

import org.apache.commons.lang3.StringUtils;

public class DateTimeConverterUtil {
    public static String convertToDateTime(String date) {
        if (StringUtils.isBlank(date) || date.contains("T")) {
            return date;
        }

        return date + "T00:00:00Z";
    }
}
