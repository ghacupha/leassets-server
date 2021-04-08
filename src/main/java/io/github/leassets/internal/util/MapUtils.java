package io.github.leassets.internal.util;

import static io.github.leassets.internal.AppConstants.DATETIME_FORMATTER;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
/*-
 * Leassets Server - Leases and assets management platform
 * Copyright © 2021 Edwin Njeru (mailnjeru@gmail.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import org.apache.commons.lang3.math.NumberUtils;

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
