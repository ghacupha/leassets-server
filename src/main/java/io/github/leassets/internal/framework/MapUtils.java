package io.github.leassets.internal.framework;

import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static io.github.leassets.internal.framework.AppConstants.DATETIME_FORMATTER;

/**
 * General utilities for mappings and conversion
 */
public class MapUtils {

    public static LocalDate dateStringToLocalDate(String dateString) {
        return LocalDate.parse(dateString, DATETIME_FORMATTER);
    }

    public static String localDateToDateString(LocalDate localDateValue) {
        return DATETIME_FORMATTER.format(localDateValue);
    }

    public static Double bigDecimalToDouble(BigDecimal bigDecimalValue) {
        return NumberUtils.toDouble(bigDecimalValue);
    }

    public static BigDecimal doubleToBigDecimal(Double doubleValue) {
        return NumberUtils.toScaledBigDecimal(doubleValue, 2, RoundingMode.HALF_EVEN);
    }
}
