package com.api.tutorials.utility;

import com.api.tutorials.exceptions.BadRequestException;

public class ValueUtils {

    public static boolean isEmpty(String value) {
        return null == value || value.isEmpty();
    }

    public static Long toLongValue(String strValue) {
        if (null == strValue) {
            throw new BadRequestException("Null value passed");
        }

        Long longValue;
        try {
            longValue = Long.valueOf(strValue);
        } catch (NumberFormatException exception) {
            throw new BadRequestException(3106, "Id is not valid positive integer");
        }

        return longValue;
    }
}
